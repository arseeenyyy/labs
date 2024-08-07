package attacks;
import ru.ifmo.se.pokemon.*;

public class ShadowPunch extends PhysicalMove {
    public ShadowPunch() {
        super(Type.GHOST, 60, 100);
    }

    @Override
    protected boolean checkAccuracy(Pokemon att, Pokemon def) {
        return true;
    }
    @Override
    protected String describe() {
        return "использует Shadow Punch";
    }
}
