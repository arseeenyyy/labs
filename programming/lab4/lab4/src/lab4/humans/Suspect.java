package lab4.humans;

import lab4.enums.RandomThings;
import lab4.enums.Transport;
import lab4.lifelessobj.Color;

import java.util.Objects;

public class Suspect extends Human{
    private boolean isMasked;
    public Suspect() {
        super();
        isMasked = Math.random() > 0.5;
    }
    public void fall() {
        System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " упал" + Color.ANSI_RESET);
    }
    public void fail() {
        System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " провалился куда - то, непонятно куда" + Color.ANSI_RESET);
    }
    public void drown(Human human) {
        System.out.println(Color.ANSI_BLUE + human.getName() + " топит" + Color.ANSI_RED + " подозрительную личность " + getName() + Color.ANSI_RESET);
    }

    public void fight(Human human, double chance) throws InterruptedException {
        System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " начинает драку " + Color.ANSI_BLUE + "с полицейским " + human.getName() + "\n" +
                Color.ANSI_RED + "Подозрительной личности под руку попадает "  + RandomThings.getRandomThings().getName() + "\n" + Color.ANSI_BLUE +
                "В свою очередь полицейскому " + human.getName() + " " + RandomThings.getRandomThings().getName() + Color.ANSI_RESET);
        Thread.sleep(2000);
        if (chance > 0.5) {
            System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " одерживает победу в этой нелегкой схватке" + Color.ANSI_RESET);
        }
        else {
            System.out.println(Color.ANSI_BLUE + "Полицейский " + human.getName() + " одерживает победу в этой нелегкой схватке" + Color.ANSI_RESET);
        }
    }
    public void flop(Human human, double chance) throws InterruptedException {
        System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " шлепнулся в воду" + Color.ANSI_RESET);
        if (chance > 0.5) {
            Thread.sleep(2000);
            drown(human);
        }
    }
    public void shoot(Human human, double chance)  throws InterruptedException {
        System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " начинает перестрелку с " + Color.ANSI_BLUE + "полицейским " + human.getName() + "\n" +
                Color.ANSI_RED + getName() + " использует " + getHasGun() + "\n" + Color.ANSI_BLUE +
                "Полицейский " + human.getName() + " использует " +  human.getHasGun() + Color.ANSI_RESET);
        Thread.sleep(2000);
        if (chance > 0.5)
            System.out.println(Color.ANSI_RED + "Подозрительная личность " + getName() + " одерживает победу в перестрелке " + Color.ANSI_RESET);
        else
            System.out.println(Color.ANSI_BLUE + "Полицейский " + human.getName() + " одерживает победу в перестрелке" + Color.ANSI_RESET);
    }
    public void persuit(Human human, double chance) throws InterruptedException {
        System.out.println(Color.ANSI_RED + "Подозрительная личность  " + getName() + " преследует полицейского  " + human.getName() + ", используя " + Transport.getRandomTransport().getTransport());
        Thread.sleep(2000);
        if (chance > 0.5)
            System.out.println("Подозрительная личность " + getName() + " догнал полицейского " + human.getName() + Color.ANSI_RESET);
        else
            System.out.println(Color.ANSI_BLUE + "Полицейский " + human.getName() + " убежал на своих двоих от подозрительной личности "  + getName() + Color.ANSI_RESET);
    }
    @Override
    public String toString() {
        if (isMasked)
            return Color.ANSI_RED + "Подозрительная личность " + getName() + " в маске и вооружен "  + getName() + "ом" + Color.ANSI_RESET;
        else
            return Color.ANSI_RED + "Подозроительная личность "  + getName() + " без маски и вооружен " + getHasGun() + "ом" + Color.ANSI_RESET;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this == null || getClass() != obj.getClass()) return false;

        Suspect suspect = (Suspect) obj;
        return (getName() == suspect.getName() && isMasked == suspect.isMasked && getHasGun() == suspect.getHasGun());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getName(), isMasked, getHasGun());
    }

}
