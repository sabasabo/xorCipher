import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class XorCipher {

    public static final int CHARACTERS_AMOUNT_IN_ENGLISH = 26;
    public static final String ERROR = "ERROR";
    public static final String ENGLISH_LETTER = "a-zA-Z";
    public static final String DIGITS = "0-9";
    // todo: public static final String punctuation_marks = ;
    public static final String EXPRESSION = "["+DIGITS+ENGLISH_LETTER+"]";
    public static final String IS = "[a-zA-Z]";

    public static String xorDecrypt(char[] encryptedBytes, String key) {
        int currentKeyIndex = 0;
        StringBuilder finalResult = new StringBuilder();

        for (int i = 0; i < encryptedBytes.length; i++) {
            String currentChar = String.valueOf((char) (encryptedBytes[i] ^ key.charAt(currentKeyIndex % key.length())));
            if (Pattern.compile(EXPRESSION).matcher(currentChar).matches()) {
                finalResult.append(currentChar);
                currentKeyIndex++;
            } else {
                return ERROR;
            }
        }
//        System.out.println(finalResult);
        return finalResult.toString();
    }

    public static char[] xorEncrypt(String text, String key) {
        int currentKeyIndex = 0;
        char[] finalResult = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {

            finalResult[i] = (char) (text.charAt(i) ^ key.charAt(currentKeyIndex % key.length()));
            currentKeyIndex++;
        }
        return finalResult;
    }

    public static List<String> guessKey(char[] encryptedText, int keySize) {
        char[] key = new char[keySize];
        for (int i = 0; i < keySize; i++) {
            key[i] = 'a';
        }
        int amountOfKeys = (int) Math.pow(CHARACTERS_AMOUNT_IN_ENGLISH, keySize);
        List<String> results = new ArrayList<>();
        for (int i = 0; i < amountOfKeys; i++) {
            String result = xorDecrypt(encryptedText, new String(key));
            if (!result.equals(ERROR)) {
                System.out.println("en " + encryptedText + "  key: " + new String(key) + "  result: " + result);
                results.add(result);
            }
            increase(key);

        }
        return results;
    }

    private static void increase(char[] key) {
        for (int i = 0; i < key.length; i++) {
            key[i] = (char) ((key[i] + 1 - 'a') % CHARACTERS_AMOUNT_IN_ENGLISH + 'a');
            if (key[i] != 'a') {
                return;
            }
        }
    }

}
