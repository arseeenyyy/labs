package arseeenyyy.com.github.common.util;

import arseeenyyy.com.github.common.models.Dragon;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private String messageToResponse;
    private ArrayList<Dragon> dragonList;
    private Dragon dragon;
    private ExecutionCode executionCode;

    public Response(String messageToResponse, ArrayList<Dragon> dragonList, Dragon dragon, ExecutionCode executionCode) {
        this.messageToResponse = messageToResponse;
        this.dragonList = dragonList;
        this.dragon = dragon;
        this.executionCode = executionCode;
    }

    public Response(String messageToResponse, ExecutionCode executionCode) {
        this.messageToResponse = messageToResponse;
        this.executionCode = executionCode;
    }
    public Response(String messageToResponse, Dragon dragon, ExecutionCode executionCode) {
        this.messageToResponse = messageToResponse;
        this.dragon = dragon;
        this.executionCode = executionCode;
    }
    public Response(Dragon dragon, ExecutionCode executionCode) {
        this.dragon = dragon;
        this.executionCode = executionCode;
    }
    public Response(ArrayList<Dragon> dragonList, ExecutionCode executionCode) {
        this.dragonList = dragonList;
        this.executionCode = executionCode;
    }

    public void setMessageToResponse(String messageToResponse) {
        this.messageToResponse = messageToResponse;
    }
    public void setDragonList(ArrayList<Dragon> dragonList) {
        this.dragonList = dragonList;
    }
    public void setDragon(Dragon dragon) {
        this.dragon = dragon;
    }
    public void setExecutionCode(ExecutionCode executionCode) {
        this.executionCode = executionCode;
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
    public String getData() {
        return (messageToResponse == null ? "" : messageToResponse + "\n") + (dragon == null ? "" : dragon.toString() + "\n") + (dragonList == null ? "" : dragonList.toString());
    }

    @Override
    public String toString() {
        return getData().trim();
    }
}