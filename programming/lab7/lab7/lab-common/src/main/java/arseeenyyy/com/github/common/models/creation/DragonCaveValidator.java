package arseeenyyy.com.github.common.models.creation;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.models.DragonCave;

import java.util.Scanner;

public class DragonCaveValidator {

    public static DragonCave validateDragonCave(Scanner scanner) {
        long numberOfTreasures;
        while (true) {
            System.out.print("enter <long> numberOfTreasures > 0 for dragon cave: ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                try {
                    numberOfTreasures = Long.parseLong(line);
                    if (numberOfTreasures < 0) throw new WrongArgumentException();
                    break;
                } catch (NumberFormatException exception) {

                }catch (WrongArgumentException exception) {

                }
            }
        }
        return new DragonCave(numberOfTreasures);
    }
}
