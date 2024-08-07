package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import utility.Console;
/**
 * Команда 'clear'. Очищает коллекцию
 * @author Arseniy Rubtsov
*/
public class Clear extends BaseCommand {
    private final Console console;
    public Clear(Console console) {
        super("clear", "clears collection");
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
            if (DragonManager.getInstance().size() == 0) {
                console.println("collection is empty\nthere's nothing to clear");
                return true;
            }
            DragonManager.getInstance().clear();
            console.println("collection was cleared");
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no argument!");
        }
        return false;
    }
}
