package arseeenyyy.com.github.client.regs;

import arseeenyyy.com.github.common.exceptions.WrongArgumentException;
import arseeenyyy.com.github.common.util.Request;
import arseeenyyy.com.github.common.util.RequestType;

import java.io.Console;
import java.util.Scanner;

public class RegistrationModule {
    private final Scanner scanner;
    Console console = System.console();
    public RegistrationModule(Scanner scanner) {
        this.scanner = scanner;
    }
    public Request askForRegistration() {
        System.out.print("do u have an account[y/n]:");
        while (true) {
            try {
                String line = scanner.nextLine().trim().toLowerCase();
                switch (line) {
                    case ("y"):
                        return loginUser();
                    case("n"):
                        return registerUser();
                    default:
                        System.out.println("unknown option. Please enter 'y' or 'n'");
                }
            }catch (Exception exception) {}
        }
    }
    private Request loginUser() {
        System.out.print("---------\nLOGGING\n---------\n");
        String login;
        String password;
        while(true) {
            try {
                System.out.print("enter login [>5symbols]: ");
                login = scanner.nextLine().trim();
                if (login.length() < 4) throw new WrongArgumentException();
                break;
            }catch (WrongArgumentException exception) {}
        }
        while (true) {
            try {
                System.out.print("enter password [>5symbols]: ");
                char[] passwd = console.readPassword();
                password = new String(passwd);
//               password = scanner.nextLine().trim();
                if (password.length() < 4) throw new WrongArgumentException();
                break;
            }catch (WrongArgumentException exception) {}
        }
        return new Request(login, password, RequestType.LOGIN);
    }
    private Request registerUser() {
        System.out.print("---------\nREGISTRATION\n---------\n");
        String login;
        String password;
        while(true) {
            try {
                System.out.print("enter login [>5symbols]: ");
                login = scanner.nextLine().trim();
                if (login.length() < 4) throw new WrongArgumentException();
                break;
            }catch (WrongArgumentException exception) {}
        }
        while (true) {
            try {
                System.out.print("enter password [>5symbols]: ");
                password = scanner.nextLine().trim();
                if (password.length() < 4) throw new WrongArgumentException();
                break;
            }catch (WrongArgumentException exception) {}
        }
        return new Request(login, password, RequestType.REGISTRATION);
    }
}