package com.arseeenyyy.github.server.handlers;

import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.serializers.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.function.Supplier;

public class RequestReader implements Supplier<Request> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestReader.class);
    private final SelectionKey key;

    public RequestReader(SelectionKey key) {
        this.key = key;
    }

    @Override
    public Request get() {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);
        try {
            int bytesRead = client.read(buffer);
            if (bytesRead == -1) {
                LOGGER.info("client disconnected" + client.getRemoteAddress());
                client.close();
                key.cancel();
                return null;
            }
            buffer.flip();
            Request request = Serializer.deserializeRequest(buffer);
            LOGGER.info("new request client\n-------------\n" + request.getCommand() + "\n" + request.getType() + "\n" + request.getUser() + "\n-------------");
            return request;
        }catch (IOException | ClassNotFoundException exception) {
            LOGGER.error("error during reading request from server");
        }
        try {
            client.close();
        }catch (IOException exception) {
            LOGGER.error("Error during closing client");
        }
        key.cancel();

        return null;
    }
}