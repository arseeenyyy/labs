package commands;

import exceptions.WrongArgumentException;
import utility.Console;
import java.util.LinkedList;
/**
 * Команда 'history'. Выводит список последних 13 команд.
 * @author Arseniy Rubtsov
*/
public class History extends BaseCommand {
    private final Console console;
    private static final int HISTORY_SIZE = 13;
    private static LinkedList<String> commandQueue = new LinkedList<>();

    public History(Console console) {
        super("history", "shows list of last 13 commands");
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
            console.println(commandQueue);
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no arguments!");
        }
        return false;
    }
    /**
     * Добавляет новую команду в историю.
     * @param command Название команды
    */
    public static void addCommandToHistory(String command) {
        if (commandQueue.size() < HISTORY_SIZE)    {
            commandQueue.addFirst(command);
        }
        if (commandQueue.size() == HISTORY_SIZE) {
            commandQueue.removeLast();
            commandQueue.addFirst(command);
        }
    }
}
