package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.exceptions.WrongArgumentException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.interfaces.InDragonManager;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

import java.util.ArrayList;

public class Show extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public Show(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("show", "show elements of collection");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }
    @Override
    public Response execute(Request request) {
        dragonManager.setCollection(dbManager.getAllElementsFromDataBase());
        dragonManager.getCollection();
        return new Response.ResponseBuilder()
                .setDragonList(dragonManager.getCollection())
                .setExecutionCode(ExecutionCode.OK)
                .build();
    }
}