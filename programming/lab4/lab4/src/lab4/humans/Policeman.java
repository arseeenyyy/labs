package lab4.humans;

import lab4.enums.RandomThings;
import lab4.enums.Transport;
import lab4.lifelessobj.Color;

import java.util.Objects;

public class Policeman extends Human {
    public Policeman() {
        super();
    }
    public void fail() {
        System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " провалился куда - то, непонятно куда" + Color.ANSI_RESET);
    }

    public void fall() {
        System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " упал на боковую" + Color.ANSI_RESET);
    }
    public void fight(Human human, double chance) throws InterruptedException {
        System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " начинает драку с " + Color.ANSI_RED + "подозрительной личностью " + human.getName() +
                Color.ANSI_BLUE + "\n" + "Полицейскому " + getName() + " под руку попадает " + RandomThings.getRandomThings().getName() + "\n" + Color.ANSI_RED +
                "В свою очередь подозрительной личности " + human.getName() + " " + RandomThings.getRandomThings().getName());
        Thread.sleep(2000);
        if (chance > 0.5)
            System.out.println("Подозрительная личность " + human.getName() + " одерживает победу в этой нелегкой схватке");
        else
            System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " одерживает победу в этой нелегкой схватке" + Color.ANSI_RESET);

    }
    public void drown(Human human) {
        System.out.println(Color.ANSI_RED + "Подозрительная личность " + human.getName() + " топит " + Color.ANSI_BLUE + "полицейского  " + getName() + Color.ANSI_RESET);
    }
    public void flop(Human human, double chance) {
        System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " шлепнулся в воду" + Color.ANSI_RESET);
        if (chance > 0.5)
            drown(human);
    }
    public void persuit(Human human, double chance) throws InterruptedException{
        System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + Color.ANSI_RED + " преследует подозреваемого " + human.getName() + Color.ANSI_BLUE + ", используя " + Transport.getRandomTransport().getTransport());
        Thread.sleep(2000);
        if (chance > 0.5)
            System.out.println("Полицейский " + getName() + Color.ANSI_RED + " догнал подозрительную личность " + human.getName());
        else
            System.out.println("Подозрительная личность " + human.getName() + Color.ANSI_BLUE + " убежал на своих двоих от полицейского " + getName() + Color.ANSI_RESET);
    }
    public void shoot(Human human, double chance) throws InterruptedException{
        System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " начинает перестрелку с подозрительной личностью " + human.getName() + "\n" +
                Color.ANSI_RED + getName() + " использует " + human.getHasGun() + "\n" +
                Color.ANSI_BLUE + getName() + " использует " +  getHasGun() + Color.ANSI_RESET);
        Thread.sleep(2000);
        if (Math.random() > 0.5)
            System.out.println(Color.ANSI_RED + "Подозрительная личность " + human.getName() + " одерживает победу в перестрелке");
        else
            System.out.println(Color.ANSI_BLUE + "Полицейский " + getName() + " одерживает победу в перестрелке" + Color.ANSI_RESET);
    }
    @Override
    public String toString() {
        return Color.ANSI_BLUE + "Полицейский " + getName() + " вооружен " + getHasGun() + "ом" + Color.ANSI_RESET;
    }
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHasGun());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Policeman policeman = (Policeman) obj;
        return (getName() == policeman.getName() && getHasGun() == policeman.getHasGun());
    }
}


