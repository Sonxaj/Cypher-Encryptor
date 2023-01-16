package components;

public class EncryptionAlgorithms {

//    Class contains all algorithms for cypher encryption
    static BasicLogic logic = new BasicLogic();

    // Vigenere Cipher
    public static String vigenereCipherAlg(String plaintext, String key){

        StringBuilder result = new StringBuilder();


        // convert text and key to arrays
        char[] plaintextTemp = plaintext.toCharArray();
        char[] keyTemp = key.toCharArray();

        // used to track position inside keyword for circular repetition
        int keyPos = 0;

        for (char c:plaintextTemp) {
            // generate ciphertext in that position
            int cipherChar = (logic.charToInt(keyTemp[keyPos]) + logic.charToInt(c)) % 26;

            // append to result
            result.append(logic.intToChar(cipherChar));

            // check if we've reached the end of the key
            // if so, start from the first letter
            if(keyPos == (key.length() - 1))
                keyPos = 0;
            else
                keyPos++;
        }

        return result.toString();
    }


    // Hill cipher
    public static String hillCipherAlg(String plaintext, String key){

        StringBuilder stringBuilder = new StringBuilder();

        // message length for convenience
        int length = plaintext.length();

        // create matrix and vector for the key and message respectively
        int[][] keyMatrix = new int[length][length];
        createKeyMatrix(key, plaintext, keyMatrix);

        int[][] messageVector = new int[length][1];

        // generate message vector
        for (int i = 0; i < length; i++) {
            messageVector[i][0] = (plaintext.charAt(i)) % 97;
        }

        // cypher matrix
        int[][] cipherMatrix = new int[length][1];

        // encrypt
        encryptHillMessage(cipherMatrix, keyMatrix, messageVector, plaintext);

        // build encrypted text from vector
        for (int i = 0; i < length; i++) {
            stringBuilder.append(logic.intToChar(cipherMatrix[i][0]));
        }

        // return encrypted text
        return stringBuilder.toString();
    }

    static void createKeyMatrix(String key, String input, int keyMatrix[][]){

        // length of string; required for key matrix
        int keyMatrixSize = input.length();

        // loop through matrix; add letter from key
        int k = 0;
        for (int i = 0; i < keyMatrixSize; i++) {
            for (int j = 0; j < keyMatrixSize; j++) {
                keyMatrix[i][j] = (key.charAt(k)) % 65;
                k++;
            }
        }
    }


    static void encryptHillMessage(int[][] cipherMatrix, int[][] keyMatrix, int[][] messageVector, String message){

        for (int i = 0; i < message.length(); i++) {

            for (int j = 0; j < 1; j++) {

                cipherMatrix[i][j] = 0;

                for (int k = 0; k < message.length(); k++) {

                    cipherMatrix[i][j] += keyMatrix[i][k] * messageVector[k][j];
                }

                cipherMatrix[i][j] %= 26;
            }
        }
    }
}
