package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.models.Coordinates;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.models.DragonCave;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class AddIfMax extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public AddIfMax(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("add_if_max", "add new dragon to the collection if its value max");
        this.dragonManager = dragonManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            Dragon currentDragon = request.getDragon();
            Integer userId = dbManager.getUserId(request);
            if (userId == null) throw new DatabaseInsertionException();
            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            boolean isMax = dragonManager.getCollection().stream()
                    .allMatch(dragon -> currentDragon.compareTo(dragon) > 0);
            if (isMax) {
                DragonCave cave = currentDragon.getCave();
                Coordinates coordinates = currentDragon.getCoordinates();
                Integer caveId = dbManager.insertNewDragonCave(cave);
                Integer coordId = dbManager.insertNewCoordinates(coordinates);
                if (caveId == null || coordId == null) throw new DatabaseInsertionException();
                Integer isInserted = dbManager.addDragonToDataBase(currentDragon, caveId, coordId, userId);
                if (isInserted != null) {
                    currentDragon.setId(isInserted);
                    dragonManager.addElement(currentDragon);
                    return new Response("new dragon was added to the collection because its max value", ExecutionCode.OK);
                }
                else throw new DatabaseInsertionException();
            } else return new Response("new dragon's value not max", ExecutionCode.ERROR);
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("unable to insert new value to database", ExecutionCode.ERROR);

        }
    }

}
