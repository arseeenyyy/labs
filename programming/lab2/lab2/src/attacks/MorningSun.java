package attacks;
import ru.ifmo.se.pokemon.*;

public class MorningSun extends StatusMove {
    Pokemon def;
    Pokemon att;
    public MorningSun() {
        super(Type.NORMAL, 0, 0);
    }
    @Override
    protected void applySelfEffects(Pokemon att) {
        Effect e1 = new Effect().turns(-1).stat(Stat.HP, -(int) att.getStat(Stat.HP));
        att.addEffect(e1);
    }
    @Override
    protected String describe() {
        return "использует Morning sun";
    }
}
