import managers.CommandManager;
import org.xml.sax.SAXException;
import utility.Runner;
import utility.StandartConsole;
import utility.Console;
import models.parseConf.XmlReader;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException {
        Console console = new StandartConsole();
        CommandManager commandManager = new CommandManager();
        String filePath = "src/data.xml";
//        if (filePath == null) {
//            console.printError("No such file, can't start program without downloading collection from file");
//            System.exit(0);
//        }

        XmlReader.readCollection(console, filePath);
        new Runner(console, commandManager).interactiveMode();
    }
}

