package commands;

import java.util.Objects;
/**
 * Класс базовой команды
 * @author Arseniy Rubtsov
*/
public class BaseCommand implements Descripable, Executable {
    private final String name;
    private final String description;
    public BaseCommand(String name, String desciption) {
        this.name = name;
        this.description = desciption;
    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public boolean execute(String[] args) {
        return true;
    }
    @Override
    public String toString() {
        return "command: '" + name + "' description: '" + description + "'";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseCommand command = (BaseCommand) o;
        return Objects.equals(name, command.name) && Objects.equals(description, command.description);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

}
