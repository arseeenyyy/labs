package models.parseConf;

import managers.DragonManager;
import models.Coordinates;
import models.Dragon;
import models.DragonCave;
import models.Color;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utility.Console;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
/**
 * Класс для чтения коллекции из формата XML
 * @author Arseniy Rubtsov
*/
public class XmlReader {
    public static String path = "src/data.xml";
    /**
     * Чтение коллекции
     * @param console
     * @see Console
    */
    public static void readCollection(Console console, String filePath) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler();
        console.println("downloading data from file");
        try {
            parser.parse(filePath, handler);
            console.println("data was downloaded");
            DragonManager.getInstance().setLastInitTime();
        }catch (FileNotFoundException exception) {
            console.printError("file wasn't found");
        }
    }
    public static String getName() {
        path = System.getenv("LAB5_DATA");
        return path;
    }
    /**
     * Класс для парсинга из исходного формата
     * @see DefaultHandler
    */
    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("dragon")) {
                int id = Integer.parseInt(attributes.getValue("id"));
                String name = attributes.getValue("name");
                Integer x = Integer.parseInt(attributes.getValue("x"));
                Float y = Float.parseFloat(attributes.getValue("y"));
                Coordinates coordinates = new Coordinates(x, y);
                LocalDateTime creationDate = LocalDateTime.parse(attributes.getValue("creationDate"));
                long age = Long.parseLong(attributes.getValue("age"));
                String description = attributes.getValue("description");
                double wingspan = Double.parseDouble(attributes.getValue("wingspan"));
                Color color = Color.valueOf(attributes.getValue("color"));
                DragonCave cave = new DragonCave(Long.parseLong(attributes.getValue("cave")));
                Dragon dragon = new Dragon(id, name, coordinates, creationDate, age, description, wingspan, color, cave);
                if (dragon.validate())
                    DragonManager.getInstance().addElement(dragon);
            }
        }
    }
}
