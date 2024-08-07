package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import utility.Console;

import java.time.LocalDateTime;
/**
 * Команда 'info'. Выводит информацию о коллекции
 * @author Arseniy Rubtsov
*/
public class Info extends BaseCommand {
    private final Console console;
    public Info(Console console) {
        super("info", "shows information about collection");
        this.console = console;
    }
    /**
     * Выполняет команду
     * @return boolean Успешность выполнения команды
     */
    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new WrongArgumentException();
            LocalDateTime lastInitTime = DragonManager.getInstance().getLastInitTime();
            if (lastInitTime == null)
                console.println("no initializations in this session");
            else
                console.println("last init time: " + lastInitTime);
            LocalDateTime lastSaveTime = DragonManager.getInstance().getLastSaveTime();
            if (lastSaveTime == null)
                console.println("no savings in this session");
            else
                console.println("last save time: " + lastSaveTime);
            console.println("collection size: " + DragonManager.getInstance().size());
            console.println("collection type: " + DragonManager.getInstance().getCollection().getClass());
            return true;
        }catch (WrongArgumentException exception) {
            console.println("command '" + getName() + "' has no argument!");
        }
        return false;
    }

}
