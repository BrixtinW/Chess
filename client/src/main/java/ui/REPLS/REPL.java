package ui.REPLS;

import java.util.Scanner;

import static ui.EscapeSequences.ERASE_SCREEN;
import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;

public abstract class REPL {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {


            System.out.print(SET_TEXT_COLOR_LIGHT_GREY + ">> ");
            String userInput = scanner.nextLine();
            System.out.println(ERASE_SCREEN);

            String[] parsedInput = userInput.split("\\s+");

            if(parsedInput.length == 0){
                System.out.println("Your attempt sucked honestly...");
                continue;
            }

            Boolean terminate = evaluate(parsedInput);

            if (terminate) { return; }

        }
    }

    protected abstract Boolean evaluate(String[] parsedInput);

}
