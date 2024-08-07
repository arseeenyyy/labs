package attacks;
import ru.ifmo.se.pokemon.*;

public class HydroPump extends SpecialMove {
    public HydroPump() {
        super(Type.WATER, 110, 50);
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {
        super.applyOppDamage(def, damage);
    }
    @Override
    protected String describe() {
        return "использует Hydro Pump";
    }
}
