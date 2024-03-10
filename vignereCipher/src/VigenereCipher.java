import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class VigenereCipher implements Cipher {
    public static void main(String[] args) {
        //       VigenereCipher myCipher = new VigenereCipher();
        //myCipher.encrypt("message.txt", "key.txt");
        //       myCipher.encrypt("encrypt_check.txt", "key_check.txt");
        //       myCipher.decrypt("decrypt_check.txt", "key_check.txt");
        //     File myFile = new File()
    }

    @Override
    public String encrypt(String message_filename, String key_filename){
        char[][] vigenereSquare = setUpVigenereSquare();
        String toEncrypt = readFile(message_filename).toUpperCase();
        String key = readFile(key_filename);
        StringBuilder encryptedMessage = new StringBuilder();

        int keyIndex = 0; // Index for tracking the current key character

        for (int i = 0; i < toEncrypt.length(); i++) {
            char c = toEncrypt.charAt(i);
            if (Character.isLetter(c)) {
                // Calculate the shift for encryption
                int keyChar = key.charAt(keyIndex) - 'A';
                int messageChar = c - 'A';
                // Ensure messageChar and keyChar are within the valid range
                messageChar = (messageChar + 26) % 26; // Handle negative values
                keyChar = (keyChar + 26) % 26; // Handle negative values
                // Encrypt the current character using Vigenère square
                char encryptedChar = vigenereSquare[messageChar][keyChar];
                encryptedMessage.append(encryptedChar);

                // Move to the next key character
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                // Non-alphabetic characters remain unchanged
                keyIndex = (keyIndex + 1) % key.length();
                encryptedMessage.append(c);
            }
        }
        return encryptedMessage.toString();
    }

    public String readFile(String filename){
        String lineTotal = "";
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            do  {
                line = br.readLine();
                if(line != null){
                    lineTotal = lineTotal + line + "\n";

                }
                else{
                    break;
                }
            } while (line != null);
            return lineTotal.trim();
        } catch (Exception e) {

        }
        return lineTotal.trim();
    }
    public char[][] setUpVigenereSquare(){
        char[][] vigenereSquare = new char[26][26];
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                vigenereSquare[i][j] = (char) ('A' + (i + j) % 26);
            }
        }
        return vigenereSquare;
    }
    //count number of non chars in the message, delete this from the total for modding
    @Override
    public String decrypt(String message_filename, String key_filename){
        char[][] vigenereSquare = setUpVigenereSquare();
        String toDecrypt = readFile(message_filename).toUpperCase();
        String key = readFile(key_filename);
        String outputString = "";
        int keyIndex = 0;
        for (int i = 0; i < toDecrypt.length(); i++) {
            char c = toDecrypt.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                keyIndex = i % key.length();
                int keyChar = key.charAt(keyIndex) - 'A';
                int messageChar = c - 'A'; //labelled from 0-25
                //so find the index of the messageChar in the row of the keyChar
                //add this to 'A'
                for (int j = 0; j < 26; j++) {
                    if(vigenereSquare[keyChar][j] == c){
                        outputString = outputString + (char) ('A' + j);
                        break;
                    }
                }
            }
            else{
                keyIndex ++;
                outputString = outputString + c;

            }

        }

        // for redact seperate into seperate characters, then if a character = punctuation or a space just concatenate into a new list of words
        return outputString;
    }


}


