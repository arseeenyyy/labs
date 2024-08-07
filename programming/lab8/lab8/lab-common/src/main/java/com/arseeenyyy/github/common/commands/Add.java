package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.interfaces.InDataBaseManager;
import com.arseeenyyy.github.common.models.Coordinates;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.models.DragonCave;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class Add extends BaseCommand {
    private final InDataBaseManager dbManager;

    public Add(InDataBaseManager dbManager) {
        super("add", "adds new dragon");
        this.dbManager = dbManager;
    }
    @Override
    public Response execute(Request request) {
        try {
            Dragon dragon = request.getDragon();
            if (dragon == null) return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
            else {
                if (dragon.validate()) {
                    DragonCave cave = dragon.getCave();
                    Coordinates coordinates = dragon.getCoordinates();
                    Integer userId = request.getUser().getId();
                    Integer coordinates_id = dbManager.insertNewCoordinates(coordinates);
                    Integer cave_id = dbManager.insertNewDragonCave(cave);
                    if (userId == null || coordinates_id == null || cave_id == null) return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
                    Integer isInserted = dbManager.addDragonToDataBase(dragon, cave_id, coordinates_id, userId);
                    if (isInserted != null)
                        return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.OK).build();
                    else return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
                } else return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
            }
        }catch (Exception exception) {}
        return new Response.ResponseBuilder().setExecutionCode(ExecutionCode.ERROR).build();
    }
}
