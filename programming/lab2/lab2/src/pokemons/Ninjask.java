package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Ninjask extends Pokemon {
    public Ninjask(String name, int level) {
        super(name, level);
        setStats(61, 90, 45, 50, 50, 160);
        setType(Type.BUG, Type.FLYING);
        setMove(new Reversal(), new Trick(), new MorningSun(), new Peck());
    }
}