package arseeenyyy.com.github.common.models.creation;


import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.models.Coordinates;

import java.util.Scanner;

public class CoordinatesValidator {

    public static Coordinates validateCoordinates(Scanner scanner) {
        Integer x;
        while (true) {
            System.out.print("enter <Integer> x coordinate: ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                try {
                    x = Integer.parseInt(line);
                    break;
                }catch (NumberFormatException exception) { }
            }
        }
        Float y;
        while (true) {
            System.out.print("enter <Float> y coordinate > -461: ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                try {
                    y = Float.parseFloat(line);
                    if (Float.compare(y, -461) < 0) throw new WrongArgumentException();
                    break;
                }catch (NumberFormatException exception) {}
                catch (WrongArgumentException exception) {}
            }
        }
        return new Coordinates(x, y);
    }
}