package attacks;
import ru.ifmo.se.pokemon.*;

public class LightScreen extends StatusMove {
    public LightScreen() {
        super(Type.PSYCHIC, 0, 100);
    }
    @Override
    protected void applyOppEffects(Pokemon p) {
        Effect e = new Effect().turns(5).stat(Stat.SPECIAL_ATTACK, -2);
        p.addEffect(e);
    }
    @Override
    protected String describe() {
        return "использует Light Screen";
    }
}