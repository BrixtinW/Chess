package dataAccess;

public interface AuthDAO {

    model.authData getAuth(String authToken) throws DataAccessException;

    void createAuth(model.authData authObj) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    void clear() throws  DataAccessException;

}
