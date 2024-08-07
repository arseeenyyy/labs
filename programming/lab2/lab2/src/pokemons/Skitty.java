package pokemons;
import attacks.*;
import ru.ifmo.se.pokemon.*;

public class Skitty extends Pokemon{
    public Skitty(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(70,50, 45, 45, 35, 50);
        setMove(new ShadowPunch(), new LightScreen(), new HydroPump());
    }
}
