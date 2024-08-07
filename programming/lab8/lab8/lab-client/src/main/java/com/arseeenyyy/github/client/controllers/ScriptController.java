package com.arseeenyyy.github.client.controllers;

import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.client.script.ScriptExecutor;
import com.arseeenyyy.github.client.util.LocaleManager;
import com.arseeenyyy.github.common.util.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


public class ScriptController implements ChildController {
    private final Client client = ApplicationClient.getClient();
    private final User user = client.getUser();
    private MainController mainController;
    private List<File> currentFilesList = new ArrayList<>();
    private String pickedFile = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button submitButton;

    @FXML
    private MenuButton scriptMenu;
    @FXML
    private Label scriptLabel;

    @FXML
    public void initialize() {
        resources = ResourceBundle.getBundle("GuiLabels", LocaleManager.getLocale());
        setLocaleText();
    }

    public void setParent(MainController mainController) {
        this.mainController = mainController;
        updateMenuButtonItems();
        setupSubmitButton();
    }

    public void updateMenuButtonItems() {
        setCurrentFiles();
        scriptMenu.getItems().clear();
        for (File file : currentFilesList) {
            MenuItem menuItem = new MenuItem(file.getName());
            menuItem.setOnAction(event -> {
                pickedFile = file.getAbsolutePath();
                scriptMenu.setText(menuItem.getText());
                System.out.println("Picked file: " + pickedFile); // Debugging line
            });
            scriptMenu.getItems().add(menuItem);
        }
    }

    @FXML
    private void handleSubmitButtonAction() {
        if (pickedFile.isEmpty()) {
            System.out.println("No file picked!"); // Debugging line
            return;
        }
        client.setUpdating(false);
        System.out.println("Submitting file: " + pickedFile); // Debugging line
        new Thread(() -> {
           ScriptExecutor.executeScript("execute_script " + pickedFile);
           Platform.runLater(() -> {
               client.setUpdating(true);
           });
        }).start();
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    private void setCurrentFiles() {
        String currentFilePath = System.getProperty("user.dir");
        File directory = new File(currentFilePath);
        currentFilesList = Arrays.stream(directory.listFiles())
                .filter(File::isFile)
                .filter(File::canRead)
                .filter(file -> file.getName().endsWith(".txt"))
                .toList();

//        currentFilesList.forEach(file -> System.out.println("Found file: " + file.getName())); // Debugging line
    }

    private void setupSubmitButton() {
        submitButton.setOnAction(event -> handleSubmitButtonAction());
    }
    public void setLocaleText() {
        scriptLabel.setText(resources.getString("Script"));
        submitButton.setText(resources.getString("OK"));
        scriptMenu.setText(resources.getString("select_script_file"));
    }
}