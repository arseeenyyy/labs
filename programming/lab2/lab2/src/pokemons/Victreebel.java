package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Victreebel extends Pokemon {
    public Victreebel(String name, int level) {
        super(name, level);
        setType(Type.GRASS, Type.POISON);
        setStats(80, 105, 65, 100, 70, 70);
        setMove(new ThunderShock(), new Amnesia(), new AirCutter(), new ShadowPunch());
    }
}