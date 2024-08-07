package managers;

import models.Dragon;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Класс, управляющий коллекцией
 * @author Arseniy Rubtsov
*/
public class DragonManager {
    private static DragonManager singletonPattern;
    private ArrayList<Dragon> collection = new ArrayList<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    /**
     * Возвращает экземпляр класса
     * @return singletonPattern
    */
    public static DragonManager getInstance() {
        if (singletonPattern == null)
            singletonPattern = new DragonManager();
        return singletonPattern;
    }
    /**
     * @return collection
    */
    public ArrayList<Dragon> getCollection() {
        return collection;
    }
    /**
     * Устанавливает новую коллекцию
    */
    public void setCollection(ArrayList<Dragon> dragons) {
        collection = dragons;
    }
    /**
     * Устанавливает время инициализации
    */
    public void setLastInitTime() {
        lastInitTime = LocalDateTime.now();
    }
    /**
     * Устанавливает время сохранения
    */
    public void setLastSaveTime() {
        lastSaveTime = LocalDateTime.now();
    }
    /**
     * @return lastInitTime
    */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }
    /**
     * @return lastSaveTime
    */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }
    /**
     * Возвращает размер коллекции
     * @return collection.size()
    */
    public int size() {
        return collection.size();
    }
    /**
     * Добавляет новый элемент в коллекцию
     * @param dragon
     * @see Dragon
    */
    public void addElement(Dragon dragon) {
        if (dragon.validate()) {
            collection.add(dragon);
        }
        update();
    }
    /**
     * Сортирует коллекцию по умолчанию
    */
    public void update() {
        Collections.sort(collection);
    }
    /**
     * Удаляет объект по его Id
     * @param id
    */
    public void removeById(int id) {
        int index = findById(id);
        collection.remove(index);
        update();
    }
    /**
     * Находит индекс объекта по его Id
     * @param id
     * @return index
    */
    public int findById(int id) {
        for (int i = 0; i < collection.size(); i ++) {
            if (collection.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
    /**
     * Очищает коллекцию
    */
    public void clear() {
        collection.clear();
    }
    /**
     * Удаляет элемент по его индексу
     * @param index
    */
    public void remove(int index) {
        collection.remove(index);
        update();
    }
    /**
     * Получает объект по его индексу
     * @param index
     * @return dragon
     * @see Dragon
    */
    public Dragon get(int index) {
        return collection.get(index);
    }
}
