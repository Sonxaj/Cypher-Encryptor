package components;

public class MainRunner {
    public static void main(String[] args){

        // apply cipher algorithm
        String cipherText = EncryptionAlgorithms.fourSquareAlg("help me obiwan kenobi", "example", "keyword");

        System.out.println("Ciphertext: " + cipherText);
    }



    public static void printResult(String plainText, String key, String cipherText){
        System.out.print("\n\n");

        System.out.print("Key:\n\n");

        for (int i = 0, size = key.length(); i < size; i += 80){
            System.out.println(key.substring(i, Math.min(i + 80, size)));
        }

        System.out.print("\n\n");

        System.out.print("Plaintext:\n\n");

        for (int i = 0, size = plainText.length(); i < size; i += 80){
            System.out.println(plainText.substring(i, Math.min(i + 80, size)));
        }

        System.out.print("\n\n");

        System.out.print("Ciphertext:\n\n");

        for (int i = 0, size = cipherText.length(); i < size; i += 80){
            System.out.println(cipherText.substring(i, Math.min(i + 80, size)));
        }
    }
}