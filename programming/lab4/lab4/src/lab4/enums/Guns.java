package lab4.enums;

public enum Guns {
    KNIFE ("нож"),
    PISTOL ("пистолет"),
    DAGGER ("кинжал"),
    RIFLE ("автомат");

    private final String gun;

    private Guns(String gun)     {
        this.gun = gun;
    }
    public String getGun() {
        return gun;
    }
    public static Guns getRandomGun() {
        Guns[] guns = Guns.values();
        int randomIndex = (int) (Math.random() * guns.length);
        return guns[randomIndex];
    }
}
