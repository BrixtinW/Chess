package ui.REPLS;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.RESET_TEXT_COLOR;

public abstract class REPL {

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            String whiteBoard = SET_BG_COLOR_BLACK + "White Board:\n\t" +
                    SET_BG_COLOR_DARK_GREY + "    H  G  F  E  D  C  B  A    " + RESET_BG_COLOR + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 1 " + SET_BG_COLOR_LIGHT_GREY + BLACK_ROOK + SET_BG_COLOR_BLACK + BLACK_KNIGHT + SET_BG_COLOR_LIGHT_GREY + BLACK_BISHOP + SET_BG_COLOR_BLACK + BLACK_KING + SET_BG_COLOR_LIGHT_GREY + BLACK_QUEEN + SET_BG_COLOR_BLACK + BLACK_BISHOP + SET_BG_COLOR_LIGHT_GREY + BLACK_KNIGHT + SET_BG_COLOR_BLACK + BLACK_ROOK + SET_BG_COLOR_DARK_GREY + " 1 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 2 " + SET_BG_COLOR_BLACK + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_BLACK + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_BLACK + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_BLACK + BLACK_PAWN + SET_BG_COLOR_LIGHT_GREY + BLACK_PAWN + SET_BG_COLOR_DARK_GREY + " 2 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 5 " + SET_BG_COLOR_LIGHT_GREY + " H " + SET_BG_COLOR_BLACK + " G " + SET_BG_COLOR_LIGHT_GREY + " F " + SET_BG_COLOR_BLACK + " E " + SET_BG_COLOR_LIGHT_GREY + " D " + SET_BG_COLOR_BLACK + " C " + SET_BG_COLOR_LIGHT_GREY + " B " + SET_BG_COLOR_BLACK + " A " + SET_BG_COLOR_DARK_GREY + " 5 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 2 " + SET_BG_COLOR_BLACK + " H " + SET_BG_COLOR_LIGHT_GREY + " G " + SET_BG_COLOR_BLACK + " F " + SET_BG_COLOR_LIGHT_GREY + " E " + SET_BG_COLOR_BLACK + " D " + SET_BG_COLOR_LIGHT_GREY + " C " + SET_BG_COLOR_BLACK + " B " + SET_BG_COLOR_LIGHT_GREY + " A " + SET_BG_COLOR_DARK_GREY + " 2 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 3 " + SET_BG_COLOR_LIGHT_GREY + " H " + SET_BG_COLOR_BLACK + " G " + SET_BG_COLOR_LIGHT_GREY + " F " + SET_BG_COLOR_BLACK + " E " + SET_BG_COLOR_LIGHT_GREY + " D " + SET_BG_COLOR_BLACK + " C " + SET_BG_COLOR_LIGHT_GREY + " B " + SET_BG_COLOR_BLACK + " A " + SET_BG_COLOR_DARK_GREY + " 3 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 2 " + SET_BG_COLOR_BLACK + " H " + SET_BG_COLOR_LIGHT_GREY + " G " + SET_BG_COLOR_BLACK + " F " + SET_BG_COLOR_LIGHT_GREY + " E " + SET_BG_COLOR_BLACK + " D " + SET_BG_COLOR_LIGHT_GREY + " C " + SET_BG_COLOR_BLACK + " B " + SET_BG_COLOR_LIGHT_GREY + " A " + SET_BG_COLOR_DARK_GREY + " 2 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 4 " + SET_BG_COLOR_LIGHT_GREY + " H " + SET_BG_COLOR_BLACK + " G " + SET_BG_COLOR_LIGHT_GREY + " F " + SET_BG_COLOR_BLACK + " E " + SET_BG_COLOR_LIGHT_GREY + " D " + SET_BG_COLOR_BLACK + " C " + SET_BG_COLOR_LIGHT_GREY + " B " + SET_BG_COLOR_BLACK + " A " + SET_BG_COLOR_DARK_GREY + " 4 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + " 2 " + SET_BG_COLOR_BLACK + " H " + SET_BG_COLOR_LIGHT_GREY + " G " + SET_BG_COLOR_BLACK + " F " + SET_BG_COLOR_LIGHT_GREY + " E " + SET_BG_COLOR_BLACK + " D " + SET_BG_COLOR_LIGHT_GREY + " C " + SET_BG_COLOR_BLACK + " B " + SET_BG_COLOR_LIGHT_GREY + " A " + SET_BG_COLOR_DARK_GREY + " 2 " + RESET_BG_COLOR  + "\n\t" +
                    SET_BG_COLOR_DARK_GREY + "    H  G  F  E  D  C  B  A    " + RESET_BG_COLOR  + "\n\t";


            System.out.println(whiteBoard);




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
