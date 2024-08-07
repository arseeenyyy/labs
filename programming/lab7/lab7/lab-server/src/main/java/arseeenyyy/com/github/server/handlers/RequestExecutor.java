package arseeenyyy.com.github.server.handlers;

import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;
import arseeenyyy.com.github.server.db.DataBaseManager;
import arseeenyyy.com.github.server.managers.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Function;

public class RequestExecutor implements Function<Request, Response> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestExecutor.class);
    private final CommandExecutor executor;
    private final DataBaseManager dbManager;

    public RequestExecutor(CommandExecutor executor, DataBaseManager dbManager) {
        this.executor = executor;
        this.dbManager = dbManager;
    }


    @Override
    public Response apply(Request request) {
        Response response;

        switch (request.getType()) {
            case REGISTRATION:
                response = dbManager.insertNewUser(request)
                        ? new Response("new user was registered", ExecutionCode.SUCCESS_REG)
                        : new Response("such login/password already exists", ExecutionCode.UNSUCCESS_REG);
                break;
            case LOGIN:
                response = dbManager.getUser(request)
                        ? new Response("login successful", ExecutionCode.SUCCESS_REG)
                        : new Response("no user found", ExecutionCode.UNSUCCESS_REG);
                break;
            case COMMAND:
                response = executor.execute(request);
                break;
            default:
                response = new Response("unknown request type", ExecutionCode.ERROR);
                break;
        }
        LOGGER.info("response for server: " + response);
        return response;
    }
}
