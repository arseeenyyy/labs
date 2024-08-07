package arseeenyyy.com.github.server.handlers;

import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Response;
import arseeenyyy.com.github.common.util.serializers.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.function.Consumer;

public class ResponseSender implements Consumer<Response> {
    private final SelectionKey key;

    private final Set<SelectionKey> workingKeys;

    public ResponseSender(SelectionKey key, Set<SelectionKey> workingKeys) {
        this.key = key;
        this.workingKeys = workingKeys;
    }
    @Override
    public void accept(Response response) {
        SocketChannel client = (SocketChannel) key.channel();
        if (response == null) {
            response = new Response("idk", ExecutionCode.ERROR);
        }
        try {
            ByteBuffer buffer = Serializer.serializeResponse(response);
            client.write(buffer);
        }catch (IOException exception) {
            return;
        }
        workingKeys.remove(key);
    }
}
