package ui.REPLS;

import ui.EscapeSequences.*;

import static ui.EscapeSequences.*;

public class GameplayUI extends REPL{

    public GameplayUI(){
//        String whiteBoard = "White Board:/n" +
//                SET_TEXT_COLOR_DARK_GREY + "/t    H  G  F  E  D  C  B  A    /n" + RESET_TEXT_COLOR +
//                SET_TEXT_COLOR_DARK_GREY + "/t    H  G  F  E  D  C  B  A    /n" + RESET_TEXT_COLOR;
//
//
//        System.out.println(whiteBoard);
    }

    @Override
    protected Boolean evaluate(String[] parsedInput) {

        switch (parsedInput[0]) {
            case "help":
                break;
            case "quit":
                return true;
//            case "create":
//                break;
//            case "list":
//                break;
//            case "join":
//                break;
            default:
                System.out.println("Ya trash cuz.\ntype help for God's sake");
        }
        return false;
    }

}
