package com.arseeenyyy.github.client.network;

import com.arseeenyyy.github.client.util.CommandBuilder;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.RequestType;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.common.util.User;
import com.arseeenyyy.github.common.util.serializers.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client {
    private SocketChannel channel;
    private InetSocketAddress address;
    private int port;
    private User user;
    public static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
    private boolean isUpdating = true;
    private final Lock lock = new ReentrantLock();
    private final int MAX_ATTEMPTS = 5;
//    private final int CHUNK_SIZE = 100;

    public Client(int port) {
        this.port = port;
        address = new InetSocketAddress(port);
    }

    public void connect() {
        try {
            channel = SocketChannel.open();
            channel.connect(address);
            channel.configureBlocking(false);
            LOGGER.info("connection successful");
        }catch (IOException exception) {
            System.exit(0);
        }
    }

    public void disconnect() {
        this.user = null;
        LOGGER.info("user disconnected");
    }

    public void sendRequest(Request request) throws IOException {
        lock.lock();
        try {
            if (channel == null || !channel.isConnected()) {
                return;
            }
            ByteBuffer buffer = Serializer.serializeRequest(request);
            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }
        }finally {
            lock.unlock();
        }
    }
    public Response getResponse() {
        lock.lock();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(channel.socket().getReceiveBufferSize());
            while (channel.read(buffer) == 0) {
                if (!channel.isOpen()) {
                    throw new IOException("connection closed unexpectedly");
                }
            }
            Response response = Serializer.deserializeResponse(buffer);
            if (response == null) throw new IOException("connection closed unexpectedly");
            buffer.clear();
            return response;
        }catch (IOException exception) {
//            reconnect();
        }catch (ClassNotFoundException exception) {

        }
        finally {
            lock.unlock();
        }
        return null;
    }
    public Response sendAndReceive(Request request) {
        try {
            sendRequest(request);
            Response response = getResponse();
            return response;
        }catch (IOException exception) {
//            reconnect();
        }
        return null;
    }
    public ArrayList<Dragon> getCollectionFromDB() {
        Request request = new Request.RequestBuilder()
                .setType(RequestType.COMMAND)
                .setCommand("show")
                .setUser(user)
                .build();
            Response response = sendAndReceive(request);
            if (response.getDragonList() == null) return new ArrayList<>();
            else return response.getDragonList();
    }

//    public ArrayList<Dragon> getCollectionFromDB() {
//        ArrayList<Dragon> fullCollection = new ArrayList<>();
//        boolean isLastChunk = false;
//
//        Request request = new Request.RequestBuilder()
//                .setType(RequestType.COMMAND)
//                .setUser(user)
//                .setCommand("show")
//                .build();
//
//        try {
//            sendRequest(request);
//
//            while (!isLastChunk) {
//                Response response = getResponse();
//                if (response.getDragonList().size() == 0) {return fullCollection;}
//                if (response != null && response.getDragonList() != null) {
//                    List<Dragon> chunk = response.getDragonList();
//                    fullCollection.addAll(chunk);
//                    isLastChunk = chunk.isEmpty() || chunk.size() < CHUNK_SIZE; // предположим, что CHUNK_SIZE - это размер чанка
//                } else {
//                    isLastChunk = true;
//                }
//            }
//        } catch (IOException | ClassNotFoundException exception) {
//            LOGGER.error("Error during collection retrieval: {}", exception.getMessage(), exception);
//        }
//
//        return fullCollection;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//    public void reconnect() {
//        int attempts = 0;
//        while (attempts != MAX_ATTEMPTS) {
//            try {
//                channel = SocketChannel.open();
//                channel.connect(address);
//                channel.configureBlocking(false);
//                return;
//            }catch (IOException exception) {
//                attempts ++;
//                LOGGER.error("reconnecting\nremaining attempts: " + (MAX_ATTEMPTS - attempts));
//                try {
//                    Thread.sleep(5000);
//                }catch (InterruptedException exception1) {}
//            }
//        }
//    }

    public void setUpdating(boolean isUpdating) {
        this.isUpdating = isUpdating;
    }
    public boolean getIsUpdating() {
        return isUpdating;
    }

}