package com.arseeenyyy.github.client.util;

import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.RequestType;
import com.arseeenyyy.github.common.util.User;


public class CommandBuilder {

    private final Client client = ApplicationClient.getClient();
    private User user;

    public Request buildCommand(String commandName) {
        user = client.getUser();
        Request request = new Request.RequestBuilder()
                .setCommand(commandName)
                .setType(RequestType.COMMAND)
                .setUser(user)
                .build();
        return request;
    }
}
