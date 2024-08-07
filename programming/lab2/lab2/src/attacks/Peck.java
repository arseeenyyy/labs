package attacks;
import ru.ifmo.se.pokemon.*;

public class Peck extends PhysicalMove {
    public Peck() {
        super(Type.FLYING, 35, 100);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        super.applyOppDamage(def, damage);
    }
    @Override
    protected String describe() {
        return "использует Peck";
    }
}