package lab4.cinema;

import lab4.interfaces.Drownable;
import lab4.lifelessobj.Color;

public class Cinema {
    public class Film {
        private String name;

        public Film() {
            if (Math.random() > 0.5)
                name = "Убийство на дне моря";
            else
                name = "Кровавый знак";
            }
            public void filmActions() throws InterruptedException{
                class WildAnimals {
                    private static String name = "дикие звери";
                    public static String getName() {
                        return name;
                    }
                }
                System.out.println(Color.ANSI_RED + "В фильме: '" + name + "' будут: " + Color.ANSI_RESET);
                Thread.sleep(1500);
                Actions.getActions();
                System.out.println(WildAnimals.getName());
            }
        }
    private enum Actions {
        KilliNGS("убийства"),
        ROBBERIES("ограбления"),
        DROWNINGS("утопления"),
        DROPPINGS("бросания"),
        TEARINGS("растерзания");

        private String name;
        private Actions(String name) {
            this.name = name;
        }
        private static void getActions() throws InterruptedException{
            System.out.println(Color.ANSI_RED + KilliNGS.name + Color.ANSI_BLUE +"\n" + ROBBERIES.name + Color.ANSI_CYAN + Color.ANSI_PURPLE
                    + "\n" + Color.ANSI_YELLOW + DROWNINGS.name + Color.ANSI_RED + "\n" + DROPPINGS.name + Color.ANSI_GREEN + "\n" + TEARINGS.name + Color.ANSI_RESET);
        }
    }
}