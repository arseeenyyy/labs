package models.parseConf;

import exceptions.NoEnoughRootsException;
import managers.DragonManager;
import models.Dragon;
import utility.Console;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для записи коллекции в XML файл
 * @author Arseniy Rubtsov
*/
public class XmlWriter {
    /**
     * Запись коллекции
     * @param console
     * @see Console
    */
    public static void writeCollection(Console console, String filePath) {
        StringBuilder xmlString = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n<collection>\n\t<dragons>");
        for (Dragon d : DragonManager.getInstance().getCollection())
            xmlString.append(d.toXml());
        xmlString.append("\n\t</dragons>\n</collection>");
        Path path = Paths.get(filePath);
        try {
            if (Files.notExists(path)) throw new FileNotFoundException();
            if (!Files.isReadable(path)) throw new NoEnoughRootsException();
            if (!Files.isWritable(path)) throw new NoEnoughRootsException();
            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
            writer.println(xmlString);
            writer.close();
            DragonManager.getInstance().setLastSaveTime();
        }catch (FileNotFoundException exception) {
            console.printError("file wasn't found");
        }catch (IOException exception) {
        }catch (NoEnoughRootsException exception) {
        }
    }
}