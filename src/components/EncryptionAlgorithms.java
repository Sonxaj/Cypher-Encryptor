package components;

import java.util.ArrayList;

public class EncryptionAlgorithms {

    // Class contains all algorithms for cypher encryption

    // Vigenere Cipher
    public static String vigenereCipherAlg(String plaintext, String key){

        StringBuilder result = new StringBuilder();

        char[] plaintextTemp = plaintext.toCharArray();
        char[] keyTemp = key.toCharArray();

        int keyPos = 0;

        for (char c:plaintextTemp) {
            int cipherChar = (BasicLogic.charToInt(keyTemp[keyPos]) + BasicLogic.charToInt(c)) % 26;

            result.append(BasicLogic.intToChar(cipherChar));

            // reset position when necessary
            if(keyPos == (key.length() - 1))
                keyPos = 0;
            else
                keyPos++;
        }
        return result.toString();
    }

    // Four-Square
    public static String fourSquareAlg(String plaintext, String key1, String key2){

        // if keyword 2 is not given, generate a random one of equal length
        if (key2.isEmpty()){
            key2 = BasicLogic.randomString(key1.length());
        }

        // start with Q2 and Q4 squares, since they're basically given

        char[][] q2 = new char[5][5];
        char[][] q4 = new char[5][5];

        int i2 = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if((i+i2) == 16) i2++; // skip letter Q

                q2[i][j] = BasicLogic.intToChar(i+i2);
                q4[i][j] = BasicLogic.intToChar(i+i2);
                i2++;
            }
            i2--;
        }


        // generate Q1 (keyword 1) and Q3 (keyword 2)

        ArrayList<Character> validCharactersKey1 = new ArrayList<>();
        ArrayList<Character> validCharactersKey2 = new ArrayList<>();

        for (char c: key1.toCharArray()) {
            if(validCharactersKey1.contains(c)){
                continue;
            }
            validCharactersKey1.add(c);
        }

        for (int i = 0; i < 26; i++) {
            if(validCharactersKey1.contains(BasicLogic.intToChar(i)) || i == 16){ // check for letter Q
                continue;
            }
            validCharactersKey1.add(BasicLogic.intToChar(i));
        }

        for (char c: key2.toCharArray()) {
            if(validCharactersKey2.contains(c)){
                continue;
            }
            validCharactersKey2.add(c);
        }

        for (int i = 0; i < 26; i++) {
            if(validCharactersKey2.contains(BasicLogic.intToChar(i)) || i == 16){
                continue;
            }
            validCharactersKey2.add(BasicLogic.intToChar(i));
        }


        char[][] q1 = new char[5][5];
        char[][] q3 = new char[5][5];

        i2 = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                q1[i][j] = validCharactersKey1.get(i+i2);
                q3[i][j] = validCharactersKey2.get(i+i2);
                i2++;
            }
            i2--;
        }


        // apply grid shenanigans

        // after working this out, the 'coordinates' of the result encrypted text has
        // the following pattern:
        //
        //      q1 --------> i position of q1 = i position of q2; j position of q1 = j position of q4
        //
        //      q3 --------> i position of q3 = i position of q4; j position of q3 = j position of q2

        StringBuilder builder = new StringBuilder();

        String cleanPlaintext = plaintext.replaceAll("\\s", "");

        for(int position = 0; position < cleanPlaintext.length()-1; position += 2){

            // searching q2
            for (int iOuter = 0; iOuter < 5; iOuter++) {
                for (int jOuter = 0; jOuter < 5; jOuter++) {

                    // hit on q2
                    if(q2[iOuter][jOuter] == cleanPlaintext.toCharArray()[position]){

                        // searching q4
                        for (int iInner = 0; iInner < 5; iInner++) {
                            for (int jInner = 0; jInner < 5; jInner++) {

                                // hit on q4
                                if(q4[iInner][jInner] == cleanPlaintext.toCharArray()[position+1]){

                                    builder.append(q1[iOuter][jInner]);
                                    builder.append(q3[iInner][jOuter]);
                                }
                            }
                        }
                    }
                }
            }
        }
        return builder.toString();
    }




    // Hill cipher
    public static String hillCipherAlg(String plaintext, String key){

        StringBuilder stringBuilder = new StringBuilder();

        int length = plaintext.length();

        int[][] keyMatrix = new int[length][length];
        createKeyMatrix(key, plaintext, keyMatrix);

        int[][] messageVector = new int[length][1];

        for (int i = 0; i < length; i++) {
            messageVector[i][0] = (plaintext.charAt(i)) % 97;
        }

        int[][] cipherMatrix = new int[length][1];

        // encrypt
        encryptHillMessage(cipherMatrix, keyMatrix, messageVector, plaintext);

        for (int i = 0; i < length; i++) {
            stringBuilder.append(BasicLogic.intToChar(cipherMatrix[i][0]));
        }

        return stringBuilder.toString();
    }

    static void createKeyMatrix(String key, String input, int[][] keyMatrix){

        int keyMatrixSize = input.length();

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
