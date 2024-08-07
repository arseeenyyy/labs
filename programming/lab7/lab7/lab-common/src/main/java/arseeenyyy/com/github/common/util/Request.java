package arseeenyyy.com.github.common.util;

import arseeenyyy.com.github.common.models.Dragon;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private Dragon dragon;
    private RequestType type;
    private String login;
    private String password;


    public Request(String command, RequestType type, String login, String password) {
        this.command = command;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public Request(String command, Dragon dragon, RequestType type, String login, String password) {
        this.command = command;
        this.dragon = dragon;
        this.type = type;
        this.login = login;
        this.password = password;
    }

    public Request(String login, String password, RequestType type) {
        this.password = password;
        this.login = login;
        this.type = type;
    }
    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public RequestType getType() {
        return type;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public Dragon getDragon() {
        return dragon;
    }
    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
    public String getData() {
        return ((command == null ? "" : command + "\n") + (dragon == null ? "" : dragon.toString()));
    }

    @Override
    public String toString() {
        return "client request:\n" + getData();
    }
}