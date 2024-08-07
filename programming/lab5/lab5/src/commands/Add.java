package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import models.Dragon;
import models.validateInput.NameValidator;
import utility.Console;
/**
 * Команда 'add'. Добавляет новый элемент в коллекцию
 * @author Arseniy Rubtsov
*/
public class Add extends BaseCommand {
    private final Console console;
    public Add(Console console) {
        super("add", "adds new element to collection");
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
            console.println("creating new dragon...");
            Dragon dragon = NameValidator.dragonNameValidator(console);
            DragonManager.getInstance().addElement(dragon);
            console.println("new dragon was added to collection");
            return true;
        } catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no argument!");
        }
        return false;
    }
}