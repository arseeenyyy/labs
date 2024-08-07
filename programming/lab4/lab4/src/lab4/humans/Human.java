package lab4.humans;

import lab4.enums.Guns;
import lab4.exception.EmptyNameException;
import lab4.interfaces.*;
import lab4.names.Name;

public abstract class Human implements Failable, Fallable, Flopable, Fightable, Shootable, Persuitable, Drownable {
    private String hasGun;
    private String name;

    public Human() throws EmptyNameException {
        this.name = Name.getRandomName().getName();
        this.hasGun = Guns.getRandomGun().getGun();
    }

    public String getName() {
        return name;
    }

    public String getHasGun() {
        return hasGun;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new EmptyNameException("aboba");
        }
    }
}
