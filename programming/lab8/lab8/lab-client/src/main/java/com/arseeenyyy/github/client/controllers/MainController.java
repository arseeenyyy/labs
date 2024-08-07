package com.arseeenyyy.github.client.controllers;

import com.arseeenyyy.github.client.App;
import com.arseeenyyy.github.client.network.ApplicationClient;
import com.arseeenyyy.github.client.network.Client;
import com.arseeenyyy.github.client.util.CommandBuilder;
import com.arseeenyyy.github.client.util.LocaleManager;
import com.arseeenyyy.github.client.util.WindowManager;
import com.arseeenyyy.github.common.models.Color;
import com.arseeenyyy.github.common.models.Dragon;
import com.arseeenyyy.github.common.util.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainController {

    private Client client = ApplicationClient.getClient();
    private User user = client.getUser();
    private final CommandBuilder commandBuilder = new CommandBuilder();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Lock lock = new ReentrantLock();

    private static ArrayList<Dragon> dragonList = new ArrayList<>();

    public static ArrayList<Dragon> getDragonList() {
        return dragonList;
    }
    private double animationProgress = 0.0;

    private RemoveByIdController removeByIdController;
    private RemoveAnyByWingspanController removeAnyByWingspanController;
    private UpdateController updateController;
    private AddController addController;
    private AddIfMaxController addIfMaxController;
    private ScriptController scriptController;
    private AuthController authController;

    @FXML
    private Label loginField;
//    @FXML
//    private Button logoutButton;
    @FXML
    private Button scriptButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Menu menu;
    @FXML
    private TextArea dragonInfo;
    //кнопочки команд
    @FXML
    private MenuItem addCommand;
    @FXML
    private MenuItem addIfMaxCommand;
    @FXML
    private MenuItem clearCommand;
    @FXML
    private MenuItem infoCommand;
    @FXML
    private MenuItem historyCommand;
    @FXML
    private MenuItem maxByColorCommand;
    @FXML
    private MenuItem printDescendingCommand;
    @FXML
    private MenuItem removeLastCommand;
    @FXML
    private MenuItem removeByIdCommand;
    @FXML
    private MenuItem updateCommand;
    @FXML
    private MenuItem removeAnyByWingspanCommand;
    @FXML
    private MenuItem helpCommand;

    @FXML
    private TableView<Dragon> tableView;
    @FXML
    private TableColumn<Dragon, Integer> idColumn;
    @FXML
    private TableColumn<Dragon, String> nameColumn;
    @FXML
    private TableColumn<Dragon, Integer> xColumn;
    @FXML
    private TableColumn<Dragon, Float> yColumn;
    @FXML
    private TableColumn<Dragon, String> dateColumn;
    @FXML
    private TableColumn<Dragon, Long> ageColumn;
    @FXML
    private TableColumn<Dragon, String> descriptionColumn;
    @FXML
    private TableColumn<Dragon, Double> wingspanColumn;
    @FXML
    private TableColumn<Dragon, Color> colorColumn;
    @FXML
    private TableColumn<Dragon, Long> caveColumn;
    @FXML
    private Button exitButton;
    @FXML
    private TextArea responseTextArea;
    @FXML
    private Canvas canvas;
    @FXML
    private TabPane tabPane;
    @FXML
    private Button RUbutton;
    @FXML
    private Button UAbutton;
    @FXML
    private Button ESbutton;
    @FXML
    private Button CZbutton;
    @FXML
    private ResourceBundle resources;

    @FXML
    public void initialize() {
        Image image = new Image("/img.png");
        imageView.setImage(image);
        initColumns();
        initUserNameLabel();
        startTableUpdateThread();
        applyResources();
        animateDragons();
        tableView.setRowFactory(tv -> {
            TableRow<Dragon> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.SECONDARY && !row.isEmpty()) {
                    Dragon dragon = row.getItem();
                    if (dragon != null && dragon.getUserId() == user.getId()) {
                        showContextMenu(row, event);
                    }
                }
            });
            return row;
        });
        canvas.setOnMouseClicked(mouseEvent -> {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                handlePrimaryClick(x, y);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                handleSecondaryClick(x, y, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });
        RUbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("ru", "RU"));
            applyResources();
        });
        UAbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("uk", "UA"));
            applyResources();
        });
        CZbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("cs", "CZ"));
            applyResources();
        });
        ESbutton.setOnAction(actionEvent -> {
            LocaleManager.setLocale(new Locale("es", "DO"));
            applyResources();
        });

    }

    private void initColumns() {
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        xColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCoordinates().getY()));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(LocaleManager.getFormattedDate(cellData.getValue().getCreationDate())));
        ageColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAge()));
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDescription()));
        wingspanColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWingspan()));
        colorColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getColor()));
        caveColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCave().getNumberOfTreasures()));
        setCustomCellFactory(idColumn);
        setCustomCellFactory(nameColumn);
        setCustomCellFactory(xColumn);
        setCustomCellFactory(yColumn);
        setCustomCellFactory(dateColumn);
        setCustomCellFactory(ageColumn);
        setCustomCellFactory(descriptionColumn);
        setCustomCellFactory(wingspanColumn);
        setCustomCellFactory(colorColumn);
        setCustomCellFactory(caveColumn);
    }
    private void initUserNameLabel() {
        if (client != null && client.getUser() != null) {
            loginField.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: blue;");
            loginField.setText(client.getUser().getLogin());
        }
    }
    private <T> void setCustomCellFactory(TableColumn<Dragon, T> column) {
        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Dragon, T> call(TableColumn<Dragon, T> param) {
                return new TableCell<>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle("");
                        } else {
                            setText(item.toString());
                            Dragon dragon = getTableView().getItems().get(getIndex());
                            if (dragon.getUserId() == client.getUser().getId()) {
                                setStyle("-fx-background-color: lightGray;");
                            } else {
                                setStyle("");
                            }
                        }
                    }
                };
            }
        });
    }


    public void startTableUpdateThread() {
        scheduler.scheduleAtFixedRate(() -> {
            if (client.getIsUpdating()) {
                Platform.runLater(this::fillTable);
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    private void handlePrimaryClick(double x, double y) {
        double cellSize = 50; // размер клетки на сетке
        double xOffset = 50; // отступ по оси X
        double yOffset = 50; // отступ по оси Y
        double scale = 0.1; // коэффициент масштабирования (1 единица = 100 на сетке)

        for (Dragon dragon : dragonList) {
            double centerX = xOffset + dragon.getCoordinates().getX() * scale;
            double centerY = yOffset + dragon.getCoordinates().getY() * scale;
            double radius = dragon.getWingspan() * scale * 0.5;

            if (Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2)) {
                dragonInfo.setText(dragon.toString());
                break;
            }
        }
    }
    private void handleSecondaryClick(double x, double y, double screenX, double screenY) {
        double cellSize = 50; // размер клетки на сетке
        double xOffset = 50; // отступ по оси X
        double yOffset = 50; // отступ по оси Y
        double scale = 0.1; // коэффициент масштабирования (1 единица = 100 на сетке)

        for (Dragon dragon : dragonList) {
            double centerX = xOffset + dragon.getCoordinates().getX() * scale;
            double centerY = yOffset + dragon.getCoordinates().getY() * scale;
            double radius = dragon.getWingspan() * scale * 0.5;

            if (Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2)) {
                break;
            }
        }
    }
    private void animateDragons() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(animationProgressProperty(), 0)),
                new KeyFrame(Duration.seconds(5), new KeyValue(animationProgressProperty(), 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    private DoubleProperty animationProgressProperty() {
        return new SimpleDoubleProperty(animationProgress) {
            @Override
            protected void invalidated() {
                animationProgress = get();
                drawDragons();
            }
        };
    }
    //=======Рисуем дракончиков=======\\
    private void drawDragons() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawGrid(gc); // метод для отрисовки сетки

        double cellSize = 50; // размер клетки на сетке
        double xOffset = 50; // отступ по оси X
        double yOffset = 50; // отступ по оси Y
        double scale = 0.1; // коэффициент масштабирования (1 единица = 100 на сетке)

        for (Dragon dragon : dragonList) {
            double centerX = xOffset + dragon.getCoordinates().getX() * scale;
            double centerY = yOffset + dragon.getCoordinates().getY() * scale;
            double maxRadius = dragon.getWingspan() * scale * 0.5;
            double radius = maxRadius * animationProgress; // Используем прогресс анимации для изменения размера

            if (dragon.getUserId() == user.getId()) {
                gc.setFill(javafx.scene.paint.Color.PURPLE);
            } else {
                gc.setFill(javafx.scene.paint.Color.RED);
            }

            gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }
    }
        //=======Создание сетки на поле=======\\
    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(javafx.scene.paint.Color.GRAY);
        gc.setLineWidth(0.5);

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double cellSize = 50;

        for (double i = 0; i < width; i += cellSize) {
            gc.strokeLine(i, 0, i, height);
        }

        for (double i = 0; i < height; i += cellSize) {
            gc.strokeLine(0, i, width, i);
        }
    }

    //=======Заполнение таблицы=======\\
    private void fillTable() {
        ArrayList<Dragon> listNew = client.getCollectionFromDB();
        if (listNew != null) {
            if (!listNew.isEmpty()) {
                dragonList = listNew;
                ObservableList<Dragon> data = FXCollections.observableList(dragonList);
                tableView.setItems(data);
            }
        } else {
            tableView.setItems(FXCollections.observableArrayList()); // Очистка таблицы в случае пустой коллекции
        }
    }

    //=======Нажатие на объект в таблице(апдейт и делит)=======\\
    private void showContextMenu(TableRow<Dragon> row, MouseEvent event) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> handleUpdateCommandWithDragon(row.getItem()));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteDragon(row.getItem()));

        contextMenu.getItems().addAll(editItem, deleteItem);
        contextMenu.show(row, event.getScreenX(), event.getScreenY());
    }
    //=======Нажатие на объект в визуализации(апдейт и делит)=======\\
    private void showContextMenu(Dragon dragon, double x, double y) {
        if (dragon.getUserId() == user.getId()) {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Edit");
            editItem.setOnAction(actionEvent -> handleUpdateCommandWithDragon(dragon));

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(actionEvent -> deleteDragon(dragon));

            contextMenu.getItems().addAll(editItem, deleteItem);
            contextMenu.show(canvas, x, y);
        }
    }


    //=======Методы для установки цветного текста в поле=======\\
    public void setRedStyle(String text) {
        responseTextArea.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: red;");
        responseTextArea.setText(text);
    }
    public void setBlueStyle(String text) {
        responseTextArea.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: blue;");
        responseTextArea.setText(text);
    }
    public TextArea getResponseTextArea() {
        return responseTextArea;
    }

    private void applyResources() {
        resources = ResourceBundle.getBundle("GuiLabels", LocaleManager.getLocale());
        idColumn.setText(resources.getString("id"));
        nameColumn.setText(resources.getString("name"));
        xColumn.setText(resources.getString("coordinate_x"));
        yColumn.setText(resources.getString("coordinate_y"));
        dateColumn.setText(resources.getString("creation_date"));
        ageColumn.setText(resources.getString("age"));
        descriptionColumn.setText(resources.getString("description"));
        wingspanColumn.setText(resources.getString("wingspan"));
        colorColumn.setText(resources.getString("color"));
        caveColumn.setText(resources.getString("number_of_treasures"));

        exitButton.setText(resources.getString("exit"));
//        logoutButton.setText(resources.getString("log_out"));

        scriptButton.setText(resources.getString("ScriptExecutor"));
        tabPane.getTabs().get(0).setText(resources.getString("main_page"));
        tabPane.getTabs().get(1).setText(resources.getString("visualization"));

        menu.setText(resources.getString("commands"));
        addCommand.setText(resources.getString("add"));
        addIfMaxCommand.setText(resources.getString("add_if_max"));
        clearCommand.setText(resources.getString("clear"));
        infoCommand.setText(resources.getString("info"));
        historyCommand.setText(resources.getString("history"));
        removeLastCommand.setText(resources.getString("remove_last"));
        removeByIdCommand.setText(resources.getString("remove_by_id"));
        updateCommand.setText(resources.getString("update"));
        removeAnyByWingspanCommand.setText(resources.getString("remove_any_by_wingspan"));
        helpCommand.setText(resources.getString("help"));

        responseTextArea.setPromptText(resources.getString("server_response"));
        dragonInfo.setPromptText(resources.getString("info_about_dragon"));
    }
    //=======Ниже логика команд=======\\
    @FXML
    private void handleScriptButton() {
        try {
            scriptController = WindowManager.switchScene("/scriptWindow.fxml", this);
        }catch (IOException exception) {
            setRedStyle("bimbimbabmba");
        }
    }

    private void deleteDragon(Dragon dragon) {
        Request request = commandBuilder.buildCommand("remove_by_id " + dragon.getId());
        Response response = client.sendAndReceive(request);
    }
    private void handleUpdateCommandWithDragon(Dragon dragon) {
        try {
            updateController = WindowManager.switchScene("/Update.fxml", this);
            updateController.setDragon(dragon);
        }catch (IOException exception) {
            setRedStyle("error switching update scene with dragon");
        }
    }

    @FXML
    private void handleRemoveLastCommand() {
        Request request = commandBuilder.buildCommand("remove_last");
        Response response = client.sendAndReceive(request);
    }
    @FXML
    private void handleHistoryCommand() {
        Request request = commandBuilder.buildCommand("history");
        Response response = client.sendAndReceive(request);
        setBlueStyle(response.getMessageToResponse());
    }
    @FXML
    private void handleClearCommand() {
        Request request = commandBuilder.buildCommand("clear");
        Response response = client.sendAndReceive(request);

    }
    @FXML
    private void handleHelpCommand() {
        Request request = commandBuilder.buildCommand("help");
        Response response = client.sendAndReceive(request);
        setBlueStyle(response.getMessageToResponse());
    }
    @FXML
    private void handleInfoCommand() {
        Request request = commandBuilder.buildCommand("info");
        Response response = client.sendAndReceive(request);
        setBlueStyle(response.getMessageToResponse());
    }
    @FXML
    private void handleRemoveAnyByWingspanCommand() {
        try {
            removeAnyByWingspanController = WindowManager.switchScene("/RemoveAnyByWingspan.fxml", this);
        }catch (IOException exception) {
            setRedStyle("error switching remove window");
        }
    }
    @FXML
    private void handleRemoveByIdCommand() {
        try {
            removeByIdController = WindowManager.switchScene("/RemoveById.fxml", this);
        }catch (IOException exception) {
            setRedStyle("error switching remove window");
        }
    }
    @FXML
    private void handleUpdateCommand() {
        try {
            updateController = WindowManager.switchScene("/Update.fxml", this);
        }catch (IOException exception) {
            setRedStyle("error switching update window");
            exception.printStackTrace();
        }
    }
    @FXML
    private void handleAddIfMaxCommand() {
        try {
            addIfMaxController = WindowManager.switchScene("/AddIfMax.fxml", this);
        }catch (IOException exception) {
            setRedStyle("error switching add if max window");
        }
    }
    @FXML
    private void handleAddCommand() {
        try {
            addController = WindowManager.switchScene("/Add.fxml", this);
        }catch (IOException exception) {
            setRedStyle("error switching add window");
        }
    }
    @FXML
    private void handleExitButton() {
        System.exit(0);
    }
//    @FXML
//    private void handleLogoutButton() {
//        client.disconnect();
//        openAuthWindow();
//    }
//
//    private void openAuthWindow() {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(MainController.class.getResource("/Auth.fxml"));
//        try {
//            loader.load();
//        }catch (IOException exception) {}
//        Parent root = loader.getRoot();
//        Scene scene = new Scene(root);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setResizable(false);
//        Stage currentStage = (Stage) logoutButton.getScene().getWindow();
//
//        currentStage.close();
//        stage.show();
//    }
}
