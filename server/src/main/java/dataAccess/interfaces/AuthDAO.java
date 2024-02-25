package dataAccess.interfaces;

import dataAccess.Exceptions.DataAccessException;
import model.AuthData;

public interface AuthDAO {

    AuthData getAuth(String authToken) throws DataAccessException;

    void createAuth(AuthData authObj) throws DataAccessException;

    void deleteAuth(String authToken) throws DataAccessException;

    void clear() throws  DataAccessException;

}
