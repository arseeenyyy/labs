package com.arseeenyyy.github.server.app;

import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.common.util.serializers.Serializer;
import com.arseeenyyy.github.server.db.DataBaseManager;
import com.arseeenyyy.github.server.handlers.RequestExecutor;
import com.arseeenyyy.github.server.handlers.RequestReader;
import com.arseeenyyy.github.server.handlers.ResponseSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.arseeenyyy.github.server.managers.CommandExecutor;
import com.arseeenyyy.github.server.managers.DragonManager;
import com.arseeenyyy.github.server.multithreading.MultithreadingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {
    private InetSocketAddress address;
    private ServerSocketChannel serverSocketChannel;
    private volatile Selector selector;
    private DataBaseManager dbManager;
    private DragonManager dragonManager;
    private CommandExecutor executor;
    private boolean isRunning = true;

    public static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private final Set<SelectionKey> workingKeys = Collections.synchronizedSet(new HashSet<>());

    public Server(int port, DataBaseManager dbManager, DragonManager dragonManager, CommandExecutor executor) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        this.address = new InetSocketAddress(port);
        serverSocketChannel.bind(address);
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        this.dbManager = dbManager;
        this.dragonManager = dragonManager;
        this.executor = executor;
    }

    public void init() {
        Connection connection = dbManager.connectToDatabase();
        if (connection == null) {
            LOGGER.error("Unable to connect to database");
            System.exit(0);
        }
    }

    public void start() {
        LOGGER.info("Server is available");
        try {
            new Thread(() -> {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                while (isRunning) {
                    try {
                        String input = consoleReader.readLine();
                        if (input.equals("exit")) {
                            LOGGER.info("Closing server program...");
                            System.exit(0);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception exception) {}

        while (true) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isValid() && !workingKeys.contains(key)) {
                        if (key.isAcceptable()) {
                            accept(key);
                        } else if (key.isReadable()) {
                            workingKeys.add(key);
                            processKey(key);
                        }
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocket = (ServerSocketChannel) key.channel();
        SocketChannel client = serverSocket.accept();
        if (client != null) {
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            LOGGER.info("Client connected: " + client.getRemoteAddress());
        } else {
            LOGGER.warn("Connection was accepted from the client but the channel couldn't be configured");
        }
    }

    private void processKey(SelectionKey key) {
        Supplier<Request> requestReader = new RequestReader(key);
        Function<Request, Response> requestExecutor = new RequestExecutor(executor, dbManager);
        Consumer<Response> responseSender = new ResponseSender(key, workingKeys);

        CompletableFuture
                .supplyAsync(requestReader, MultithreadingManager.getRequestThreadPool())
                .thenApplyAsync(requestExecutor, MultithreadingManager.getProcessingThreadPool())
                .thenAcceptAsync(responseSender, MultithreadingManager.getResponseThreadPool())
                .whenComplete((result, throwable) -> workingKeys.remove(key));
    }
}
