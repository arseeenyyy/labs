package arseeenyyy.com.github.client.app;

import arseeenyyy.com.github.client.regs.RegistrationModule;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.models.creation.NameValidator;
import arseeenyyy.com.github.common.util.ExecutionCode;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.RequestType;
import arseeenyyy.com.github.common.util.Response;
import arseeenyyy.com.github.common.util.serializers.Serializer;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static SocketChannel channel;
    private static InetSocketAddress address;
    private static LinkedList<Request> requestQueue = new LinkedList<>();
    private static String login;
    private static String password;
    private static Scanner scanner = new Scanner(System.in);

    public static void connect(int port) {
        address = new InetSocketAddress(port);
        int attempts = 0;
        final int MAX_ATTEMPTS = 5;

        while (attempts < MAX_ATTEMPTS) {
            try {
                channel = SocketChannel.open(address);
                channel.configureBlocking(false);
                System.out.println("Connection success");

                reconnectAndResend();
                break;
            } catch (ConnectException e) {
                System.err.println("Unable to connect to server: " + e.getMessage());
                attempts++;
                System.err.println("Remaining attempts: " + (MAX_ATTEMPTS - attempts));
                try {
                    Thread.sleep(5000);  // Пауза перед следующей попыткой подключения
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    System.err.println("Connection attempt interrupted");
                    return;
                }
            } catch (IOException e) {
                System.err.println("IOException during connection attempt: " + e.getMessage());
                return;
            } catch (ClassNotFoundException e) {

            }
        }
        if (attempts == MAX_ATTEMPTS) {
            System.err.println("Max number of connection attempts reached");
            System.exit(0);
        }
    }

    public static void start() {
        try {
            while (true) {
                System.out.print(">");
                String command = scanner.nextLine().trim().toLowerCase();
                if (command.isEmpty()) continue;
                else if (command.equals("exit")) throw new WrongArgumentException();
                else if (command.contains("add")) {
                    Dragon dragon = NameValidator.dragonNameValidator(scanner);
                    Request request = new Request(command, dragon, RequestType.COMMAND, login, password);
                    sendRequest(request);
                    System.out.println(getResponse());
                }
                else if (command.contains("execute_script")) {
                    ScriptExecutor.executeScript(command, login, password);
                }
                else if (command.equals("quit")) {
                    register(scanner);
                }
                else {
                    Request request = new Request(command, RequestType.COMMAND, login, password);
                    sendRequest(request);
                    Response response = getResponse();
                    System.out.println("------------------\n" + response + "\n------------------");
                }
            }
        } catch (IOException e) {
            connect(address.getPort());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchElementException exception) {
            System.err.println("ctrl d pressed :3");
        } catch (SecurityException exception) {
            System.err.println("unable to read file:((");
        }catch (WrongArgumentException exception) {
            System.out.println("terminating client application...");
            System.exit(0);
        }
    }

    public static void sendRequest(Request request) throws IOException {
        requestQueue.add(request);
        if (channel == null || !channel.isConnected()) {
            connect(address.getPort());
            return;
        }
        ByteBuffer buffer = Serializer.serializeRequest(request);
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        requestQueue.remove(request);
    }

    public static Response getResponse() throws IOException, ClassNotFoundException {
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
    }

    private static void register(Scanner scanner) throws IOException, ClassNotFoundException {
        RegistrationModule registrationModule = new RegistrationModule(scanner);
        boolean isRegistered = false;
        while (!isRegistered) {
            Request registration = registrationModule.askForRegistration();
            if (registration != null) {
                sendRequest(registration);
                Response response = getResponse();
                System.out.println(response);
                if (response.getExecutionCode().equals(ExecutionCode.SUCCESS_REG)) {
                    isRegistered = true;
                    login = registration.getLogin();
                    password = registration.getPassword();
                    System.out.println("welcome!!!!!!!!!!!!!!");
                } else {
                    System.out.println("Registration/Login failed. Please try again");
                }
            }
        }
        start();
    }
    private static void reconnectAndResend() throws IOException, ClassNotFoundException {
            try {
                register(scanner);
            } catch (Exception exception) {
                System.err.println("Failed to resend a request after reconnecting: " + exception.getMessage());
            }
    }
}


