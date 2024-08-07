package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Delcatty extends Skitty{
    public Delcatty(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(70, 65, 65, 55, 55, 90);
        setMove(new ShadowPunch(), new LightScreen(), new HydroPump(), new Leer());
    }
}
