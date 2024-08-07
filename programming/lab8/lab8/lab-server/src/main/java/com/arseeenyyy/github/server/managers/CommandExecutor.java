package com.arseeenyyy.github.server.managers;

import com.arseeenyyy.github.common.commands.BaseCommand;
import com.arseeenyyy.github.common.commands.History;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class CommandExecutor {
    private final CommandManager commandManager;

    public CommandExecutor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }
    public Response execute(Request request) {
        String[] userCommand = request.getCommand().contains(" ") ? request.getCommand().split(" ", 2) : new String[]{request.getCommand(), ""};
        BaseCommand command = commandManager.getCommandList().get(userCommand[0]);
        if (command == null) {
//            return new Response("unknown command, type 'help' to see all available commands", ExecutionCode.ERROR);
            return new Response.ResponseBuilder().setMessageToResponse(" ").setExecutionCode(ExecutionCode.ERROR).build();
        }
        Response response = command.execute(request);
        if (response.getExecutionCode() == ExecutionCode.OK && !request.getCommand().equals("show")) {
            History.addCommandToHistory(userCommand[0]);
        }
        return response;
    }
}