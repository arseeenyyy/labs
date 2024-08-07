package com.arseeenyyy.github.common.util;

import com.arseeenyyy.github.common.interfaces.Builder;
import com.arseeenyyy.github.common.models.Dragon;

import java.io.Serializable;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private String messageToResponse;
    private ArrayList<Dragon> dragonList;
    private Dragon dragon;
    private ExecutionCode executionCode;

    public Response(ResponseBuilder builder) {
        this.messageToResponse = builder.messageToResponse;
        this.dragonList = builder.dragonList;
        this.dragon = builder.dragon;
        this.executionCode = builder.executionCode;
    }

    public String getMessageToResponse() {
        return messageToResponse;
    }
    public ExecutionCode getExecutionCode() {
        return executionCode;
    }

    public ArrayList<Dragon> getDragonList() {
        return dragonList;
    }
    public Dragon getDragon() {
        return dragon;
    }
    @Override
    public String toString() {
        return "[message: " + messageToResponse + "\ndragonList: " + dragonList + "\ndragon: " + dragon + "\nexecution code: " + executionCode + "]";
    }


    public static class ResponseBuilder implements Builder<Response>, Serializable {
        private String messageToResponse;
        private ArrayList<Dragon> dragonList;
        private Dragon dragon;
        private ExecutionCode executionCode;

        public ResponseBuilder setMessageToResponse(String messageToResponse) {
            this.messageToResponse = messageToResponse;
            return this;
        }
        public ResponseBuilder setDragonList(ArrayList<Dragon> dragonList) {
            this.dragonList = dragonList;
            return this;
        }
        public ResponseBuilder setDragon(Dragon dragon) {
            this.dragon = dragon;
            return this;
        }
        public ResponseBuilder setExecutionCode(ExecutionCode executionCode) {
            this.executionCode = executionCode;
            return this;
        }
        public Response build() {
            return new Response(this);
        }
    }
}