package dataAccess;

public interface AuthDAO {

    model.authData getAuth(String authToken) throws DataAccessException;

    void createAuth(model.authData authObj) throws DataAccessException;

    void deleteAuth() throws DataAccessException;

    void clear() throws  DataAccessException;

}
