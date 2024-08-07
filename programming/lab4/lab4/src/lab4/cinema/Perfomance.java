package lab4.cinema;

import lab4.exception.EmptyNameException;
import lab4.humans.Policeman;
import lab4.humans.Shortie;
import lab4.humans.Suspect;
import lab4.lifelessobj.Color;
import lab4.lifelessobj.Noise;
import lab4.exception.NumberException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Perfomance {
    private static ArrayList<Policeman> policemen = new ArrayList<>();
    private static ArrayList<Suspect> suspects = new ArrayList<>();
    private static int number;
    public static void start() throws InterruptedException{
        Scanner num;
        System.out.print(Color.ANSI_YELLOW + "Введите количество действующих лиц в киносеансе: " + Color.ANSI_RESET);
        while (true) {
            num = new Scanner(System.in);
            try {
                number = num.nextInt();
                if (number < 4) {
                    throw new NumberException("");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println(Color.ANSI_RED + "Неправильной ввод, введите число" + Color.ANSI_RESET);
            }catch (NumberException e) {
                System.out.println(Color.ANSI_PURPLE + "Введите число больше 4" + Color.ANSI_RESET);
            }
        }
        Cinema cinema = new Cinema();
        Cinema.Film film = cinema.new Film();
        film.filmActions();
        Shortie.takeAseat();
        Thread.sleep(1500);

        System.out.println(Color.ANSI_YELLOW + "Свет гаснет, начинается киносеанс" + Color.ANSI_RESET);
        for(int i = 0; i < number; i ++) {
            Policeman policeman = new Policeman();
            Suspect suspect = new Suspect();
            if (policeman.getName().equals("")) {
                throw new EmptyNameException("");
            }
            if (suspect.getName().equals("")) {
                throw new EmptyNameException("");
            }
            policemen.add(policeman);
            suspects.add(suspect);
            Thread.sleep(500);
            System.out.println(policemen.get(i));
            Thread.sleep(500);
            System.out.println(suspects.get(i));
        }
    }
    private static void callMethod(int i) throws InterruptedException {
        int random = (int) (Math.random() * 5 + 1);
        double chance = Math.random();
        switch (random) {
            case 1:
                if (Math.random() > 0.5) {
                    suspects.get(i).fall();
                    suspects.remove(suspects.get(i));
                }
                else {
                    policemen.get(i).fall();
                    policemen.remove(policemen.get(i));
                }
            case 2:
                if (Math.random() > 0.5) {
                    suspects.get(i).fail();
                    suspects.remove(suspects.get(i));
                }
                else {
                    policemen.get(i).fail();
                    policemen.remove(policemen.get(i));
                }
            case 3:
                if (Math.random() > 0.5) {
                    suspects.get(i).flop(policemen.get(i), chance);
                    if (chance > 0.5)
                        suspects.remove(suspects.get(i));
                }
                else {
                    policemen.get(i).flop(policemen.get(i), chance);
                    if (chance > 0.5)
                        policemen.remove(policemen.get(i));
                }
            case 4:
                if (Math.random() > 0.5) {
                    suspects.get(i).persuit(policemen.get(i), chance);
                    if (chance > 0.5)
                        policemen.remove(policemen.get(i));
                }
                else {
                    policemen.get(i).persuit(suspects.get(i), chance);
                    if (chance > 0.5)
                        suspects.remove(suspects.get(i));
                }
            case 5:
                if (Math.random() > 0.5) {
                    suspects.get(i).fight(policemen.get(i), chance);
                    if (chance > 0.5)
                        policemen.remove(policemen.get(i));
                    else
                        suspects.remove(suspects.get(i));
                }
                else {
                    policemen.get(i).fight(suspects.get(i), chance);
                    if (chance > 0.5)
                        policemen.remove(policemen.get(i));
                    else
                        suspects.remove(suspects.get(i));
                }
            case 6:
                if (Math.random() > 0.5) {
                    suspects.get(i).shoot(policemen.get(i), chance);
                    if (chance > 0.5)
                        policemen.remove(policemen.get(i));
                    else
                        suspects.remove(suspects.get(i));
                }
                else {
                    policemen.get(i).shoot(suspects.get(i), chance);
                    if (chance > 0.5)
                        policemen.remove(policemen.get(i));
                    else
                        suspects.remove(suspects.get(i));
                }
        }
    }
    public static void actions() throws InterruptedException {
        Shortie.scream();
        for (int i = 0; i < number * 2; i++) {
            if (i >= policemen.size() - 1 | i >= suspects.size() - 1) {
                System.out.println(Color.ANSI_PURPLE + "Наступает ночь");
                Noise.resetNoiseLevel(number);
                Shortie.sleep();
                Thread.sleep(2000);
                Shortie.wakeUp();

                break;
            }
            else {
                Noise.addjustNoiseLevel();
                Thread.sleep(1500);
                callMethod(0);
                Noise.levelOfNoise(number);
            }
        }
    }
}
