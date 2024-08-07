package arseeenyyy.com.github.common.models.creation;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;

import java.util.Scanner;

public class WingspanValidator {

    public static double validateWingspan(Scanner scanner) {
        double wingspan;
        while (true) {
            System.out.print("enter <double> wingspan: ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                try {
                    wingspan = Double.parseDouble(line);
                    if (wingspan < 0) throw new WrongArgumentException();
                    break;
                }catch (NumberFormatException exception) {
                } catch (WrongArgumentException exception) {}
            }
        }
        return wingspan;
    }
}