package arseeenyyy.com.github.common.util.serializers;


import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.Response;

import java.io.*;
import java.nio.ByteBuffer;

public class Serializer {


    public static ByteBuffer serializeRequest(Request request) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(request);
            oos.flush();
            return ByteBuffer.wrap(baos.toByteArray());
        }
    }

    public static Response deserializeResponse(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        Response response = null;
        buffer.flip();
        if (buffer.hasRemaining()) {
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()))) {
                response = (Response) ois.readObject();
            }
        }
        return response;
    }


    public static Request deserializeRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        buffer.flip();
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()))) {
            Request request = (Request) ois.readObject();
            return request;
        }
    }

    public static ByteBuffer serializeResponse(Response response) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(response);
            oos.flush();
            return ByteBuffer.wrap(baos.toByteArray());
        }
    }
}
