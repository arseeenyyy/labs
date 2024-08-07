package arseeenyyy.com.github.server.app;
import arseeenyyy.com.github.server.db.DataBaseManager;
import arseeenyyy.com.github.server.managers.CommandExecutor;
import arseeenyyy.com.github.server.managers.CommandManager;
import arseeenyyy.com.github.server.managers.DragonManager;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        int port = 1; //enter your port or use parseInt(args)
        DragonManager dragonManager = new DragonManager();
        DataBaseManager dbManager = new DataBaseManager();
        CommandManager commandManager = new CommandManager(dragonManager, dbManager);
        CommandExecutor executor = new CommandExecutor(commandManager);
        Server server = new Server(port, dbManager, dragonManager, executor);
        server.init();
        server.start();
    }
}