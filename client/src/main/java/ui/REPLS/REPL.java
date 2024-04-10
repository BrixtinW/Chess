package ui.REPLS;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_COLOR;

public abstract class REPL {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {




            System.out.print(SET_TEXT_COLOR_LIGHT_GREY + "\n>> ");
            String userInput = scanner.nextLine();
            System.out.println(ERASE_SCREEN);

            String[] parsedInput = userInput.split("\\s+");

            Boolean terminate = evaluate(parsedInput);

            if (terminate) { return; }

        }
    }

    protected abstract Boolean evaluate(String[] parsedInput);

}
