package models.validateInput;

import exceptions.WrongInputException;
import utility.Console;
/**
 * Класс для запрашивания размаха крыльев дракона
 * @author Arseniy Rubtsov
*/
public class WingspanValidator {
    /**
     * @param console
     * @see Console
     * @return wingspan
    */
    public static double validateWingspan(Console console) {
        double wingspan;
        while (true) {
            console.print("enter <double> wingspan: ");
            String line = console.readln().trim();
            if (!line.isEmpty()) {
                try {
                    wingspan = Double.parseDouble(line);
                    if (wingspan < 0) throw new WrongInputException();
                    break;
                }catch (NumberFormatException exception) {
                } catch (WrongInputException exception) {}
            }
        }
        return wingspan;
    }
}