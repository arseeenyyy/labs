package arseeenyyy.com.github.server.managers;

import arseeenyyy.com.github.common.commands.*;
import arseeenyyy.com.github.common.interfaces.InCommandManager;
import arseeenyyy.com.github.server.db.DataBaseManager;

import java.util.HashMap;
import java.util.Map;

public class CommandManager implements InCommandManager {
    private final Map<String, BaseCommand> commandList = new HashMap<>();
    private final DragonManager dragonManager;
    private final DataBaseManager dbManager;

    public CommandManager(DragonManager dragonManager, DataBaseManager dbManager) {
        this.dragonManager = dragonManager;
        this.dbManager = dbManager;
        commandList.put("info", new Info(dbManager, dragonManager));
        commandList.put("add", new Add(dbManager, dragonManager));
        commandList.put("clear", new Clear(dragonManager, dbManager));
        commandList.put("help", new Help(this));
        commandList.put("add_if_max", new AddIfMax(dbManager, dragonManager));
        commandList.put("max_by_color", new MaxByColor(dbManager, dragonManager));
        commandList.put("remove_any_by_wingspan", new RemoveAnyByWingspan(dbManager, dragonManager));
        commandList.put("remove_by_id", new RemoveById(dbManager, dragonManager));
        commandList.put("remove_last", new RemoveLast(dbManager, dragonManager));
        commandList.put("show", new Show(dbManager, dragonManager));
        commandList.put("print_descending", new PrintDescending(dbManager, dragonManager));
        commandList.put("history", new History());
    }
    public Map<String, BaseCommand> getCommandList() {
        return commandList;
    }
}
