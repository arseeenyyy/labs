package com.arseeenyyy.github.common.interfaces;

import com.arseeenyyy.github.common.models.Coordinates;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.models.DragonCave;
import com.arseeenyyy.github.common.util.Request;

import java.sql.Connection;
import java.util.ArrayList;

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
    boolean removeDragonByWingspan(double wingspan, int userId);
    boolean removeDragonById(int id, int userid);
    void updateDragon(Request request);
}