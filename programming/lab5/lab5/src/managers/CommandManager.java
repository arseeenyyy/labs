package managers;
import commands.*;
import utility.Console;
import utility.StandartConsole;
import utility.ExecutionCode;

import java.util.HashMap;
import java.util.Map;
/**
 * Менеджер управления командами
 * @author Arseniy Rubtsov
*/
public class CommandManager {
    private final Console console = new StandartConsole();
    private final Map<String, BaseCommand> commandList = new HashMap<>();

    public CommandManager() {
        commandList.put("help", new Help(console, this));
        commandList.put("info", new Info(console));
        commandList.put("show", new Show(console));
        commandList.put("add", new Add(console));
        commandList.put("update", new Update(console));
        commandList.put("remove_by_id", new RemoveById(console));
        commandList.put("clear", new Clear(console));
        commandList.put("save", new Save(console));
        commandList.put("execute_script", new ExecuteScript(console, this));
        commandList.put("exit", new Exit(console));
        commandList.put("remove_last", new RemoveLast(console));
        commandList.put("add_if_max", new AddIfMax(console));
        commandList.put("remove_any_by_wingspan", new RemoveAnyByWingspan(console));
        commandList.put("max_by_color", new MaxByColor(console));
        commandList.put("print_descending", new PrintDescending(console));
        commandList.put("history", new History(console));
    }
    /**
     * Возвращает список команд
    */
    public Map<String, BaseCommand> getCommandList() {
        return commandList;
    }
    /**
     * Исполняет команду
     * @param userCommand
     * @return ExecutionCode
     * @see ExecutionCode
    */
    public ExecutionCode executeCommand(String[] userCommand) {
        if (userCommand[0].equals("")) return ExecutionCode.OK_CODE;
        var command = commandList.get(userCommand[0]);

        if (command == null) {
            console.printError("unknown command");
            return ExecutionCode.ERROR_CODE;
        }
        if (!command.execute(userCommand)) return ExecutionCode.ERROR_CODE;
        else {
            History.addCommandToHistory(userCommand[0]);
            return ExecutionCode.OK_CODE;
        }
    }
}
