package com.arseeenyyy.github.server.app;

import com.arseeenyyy.github.server.db.DataBaseManager;
import com.arseeenyyy.github.server.managers.CommandExecutor;
import com.arseeenyyy.github.server.managers.CommandManager;
import com.arseeenyyy.github.server.managers.DragonManager;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        DragonManager dragonManager = new DragonManager();
        DataBaseManager dbManager = new DataBaseManager();
        CommandManager commandManager = new CommandManager(dragonManager, dbManager);
        CommandExecutor executor = new CommandExecutor(commandManager);
        Server server = new Server(4444, dbManager, dragonManager, executor);
        server.init();
        server.start();
    }
}
