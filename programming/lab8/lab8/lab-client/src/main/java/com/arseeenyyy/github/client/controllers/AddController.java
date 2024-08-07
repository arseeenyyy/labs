package com.arseeenyyy.github.client.controllers;

import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.client.util.LocaleManager;
import com.arseeenyyy.github.common.models.Color;
import com.arseeenyyy.github.common.models.Coordinates;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.models.DragonCave;
import com.arseeenyyy.github.common.util.Request;
import com.arseeenyyy.github.common.util.RequestType;
import com.arseeenyyy.github.common.util.Response;
import com.arseeenyyy.github.common.util.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class AddController implements ChildController {
    private Client client = ApplicationClient.getClient();
    private User user = client.getUser();
    private MainController mainController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField nameField;

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField wingspanField;

    @FXML
    private MenuButton colorField;

    @FXML
    private TextField caveField;

    @FXML
    private Button submitButton;
    @FXML
    private Label addLabel;

    private Color selectedColor;

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
        for (MenuItem menuItem : colorField.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                selectedColor = Color.valueOf(menuItem.getText().toUpperCase());
                colorField.setText(menuItem.getText());
            });
        }

        submitButton.setOnAction(actionEvent -> {
            if (selectedColor == null || !validFields()) {
                return;
            }
            Dragon newDragon = new Dragon(
                    nameField.getText(),
                    new Coordinates(Integer.parseInt(xField.getText()), Float.parseFloat(yField.getText())),
                    Long.parseLong(ageField.getText()),
                    descriptionField.getText(),
                    Double.parseDouble(wingspanField.getText()),
                    selectedColor,
                    new DragonCave(Long.parseLong(caveField.getText())),
                    user.getId()
            );
            Request request = new Request.RequestBuilder()
                    .setCommand("add")
                    .setUser(user)
                    .setDragon(newDragon)
                    .setType(RequestType.COMMAND)
                    .build();
            Response response = client.sendAndReceive(request);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        });
    }

    private void setErrorStyle(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private void resetStyle(Control control) {
        control.setStyle("");
    }

    private boolean isValidXCoordinate(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean isValidYCoordinate(String value) {
        try {
            Float y = Float.parseFloat(value);
            return (Float.compare(y, -469) > 0);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean isValidLongField(String value) {
        try {
            long longField = Long.parseLong(value);
            return (Long.compare(longField, 0) > 0);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean isValidDoubleField(String value) {
        try {
            double doubleField = Double.parseDouble(value);
            return (Double.compare(doubleField, 0) > 0);
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private boolean validFields() {
        boolean isValid = true;

        if (nameField.getText().isEmpty()) {
            setErrorStyle(nameField);
            isValid = false;
        } else resetStyle(nameField);
        if (!isValidXCoordinate(xField.getText())) {
            setErrorStyle(xField);
            isValid = false;
        } else resetStyle(xField);
        if (!isValidYCoordinate(yField.getText())) {
            setErrorStyle(yField);
            isValid = false;
        } else resetStyle(yField);
        if (!isValidLongField(ageField.getText())) {
            setErrorStyle(ageField);
            isValid = false;
        } else resetStyle(ageField);
        if (descriptionField.getText().isEmpty()) {
            setErrorStyle(descriptionField);
            isValid = false;
        } else resetStyle(descriptionField);
        if (!isValidDoubleField(wingspanField.getText())) {
            setErrorStyle(wingspanField);
            isValid = false;
        } else resetStyle(wingspanField);
        if (!isValidLongField(caveField.getText())) {
            setErrorStyle(caveField);
            isValid = false;
        } else resetStyle(caveField);
        return isValid;
    }

    public void setLocaleText() {

        addLabel.setText(resources.getString("add"));
        nameField.setPromptText(resources.getString("name"));
        xField.setPromptText(resources.getString("coordinate_x"));
        yField.setPromptText(resources.getString("coordinate_y"));
        ageField.setPromptText(resources.getString("age"));
        descriptionField.setPromptText(resources.getString("description"));
        wingspanField.setPromptText(resources.getString("wingspan"));
        colorField.setText(resources.getString("color"));
        caveField.setPromptText(resources.getString("number_of_treasures"));
        submitButton.setText(resources.getString("OK"));
    }


}
