package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.interfaces.Descripable;
import arseeenyyy.com.github.common.interfaces.Executable;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class BaseCommand implements Executable, Descripable {
    private final String name;
    private final String description;

    public BaseCommand(String name, String description) {
        this.name = name;
        this.description = description;
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
    public Response execute(Request request) {
        return null;
    }

}
