package arseeenyyy.com.github.client.app;

import arseeenyyy.com.github.common.exceptions.RecursionException;
import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.models.Dragon;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.RequestType;

import java.io.*;
import java.util.Stack;

public class ScriptExecutor {
    private static Stack<File> stack = new Stack<>();

    public static void executeScript(String args, String login, String password) {
        String[] userCommand = args.contains(" ") ? args.split(" ", 2) : new String[] {args, ""};
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
                            Client.sendRequest(new Request(currentLine, dragon, RequestType.COMMAND, login, password));
                            Client.getResponse();
                        }
                        else if (currentLine.toLowerCase().contains("update")) {
                            String[] argsForDragon = new String[9];
                            for (int i = 0; i < argsForDragon.length; i++) {
                                String arg;
                                if ((arg = bufferedReader.readLine()) != null) {
                                    argsForDragon[i] = arg;
                                }
                            }
                            Dragon dragon = new Dragon(argsForDragon);
                            Client.sendRequest(new Request(currentLine, dragon, RequestType.COMMAND, login, password));
                            Client.getResponse();
                        }
                        else if (currentLine.toLowerCase().contains("execute_script")) {
                            String[] argsForCommand = currentLine.contains(" ") ? currentLine.split(" ", 2) : new String[]{currentLine, ""};
                            if (argsForCommand[1].isEmpty()) throw new WrongArgumentException();
                            File fileNew = new File(argsForCommand[1]);
                            if (!fileNew.canRead() || !file.exists()) throw new FileNotFoundException();

                            if (stack.contains(fileNew)) throw new RecursionException();
                            else {
                                executeScript(currentLine, login, password);
                            }
                        }
                        else if (currentLine.trim().equals(""))
                            continue;
                        else {
                            Client.sendRequest(new Request(currentLine, RequestType.COMMAND, login, password));
                            Client.getResponse();
                        }
                    }catch (IllegalArgumentException | ArrayIndexOutOfBoundsException exception) {
                        System.err.println("cannot create new dragon with arguments from script!!");
                    }catch (RecursionException exception) {
                        System.err.println("recursion detected and was skipped");
                    }

                }
            }catch (IOException exception) {
            }catch (ClassNotFoundException exception) {
            }catch (WrongArgumentException exception) {
            } finally {
                stack.pop();
            }

        }catch (WrongArgumentException exception) {
        }catch (FileNotFoundException exception) {
        }catch (SecurityException exception) {
        }catch (RecursionException exception) {
            System.err.println("recursion detected");
        }
    }

}
