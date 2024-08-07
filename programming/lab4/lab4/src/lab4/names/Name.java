package lab4.names;

public enum Name {
    ANTON("Антон"),
    EVGENIY("Евгений"),
    ARSENIY("Арсений"),
    MAKSIM("Максим"),
    IVAN("Иван"),
    VICTOR("Виктор"),
    ALEXEY("Алексей"),
    BORIS("Борис"),
    ARTUR("Артур"),
    ARTEM("Артем"),
    VADIM("Вадим"),
    GEORGIY("Георгий"),
    DENIS("Денис"),
    DMITRII("Дмитрий"),
    STILYAGA("Стиляга"),
    SOPLIVIY("Сопливый"),
    HLAM("Хлам"),
    SHEPTUN("Шептун"),
    ZUBILO("Зубило"),
    CHICHA("Чича"),
    STUDENT("Студент"),
    BIDJO("Биджо"),
    BASHKA("Башка"),
    FAT("Толстый"),
    HOREK("Хорек"),
    EMPTY_NAME("");

    private String name;

    private Name(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public static Name getRandomName() {
        Name[] names = Name.values();
        int randomIndex = (int) (Math.random() * names.length);
        return names[randomIndex];
    }
}
