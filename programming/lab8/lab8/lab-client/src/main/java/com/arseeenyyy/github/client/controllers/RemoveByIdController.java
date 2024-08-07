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

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class RemoveByIdController implements ChildController {
    private Client client = ApplicationClient.getClient();
    private User user = client.getUser();
    private int userId = user.getId();
    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    private ArrayList<Dragon> dragonList;

    @FXML
    private MenuButton idChoiceField;

    @FXML
    private Button submitButton;

    @FXML
    private Label errorMessageLabel;
    @FXML
    private Label removeLabel;

    private Integer dragonId;

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
        dragonList = new ArrayList<>(MainController.getDragonList());
        idChoiceField.getItems().clear();

        for (Dragon dragon : dragonList) {
            if (dragon.getUserId() == userId) {
                var menuItem = new MenuItem(String.valueOf(dragon.getId()));
                menuItem.setOnAction(actionEvent -> {
                    dragonId = Integer.parseInt(menuItem.getText());
                    idChoiceField.setText(menuItem.getText());
                });
                idChoiceField.getItems().add(menuItem);
            }
        }

        submitButton.setOnAction(actionEvent -> {
            if (dragonId == null) {
                errorMessageLabel.setText("Please select a Dragon ID.");
            } else {
                Request request = new Request.RequestBuilder()
                        .setCommand("remove_by_id " + dragonId)
                        .setUser(user)
                        .setType(RequestType.COMMAND)
                        .build();
                Response response = client.sendAndReceive(request);
                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();
            }
        });
    }
    public void setLocaleText() {
        removeLabel.setText(resources.getString("remove_by_id"));
        submitButton.setText(resources.getString("OK"));

    }
}
