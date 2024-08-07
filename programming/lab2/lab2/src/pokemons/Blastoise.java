package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Blastoise extends Wartortle {
    public Blastoise(String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(79, 83, 100, 85, 105, 78);
        setMove(new ThunderShock(), new Amnesia(), new AirCutter());
    }
}