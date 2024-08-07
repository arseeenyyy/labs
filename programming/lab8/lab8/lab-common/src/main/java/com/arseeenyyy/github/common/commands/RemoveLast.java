package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.interfaces.InDragonManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class RemoveLast extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public RemoveLast(InDragonManager dragonManager, InDataBaseManager dbManager) {
        super("remove_last", "removes last dragon from database");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Integer userId = request.getUser().getId();
            if (userId == null) throw new DatabaseInsertionException();
            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            if (dragonManager.size() == 0) return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.OK).build();
            dragonManager.update();
            int dragonId = dragonManager.get(dragonManager.size() - 1).getId();
            dbManager.removeDragonById(dragonId, userId);
            return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.OK).build();
        }catch (DatabaseInsertionException exception) {}
        return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
    }

}
