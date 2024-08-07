package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.exceptions.DatabaseInsertionException;
import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.interfaces.InDragonManager;
import com.arseeenyyy.github.common.models.Coordinates;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.models.DragonCave;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class AddIfMax extends BaseCommand {
    private final InDataBaseManager dbManager;
    private final InDragonManager dragonManager;

    public AddIfMax(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("add_if_max", "adds new dragon if it has max value");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            Dragon currentDragon = request.getDragon();
            Integer userId = request.getUser().getId();
            if (currentDragon == null) return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
            else {
                if (currentDragon.validate()) {
                    try {
                        dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
                        boolean isMax = dragonManager.getCollection().stream()
                                .allMatch(dragon -> currentDragon.compareTo(dragon) > 0);
                        if (isMax) {
                            DragonCave cave = currentDragon.getCave();
                            Coordinates coordinates = currentDragon.getCoordinates();
                            Integer caveId = dbManager.insertNewDragonCave(cave);
                            Integer coordinatesId = dbManager.insertNewCoordinates(coordinates);
                            if (caveId == null || coordinatesId == null) throw new DatabaseInsertionException();
                            Integer isInserted = dbManager.addDragonToDataBase(currentDragon, caveId, coordinatesId, userId);
                            if (isInserted != null) {
                                return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.OK).build();
                            } else throw new DatabaseInsertionException();
                        } else throw new DatabaseInsertionException();
                    }catch (DatabaseInsertionException exception) {
                        return new Response.ResponseBuilder()
                                .setExecutionCode(ExecutionCode.ERROR).build();
                    }
                } else return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
            }
        } catch (Exception exception) {}
        return new Response.ResponseBuilder()
                .setExecutionCode(ExecutionCode.ERROR)
                .build();
    }
}