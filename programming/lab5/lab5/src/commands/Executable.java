package commands;
/**
 * Интерфейс для выполнения всех команд
 * @author Arseniy Rubtsov
*/
public interface Executable {
    /**
     * Выполнить что - то
     * @param args Команда и аргумент
     * @return boolean Результат выполнения
    */
    boolean execute(String[] args);
}
