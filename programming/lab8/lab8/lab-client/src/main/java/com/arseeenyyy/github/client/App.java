package com.arseeenyyy.github.client;
import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


public class App extends Application {
    public static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Override
    public void start(Stage primaryStage) {
        try {
            Locale.setDefault(new Locale("ru","RU"));
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/Auth.fxml")));
            loader.setResources(ResourceBundle.getBundle("GuiLabels"));
            LOGGER.info("Starting application");
            Parent root = loader.load();
            primaryStage.setTitle("authorization");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            LOGGER.error("Error loading FXML file", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

