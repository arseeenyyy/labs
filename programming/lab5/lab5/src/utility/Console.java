package utility;
/**
 * Консоль для ввода и вывода в стандартный поток
 * @author Arseniy Rubtsov
*/
public interface Console {
    void println(Object o);
    void print(Object o);
    void printError(Object o);
    String readln();
    void printTable(Object o1, Object o2);
}
