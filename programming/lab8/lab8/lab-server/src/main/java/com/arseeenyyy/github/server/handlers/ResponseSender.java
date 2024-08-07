package com.arseeenyyy.github.server.handlers;


import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.ExecutionCode;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.common.util.serializers.Serializer;
import com.arseeenyyy.github.server.app.Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ResponseSender implements Consumer<Response> {
    private final SelectionKey key;
    private final int CHUNK_SIZE = 100;

    private final Set<SelectionKey> workingKeys;

    public ResponseSender(SelectionKey key, Set<SelectionKey> workingKeys) {
        this.key = key;
        this.workingKeys = workingKeys;
    }

    @Override
    public void accept(Response response) {
        SocketChannel client = (SocketChannel) key.channel();
        if (response == null) {
            response = new Response.ResponseBuilder()
                    .setMessageToResponse("idk")
                    .setExecutionCode(ExecutionCode.ERROR)
                    .build();
//        } else if (response.getDragonList() != null) {
//            try {
//                sendCollectionInChunks(client, response.getDragonList());
//            }catch (IOException exception) {Server.LOGGER.error("error with chunks", exception);}
        } else {
            sendResponse(response, client);
        }
        workingKeys.remove(key);
    }

    private void sendResponse(Response response, SocketChannel client) {
        try {
            ByteBuffer buffer = Serializer.serializeResponse(response);
//            while (buffer.hasRemaining()) {
                client.write(buffer);
//            }
            Server.LOGGER.info("response sent\n" + response.getExecutionCode());
        }catch (IOException exception) {
            System.err.println("response sender error");
        }
    }

    private void sendCollectionInChunks(SocketChannel client, List<Dragon> fullCollection) throws IOException {
        int chunkSize = 100; // определите размер чанка
        int start = 0;
        while (start < fullCollection.size()) {
            int end = Math.min(start + chunkSize, fullCollection.size());
            List<Dragon> chunk = fullCollection.subList(start, end);
            Response chunkResponse = new Response.ResponseBuilder()
                    .setDragonList(new ArrayList<>(chunk))
                    .setExecutionCode(ExecutionCode.OK)
                    .build();
            ByteBuffer buffer = Serializer.serializeResponse(chunkResponse);
            client.write(buffer);
            start = end;
        }
    }
}