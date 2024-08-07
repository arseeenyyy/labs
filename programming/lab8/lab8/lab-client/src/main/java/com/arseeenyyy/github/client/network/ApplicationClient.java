package com.arseeenyyy.github.client.network;

public class ApplicationClient {
    private static Client client = new Client(4444);
    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        ApplicationClient.client = client;
    }
}