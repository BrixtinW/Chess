package ui.REPLS;

import ui.ServerFacade;

import java.util.Scanner;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_LIGHT_GREY;

public class PostloginUI extends REPL {

    public static String authToken = null;

    @Override
    protected Boolean evaluate(String[] parsedInput) {

        switch (parsedInput[0]) {
            case "help":
                System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - well it seems to be working for you so far. Read and learn." + SET_TEXT_COLOR_RED + "\n\tquit" + SET_TEXT_COLOR_LIGHT_GREY + " - Exit the program." + SET_TEXT_COLOR_RED + "\n\tlogin <USERNAME> <PASSWORD>" + SET_TEXT_COLOR_LIGHT_GREY + " - abandon hope all ye who enter here..." + SET_TEXT_COLOR_RED + "\n\tregister <USERNAME> <PASSWORD> <EMAIL>" + SET_TEXT_COLOR_LIGHT_GREY + " - Joooiiin ussssssss :]");
                break;
            case "logout":
                ServerFacade.logout(authToken);
                return true;
            case "create":
//                    NOT FINISHED
                break;
            case "list":
                ServerFacade.listGames(authToken);
                break;
            case "join":
//                    NOT FINISHED
                break;
            case "observe":
//                    NOT FINISHED
                break;
            default:
                System.out.println("Ya trash cuz.\ntype help for God's sake");
        }
        return false;
    }







}
