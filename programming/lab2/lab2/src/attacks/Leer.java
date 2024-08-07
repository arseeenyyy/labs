package attacks;
import ru.ifmo.se.pokemon.*;

public class Leer extends StatusMove{
    public Leer() {
        super(Type.NORMAL, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().stat(Stat.DEFENSE, -1);
        p.addEffect(e);
    }
    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        if (!def.isAlive())
            System.out.println("покемон " + att + " вырубает " + def);
        return super.checkAccuracy(att, def);
    }
    @Override
    protected String describe() {
        return "использует Leer";
    }
}
