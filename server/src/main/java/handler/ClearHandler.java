package handler;

import dataAccess.DataAccessException;
import spark.Request;
import spark.Response;
import service.*;

import java.util.List;

public class ClearHandler {

    public static Object clearApplication(Request request, Response response) throws DataAccessException {

        service.Clear.clear();
        response.status(200); // MAYBE CHANGE THE STATUS CODE? I'M NOT SURE WHAT GOES HERE
        return "";
    }
}
