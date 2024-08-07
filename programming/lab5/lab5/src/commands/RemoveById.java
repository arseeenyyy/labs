package commands;

import exceptions.ObjectNotFoundException;
import exceptions.WrongArgumentException;
import managers.DragonManager;
import utility.Console;
/**
 * Команда 'remove_by_id'. Удаляет элемент по значению поля id
 * @author Arseniy Rubtsov
*/
public class RemoveById extends BaseCommand {
    private final Console console;
    public RemoveById(Console console) {
        super("remove_by_id <id>", "removes element from collection by its id");
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
            int id = Integer.parseInt(args[1]);
            if (DragonManager.getInstance().findById(id) == -1) throw new ObjectNotFoundException();
            DragonManager.getInstance().removeById(id);
            console.println("dragon with id=" + id + " was removed");
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' should has argument!");
        }catch (NumberFormatException exception) {
            console.printError("id should be integer number and grader than 0!");
        }catch (ObjectNotFoundException exception) {
            console.printError("no such dragon with id=" + args[1] + " in collection");
        }
        return false;
    }
}
