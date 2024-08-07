package attacks;
import ru.ifmo.se.pokemon.*;

public class AirCutter extends SpecialMove {
    public AirCutter() {
        super(Type.FLYING, 60, 95);
    }

    @Override
    protected double calcCriticalHit(Pokemon att, Pokemon def) {
        if (Math.random() < (att.getStat(Stat.SPEED) / 256.0))
            return 2.0;
        else
            return 1.0;
    }
    @Override
    protected String describe() {
        return "использует Air Cutter";
    }
}
