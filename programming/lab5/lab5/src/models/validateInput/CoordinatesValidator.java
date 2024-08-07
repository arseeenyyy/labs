package models.validateInput;

import exceptions.WrongInputException;
import models.Coordinates;
import utility.Console;

/**
 * Класс для запрашивания координат с клавиатуры
 * @author Arseniy Rubtsov
*/

public class CoordinatesValidator {
    /**
     * @param console
     * @see Console
     * @return Coordinates
     * @see Coordinates
    */
    public static Coordinates validateCoordinates(Console console) {
        Integer x;
        while (true) {
            console.print("enter <Integer> x coordinate: ");
            String line = console.readln().trim();
            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);
                    break;
                }catch (NumberFormatException exception) { }
            }
        }
        Float y;
        while (true) {
            console.print("enter <Float> y coordinate > -461: ");
            String line = console.readln().trim();
            if (!line.isEmpty()) {
                try {
                    y = Float.parseFloat(line);
                    if (Float.compare(y, -461) < 0) throw new WrongInputException();
                    break;
                }catch (NumberFormatException exception) {}
                catch (WrongInputException exception) {}
            }
        }
        return new Coordinates(x, y);
    }
}