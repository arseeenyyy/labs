package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

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
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            StringBuilder sb = new StringBuilder();
            Integer userId = dbManager.getUserId(request);
            if (userId == null) throw new DatabaseInsertionException();
            dragonManager.setCollection(dbManager.getAlienCollection(userId));
            if (dragonManager.size() == 0) sb.append("alien collection is empty\n");
            else {
                sb.append("alien collection\n");
                dragonManager.getCollection().forEach(dragon -> sb.append(dragon).append("\n"));
            }
            dragonManager.setCollection(dbManager.getCollectionFromDataBase(userId));
            if (dragonManager.size() == 0) sb.append("user collection is empty\n");
            else {
                sb.append("user collection\n");
                dragonManager.getCollection().forEach(dragon -> sb.append(dragon).append("\n"));
            }
            return new Response(sb.toString(), ExecutionCode.OK);
        } catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("user wasn't found", ExecutionCode.ERROR);
        }
    }
}