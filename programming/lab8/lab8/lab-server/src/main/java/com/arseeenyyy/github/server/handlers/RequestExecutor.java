package com.arseeenyyy.github.server.handlers;

import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.server.db.DataBaseManager;
import com.arseeenyyy.github.server.managers.CommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
                        ? new Response.ResponseBuilder().setMessageToResponse(dbManager.getUserId(request).toString()).setExecutionCode(ExecutionCode.SUCCESS_REG).build()
                        : new Response.ResponseBuilder().setMessageToResponse("NOOOOOOO").setExecutionCode(ExecutionCode.UNSUCCESS_REG).build();
                break;
            case LOGIN:
                response = dbManager.getUser(request)
                        ? new Response.ResponseBuilder().setMessageToResponse(dbManager.getUserId(request).toString()).setExecutionCode(ExecutionCode.SUCCESS_REG).build()
                        : new Response.ResponseBuilder().setMessageToResponse("no user found").setExecutionCode(ExecutionCode.UNSUCCESS_REG).build();
                break;
            case COMMAND:
                response = executor.execute(request);
                break;
            default:
                response = new Response.ResponseBuilder().setMessageToResponse("unknown request type").setExecutionCode(ExecutionCode.ERROR).build();
                break;
        }
        return response;
    }
}
