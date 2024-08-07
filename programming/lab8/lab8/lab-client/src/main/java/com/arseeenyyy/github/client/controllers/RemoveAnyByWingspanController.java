package com.arseeenyyy.github.client.controllers;

import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.client.util.LocaleManager;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.RequestType;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.common.util.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;


public class RemoveAnyByWingspanController implements ChildController {
    private Client client = ApplicationClient.getClient();
    User user = client.getUser();
    private int userId = user.getId();
    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    private ArrayList<Dragon> dragonList;

    @FXML
    private Label removeLabel;
    @FXML
    private MenuButton idChoiceField;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorMessageLabel;

    private Double wingspan;
    @FXML
    public void initialize() {
        resources = ResourceBundle.getBundle("GuiLabels", LocaleManager.getLocale());
        setLocaleText();
        updateMenuButtonItems();
    }
    public void setParent(MainController mainController) {
        this.mainController = mainController;
    }

    public void updateMenuButtonItems() {
        dragonList = MainController.getDragonList();
        idChoiceField.getItems().clear();

        for (Dragon dragon : dragonList) {
            if (dragon.getUserId() == userId) {
                var menuItem = new MenuItem(String.valueOf(dragon.getWingspan()));
                menuItem.setOnAction(actionEvent -> {
                    wingspan = Double.parseDouble(menuItem.getText());
                    idChoiceField.setText(menuItem.getText());
                });
                idChoiceField.getItems().add(menuItem);
            }
        }

        submitButton.setOnAction(actionEvent -> {
            if (wingspan == null) {
                errorMessageLabel.setText("Please select a Dragon wingspan.");
            } else {
                Request request = new Request.RequestBuilder()
                        .setCommand("remove_any_by_wingspan " + wingspan)
                        .setUser(user)
                        .setType(RequestType.COMMAND)
                        .build();
                Response response = client.sendAndReceive(request);
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            }
        });
    }
    public Button getSubmitButton() {
        return submitButton;
    }
    public void setLocaleText() {
        removeLabel.setText(resources.getString("remove_any_by_wingspan"));
        submitButton.setText(resources.getString("OK"));

    }
}
