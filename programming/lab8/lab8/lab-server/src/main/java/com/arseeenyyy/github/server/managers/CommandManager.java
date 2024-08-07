package com.arseeenyyy.github.server.managers;

import com.arseeenyyy.github.common.commands.*;
import com.arseeenyyy.github.common.interfaces.InCommandManager;
import com.arseeenyyy.github.server.db.DataBaseManager;

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
        commandList.put("clear", new Clear(dbManager, dragonManager));
        commandList.put("update", new Update(dbManager));
        commandList.put("add", new Add(dbManager));
        commandList.put("help", new Help(this));
        commandList.put("add_if_max", new AddIfMax(dbManager, dragonManager));
        commandList.put("remove_any_by_wingspan", new RemoveAnyByWingspan(dbManager));
        commandList.put("remove_by_id", new RemoveById(dbManager));
        commandList.put("remove_last", new RemoveLast(dragonManager, dbManager));
        commandList.put("show", new Show(dbManager, dragonManager));
        commandList.put("history", new History());
    }
    public Map<String, BaseCommand> getCommandList() {
        return commandList;
    }
}