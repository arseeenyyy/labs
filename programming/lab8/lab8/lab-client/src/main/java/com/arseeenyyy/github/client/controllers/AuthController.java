package com.arseeenyyy.github.client.controllers;
import com.arseeenyyy.github.client.App;
import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.client.util.LocaleManager;
import com.arseeenyyy.github.common.util.*;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.image.Kernel;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class AuthController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private TextField loginField;
    @FXML
    private Label auth;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button okButton;
    @FXML
    private CheckBox registerCheckBox;
    @FXML
    private Label messageLabel;
    @FXML
    private TextArea responseTextArea;
    @FXML
    private Button UAbutton;
    @FXML
    private Button RUbutton;
    @FXML
    private Button CZbutton;
    @FXML
    private Button ESbutton;
    private Client client = ApplicationClient.getClient();
    private User user;

    @FXML
    public void initialize() {
        client.connect();
        okButton.setOnAction(this::handleOkButton);
        RUbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("ru", "RU"));
            setLocaleText();
        });
        UAbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("uk", "UA"));
            setLocaleText();
        });
        CZbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("cs", "CZ"));
            setLocaleText();
        });
        ESbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("es", "DO"));
            setLocaleText();
        });
    }

    private void handleOkButton(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();
        boolean isRegistration = registerCheckBox.isSelected();
        user = new User(login, password);
        Request request = new Request.RequestBuilder()
                .setUser(user)
                .setType(isRegistration ? RequestType.REGISTRATION : RequestType.LOGIN)
                .build();
        Response response = client.sendAndReceive(request);
        handleResponse(response);
    }

    private void handleResponse(Response response) {
        if (response.getExecutionCode() == ExecutionCode.SUCCESS_REG) {
            user.setId(Integer.parseInt(response.getMessageToResponse()));
            client.setUser(user);
            openMainWindow();
        } else {
            responseTextArea.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: red;");
            responseTextArea.setText("registration/login_error");
        }
    }

    private void handleError(Throwable throwable) {
        responseTextArea.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: red;");
        throwable.printStackTrace();
    }

    private void openMainWindow() {
        try {
            Stage currentStage = (Stage) okButton.getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
            loader.setResources(ResourceBundle.getBundle("GuiLabels"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Window");
            stage.show();
            stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setLocaleText() {
        resources = ResourceBundle.getBundle("GuiLabels", LocaleManager.getLocale());
        auth.setText(resources.getString("authorization"));
        loginField.setPromptText(resources.getString("login"));
        passwordField.setPromptText(resources.getString("password"));
        okButton.setText(resources.getString("OK"));
        registerCheckBox.setText(resources.getString("register"));
    }
}
