package dataAccess.interfaces;

import dataAccess.Exceptions.DataAccessException;
import dataAccess.Exceptions.UnauthorizedRequest;
import model.UserData;

public interface UserDAO {

/* Oftentimes, the parameters and return values of your DAO methods will be the model objects described
in the previous section (UserData, GameData, and AuthData). For example, your DAO classes will certainly
need to provide a method for creating new UserData objects in the data store. This method might have a
signature that looks like this:
void insertUser(UserData u) throws DataAccessException{};
 */
//    void insertUser(UserData u) throws DataAccessException{};

    UserData getUser(String username) throws DataAccessException, UnauthorizedRequest;

    void createUser(UserData user) throws DataAccessException;

    void clear() throws  DataAccessException;

}
