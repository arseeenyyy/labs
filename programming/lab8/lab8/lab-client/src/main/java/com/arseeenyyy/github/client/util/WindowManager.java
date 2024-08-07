package com.arseeenyyy.github.client.util;

import com.arseeenyyy.github.client.controllers.ChildController;
import com.arseeenyyy.github.client.controllers.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class WindowManager {
    private static final Object resourceBundleLock = new Object();
    public static <T extends ChildController> T switchScene(String fxmlFile, MainController mainController) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(WindowManager.class.getResource(fxmlFile));
        synchronized (resourceBundleLock) {
            loader.setResources(ResourceBundle.getBundle("GuiLabels"));
        }
        loader.load();
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        T loadedController = loader.getController();
        loadedController.setParent(mainController);
        return loadedController;
    }
}