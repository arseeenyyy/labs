package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import utility.Console;
/**
 * Команда 'remove_any_by_wingspan'. Удаляет элемент из коллекции по значению поля wingspan
 * @author Arseniy Rubtsov
*/
public class RemoveAnyByWingspan extends BaseCommand {
    private final Console console;
    public RemoveAnyByWingspan(Console console) {
        super("remove_any_by_wingspan <wingspan>", "removes one element from collection by input wingspan");
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
                console.println("collection is empty\nthere's nothing to remove");
                return true;
            }
            double wingspan = Double.parseDouble(args[1]);
            for (int i = 0; i < DragonManager.getInstance().size(); i ++) {
                if (Double.compare(wingspan, DragonManager.getInstance().get(i).getWingspan()) == 0) {
                    console.println("dragon with wingspan=" + wingspan + " was found and removed");
                    DragonManager.getInstance().remove(i);
                    return true;
                }
            }
            console.println("dragon with wingspan=" + wingspan + " wasn't found");
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has argument!");
        }catch (NumberFormatException exception) {
            console.printError("argument should be <double> number!");
        }
        return false;
    }
}
