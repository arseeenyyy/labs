package com.arseeenyyy.github.common.util;
import com.arseeenyyy.github.common.interfaces.Builder;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.RequestType;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private Dragon dragon;
    private RequestType type;
    private User user;

    public Request(RequestBuilder builder) {
        this.command = builder.command;
        this.dragon = builder.dragon;
        this.type = builder.type;
        this.user = builder.user;
    }
    public String getCommand() {
        return command;
    }
    public RequestType getType() {
        return type;
    }
    public Dragon getDragon() {
        return dragon;
    }
    public User getUser() {
        return user;
    }
    @Override
    public String toString() {
        return "[command: " + command + "\ndragon: " + dragon + "\ntype: " + type + "\nuser: " + user.getLogin() + " " + user.getPassword() + "]";
    }

    public static class RequestBuilder implements Builder<Request>, Serializable {
        private String command;
        private Dragon dragon;
        private RequestType type;
        private User user;

        public RequestBuilder setCommand(String command) {
            this.command = command;
            return this;
        }

        public RequestBuilder setDragon(Dragon dragon) {
            this.dragon = dragon;
            return this;
        }
        public RequestBuilder setType(RequestType type) {
            this.type = type;
            return this;
        }
        public RequestBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }

}