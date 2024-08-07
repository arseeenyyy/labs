package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class RemoveAnyByWingspan extends BaseCommand {
    private final InDataBaseManager dbManager;

    public RemoveAnyByWingspan(InDataBaseManager dbManager) {
        super("remove_any_by_wingspan", "removes 1 dragon by its wingspan");
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().split(" ", 2);
        try {
            double wingspan = Double.parseDouble(userCommand[1]);
            Integer userId = request.getUser().getId();
            if (userId == null) throw new DatabaseInsertionException();
            dbManager.removeDragonByWingspan(wingspan, userId);
            return new Response.ResponseBuilder()
                    .setExecutionCode(ExecutionCode.OK)
                    .build();
        }catch (DatabaseInsertionException | NumberFormatException exception) {
        }
        return new Response.ResponseBuilder()
                .setExecutionCode(ExecutionCode.ERROR)
                .build();
    }
}