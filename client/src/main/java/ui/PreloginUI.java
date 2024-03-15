package ui;

import java.util.Scanner;

public class PreloginUI {

        public void start() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Read input from the user
                System.out.print(">> ");
                String userInput = scanner.nextLine();

                // Evaluate the input
                String result = evaluate(userInput);

                // Print the result
                System.out.println(result);
            }
        }

        private String evaluate(String input) {
            // For simplicity, let's just echo back the input
            return input;
        }


}
