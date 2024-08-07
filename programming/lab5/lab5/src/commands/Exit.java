package commands;

import exceptions.WrongArgumentException;
import utility.Console;
/**
 * Команда 'exit'. Заканчивает работу программы
 * @author Arseniy Rubtsov
*/
public class Exit extends BaseCommand {
    private final Console console;
    public Exit(Console console) {
        super("exit", "stops programm(without saving collection)");
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
            console.println("closing programm..");
            System.exit(0);
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no arguments!");
        }
        return false;
    }
}
