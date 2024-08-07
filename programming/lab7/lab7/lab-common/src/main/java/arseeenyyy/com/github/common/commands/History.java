package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

import java.util.LinkedList;

public class History extends BaseCommand {
    private static final int HISTORY_SIZE = 13;
    private static LinkedList<String> commandQueue = new LinkedList<>();

    public History() {
        super("history", "show command history");
    }

    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[] {request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();
            return new Response(commandQueue.toString(), ExecutionCode.OK);
        }catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }
    }

    public static void addCommandToHistory(String commandName) {
        if (commandQueue.size() < HISTORY_SIZE)
            commandQueue.addFirst(commandName);
        else if (commandQueue.size() == HISTORY_SIZE) {
            commandQueue.removeLast();
            commandQueue.addFirst(commandName);
        }
    }

}