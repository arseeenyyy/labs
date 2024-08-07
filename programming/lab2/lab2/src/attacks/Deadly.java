package attacks;
import ru.ifmo.se.pokemon.*;

public class Deadly extends SpecialMove {
    Pokemon def;
    Pokemon att;
    public Deadly(Type var1, double var2, double var4) {
        super(var1, var2, var4);
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        this.def = def;
        this.att = att;
        return true;
    }
    @Override
    protected void applyOppDamage(Pokemon def, double damage) {

        if (!def.isAlive())
            System.out.println("покемон " + this.att.toString() + " вырубает " + this.def.toString());
    }
}
