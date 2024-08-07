package utility;
import java.util.Scanner;
/**
 * Класс консоли
 * @author Arseniy Rubtsov
*/

public class StandartConsole implements Console {
    private static Scanner scanner = new Scanner(System.in);
    /**
     * Выводит Object.toString() в консоль
     * @param o Объект для печати
    */
    @Override
    public void print(Object o) {
        System.out.print(o);
    }
    /**
     * Выводит Object.toString() + "\n" в консоль
     * @param o Объект для печати
    */
    @Override
    public void println(Object o) {
        System.out.println(o);
    }
    /**
     * Выводит Object.toString() в консоль
     * @param o Ошибка для печати
    */
    @Override
    public void printError(Object o) {
        System.err.println(o);
    }
    /**
     * Выводит таблицу из 2 колонок
     * @param o1 Объект первой колонки
     * @param o2 Объект второй колонки
    */
    @Override
    public void printTable(Object o1, Object o2) {
        System.out.printf(" %-50s%-5s%n", o1, o2);;
    }
    /**
     * Считывает ввод с клавиатуры
     * @return String считанную с консоли строку
    */
    @Override
    public String readln() {
        return scanner.nextLine();
    }
}