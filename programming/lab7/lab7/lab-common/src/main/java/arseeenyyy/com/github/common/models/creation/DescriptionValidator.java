package arseeenyyy.com.github.common.models.creation;


import java.util.Scanner;

public class DescriptionValidator {

    public static String validateDescription(Scanner scanner) {
        String description;
        while (true) {
            System.out.print("enter description about dragon: ");
            description = scanner.nextLine().trim();
            if (!description.isEmpty())
                break;
        }
        return description;
    }
}