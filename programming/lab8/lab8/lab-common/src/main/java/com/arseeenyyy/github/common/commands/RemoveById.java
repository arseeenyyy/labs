package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class RemoveById extends BaseCommand {
    private final InDataBaseManager dbManager;

    public RemoveById(InDataBaseManager dbManager) {
        super("remove_by_id <id>", "removes dragon by its id");
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().split(" ", 2);
        try {
            int dragonId = Integer.parseInt(userCommand[1]);
            Integer userId = request.getUser().getId();
            if (userId == null) throw new DatabaseInsertionException();
            dbManager.removeDragonById(dragonId, userId);
            return new Response.ResponseBuilder()
                    .setExecutionCode(ExecutionCode.OK)
                    .build();
        }catch (DatabaseInsertionException exception) {
            return new Response.ResponseBuilder()
                    .setExecutionCode(ExecutionCode.ERROR)
                    .build();
        }
    }
}
