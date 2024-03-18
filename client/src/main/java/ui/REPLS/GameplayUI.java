package ui.REPLS;

import ui.ServerFacade;

import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;
import static ui.EscapeSequences.SET_TEXT_COLOR_RED;

public class GameplayUI extends REPL{

    @Override
    protected Boolean evaluate(String[] parsedInput) {

        switch (parsedInput[0]) {
            case "help":
                break;
            case "logout":
                return true;
            case "create":
                break;
            case "list":
                break;
            case "join":
                break;
            default:
                System.out.println("Ya trash cuz.\ntype help for God's sake");
        }
        return false;
    }

}
