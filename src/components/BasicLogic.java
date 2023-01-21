package components;

import java.util.Random;

public class BasicLogic {

    // conversion stuff
    public static int charToInt(char input){
        return (input - 97);
    }

    public static char intToChar(int input){
        return (char)(input + 97);
    }

    public static boolean isCharacter(char input){
        // check if current character is only alphabetical
        // i.e., the unicode value is between 97 (a) and 122 (z) OR 65 (A) and 90 (Z)
        return (input >= 65 && input <= 90) || (input >= 97 && input <= 122);
    }

    public static String padString(String s){
        // leave if already max length
        if(s.length() == 512) return s;

        StringBuilder sBuilder = new StringBuilder(s);

        // pad with x's until 512 limit
        sBuilder.append("x".repeat(Math.max(0, 512 - sBuilder.length())));

        return sBuilder.toString();
    }

    public static String randomString(int length){

        Random random = new Random();
        int upperBound = 26; // letter z
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(intToChar(random.nextInt(upperBound)));
        }

        return stringBuilder.toString();
    }
}