package models.validateInput;
import utility.Console;
import models.Dragon;
/**
 * Класс для запрашивания имени дракона
 * @author Arseniy Rubtsov
*/
public class NameValidator {
    /**
     * @param console
     * @see Console
     * @return dragon
     * @see Dragon
    */
    public static Dragon dragonNameValidator(Console console) {
        String name;
        while (true) {
            console.print("enter dragon name: ");
            name = console.readln().trim();
            if (!name.isEmpty()) break;
        }
        var coordinates = CoordinatesValidator.validateCoordinates(console);
        var age = AgeValidator.validateAge(console);
        var description = DescriptionValidator.validateDescription(console);
        var wingspan = WingspanValidator.validateWingspan(console);
        var color = ColorValidator.validateColor(console);
        var dragonCave = DragonCaveValidator.validateDragonCave(console);
        return new Dragon(name, coordinates, age, description, wingspan, color, dragonCave);
    }
    public static Dragon dragonNameValidatorWithId(Console console, int id) {
        String name;
        while (true) {
            console.print("enter dragon name: ");
            name = console.readln().trim();
            if (!name.isEmpty() && name.getBytes()[0] == -1) break;
        }
        var coordinates = CoordinatesValidator.validateCoordinates(console);
        var age = AgeValidator.validateAge(console);
        var description = DescriptionValidator.validateDescription(console);
        var wingspan = WingspanValidator.validateWingspan(console);
        var color = ColorValidator.validateColor(console);
        var dragonCave = DragonCaveValidator.validateDragonCave(console);
        return new Dragon(id, name, coordinates, age, description, wingspan, color, dragonCave);
    }
}
