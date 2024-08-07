package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import utility.Console;
/**
 * Команда 'remove_last'. Удаляет последний элемент коллекции.
*/
public class RemoveLast extends BaseCommand {
    private final Console console;
    public RemoveLast(Console console) {
        super("remove_last", "removes last element of collection");
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
                console.println("collection is empty, there's nothing to remove");
                return true;
            }
            DragonManager.getInstance().remove(DragonManager.getInstance().size() - 1);
            console.println("last dragon was removed");
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no arguments!");
        }
        return false;
    }
}
