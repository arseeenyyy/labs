package attacks;
import ru.ifmo.se.pokemon.*;

public class Trick extends SpecialMove {
    public Trick() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected String describe() {
        return "использует Trick";
    }
}
