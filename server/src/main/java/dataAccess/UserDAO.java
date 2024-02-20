package dataAccess;

public interface UserDAO {

/* Oftentimes, the parameters and return values of your DAO methods will be the model objects described
in the previous section (UserData, GameData, and AuthData). For example, your DAO classes will certainly
need to provide a method for creating new UserData objects in the data store. This method might have a
signature that looks like this:
void insertUser(UserData u) throws DataAccessException{};
 */
//    void insertUser(UserData u) throws DataAccessException{};

    void getUser() throws DataAccessException;

    void createUser() throws DataAccessException;

    void clear() throws  DataAccessException;

}
