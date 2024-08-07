package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Wartortle extends Pokemon {
    public Wartortle(String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(59, 63, 80, 65, 80, 58);
        setMove(new ThunderShock(), new Amnesia());
    }
}