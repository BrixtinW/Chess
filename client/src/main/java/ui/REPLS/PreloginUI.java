package ui.REPLS;

import ui.REPLS.PostloginUI;
import ui.ServerFacade;
import static ui.EscapeSequences.*;

public class PreloginUI extends REPL {

         @Override
        protected Boolean evaluate(String[] parsedInput) {

            switch (parsedInput[0]) {
                case "help":
                    System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - well it seems to be working for you so far. Read and learn." + SET_TEXT_COLOR_RED + "\n\tquit" + SET_TEXT_COLOR_LIGHT_GREY + " - Exit the program." + SET_TEXT_COLOR_RED + "\n\tlogin <USERNAME> <PASSWORD>" + SET_TEXT_COLOR_LIGHT_GREY + " - abandon hope all ye who enter here..." + SET_TEXT_COLOR_RED + "\n\tregister <USERNAME> <PASSWORD> <EMAIL>" + SET_TEXT_COLOR_LIGHT_GREY + " - Joooiiin ussssssss :]");
                    break;
                case "quit":
                    return true;
                case "login":
                    if(parsedInput.length == 3){
                        String[] responseArray = ServerFacade.login(parsedInput);
                            if (responseArray.length == 2){
                                PostloginUI repl = new PostloginUI();
                                repl.authToken = responseArray[1];
                                System.out.print(repl.authToken);
                                repl.start();
                            }
                    } else {
                        System.out.print("type 'login' and then your username and password after it. Your format was wrong >:[");
                    }
                case "register":
                    if(parsedInput.length == 4) {
                        String[] responseArray = ServerFacade.register(parsedInput);
                        if (responseArray.length == 2) {
                            PostloginUI repl = new PostloginUI();
                            repl.authToken = responseArray[1];
                            System.out.print(repl.authToken);
                            repl.start();
                        }
                    } else {
                        System.out.print("type 'register' and then your username, password  and email after it. Your format was wrong >:[");
                    }
                default:
                    System.out.println("Ya trash cuz.\ntype help for God's sake");
            }
            return false;
        }


}
