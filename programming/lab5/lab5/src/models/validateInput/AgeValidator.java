package models.validateInput;

import exceptions.WrongArgumentException;
import utility.Console;
/**
 * Класс для запрашивания возраста с клавиатуры
 * @author Arseniy Rubtsov
*/

public class AgeValidator {
    /**
     * @param console
     * @see Console
     * @return long age
    */
    public static long validateAge(Console console) {
        long age;
        while(true) {
            console.print("enter <long> age > 0: ");
            String line = console.readln().trim();
            if (!line.isEmpty()) {
                try {
                    age = Long.parseLong(line);
                    if (age < 0) throw new WrongArgumentException();
                    break;
                }catch (NumberFormatException exception) {

                }catch (WrongArgumentException exception) {}
            }
        }
        return age;
    }
}