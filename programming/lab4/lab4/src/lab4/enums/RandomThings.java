package lab4.enums;

public enum RandomThings {
    CHAIR("стул"),
    TABLE("стол"),
    LAPTOP("ноутбук"),
    IPHONE("айфон"),
    CUCUMBER("огурец"),
    BOTTLE("бутылка"),
    SHOVEL("лопата"),
    FORK("вилка"),
    PIPE("труба"),
    SPOON("ложка"),
    KIRIESHKA("кириешка");

    private String name;

    private RandomThings(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static RandomThings getRandomThings() {
        RandomThings[] randomThings = RandomThings.values();
        int randomIndex = (int) (Math.random() * 11);
        return randomThings[randomIndex];
    }
}
