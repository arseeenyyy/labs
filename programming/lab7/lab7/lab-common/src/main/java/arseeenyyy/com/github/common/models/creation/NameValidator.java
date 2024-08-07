package arseeenyyy.com.github.common.models.creation;

import arseeenyyy.com.github.common.models.Dragon;

import java.util.Scanner;

public class NameValidator {

    public static Dragon dragonNameValidator(Scanner scanner) {
        String name;
        while (true) {
            System.out.print("enter dragon name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) break;
        }
        var coordinates = CoordinatesValidator.validateCoordinates(scanner);
        var age = AgeValidator.validateAge(scanner);
        var description = DescriptionValidator.validateDescription(scanner);
        var wingspan = WingspanValidator.validateWingspan(scanner);
        var color = ColorValidator.validateColor(scanner);
        var dragonCave = DragonCaveValidator.validateDragonCave(scanner);
        return new Dragon(name, coordinates, age, description, wingspan, color, dragonCave);
    }
    public static Dragon dragonNameValidatorWithId(Scanner scanner, int id) {
        String name;
        while (true) {
            System.out.print("enter dragon name: ");
            name = scanner.nextLine().trim();
            if (!name.isEmpty()) break;
        }
        var coordinates = CoordinatesValidator.validateCoordinates(scanner);
        var age = AgeValidator.validateAge(scanner);
        var description = DescriptionValidator.validateDescription(scanner);
        var wingspan = WingspanValidator.validateWingspan(scanner);
        var color = ColorValidator.validateColor(scanner);
        var dragonCave = DragonCaveValidator.validateDragonCave(scanner);
        return new Dragon(id, name, coordinates, age, description, wingspan, color, dragonCave);
    }
}