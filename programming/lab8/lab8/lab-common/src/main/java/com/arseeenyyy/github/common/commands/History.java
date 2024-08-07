package com.arseeenyyy.github.common.commands;

import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;

import java.util.LinkedList;

public class History extends BaseCommand {
    private static final int HISTORY_SIZE = 13;
    private static LinkedList<String> commandQueue = new LinkedList<>();

    public History() {
        super("history", "show command history");
    }

    @Override
    public Response execute(Request request) {
        return new Response.ResponseBuilder()
                .setMessageToResponse(commandQueue.toString())
                .setExecutionCode(ExecutionCode.OK)
                .build();
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
