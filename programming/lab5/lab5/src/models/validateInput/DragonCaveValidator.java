package models.validateInput;

import exceptions.WrongInputException;
import models.DragonCave;
import utility.Console;
    /**
     * Класс для запрашивания количества драгоценностей
     * @see DragonCave
     * @author Arseniy Rubtsov
    */
    public class DragonCaveValidator {
        /**
         * @param console
         * @see Console
         * @return dragonCave
         * @see DragonCave
        */
        public static DragonCave validateDragonCave(Console console) {
            long numberOfTreasures;
            while (true) {
                console.print("enter <long> numberOfTreasures > 0 for dragon cave: ");
                String line = console.readln().trim();
                if (!line.isEmpty()) {
                    try {
                        numberOfTreasures = Long.parseLong(line);
                        if (numberOfTreasures < 0) throw new WrongInputException();
                        break;
                    } catch (NumberFormatException exception) {

                    }catch (WrongInputException exception) {

                    }
                }
            }
            return new DragonCave(numberOfTreasures);
        }
    }


