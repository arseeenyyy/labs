package com.arseeenyyy.github.server.managers;

import com.arseeenyyy.github.common.interfaces.InDragonManager;
import com.arseeenyyy.github.common.models.Dragon;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragonManager implements InDragonManager {
    private List<Dragon> collection = Collections.synchronizedList(new ArrayList<Dragon>());
    private LocalDateTime lastInitTime;
    /**
     * @return collection
     */
    public ArrayList<Dragon> getCollection() {
        synchronized (collection) {
            return new ArrayList<>(collection);
        }
    }
    /**
     * Устанавливает новую коллекцию
     */
    public void setCollection(ArrayList<Dragon> dragons) {
        synchronized (collection) {
            collection = Collections.synchronizedList(dragons);
        }
        setLastInitTime();
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
    /**
     * @return lastInitTime
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }
    /**
     * @return lastSaveTime
     */
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
        if (dragon.validate())
            collection.add(dragon);
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

    public boolean removeById(int id) {
        boolean wasRemoved = collection.removeIf(dragon -> dragon.getId() == id);
        if (wasRemoved)
            update();
        return wasRemoved;
    }

    /**
     * Находит индекс объекта по его Id
     * @param id
     * @return index
     */

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
