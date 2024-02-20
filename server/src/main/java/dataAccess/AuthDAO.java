package dataAccess;

public interface AuthDAO {

    void getAuth() throws DataAccessException;

    void createAuth() throws DataAccessException;

    void deleteAuth() throws DataAccessException;

    void clear() throws  DataAccessException;

}
