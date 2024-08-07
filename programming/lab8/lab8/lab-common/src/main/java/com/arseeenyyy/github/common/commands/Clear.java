package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.interfaces.InDragonManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;


public class Clear extends BaseCommand {
    private final InDataBaseManager dbManager;
    private final InDragonManager dragonManager;

    public Clear(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("clear", "clear collection");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Integer userId = request.getUser().getId();
            if (userId == null) throw new DatabaseInsertionException();
            dbManager.deleteAllByUserId(userId);
            dragonManager.setCollection(dbManager.getAllElementsFromDataBase());
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
