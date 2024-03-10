
public class SortedLinkedList implements SortedList{
    //Node properties: prev, next, string
    //where is ascending or descending specified??
    private Node first;
    private Node last;
    private boolean ascending = true;
    public static void main(String[] args) {}

    public int size() {
        Node currentNode = first;
        int mysize = 1;
        while(currentNode.getNext()!= null){
            currentNode = currentNode.getNext();
            mysize++;
        }
        return mysize;
    }

    public void add(String string) {
        Node newNode = new Node(string);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else {
            Node currentNode = first;
            while (currentNode != null) {
                int comparison = currentNode.getString().compareToIgnoreCase(string);
                if ((comparison > 0 && ascending) || (comparison < 0 && !ascending)) {
                    // Insert newNode before currentNode
                    newNode.setNext(currentNode);
                    newNode.setPrev(currentNode.getPrev());
                    if (currentNode.getPrev() != null) {
                        currentNode.getPrev().setNext(newNode);
                    } else {
                        // newNode is the new first node
                        first = newNode;
                    }
                    currentNode.setPrev(newNode);
                    break;
                } else if (currentNode.getNext() == null) {
                    // Insert newNode at the end of the list
                    newNode.setPrev(currentNode);
                    currentNode.setNext(newNode);
                    last = newNode;
                    break;
                }
                currentNode = currentNode.getNext();
            }
        }
    }


    public void add(Node nodeToadd) {
        if (first == null) {
            first = nodeToadd;
            last = nodeToadd;
        } else {
            Node currentNode = first;
            while (currentNode != null) {
                int comparison = currentNode.getString().compareToIgnoreCase(nodeToadd.getString());
                if ((comparison > 0 && ascending) || (comparison < 0 && !ascending)) {
                    // Insert nodeToadd before currentNode
                    nodeToadd.setNext(currentNode);
                    nodeToadd.setPrev(currentNode.getPrev());
                    if (currentNode.getPrev() != null) {
                        currentNode.getPrev().setNext(nodeToadd);
                    } else {
                        // nodeToadd is the new first node
                        first = nodeToadd;
                    }
                    currentNode.setPrev(nodeToadd);
                    break;
                } else if (currentNode.getNext() == null) {
                    // Insert nodeToadd at the end of the list
                    nodeToadd.setPrev(currentNode);
                    currentNode.setNext(nodeToadd);
                    last = nodeToadd;
                    break;
                }
                currentNode = currentNode.getNext();
            }
        }
    }
    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;

    }

    public Node get(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        Node currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }


    public boolean isPresent(String string) {
        Node currentNode = first;
        while(currentNode != null){
            if(currentNode.getString().equals(string)){
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }


    public boolean removeFirst(){
        if (first != null) {
            if (first == last) {
                // The list only contains one node
                first = null;
                last = null;
            } else {
                first = first.getNext();
                first.setPrev(null);
            }
            return true;
        }
        return false;
    }

    public boolean removeLast(){
        if (last != null) {
            if (first == last) {
                // The list only contains one node
                first = null;
                last = null;
            } else {
                last = last.getPrev();
                last.setNext(null);
            }
            return true;
        }
        return false;
    }


    public boolean remove(int index){
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        }
        Node currentNode = first;
        for (int i = 0; i < index; i++) {
            currentNode = currentNode.getNext();
        }
        if (currentNode.getPrev() != null) {
            currentNode.getPrev().setNext(currentNode.getNext());
        } else {
            first = currentNode.getNext();
        }
        if (currentNode.getNext() != null) {
            currentNode.getNext().setPrev(currentNode.getPrev());
        } else {
            last = currentNode.getPrev();
        }
        return true;
    }

    public boolean remove(String string){
        Node currentNode = first;
        while (currentNode != null) {
            if (currentNode.getString().equals(string)) {
                if (currentNode.getPrev() != null) {
                    currentNode.getPrev().setNext(currentNode.getNext());
                } else {
                    first = currentNode.getNext();
                }
                if (currentNode.getNext() != null) {
                    currentNode.getNext().setPrev(currentNode.getPrev());
                } else {
                    last = currentNode.getPrev();
                }
                return true;
            }
            currentNode = currentNode.getNext();
        }
        return false;
    }
    @Override
    public void orderAscending() {
        Node currentNode = first;
        SortedLinkedList sortedList = new SortedLinkedList();
        while (currentNode != null) {
            sortedList.add(currentNode.getString());
            currentNode = currentNode.getNext();
        }
        first = sortedList.getFirst();
        last = sortedList.getLast();
        ascending = true;
    }

    public void orderDescending() {
        Node currentNode = first;
        SortedLinkedList sortedList = new SortedLinkedList();
        sortedList.ascending = false;
        while (currentNode != null) {
            sortedList.add(currentNode.getString());
            currentNode = currentNode.getNext();
        }
        first = sortedList.getFirst();
        last = sortedList.getLast();
        ascending = false;
    }


    public void print() {
        Node currentNode = first;
        while (currentNode != null) {
            System.out.println(currentNode.getString());
            currentNode = currentNode.getNext();
        }
    }
}
