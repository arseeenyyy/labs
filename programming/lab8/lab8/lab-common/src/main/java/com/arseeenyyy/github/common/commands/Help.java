package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.interfaces.InCommandManager;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

public class Help extends BaseCommand {
    private final InCommandManager commandManager;

    public Help(InCommandManager commandManager) {
        super("help", "shows list of commands");
        this.commandManager = commandManager;
    }

    @Override
    public Response execute(Request request) {
        StringBuilder sb = new StringBuilder();
        commandManager.getCommandList().values().forEach(command -> {
            String tableRow = String.format("%-50s%-5s%n", command.getName(), command.getDescription());
            sb.append(tableRow);
        });
        return new Response.ResponseBuilder()
                .setMessageToResponse(sb.toString())
                .setExecutionCode(ExecutionCode.OK)
                .build();
    }
}
