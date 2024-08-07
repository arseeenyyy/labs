package lab4.humans;

import lab4.interfaces.Invokable;
import lab4.lifelessobj.Color;
import lab4.lifelessobj.Noise;

public class Shortie {
    public static void takeAseat() throws InterruptedException{
        Thread.sleep(2000);
        System.out.println(Color.ANSI_CYAN + "Коротышки бегут занимать места" + Color.ANSI_WHITE);
    }

    public static void scream() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(Color.ANSI_CYAN + "Коротышки пугаются и начинают кричать " + "\n" + "Уровень шума растет" + "\n" +
                "Текущий уровень шума: " + Color.ANSI_GREEN + Noise.noiseLevel + Color.ANSI_RESET);
    }

    public static void sleep() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(Color.ANSI_CYAN + "Коротышки засыпают" + Color.ANSI_RESET);
        Thread.sleep(1500);
        System.out.println(Color.ANSI_CYAN + "Коротышки сладко спять" + Color.ANSI_RESET);
    }
    public static void hear() {
        class Bell {
            private String name;
            private Bell() {
                name = "колокола";
            }
        }
        Bell bell = new Bell();
        System.out.println(Color.ANSI_YELLOW + "услышали звон " + bell.name + Color.ANSI_RESET);
    }
    public static void eat() {
        Eatery eatery = new Eatery();
        System.out.println(Color.ANSI_YELLOW + "коротышки бегут в " + Eatery.name + Color.ANSI_RESET);
    }
    static Invokable inv = new Invokable() {
        @Override
        public void invoke() {
            class Breakfast {
                private String name;
                private Breakfast() {
                    name = "завтрак";
                }
            }
            Breakfast breakfast = new Breakfast();
            System.out.println(Color.ANSI_GREEN + "Призвал коротышек на " + breakfast.name + Color.ANSI_RESET);
        }
    };
    private static class Eatery {
        private static String name;
        private Eatery() {
            name = "столовая";
        }
    }
    public static void wakeUp() throws InterruptedException{
        System.out.println(Color.ANSI_CYAN + "Коротышки проснулись" + Color.ANSI_RESET);
        Thread.sleep(1500);
        Shortie.hear();
        Thread.sleep(1000);
        inv.invoke();
        Thread.sleep(1000);
        Shortie.eat();
    }
}