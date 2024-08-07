package arseeenyyy.com.github.server.managers;

import arseeenyyy.com.github.common.commands.BaseCommand;
import arseeenyyy.com.github.common.commands.History;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class CommandExecutor {
    private final CommandManager commandManager;

    public CommandExecutor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        BaseCommand command = commandManager.getCommandList().get(userCommand[0]);
        if (command == null) {
            return new Response("unknown command, type 'help' to see all available commands", ExecutionCode.ERROR);
        }
        Response response = command.execute(request);
        if (response.getExecutionCode() == ExecutionCode.OK) {
            History.addCommandToHistory(userCommand[0]);
        }
        return response;
    }
}
