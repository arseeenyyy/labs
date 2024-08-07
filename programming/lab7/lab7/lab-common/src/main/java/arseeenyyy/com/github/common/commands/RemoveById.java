package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class RemoveById extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public RemoveById(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("remove_by_id <id>", "remove element from the collection by its id");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        try {
            if (userCommand[1].isEmpty()) throw new WrongArgumentException();
            int dragonId = Integer.parseInt(userCommand[1]);
            Integer userId = dbManager.getUserId(request);
            if (userId == null) throw new DatabaseInsertionException();
            boolean wasRemoved = dbManager.removeDragonById(dragonId, userId);
            if (wasRemoved) {
                dbManager.getCollectionFromDataBase(userId);
                return new Response("dragon with id=" + dragonId + " was removed", ExecutionCode.OK);
            } else return new Response("dragon with id=" + dragonId + " wasn't found", ExecutionCode.ERROR);
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' should has arguments!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("user wasn't found", ExecutionCode.ERROR);
        }catch (NumberFormatException exception) {
            return new Response("argument should be <int>", ExecutionCode.ERROR);
        }
    }
}
