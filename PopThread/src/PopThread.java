import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PopThread implements Runnable {
    private final String[] filenames;
    private static volatile int currentPage = 1;


    public PopThread(ArrayList<String> fileNames) {
        int size = fileNames.size();
        this.filenames = fileNames.toArray(new String[size]);
    }

    @Override
    public void run() {
        Map<String, String> fileContents = new HashMap<>();
        int numberOfFiles = 0;
        for (String filename : filenames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                StringBuilder content = new StringBuilder();
                String line;
                int pageNum = 0;
                while ((line = reader.readLine()) != null) {
                    //
                    content.append(line).append("\n");

                    if (line.matches("#\\d{3}/\\d{3}")) {
                        pageNum = Integer.parseInt(line.substring(1, 4));
                        numberOfFiles = Integer.parseInt(line.substring(5, 8));
                    }
                }

                fileContents.put(Integer.toString(pageNum), content.toString());
            } catch (IOException e) {
            }
        }

        while (currentPage <= numberOfFiles) {
//  if both threads find the same file what happens?
            String pageNumStr = String.valueOf(currentPage);
            if (fileContents.containsKey(pageNumStr)) {
                try {
                    File f = new File("result.txt");
                    if (!f.exists()) {
                        f.createNewFile();
                    }
                    if (currentPage == 1) {
                        Files.write(Paths.get("result.txt"), "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
                        Files.write(Paths.get("result.txt"), fileContents.get(pageNumStr).getBytes(), StandardOpenOption.APPEND);

                    } else {
                        Files.write(Paths.get("result.txt"), fileContents.get(pageNumStr).getBytes(), StandardOpenOption.APPEND);

                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                currentPage += 1;
            }
        }


    }
    public static void main(String[] args) {
        // Example usage
        ArrayList<String> files1 = new ArrayList<>();
        files1.add("2003-08-27.txt");
        files1.add("1831-06-01.txt");

        ArrayList<String> files2 = new ArrayList<>();
        files2.add("1961-04-12.txt");
        files2.add("1972-12-11.txt");

        // Create and start threads for each list of files
        Thread thread1 = new Thread(new PopThread(files1));
        Thread thread2 = new Thread(new PopThread(files2));
        thread1.start();
        thread2.start();
    }
}
