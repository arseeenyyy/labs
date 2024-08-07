package arseeenyyy.com.github.common.models.creation;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;

import java.util.Scanner;

public class AgeValidator {

    public static long validateAge(Scanner scanner) {
        long age;
        while(true) {
            System.out.print("enter <long> age > 0: ");
            String line = scanner.nextLine().trim();
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