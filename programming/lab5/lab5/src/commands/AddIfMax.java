package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import models.Dragon;
import models.validateInput.NameValidator;
import utility.Console;
/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение 'age' является максимальным
 * @author Arseiy Rubtsov
*/
public class AddIfMax extends BaseCommand {
    private final Console console;
    public AddIfMax(Console console) {
        super("add_if_max {element}", "adds element to collection if its age value is max");
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
            console.println("create new dragon and check if its age value is max: ");
            Dragon dragon = NameValidator.dragonNameValidator(console);
            long dragon_age = dragon.getAge();
            long current_maximum = 0;
            for (Dragon d : DragonManager.getInstance().getCollection()) {
                current_maximum = Math.max(dragon_age, d.getAge());
            }
            if (dragon_age < current_maximum) {
                console.println("new dragon's age value isn't max\nnew dragon wasn't added to collection");
                return true;
            }
            else {
                console.println("new dragon's age value is max\nnew dragon was added to collection");
                return true;
            }
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no argument!");
        }
        return false;
    }
}
