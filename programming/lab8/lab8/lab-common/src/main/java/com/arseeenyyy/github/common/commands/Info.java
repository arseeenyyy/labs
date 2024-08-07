package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.exceptions.WrongArgumentException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.interfaces.InDragonManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class Info extends BaseCommand {
    private final InDataBaseManager dbManager;
    private final InDragonManager dragonManager;

    public Info(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("info", "show information about collection");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }
    @Override
    public Response execute(Request request) {
        try {
            Integer userId = request.getUser().getId();
            if (userId == null) throw new DatabaseInsertionException();
            StringBuilder sb = new StringBuilder();
            String lastInitTime = dragonManager.getLastInitTime() == null ? "no inits in this session\n" : "lastInitTime: " + dragonManager.getLastInitTime() + "\n";
            sb.append(lastInitTime);
            dragonManager.setCollection(dbManager.getAllElementsFromDataBase());
            sb.append("total collection size: " + dragonManager.size() + "\n");
            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            sb.append("user's collection size: " + dragonManager.size() + "\n");
            sb.append("collection type: " + dragonManager.getCollection().getClass());
            return new Response.ResponseBuilder()
                    .setMessageToResponse(sb.toString())
                    .setExecutionCode(ExecutionCode.OK)
                    .build();
        }catch (DatabaseInsertionException exception) {
        }
        return new Response.ResponseBuilder()
                .setMessageToResponse("error")
                .setExecutionCode(ExecutionCode.ERROR)
                .build();
    }
}
