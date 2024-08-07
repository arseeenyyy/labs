package commands;

import exceptions.*;
import managers.CommandManager;
import managers.DragonManager;
import models.Dragon;
import utility.Console;

import java.io.*;
import java.util.Stack;
import java.util.Arrays;
/**
 * Команда 'execute_script'. Выполняет скрипт из указанного файла
 * @author Arseniy Rubtsov
*/
public class ExecuteScript extends BaseCommand {
    private final Console console;
    private final CommandManager commandManager;
    private static Stack<File> stack = new Stack<File>();
    public ExecuteScript(Console console, CommandManager commandManager) {
        super("execute_script <file_name>", "read and execute script from file");
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
            if (args[1].isEmpty()) throw new WrongArgumentException();
            File file = new File(args[1]);
            if (!file.exists()) throw new FileNotFoundException();
            if (!file.canRead()) throw new NoEnoughRootsException();
            stack.push(file);
            String currentLine;
            String[] argsForAddCommand = new String[8];
            try (BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(file))) {
                while ((currentLine = getNextCommand(buffer)) != null) {
                    String[] userCommand = currentLine.contains(" ") ? currentLine.trim().split(" ", 2) : new String[]{currentLine, ""};
                    switch (userCommand[0]) {
                        case ("add"):
                            for (int i = 0; i < argsForAddCommand.length; i++) {
                                if ((currentLine = getNextCommand(buffer)) != null) {
                                    argsForAddCommand[i] = currentLine;
                                }
                            }
                            console.println("current command: 'add'\ncurrent arguments: " + Arrays.toString(argsForAddCommand));
                            if (Dragon.validateArguments(argsForAddCommand)) {
                                Dragon dragon = new Dragon(argsForAddCommand);
                                console.println("new dragon values are correct\nnew dragon was added");
                                DragonManager.getInstance().addElement(dragon);
                            } else console.println("new dragon values are incorrect\nnew dragon wasn't added");
                            break;
                        case ("add_if_max"):
                            for (int i = 0; i < argsForAddCommand.length; i++) {
                                if ((currentLine = getNextCommand(buffer)) != null) {
                                    argsForAddCommand[i] = currentLine;
                                }
                            }
                            console.println("current command: 'add_if_max'\ncurrent arguments: " + Arrays.toString(argsForAddCommand));
                            if (Dragon.validateArguments(argsForAddCommand)) {
                                Dragon dragon2 = new Dragon(argsForAddCommand);
                                long currentMaxAge = 0;
                                for (Dragon d : DragonManager.getInstance().getCollection())
                                    currentMaxAge = Math.max(dragon2.getAge(), d.getAge());
                                if (dragon2.getAge() > currentMaxAge)
                                    console.println("new dragon wasn't added");
                                else {
                                    console.println("new dragon was added because his age value is max");
                                    DragonManager.getInstance().addElement(dragon2);
                                }
                            } else {
                                console.println("dragon wasn't added because of incorrect values");
                            }
                            break;
                        case ("execute_script"):
                            File secondFile = new File(userCommand[1]);
                            if (stack.contains(secondFile)) {
                                console.printError("recursion detected");
                                continue;
                            }

                        default:
                            console.println("current command: '" + currentLine + "'");
                            commandManager.executeCommand(userCommand);
                            break;

                    }
                }

            } finally {
                stack.pop();
            }
            console.println("script was done!");
            return true;
            }catch (IOException exception) {
                console.printError("ops, smth went wrong!");
            }catch (WrongArgumentException exception) {
                console.printError("command '" + getName() + "' should has argument!");
            }catch (NoEnoughRootsException exception) {
                console.printError("unable to read script from file\nno enough roots for this!");
            }
            return false;
    }

    private String getNextCommand(BufferedInputStream bufferedInputStream) throws IOException {
        StringBuilder line = new StringBuilder();
        int byteData;
        while ((byteData = bufferedInputStream.read()) != -1) {
            char charData = (char) byteData;
            if (charData == '\n') {
                break;
            } else if (charData == '\r') {
                bufferedInputStream.mark(1);
                if ((byte) bufferedInputStream.read() != '\n') {
                    bufferedInputStream.reset();
                }
                break;
            }else {
                line.append(charData);
            }
        }
        return line.length() > 0 ? line.toString() : null;
    }
}