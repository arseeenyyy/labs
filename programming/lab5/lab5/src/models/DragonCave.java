package models;
import java.util.Queue;
/**
 * Класс пещеры
 * @author Arseniy Rubtsov
*/

public class DragonCave implements Comparable<DragonCave>{
    private long numberOfTreasures;
    /**
     * Конструктор класса
     * @param numberOfTreasures
    */
    public DragonCave(long numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }
    public void setNumberOfTreasures(long numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }
    public long getNumberOfTreasures() {
        return numberOfTreasures;
    }
    @Override
    public String toString() {
        return numberOfTreasures + "";
    }
    @Override
    public int compareTo(DragonCave cave) {
        return Long.compare(this.numberOfTreasures, cave.numberOfTreasures);
    }
}