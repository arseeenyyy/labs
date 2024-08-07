package arseeenyyy.com.github.common.interfaces;

import arseeenyyy.com.github.common.models.Coordinates;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.models.DragonCave;
import arseeenyyy.com.github.common.util.Request;

import java.rmi.ConnectIOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public interface InDataBaseManager {
    Connection connectToDatabase();
    ArrayList<Dragon> getCollectionFromDataBase(int user_id);
    ArrayList<Dragon> getAlienCollection(int userId);
    boolean getUser(Request request);
    Integer getUserId(Request request);
    boolean insertNewUser(Request request);
    Integer insertNewDragonCave(DragonCave cave);
    Integer insertNewCoordinates(Coordinates coordinates);
//    boolean addElement(Dragon dragon, int caveId, int coordinateId, int userId);
    boolean deleteAllByUserId(int id);
    Integer addDragonToDataBase(Dragon dragon, int caveId, int coordinatesId, int userId);
    ArrayList<Dragon> getAllElementsFromDataBase();
    boolean removeDragonByWingspan(double wingspan);
    boolean removeDragonById(int id, int userid);
}
