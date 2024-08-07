package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.exceptions.WrongDataBaseIdException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.models.Coordinates;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.models.DragonCave;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class Add extends BaseCommand {
    private final InDataBaseManager dbManager;
    private final InDragonManager dragonManager;

    public Add(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("add", "add new dragon to the collection");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }
    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[] {request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            Dragon dragon = request.getDragon();
            if (dragon == null) return new Response("no dragon was sent", ExecutionCode.ERROR);
            else {
                if (dragon.validate()) {
                    try {
                        DragonCave cave = dragon.getCave();
                        Coordinates coordinates = dragon.getCoordinates();
                        Integer userId = dbManager.getUserId(request);
                        Integer caveId = dbManager.insertNewDragonCave(cave);
                        Integer coordId = dbManager.insertNewCoordinates(coordinates);
                        if (caveId == null || coordId == null || userId == null) throw new DatabaseInsertionException();
                        Integer isInserted = dbManager.addDragonToDataBase(dragon, caveId, coordId, userId);
                        if (isInserted != null) {
                            dragon.setId(isInserted);
                            dragonManager.addElement(dragon);
                            return new Response("new dragon was added", ExecutionCode.OK);
                        }
                        else {
                            return new Response("new dragon wasn't added to collection", ExecutionCode.ERROR);
                        }

                    }catch (DatabaseInsertionException exception) {
                        return new Response("unable to insert values into database", ExecutionCode.ERROR);
                    }
                }
            }
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has argument!!", ExecutionCode.ERROR);
        }
        return null;
    }
}
