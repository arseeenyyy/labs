package program;
import pokemons.*;
import ru.ifmo.se.pokemon.Battle;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Ninjask p1 = new Ninjask("ubuntu", 1);
        Blastoise p2 = new Blastoise("windows", 1);
        Delcatty p3 = new Delcatty("minty", 1);
        Skitty p4 = new Skitty("sweety", 1);
        Victreebel p5 = new Victreebel("iphone", 1);
        Wartortle p6 = new Wartortle("android", 1);
        b.addAlly(p1);
        b.addFoe(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addAlly(p5);
        b.addFoe(p6);
        b.go();
    }
}
