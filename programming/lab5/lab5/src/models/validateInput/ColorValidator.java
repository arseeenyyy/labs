package models.validateInput;
import models.Color;
import utility.Console;

import java.util.NoSuchElementException;
/**
 * Класс для запрашивания цвета с клавиатуры
 * @author Arseniy Rubtsov
*/

public class ColorValidator {
    /**
     * @param console
     * @see Console
     * @return color
    */
    public static Color validateColor(Console console) {
        Color color;
        while(true) {
            console.print("enter color (" + Color.getNames() + "): ");
            var line = console.readln().trim().toUpperCase();
            if (!line.isEmpty()) {
                try {
                    color = Color.valueOf(line);
                    break;
                }catch (NoSuchElementException exception) {

                }catch (NullPointerException exception) {

                }catch (IllegalStateException exception) {

                }catch (IllegalArgumentException exception) {

                }
            }
        }
        return color;
    }
}
