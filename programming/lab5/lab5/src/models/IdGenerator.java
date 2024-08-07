package models;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Класс для генерации уникальных Id
 * @author Arseniy Rubtsov
*/
public class IdGenerator {
    private static final Random RANDOM = new Random();
    private static final int ID_BOUND = 10000;
    private static Set<Integer> ids = new HashSet<>();
    /**
     * @return int uniqueId
    */
    public static int generateUniqueRandomId() {
        int uniqueId;
        do {
            uniqueId = RANDOM.nextInt(ID_BOUND);
        } while (!ids.add(uniqueId));
        return uniqueId;
    }
}
