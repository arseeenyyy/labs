package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class RemoveLast extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public RemoveLast(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("remove_last", "remove_last element from database");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[] {request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            Integer userId = dbManager.getUserId(request);
            if (userId == null) throw new DatabaseInsertionException();

            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            if (dragonManager.size() == 0) return new Response("collection already empty", ExecutionCode.OK);
            dragonManager.update();
            int dragonId = dragonManager.get(dragonManager.size() - 1).getId();
            boolean wasRemoved = dbManager.removeDragonById(dragonId, userId);
            if (wasRemoved) {
                dragonManager.remove(dragonManager.size() - 1);
                return new Response("dragon was removed", ExecutionCode.OK);
            } else return new Response("dragon wasn't removed", ExecutionCode.ERROR);

        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("user wasn't found", ExecutionCode.ERROR);
        }
    }
}
