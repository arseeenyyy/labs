package models;
import java.util.Set;
/*
* Перечисление цветов
* @author Arseniy Rubtsov
*/

public enum Color {
    GREEN,
    BLUE,
    YELLOW,
    BROWN;
    /**
    * @return Строка со всеми элементами enum класса
    */
    public static String getNames() {
        StringBuilder nameList = new StringBuilder();
        for (var color : values()) {
            nameList.append(color.  name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
    /**
    * @return максимальное значение
    */
    public static Color getMaxValue(Set<Color> colors) {
        Color maxColor = null;
        for (Color color : colors) {
            if (maxColor == null || color.compareTo(maxColor) > 0) {
                maxColor = color;
            }
        }
        return maxColor;
    }
    public static boolean validateColor(String value) {
        for (Color color : Color.values()) {
            if (color.name().equalsIgnoreCase(value))
                return true;
        }
        return false;
    }
}
