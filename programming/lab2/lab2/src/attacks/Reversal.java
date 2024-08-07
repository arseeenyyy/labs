package attacks;
import ru.ifmo.se.pokemon.*;

public class Reversal extends PhysicalMove {
    public Reversal() {

        super(Type.FIGHTING, 200, 100);
    }
    @Override
    protected void applySelfEffects(Pokemon p) {
        if (p.getStat(Stat.HP) == 1)
            p.setMod(Stat.ATTACK, 6);
        else if (p.getStat(Stat.HP) >= 2 && p.getStat(Stat.HP) <= 5)
            p.setMod(Stat.ATTACK, 4);
        else if (p.getStat(Stat.HP) >= 6 && p.getStat(Stat.HP) <= 12)
            p.setMod(Stat.ATTACK, 3);
        else if (p.getStat(Stat.HP) >= 13 && p.getStat(Stat.HP) <= 21)
            p.setMod(Stat.ATTACK, 2);
        else if (p.getStat(Stat.HP) >= 22 && p.getStat(Stat.HP) <= 42)
            p.setMod(Stat.ATTACK, 2);
        else
            p.setMod(Stat.ATTACK, 1);
    }
    @Override
    protected String describe() {
        return "использует Reversal";
    }
}