package lab4.lifelessobj;

public class Noise {
    public static int noiseLevel;
    public static void levelOfNoise(int number) {
        System.out.println(Color.ANSI_GREEN + "Уровень шума в данный момент: " + noiseLevel);
        if (noiseLevel < Math.round(number / 3))
            System.out.println("В кинотеатре пока тихо");
        else if (noiseLevel >= Math.round(number / 3) && noiseLevel <= Math.round(number / 2))
            System.out.println("В кинотеатре становится шумно");
        else
            System.out.println("В кинотеатре шумно, коротышки во всю визжат" + Color.ANSI_RESET);
    }
    public static void addjustNoiseLevel() {
        noiseLevel ++;
    }
    public static void resetNoiseLevel(int number) {
        noiseLevel = 0;
        System.out.println(Color.ANSI_GREEN + "В кинотеатре теперь тихо\nуровень шума: " + noiseLevel + Color.ANSI_RESET);
    }
}
