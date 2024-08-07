package lab4.interfaces;

import lab4.humans.Human;
public interface Fightable {
    void fight(Human human, double chance) throws InterruptedException;
}
