package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import utility.Console;
/**
 * Команда 'show'. Выводит все элементы коллекции
 * @author Arseniy Rubtsov
*/
public class Show extends BaseCommand {
    private final Console console;
    public Show(Console console) {
        super("show", "show's all collection elements");
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
                console.println("collection is empty\nthere's nothing to show");
                return true;
            }
            else {
                DragonManager.getInstance().getCollection().forEach(dragon -> {
                    console.println(dragon);
                });
                return true;
            }
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no arguments!");
        }
        return false;
    }
}
