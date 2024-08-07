package utility;
import managers.CommandManager;
import java.io.File;
import java.util.Stack;
import java.util.NoSuchElementException;

/**
 * Класс Runner
 * @author Rubtsov Arseniy
*/
public class Runner {

    private Console console;
    private final CommandManager commandManager;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }
    /**
     * Интерактивный режим
    */
    public void interactiveMode() {
        try {
            ExecutionCode commandStatus;
            String[] userCommand = {"", ""};
            while (true) {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandStatus = commandManager.executeCommand(userCommand);

                if (commandStatus == ExecutionCode.EXIT_CODE)
                    System.exit(0);
            }
        } catch (NoSuchElementException exception) {
            console.println("CTRL D was pressed, don't do this again pls...");
        }
    }
}
