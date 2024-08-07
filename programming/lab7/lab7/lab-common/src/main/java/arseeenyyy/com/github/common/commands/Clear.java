package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;


public class Clear extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public Clear(InDragonManager dragonManager, InDataBaseManager dbManager) {
        super("clear", "clear collection");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[] {request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            Integer userId = dbManager.getUserId(request);
            if (userId == null) return new Response("cannot find user's id", ExecutionCode.ERROR);
            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            if (dragonManager.size() == 0) return new Response("collection is empty, nothing to clear", ExecutionCode.ERROR);
            dragonManager.clear();
            dbManager.deleteAllByUserId(userId);
            return new Response("collection was cleared", ExecutionCode.OK);
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }
    }

}
