package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InDataBaseManager;
import arseeenyyy.com.github.common.interfaces.InDragonManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

import java.util.stream.IntStream;

public class PrintDescending extends BaseCommand {
    private final InDragonManager dragonManager;
    private final InDataBaseManager dbManager;

    public PrintDescending(InDataBaseManager dbManager, InDragonManager dragonManager) {
        super("print_descending", "print collection elements in reverse order");
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            dragonManager.setCollection(dbManager.getAllElementsFromDataBase());
            StringBuilder sb = new StringBuilder();
            IntStream.range(0, dragonManager.size())
                    .mapToObj(i -> dragonManager.get(dragonManager.size() - 1 - i))
                    .forEach(dragon -> sb.append(dragon).append("\n"));
            return new Response(sb.toString(), ExecutionCode.OK);
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }
    }
}
