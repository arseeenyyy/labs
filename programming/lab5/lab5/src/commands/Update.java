package commands;

import exceptions.ObjectNotFoundException;
import exceptions.WrongArgumentException;
import managers.DragonManager;
import models.validateInput.NameValidator;
import utility.Console;
/**
 * Команда 'update'. Обновляет элемент коллекции по заданному id
 * @author Arseniy Rubtsov
*/
public class Update extends BaseCommand{
    private final Console console;
    public Update(Console console) {
        super("update <id> {element}", "updates element by its id");
        this.console = console;
    }
    /**
     * Выполняет команду
     * @return boolean Успешность выполнения команды
     */
    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongArgumentException();
            if (DragonManager.getInstance().size() == 0) {
                console.println("collection empty, there's nothing to remove");
                return true;
            }
            int id = Integer.parseInt(args[1]);
            int index = DragonManager.getInstance().findById(id);
            if (index == -1) throw new ObjectNotFoundException();
            DragonManager.getInstance().removeById(id);
            DragonManager.getInstance().addElement(NameValidator.dragonNameValidatorWithId(console, id));
            console.println("dragon with id=" + id + "was updated");
            return true;
        } catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' should has argument!");
        } catch (ObjectNotFoundException exception) {
            console.printError("no such dragon with id=" + args[1] + " in collection");
        } catch (NumberFormatException exception) {
            console.printError("argument should be int number and grader than 0!");
        }
        return false;
    }
}
