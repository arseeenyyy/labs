package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

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
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[] {request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            Integer userId = dbManager.getUserId(request);
            if (userId == null) throw new DatabaseInsertionException();
            StringBuilder sb = new StringBuilder();
            String lastInitTime = dragonManager.getLastInitTime() == null ? "no inits in this session\n" : "lastInitTime: " + dragonManager.getLastInitTime() + "\n";
            sb.append(lastInitTime);
            dragonManager.setCollection(dbManager.getAllElementsFromDataBase());
            sb.append("total collection size: " + dragonManager.size() + "\n");
            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            sb.append("user's collection size: " + dragonManager.size() + "\n");
            sb.append("collection type: " + dragonManager.getCollection().getClass());
            return new Response(sb.toString(), ExecutionCode.OK);
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("user not found", ExecutionCode.ERROR);
        }
    }
}
