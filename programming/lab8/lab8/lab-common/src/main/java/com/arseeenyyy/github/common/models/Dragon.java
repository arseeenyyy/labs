package com.arseeenyyy.github.common.models;

import com.arseeenyyy.github.common.util.IdGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Dragon implements Comparable<Dragon>, Serializable {
    /**
     * Значение поля должно быть больше 0
     * Значение поля должно быть уникальным
     * Значение этого поля должно генерироваться автоматически
     * @see IdGenerator
     */
    private Integer id;
    private int userId;
    /**
     * Значение поля не может быть null
     * Строка не может быть пустой
     */
    private String name;
    /**
     * Поле не может быть null
     * @see Coordinates
     */
    private Coordinates coordinates;
    /**
     * Значение поля не может быть null
     * Значение поля должно генерироваться автоматически
     */
    private java.time.LocalDateTime creationDate;
    /**
     * Значение поля должно быть больше 0
     */
    private long age;
    /**
     * Значение поля не может быть null
     */
    private String description;
    /**
     * Значение поля должно быть больше 0
     */
    private double wingspan;
    /**
     * Значение поля не может быть null
     * @see Color
     */
    private Color color;
    /**
     * Значение поля не может быть null
     */
    private DragonCave cave;

    /**
     * Конструктор с заданными параметрами
     * @param id
     * @param name
     * @param coordinates
     * @see Coordinates
     * @param creationDate
     * @param age
     * @param description
     * @param wingspan
     * @param color
     * @param cave
     * @see DragonCave
     */
    public Dragon(int id, String name, Coordinates coordinates, LocalDateTime creationDate, long age, String description, double wingspan, Color color, DragonCave cave, int userId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.color = color;
        this.cave = cave;
        this.userId = userId;
    }
    /**
     * Конструктор с заданными параметрами
     * @param id
     * @param name
     * @param coordinates
     * @see Coordinates
     * @param age
     * @param description
     * @param wingspan
     * @param color
     * @param cave
     * @see DragonCave
     */
    public Dragon(int id, String name, Coordinates coordinates, long age, String description, double wingspan, Color color, DragonCave cave, int userId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.color = color;
        this.cave = cave;
        this.userId = userId;
    }

    /**
     * Конструктор с заданными параметрами
     * @param args - параметры в массиве
     */
    public Dragon(String[] args) throws NumberFormatException, IllegalArgumentException, ArrayIndexOutOfBoundsException {
        if (args.length == 8) {
            this.id = IdGenerator.generateUniqueRandomId();
            this.creationDate = LocalDateTime.now();
            this.name = args[0];
            this.coordinates = new Coordinates(Integer.parseInt(args[1]), Float.parseFloat(args[2]));
            this.age = Long.parseLong(args[3]);
            this.description = args[4];
            this.wingspan = Double.parseDouble(args[5]);
            this.color = Color.valueOf(args[6].toUpperCase());
            this.cave = new DragonCave(Integer.parseInt(args[7]));
        }
        if (args.length == 9) {
            this.id = Integer.parseInt(args[0]);
            this.creationDate = LocalDateTime.now();
            this.name = args[1];
            this.coordinates = new Coordinates(Integer.parseInt(args[2]), Float.parseFloat(args[3]));
            this.age = Long.parseLong(args[4]);
            this.description = args[5];
            this.wingspan = Double.parseDouble(args[6]);
            this.color = Color.valueOf(args[7].toUpperCase());
            this.cave = new DragonCave(Integer.parseInt(args[8]));
        }
    }

    /**
     * Конструктор с заданными параметрами
     * @param name
     * @param coordinates
     * @see Coordinates
     * @param age
     * @param description
     * @param wingspan
     * @param color
     * @param cave
     * @see DragonCave
     */
    public Dragon(String name, Coordinates coordinates, long age, String description, double wingspan, Color color, DragonCave cave, int userId) {
        id = IdGenerator.generateUniqueRandomId();
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.age = age;
        this.description = description;
        this.wingspan = wingspan;
        this.color = color;
        this.cave = cave;
        this.userId = userId;
    }

    public Integer getId() {return id;}
    public String getName() {return name;}
    public Coordinates getCoordinates() {return coordinates;}
    public LocalDateTime getCreationDate() {return creationDate;}
    public long getAge() {return age;}
    public String getDescription() {return description;}
    public double getWingspan() {return wingspan;}
    public Color getColor() {return color;}
    public DragonCave getCave() {return cave;}
    public int getUserId() {return userId;}

    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCoordinates(Coordinates coordinates) {this.coordinates = coordinates;}
    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}
    public void setAge(long age) {this.age = age;}
    public void setDescription(String description) {this.description = description;}
    public void setWingspan(double wingspan) {this.wingspan = wingspan;}
    public void setColor(Color color) {this.color = color;}
    public void setCave(DragonCave cave) {this.cave = cave;}
    public void setUserId(int userId) {this.userId = userId;}

    @Override
    public String toString() {
        return "dragon[" +
                "id=" + id +
                " name='" + name + "' coordinates=" + coordinates +
                " creationDate='" + creationDate + "' age=" + age + " description='" + description + "'" +
                " wingspan=" + wingspan + " color='" + color + "' cave(numberOfTreasures)=" + cave +
                "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == null) return false;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return this.description.equals(dragon.description) && this.color.equals(dragon.color) && this.age == dragon.age && this.name.equals(dragon.name);
    }

    @Override
    public int compareTo(Dragon dragon) {
        int result = Integer.compare(this.id, dragon.id);
        if(result == 0)
            result = this.name.compareTo(dragon.name);
        if (result == 0)
            result = this.coordinates.compareTo(dragon.coordinates);
        if (result == 0)
            result = this.creationDate.compareTo(dragon.creationDate);
        if (result == 0)
            result = Long.compare(this.age, dragon.age);
        if (result == 0)
            result = this.description.compareTo(dragon.description);
        if (result == 0)
            result = Double.compare(this.wingspan, dragon.wingspan);
        if (result == 0)
            result = this.color.compareTo(dragon.color);
        if (result == 0)
            result = cave.compareTo(dragon.cave);
        return result;
    }

    public static boolean validateArguments(String[] args) {
        if (args[0] == null || args[0].isEmpty()) return false;
        try {
            Integer x = Integer.parseInt(args[1]);
        }catch (NumberFormatException exception) {
            return false;
        }
        try {
            Float y = Float.parseFloat(args[2]);
            if (y <= -461) return false;
        }catch (NumberFormatException exception) {
            return false;
        }
        try {
            long age = Long.parseLong(args[3]);
            if (age < 0) return false;
        }catch (NumberFormatException exception) {
            return false;
        }
        if (args[4].isEmpty() || args[4] == null) return false;
        try {
            double wingspan = Double.parseDouble(args[5]);
            if (wingspan < 0) return false;
        }catch (NumberFormatException exception) {
            return false;
        }
        if (!Color.validateColor(args[6])) return false;
        try {
            long cave = Long.parseLong(args[7]);
        }catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }
    public boolean validate() {
        if (id < 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (age < 0) return false;
        if (description == null || description.isEmpty()) return false;
        if (wingspan < 0) return false;
        if (color == null) return false;
        if (cave == null) return false;
        if (cave.getNumberOfTreasures() < 0) return false;
        return true;
    }

}

