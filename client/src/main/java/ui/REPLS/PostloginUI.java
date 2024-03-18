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
                System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - Well it seems to be working for you so far. Read and learn." + SET_TEXT_COLOR_RED + "\n\tlogout" + SET_TEXT_COLOR_LIGHT_GREY + " - Logout user." + SET_TEXT_COLOR_RED + "\n\tcreate <Name>" + SET_TEXT_COLOR_LIGHT_GREY + " - Create a game and give it a name" + SET_TEXT_COLOR_RED + "\n\tlist" + SET_TEXT_COLOR_LIGHT_GREY + " - List all available games" +  SET_TEXT_COLOR_RED +"\n\tjoin <ID> <WHITE|BLACK|[empty]>" + SET_TEXT_COLOR_LIGHT_GREY + " - Join an existing game.");
                break;
            case "logout":
                ServerFacade.logout(authToken);
                return true;
            case "create":
                String gameID = ServerFacade.createGame(parsedInput, authToken);
                System.out.println("Created Game #" + gameID);
                break;
            case "list":
                ServerFacade.listGames(authToken);
                break;
            case "join":
                ServerFacade.joinGame(parsedInput, authToken);
                GameplayUI repl = new GameplayUI();
                repl.start();
                break;
            default:
                System.out.println("Ya trash cuz.\ntype help for God's sake");
        }
        return false;
    }







}
