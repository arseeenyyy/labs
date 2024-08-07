package models.validateInput;

import utility.Console;
/**
 * Класс для запрашивания описания с клавиатуры
 * @author Arseniy Rubtsov
*/
public class DescriptionValidator {
    /**
     * @param console
     * @see Console
     * @return description
    */
    public static String validateDescription(Console console) {
        String description;
        while (true) {
            console.print("enter description about dragon: ");
            description = console.readln().trim();
            if (!description.isEmpty())
                break;
        }
        return description;
    }
}