package commands;

import exceptions.NoEnoughRootsException;
import exceptions.WrongArgumentException;
import models.parseConf.XmlReader;
import models.parseConf.XmlWriter;
import utility.Console;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Команда 'save'. Сохраняет элемент в коллекцию
 * @author Arseniy Rubtsov
*/
public class Save extends BaseCommand {
    private final Console console;

    public Save(Console console) {
        super("save", "saves collection to file");
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
            Path path = Paths.get(XmlReader.path);
            if (!Files.isWritable(path)) throw new NoEnoughRootsException();
            XmlWriter.writeCollection(console, XmlReader.path);
            console.println("collection was saved");
            return true;
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no arguments!");
        }catch (NoEnoughRootsException exception) {
            console.printError("unable to write file:((");
        }
        return false;
    }

}
