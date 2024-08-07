package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class Update extends BaseCommand {
    private final InDataBaseManager dbManager;

    public Update(InDataBaseManager dbManager) {
        super("update <id>", "updates dragon by its id");
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            dbManager.updateDragon(request);
            return new Response.ResponseBuilder()
                    .setExecutionCode(ExecutionCode.OK)
                    .build();
        }catch (Exception exception) {
            return new Response.ResponseBuilder()
                    .setExecutionCode(ExecutionCode.ERROR)
                    .setMessageToResponse(exception.getMessage())
                    .build();
        }
    }
}