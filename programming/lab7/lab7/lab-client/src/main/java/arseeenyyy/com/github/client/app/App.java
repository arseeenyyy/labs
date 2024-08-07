package arseeenyyy.com.github.client.app;

public class App {
    public static void main(String[] args) {
        try {
            if (args[0].isEmpty()) {
                System.err.println("wrong args");
                System.exit(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Client.connect(Integer.parseInt(args[0]));
            Client.start();
        }catch (NumberFormatException exception) {
            System.err.println("wrong input port");
            System.exit(0);
        }
    }
}