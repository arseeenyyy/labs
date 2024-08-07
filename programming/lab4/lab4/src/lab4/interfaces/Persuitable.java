package lab4.interfaces;

import lab4.humans.Human;
public interface Persuitable {
    void persuit(Human human, double chance) throws InterruptedException;
}
