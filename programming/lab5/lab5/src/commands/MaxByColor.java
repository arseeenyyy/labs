package commands;

import exceptions.WrongArgumentException;
import managers.DragonManager;
import models.Color;
import utility.Console;

import java.util.HashSet;
import java.util.Set;
/**
 * Команда 'max_by_color'. Выводит первый элемент с максимальным значением color
 * @author Arseniy Rubtsov
*/
public class MaxByColor extends BaseCommand {
    private final Console console;

    public MaxByColor(Console console) {
        super("max_by_color", "shows max element from collection by max <color> value");
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
            if (DragonManager.getInstance().size() == 0) {
                console.println("collection is empty\nthere's nothing to show");
                return true;
            }
            Set<Color> colors = new HashSet<>();
            for (int i = 0; i < DragonManager.getInstance().size(); i ++) {
                colors.add(DragonManager.getInstance().get(i).getColor());
            }
            Color maxValue = Color.getMaxValue(colors);
            console.println("current max <color> value is: " + maxValue);
            for (int i = 0; i < DragonManager.getInstance().size(); i ++) {
                if (DragonManager.getInstance().get(i).getColor().equals(maxValue)) {
                    DragonManager.getInstance().remove(i);
                    console.println("element with max <color> value was removed");
                    return true;
                }
            }
        }catch (WrongArgumentException exception) {
            console.printError("command '" + getName() + "' has no argument!");
        }
        return false;
    }
}