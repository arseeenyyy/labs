package arseeenyyy.com.github.common.models.creation;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;

import java.util.Scanner;

public class IdValidator {

    public static int validateId(Scanner scanner) {
        int id;
        while (true) {
            System.out.print("enter <int> id > 0: ");
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                try {
                    id = Integer.parseInt(line);
                    if (id <= 0) throw new WrongArgumentException();
                    break;
                }catch (NumberFormatException exception) {

                }catch (WrongArgumentException exception) {

                }
            }
        }
        return id;
    }
}