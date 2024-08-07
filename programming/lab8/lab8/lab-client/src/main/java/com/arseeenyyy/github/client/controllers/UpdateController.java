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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateController implements ChildController {
    private final Client client = ApplicationClient.getClient();
    private User user = client.getUser();
    private int userId = user.getId();
    private MainController mainController;

    @FXML
    private ResourceBundle resources;
    private ArrayList<Dragon> dragonList;
    @FXML
    private Label updateLabel;

    @FXML
    private MenuButton idChoiceField;

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

    private Integer dragonId;
    private Color selectedColor;
    @FXML
    public void initialize() {
        resources = ResourceBundle.getBundle("GuiLabels", LocaleManager.getLocale());
        setLocaleText();
        updateMenuButtonItems();
        setupSubmitButton();
    }

    public void setParent(MainController mainController) {
        this.mainController = mainController;
        this.dragonList = MainController.getDragonList();
    }

    private void setIdChoiceField() {
        if (dragonList == null) {
            return;
        }

        idChoiceField.getItems().clear();
        for (Dragon dragon : dragonList) {
            if (dragon.getUserId() == userId) {
                MenuItem menuItem = new MenuItem(String.valueOf(dragon.getId()));
            }
        }
    }

    public void updateMenuButtonItems() {
        if (dragonList == null) {
            return;
        }

        idChoiceField.getItems().clear();

        for (Dragon dragon : dragonList) {
            if (dragon.getUserId() == userId) {
                MenuItem menuItem = new MenuItem(String.valueOf(dragon.getId()));
                menuItem.setOnAction(actionEvent -> {
                    dragonId = Integer.parseInt(menuItem.getText());
                    idChoiceField.setText(menuItem.getText());
                });
                idChoiceField.getItems().add(menuItem);
            }
        }

        for (MenuItem menuItem : colorField.getItems()) {
            menuItem.setOnAction(actionEvent -> {
                selectedColor = Color.valueOf(menuItem.getText().toUpperCase());
                colorField.setText(menuItem.getText());
            });
        }

        if (idChoiceField.getItems().isEmpty()) {
            idChoiceField.setText("No IDs available");
        } else {
            idChoiceField.setText("Select ID");
        }
    }
    private void setupSubmitButton() {
        submitButton.setOnAction(event -> handleSubmitButtonAction());
    }

    @FXML
    private void handleSubmitButtonAction() {
        if (dragonId == null || selectedColor == null || !validFields()) {
            return;
        }
        Dragon updatedDragon = new Dragon(dragonId,
                nameField.getText(),
                new Coordinates(Integer.parseInt(xField.getText()),
                        Float.parseFloat(yField.getText())),
                Long.parseLong(ageField.getText()),
                descriptionField.getText(),
                Double.parseDouble(wingspanField.getText()),
                selectedColor,
                new DragonCave(Long.parseLong(caveField.getText())),
                user.getId());
        Request request = new Request.RequestBuilder()
                .setCommand("update")
                .setUser(user)
                .setDragon(updatedDragon)
                .setType(RequestType.COMMAND)
                .build();
        Response response = client.sendAndReceive(request);
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
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
        }catch (NumberFormatException exception) {
            return false;
        }
    }
    private boolean isValidYCoordinate(String value) {
        try {
            Float y = Float.parseFloat(value);
            return (Float.compare(y, -461) > 0);
        }catch (NumberFormatException exception) {
            return false;
        }
    }
    private boolean isValidLongField(String value) {
        try {
            long longField = Long.parseLong(value);
            return (Long.compare(longField, 0) > 0);
        }catch (NumberFormatException exception) {
            return false;
        }
    }
    private boolean isValidDoubleField(String value) {
        try {
            double doubleField = Double.parseDouble(value);
            return (Double.compare(doubleField, 0) > 0);
        }catch (NumberFormatException exception) {
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
    public void setDragon(Dragon dragon) {
        idChoiceField.getItems().clear();

        MenuItem menuItem = new MenuItem(String.valueOf(dragon.getId()));
        menuItem.setOnAction(actionEvent -> {
            dragonId = dragon.getId();
            idChoiceField.setText(menuItem.getText());
        });
        idChoiceField.getItems().add(menuItem);
        idChoiceField.setText(menuItem.getText());
        nameField.setText(dragon.getName());
        xField.setText(String.valueOf(dragon.getCoordinates().getX()));
        yField.setText(String.valueOf(dragon.getCoordinates().getY()));
        ageField.setText(String.valueOf(dragon.getAge()));
        descriptionField.setText(dragon.getDescription());
        wingspanField.setText(String.valueOf(dragon.getWingspan()));
        colorField.setText(dragon.getColor().toString());
        caveField.setText(String.valueOf(dragon.getCave().getNumberOfTreasures()));
    }
    public Button getSubmitButton() {
        return submitButton;
    }
    public void setLocaleText() {
        updateLabel.setText(resources.getString("update"));
        idChoiceField.setText(resources.getString("id"));
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