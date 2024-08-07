package arseeenyyy.com.github.common.models;

import java.io.Serializable;

public class DragonCave implements Comparable<DragonCave>, Serializable {
    private long numberOfTreasures;

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