

public class redact {
    public static String redact(String content, String[] redactWords) {
        String[] originalListWords = content.split("\\b"); // Split using word boundaries

        // Converts redact words to lowercase
        String[] redactWordsLowerCase = new String[redactWords.length];
        for (int i = 0; i < redactWords.length; i++) {
            redactWordsLowerCase[i] = redactWords[i].toLowerCase();
        }

        // Redacts words
        for (int i = 0; i < originalListWords.length; i++) {
            String word = originalListWords[i];
            if (word.matches("\\w+")) { // Check if it's a word (contains only letters and digits)
                if (containsIgnoreCase(redactWordsLowerCase, word.toLowerCase())) {
                    originalListWords[i] = numberAsterisks(word.length());
                }
            }
        }

        // Concatenates redacted content
        StringBuilder strBuilder = new StringBuilder();
        for (String word : originalListWords) {
            strBuilder.append(word);
        }
        return strBuilder.toString();
    }

    public static String numberAsterisks(int length) {
        StringBuilder asterisks = new StringBuilder();
        for (int i = 0; i < length; i++) {
            asterisks.append("*");
        }
        return asterisks.toString();
    }

    public static boolean containsIgnoreCase(String[] array, String key) {
        for (String str : array) {
            if (str.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

}