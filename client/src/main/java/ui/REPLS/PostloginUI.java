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
                System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - List all valid commands" + SET_TEXT_COLOR_RED + "\n\tlogout" + SET_TEXT_COLOR_LIGHT_GREY + " - Logout user." + SET_TEXT_COLOR_RED + "\n\tcreate <Name>" + SET_TEXT_COLOR_LIGHT_GREY + " - Create a game and give it a name" + SET_TEXT_COLOR_RED + "\n\tlist" + SET_TEXT_COLOR_LIGHT_GREY + " - List all available games" +  SET_TEXT_COLOR_RED +"\n\tjoin <ID> <WHITE|BLACK|[empty]>" + SET_TEXT_COLOR_LIGHT_GREY + " - Join an existing game");
                break;
            case "logout":
                ServerFacade.logout(authToken);
                return true;
            case "create":
                if (parsedInput.length != 2){
                    System.out.println("Invalid Format\nType 'create' followed by the desired game name");
                    break;
                }
                String gameID = ServerFacade.createGame(parsedInput, authToken);
                System.out.println("Created Game #" + gameID);
                break;
            case "list":
                ServerFacade.listGames(authToken);
                break;
            case "join":
                if (parsedInput.length < 2 || parsedInput.length > 3){
                    System.out.println("Invalid Format\n Type 'join' followed by which game you want to join, and the color you want to join as. If you just want to observe, omit the color.");
                    break;
                }
                String message = ServerFacade.joinGame(parsedInput, authToken);
                if (message == null){
                    System.out.println("Unable to join game\n Make sure the player color is available by typing 'list'");
                    break;
                }
                GameplayUI repl;
                if (parsedInput.length == 3){
                    repl = new GameplayUI(authToken, parsedInput[1], parsedInput[2]);
                } else {
                    repl = new GameplayUI(authToken, parsedInput[1], null);
                }
                repl.start();
                System.out.println("Quit Game Successfully!");
                break;
            default:
                System.out.println("Invalid Command\nType help to see all valid commands");
        }
        return false;
    }







}
