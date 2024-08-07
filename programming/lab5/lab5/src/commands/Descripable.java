package commands;
/**
 * Интерфейс, для предоставления описания и названия команды
 * @author Arseniy Rubtsov
*/
public interface Descripable {
    /**
     * Получить название команды
     * @return String название команды
    */
    String getName();
    /**
     * Получить описание команды
     * @return String описание команды
    */
    String getDescription();

}
