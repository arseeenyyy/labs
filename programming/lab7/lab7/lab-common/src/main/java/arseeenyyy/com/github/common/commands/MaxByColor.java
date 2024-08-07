package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.DatabaseInsertionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.models.Color;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

import java.util.Set;
import java.util.stream.Collectors;

public class MaxByColor extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public MaxByColor(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("max_by_color", "show any element from collection with max <color> value");
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
            if (dragonManager.size() == 0) return new Response("collection is empty", ExecutionCode.OK);
            Set<Color> colors = dragonManager.getCollection().stream()
                    .map(Dragon::getColor)
                    .collect(Collectors.toSet());
            Color currentMaxColor = Color.getMaxValue(colors);
            return dragonManager.getCollection().stream()
                    .filter(dragon -> dragon.getColor().equals(currentMaxColor))
                    .findFirst()
                    .map(dragon -> new Response(dragon, ExecutionCode.OK))
                    .orElse(new Response("no dragon found with current max color value", ExecutionCode.ERROR));
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has argument!", ExecutionCode.ERROR);
        }catch (DatabaseInsertionException exception) {
            return new Response("no user found", ExecutionCode.ERROR);
        }
    }
}
