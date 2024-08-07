package commands;

import exceptions.WrongArgumentException;
import managers.CommandManager;
import utility.Console;
/**
 * Команда 'help'. Выводит таблицу с названием и описанием команды
 * @author Arseniy Rubtsov
*/
public class Help extends BaseCommand {
    private final CommandManager commandManager;
    private final Console console;
    public Help(Console console, CommandManager commandManager) {
        super("help", "show's list of available commands");
        this.console = console;
        this.commandManager = commandManager;
    }
    /**
     * Выполняет команду
     * @return boolean Успешность выполнения команды
     */
    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new WrongArgumentException();
            commandManager.getCommandList().values().forEach(command -> {console.printTable(command.getName(), command.getDescription());});
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no arguments!");
        }
        return false;
    }
}
