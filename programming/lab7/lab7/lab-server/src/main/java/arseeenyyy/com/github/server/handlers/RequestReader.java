package arseeenyyy.com.github.server.handlers;

import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.serializers.Serializer;
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
            client.read(buffer);
            Request request = Serializer.deserializeRequest(buffer);
            LOGGER.info("request from server: " + request);
            return request;
        }catch (IOException | ClassNotFoundException exception) {
            LOGGER.error("error during reading request from server");
        }
        return null;
    }
}
