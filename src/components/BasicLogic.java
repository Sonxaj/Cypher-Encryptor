package components;

public class BasicLogic {

    // conversion stuff
    public int charToInt(char input){
        return (input - 97);
    }

    public char intToChar(int input){
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
}
