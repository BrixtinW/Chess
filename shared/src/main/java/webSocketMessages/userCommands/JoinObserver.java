package webSocketMessages.userCommands;

public class JoinObserver extends UserGameCommand{
    public JoinObserver(String authToken, Integer gameID) {
        super(authToken, gameID);
    }
}