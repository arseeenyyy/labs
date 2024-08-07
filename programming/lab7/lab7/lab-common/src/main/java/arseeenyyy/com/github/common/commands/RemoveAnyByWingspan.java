package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class RemoveAnyByWingspan extends BaseCommand {
    private final InDataBaseManager dbManager;
    private final InDragonManager dragonManager;

    public RemoveAnyByWingspan(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("remove_any_by_wingspan <wingspan>", "remove one element from the collection by its wingspan");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }
    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        try {
            if (userCommand[1].isEmpty()) throw new WrongArgumentException();
            double wingspan = Double.parseDouble(userCommand[1]);
            Integer userId = dbManager.getUserId(request);
            if (userId == null) throw new DatabaseInsertionException();
            boolean wasRemoved = dbManager.removeDragonByWingspan(wingspan);
            if (wasRemoved) {
                dbManager.getCollectionFromDataBase(userId);
                return new Response("dragon with wingspan=" + wingspan + " was removed", ExecutionCode.OK);
            }else {
                return new Response("dragon with wingspan=" + wingspan + " wasn't found", ExecutionCode.ERROR);
            }
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' should has arguments!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("user wasn't found", ExecutionCode.ERROR);
        }catch (NumberFormatException exception) {
            return new Response("argument should be <double>", ExecutionCode.ERROR);
        }
    }
}
