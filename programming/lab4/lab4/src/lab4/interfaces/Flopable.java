package lab4.interfaces;

import lab4.humans.Human;

public interface Flopable  {
    void flop(Human human, double chance) throws InterruptedException;
}
