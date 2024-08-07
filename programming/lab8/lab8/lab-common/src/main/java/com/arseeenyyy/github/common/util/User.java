package com.arseeenyyy.github.common.util;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String login;
    private String password;

    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public int getId() {return id;}
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(int id) {this.id = id;}
}
