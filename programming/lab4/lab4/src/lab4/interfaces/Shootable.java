package lab4.interfaces;

import lab4.humans.Human;

public interface Shootable {
    void shoot(Human human, double chance) throws InterruptedException;
}
