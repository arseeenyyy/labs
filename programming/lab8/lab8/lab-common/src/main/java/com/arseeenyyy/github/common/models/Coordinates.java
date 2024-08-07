package com.arseeenyyy.github.common.models;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
    private Integer x;
    private Float y;
    /**
     * Конструктор класса
     * @param x
     * param y
     */
    public Coordinates(Integer x, Float y) {
        this.x = x;
        this.y = y;
    }
    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(Float y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }
    public Float getY() {
        return y;
    }
    @Override
    public String toString() {
        return x + ";" + y;
    }
    @Override
    public int compareTo(Coordinates coordinates) {
        if (coordinates == null)
            return 1;
        int result = Integer.compare(this.x, coordinates.x);
        if (result == 0)
            result = Float.compare(this.y, coordinates.y);
        return result;
    }
}
