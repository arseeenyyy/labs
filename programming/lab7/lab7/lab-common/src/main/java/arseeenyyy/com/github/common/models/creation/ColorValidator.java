package arseeenyyy.com.github.common.models.creation;

import arseeenyyy.com.github.common.models.Color;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ColorValidator {

    public static Color validateColor(Scanner scanner) {
        Color color;
        while(true) {
            System.out.print("enter color (" + Color.getNames() + "): ");
            var line = scanner.nextLine().trim().toUpperCase();
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
