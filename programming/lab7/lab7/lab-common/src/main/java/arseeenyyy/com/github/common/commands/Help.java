package arseeenyyy.com.github.common.commands;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.interfaces.InCommandManager;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

public class Help extends BaseCommand {
    private final InCommandManager commandManager;

    public Help(InCommandManager commandManager) {
        super("help", "show list of available commands");
        this.commandManager = commandManager;
    }


    @Override
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        try {
            if (!userCommand[1].isEmpty()) throw new WrongArgumentException();

            StringBuilder sb = new StringBuilder();
            commandManager.getCommandList().values().forEach(command -> {
                String tableRow = String.format("%-50s%-5s%n", command.getName(), command.getDescription());
                sb.append(tableRow);
            });
            return new Response(sb.toString().trim(), ExecutionCode.OK);
        } catch (WrongArgumentException exception) {
            return new Response("command '" + getName() + "' shouldn't has arguments!", ExecutionCode.ERROR);
        }
    }
}
