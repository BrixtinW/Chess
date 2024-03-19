package ui.REPLS;

import ui.REPLS.PostloginUI;
import ui.ServerFacade;
import static ui.EscapeSequences.*;

public class PreloginUI extends REPL {

         @Override
        protected Boolean evaluate(String[] parsedInput) {

            switch (parsedInput[0]) {
                case "help":
                    System.out.println( SET_TEXT_COLOR_RED + "\thelp" + SET_TEXT_COLOR_LIGHT_GREY + " - List all valid commands" + SET_TEXT_COLOR_RED + "\n\tquit" + SET_TEXT_COLOR_LIGHT_GREY + " - Exit the program" + SET_TEXT_COLOR_RED + "\n\tlogin <USERNAME> <PASSWORD>" + SET_TEXT_COLOR_LIGHT_GREY + " - Login to an existing account" + SET_TEXT_COLOR_RED + "\n\tregister <USERNAME> <PASSWORD> <EMAIL>" + SET_TEXT_COLOR_LIGHT_GREY + " - Register to create an account");
                    break;
                case "quit":
                    return true;
                case "login":
                    if(parsedInput.length == 3){
                        String[] responseArray = ServerFacade.login(parsedInput);
                            if (responseArray.length == 2){
                                PostloginUI repl = new PostloginUI();
                                repl.authToken = responseArray[1];
                                System.out.println("Logged In Successfully!");
                                repl.start();
                                break;
                            }
                    } else {
                        System.out.print("Invalid Format\nType 'login' and then your username and password after it.");
                        break;
                    }
                case "register":
                    if(parsedInput.length == 4) {
                        String[] responseArray = ServerFacade.register(parsedInput);
                        if (responseArray.length == 2) {
                            PostloginUI repl = new PostloginUI();
                            repl.authToken = responseArray[1];
                            System.out.println("Registered Successfully!");
                            repl.start();
                            break;
                        }
                    } else {
                        System.out.print("Invalid Format\nType 'register' and then your username, password  and email after it");
                        break;
                    }
                default:
                    System.out.println("Invalid Command\nType help to see all valid commands");
            }
            return false;
        }


}
