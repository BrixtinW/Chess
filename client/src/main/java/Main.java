import ui.REPLS.PreloginUI;
import ui.ServerFacade;

public class Main {
    public static void main(String[] args) {
/*        Server server = new Server();
          var port = server.run(0);
          ServerFacade.SERVER_URL = ServerFacade.SERVER_URL + port; */

        ServerFacade.SERVER_URL = ServerFacade.SERVER_URL + "8080";

        PreloginUI repl = new PreloginUI();
        repl.start();

/*         server.stop();  */
    }
}