package com.arseeenyyy.github.client.script;
import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.common.exceptions.RecursionException;
import com.arseeenyyy.github.common.exceptions.WrongArgumentException;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.RequestType;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.common.util.User;

import java.io.*;
import java.util.Stack;

public class ScriptExecutor {
    private static Stack<File> stack = new Stack<>();
    private static Client client = ApplicationClient.getClient();
    private static User user = client.getUser();

    public static void executeScript(String args) {
        String[] userCommand = args.contains(" ") ? args.split(" ", 2) : new String[]{args, ""};
        try {
            if (userCommand[1].isEmpty()) throw new WrongArgumentException();
            File file = new File(userCommand[1]);
            if (!file.exists()) throw new FileNotFoundException();
            if (!file.canRead()) throw new SecurityException();
            String currentLine;
            if (stack.contains(file)) throw new RecursionException();
            stack.push(file);

            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bis))) {

                while ((currentLine = bufferedReader.readLine()) != null) {
                    try {
                        if (currentLine.toLowerCase().contains("add")) {
                            String[] argsForDragon = new String[8];
                            for (int i = 0; i < argsForDragon.length; i++) {
                                String arg;
                                if ((arg = bufferedReader.readLine()) != null) {
                                    argsForDragon[i] = arg;
                                }
                            }
                            Dragon dragon = new Dragon(argsForDragon);
                            Request request = new Request.RequestBuilder()
                                    .setDragon(dragon)
                                    .setCommand(currentLine)
                                    .setType(RequestType.COMMAND)
                                    .setUser(user)
                                    .build();

                            client.sendAndReceive(request);
                        } else if (currentLine.toLowerCase().contains("update")) {
                            String[] argsForDragon = new String[9];
                            for (int i = 0; i < argsForDragon.length; i++) {
                                String arg;
                                if ((arg = bufferedReader.readLine()) != null) {
                                    argsForDragon[i] = arg;
                                }
                            }
                            Dragon dragon = new Dragon(argsForDragon);
                            Request request = new Request.RequestBuilder()
                                    .setDragon(dragon)
                                    .setCommand(currentLine)
                                    .setType(RequestType.COMMAND)
                                    .setUser(user).
                                    build();
                            client.sendAndReceive(request);
                        } else if (currentLine.toLowerCase().contains("execute_script")) {
                            String[] argsForCommand = currentLine.contains(" ") ? currentLine.split(" ", 2) : new String[]{currentLine, ""};
                            if (argsForCommand[1].isEmpty()) throw new WrongArgumentException();
                            File fileNew = new File(argsForCommand[1]);
                            if (!fileNew.canRead() || !file.exists()) throw new FileNotFoundException();

                            if (stack.contains(fileNew)) throw new RecursionException();
                            else {
                                executeScript(currentLine);
                            }
                        } else if (currentLine.trim().equals(""))
                            continue;
                        else {
                            Request request = new Request.RequestBuilder()
                                    .setCommand(currentLine)
                                    .setType(RequestType.COMMAND)
                                    .setUser(user)
                                    .build();
                            Response response = client.sendAndReceive(request);
                        }
                    } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException exception) {
                        System.err.println("cannot create new dragon with arguments from script!!");
                    } catch (RecursionException exception) {
                        System.err.println("recursion detected and was skipped");
                    }

                }
            } catch (IOException exception) {
                System.err.println("io exception");
            } catch (WrongArgumentException exception) {
                exception.printStackTrace();
            } finally {
                stack.pop();
            }

        } catch (WrongArgumentException exception) {
//            exception.printStackTrace();
        } catch (FileNotFoundException exception) {
            System.err.println("file wasn't found");
        } catch (SecurityException exception) {
            System.err.println("error with file roots");
        } catch (RecursionException exception) {
            System.err.println("recursion detected");
        }
    }
}