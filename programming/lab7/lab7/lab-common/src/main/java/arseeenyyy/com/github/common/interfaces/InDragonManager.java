package arseeenyyy.com.github.common.interfaces;

import arseeenyyy.com.github.common.models.Dragon;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface InDragonManager {
    ArrayList<Dragon> getCollection();
    void setCollection(ArrayList<Dragon> dragons);
    void setLastInitTime();
    LocalDateTime getLastInitTime();
    int size();
    void addElement(Dragon dragon);
    void update();
    boolean removeById(int id);
    //    int findById(int id);
    void clear();
    void remove(int index);
    Dragon get(int index);

}
