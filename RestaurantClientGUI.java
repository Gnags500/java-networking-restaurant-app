import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.SocketWrapper;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantClientGUI extends Application {
    private Stage primaryStage;
    private SocketWrapper socketWrapper;
    private Restaurant restaurant;
    
    private Label restaurantNameLabel;
    private ListView<String> ordersListView;
    private TextArea orderDetailsArea;
    private TextArea statusArea;
    private int orderCount = 0;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Restaurant Order Management System");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        
        connectToServer();
        
        Scene scene = new Scene(createMainLayout());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                // Show input dialog for restaurant name
                Platform.runLater(() -> {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Restaurant Selection");
                    dialog.setHeaderText("Enter Restaurant Name");
                    dialog.setContentText("Restaurant Name:");
                    
                    java.util.Optional<String> result = dialog.showAndWait();
                    result.ifPresent(restaurantName -> {
                        try {
                            socketWrapper = new SocketWrapper("127.0.0.1", 33333);
                            socketWrapper.write(restaurantName);
                            restaurant = (Restaurant) socketWrapper.read();
                            
                            // Update UI with restaurant details
                            Platform.runLater(() -> {
                                restaurantNameLabel.setText(restaurant.getName() + " - Order Management");
                                updateRestaurantDetails();
                                addLogMessage("Connected as " + restaurant.getName());
                            });
                            
                            // Listen for incoming orders
                            listenForOrders();
                        } catch (Exception e) {
                            Platform.runLater(() -> showAlert("Connection Error", 
                                "Failed to connect: " + e.getMessage()));
                        }
                    });
                });
            } catch (Exception e) {
                Platform.runLater(() -> showAlert("Error", "Connection error: " + e.getMessage()));
            }
        }).start();
    }

    private void listenForOrders() {
        new Thread(() -> {
            try {
                while (true) {
                    Object o = socketWrapper.read();
                    if (o instanceof RestaurantData) {
                        RestaurantData data = (RestaurantData) o;
                        Platform.runLater(() -> {
                            orderCount++;
                            addOrderToList(data);
                            addLogMessage("New Order #" + orderCount + " received at " + 
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                            
                            // Show notification
                            showAlert("New Order", 
                                "Order #" + orderCount + " - " + data.getFlist().size() + " items");
                        });
                    }
                }
            } catch (Exception e) {
                Platform.runLater(() -> addLogMessage("Disconnected: " + e.getMessage()));
            }
        }).start();
    }

    private void addOrderToList(RestaurantData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderCount).append(" | Items: ").append(data.getFlist().size());
        double total = 0;
        for (FoodItems item : data.getFlist()) {
            total += item.getPrice();
        }
        sb.append(" | Total: $").append(String.format("%.2f", total));
        
        ordersListView.getItems().add(0, sb.toString());
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-background-color: #f5f5f5;");

        // Header
        restaurantNameLabel = new Label("Restaurant Order Management");
        restaurantNameLabel.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: #333333;");
        
        Separator separator = new Separator();
        
        // Main content
        HBox contentPane = new HBox(15);
        contentPane.setPrefHeight(600);
        
        // Left panel - Orders list
        VBox leftPanel = createLeftPanel();
        leftPanel.setPrefWidth(400);
        
        // Right panel - Details and status
        VBox rightPanel = createRightPanel();
        rightPanel.setPrefWidth(500);
        
        contentPane.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, Priority.ALWAYS);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);
        
        mainLayout.getChildren().addAll(restaurantNameLabel, separator, contentPane);
        return mainLayout;
    }

    private VBox createLeftPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #ffffff;");
        
        Label ordersLabel = new Label("Incoming Orders");
        ordersLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        ordersListView = new ListView<>();
        ordersListView.setPrefHeight(500);
        ordersListView.setStyle("-fx-font-size: 11;");
        ordersListView.setOnMouseClicked(e -> showOrderDetails());
        
        ScrollPane scrollPane = new ScrollPane(ordersListView);
        scrollPane.setFitToWidth(true);
        
        Button markCompleteBtn = new Button("Mark as Completed");
        markCompleteBtn.setPrefWidth(150);
        markCompleteBtn.setStyle("-fx-font-size: 11; -fx-padding: 8;");
        markCompleteBtn.setOnAction(e -> markOrderComplete());
        
        HBox buttonBox = new HBox(5);
        buttonBox.getChildren().add(markCompleteBtn);
        
        panel.getChildren().addAll(ordersLabel, scrollPane, buttonBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        return panel;
    }

    private VBox createRightPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1; -fx-padding: 10; -fx-background-color: #ffffff;");
        
        // Restaurant Details
        Label detailsLabel = new Label("Restaurant Details");
        detailsLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        
        TextArea restaurantDetailsArea = new TextArea();
        restaurantDetailsArea.setPrefHeight(120);
        restaurantDetailsArea.setWrapText(true);
        restaurantDetailsArea.setEditable(false);
        restaurantDetailsArea.setStyle("-fx-font-size: 10;");
        
        // Order Details
        Label orderLabel = new Label("Order Details");
        orderLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        
        orderDetailsArea = new TextArea();
        orderDetailsArea.setPrefHeight(200);
        orderDetailsArea.setWrapText(true);
        orderDetailsArea.setEditable(false);
        orderDetailsArea.setStyle("-fx-font-size: 10;");
        
        // Status/Log
        Label statusLabel = new Label("Activity Log");
        statusLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        
        statusArea = new TextArea();
        statusArea.setPrefHeight(150);
        statusArea.setWrapText(true);
        statusArea.setEditable(false);
        statusArea.setStyle("-fx-font-size: 9; -fx-font-family: 'Courier New';");
        
        panel.getChildren().addAll(
            detailsLabel, restaurantDetailsArea,
            orderLabel, orderDetailsArea,
            statusLabel, statusArea
        );
        
        VBox.setVgrow(restaurantDetailsArea, Priority.SOMETIMES);
        VBox.setVgrow(orderDetailsArea, Priority.SOMETIMES);
        VBox.setVgrow(statusArea, Priority.ALWAYS);
        
        return panel;
    }

    private void updateRestaurantDetails() {
        if (restaurant == null) return;
        
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(restaurant.getName()).append("\n");
        sb.append("Score: ").append(restaurant.getScore()).append("\n");
        sb.append("Price Level: ").append(restaurant.getPrice()).append("\n");
        sb.append("Zipcode: ").append(restaurant.getZipcode()).append("\n");
        sb.append("Categories: ").append(String.join(", ", restaurant.getCategories())).append("\n");
        sb.append("Total Menu Items: ").append(restaurant.foodList.size()).append("\n");
        
        // This is a workaround since we don't have direct access to the text area
        addLogMessage(sb.toString().replace("\n", " | "));
    }

    private void showOrderDetails() {
        int selectedIdx = ordersListView.getSelectionModel().getSelectedIndex();
        if (selectedIdx < 0) return;
        
        String orderSummary = ordersListView.getItems().get(selectedIdx);
        orderDetailsArea.setText(orderSummary);
        
        addLogMessage("Selected: " + orderSummary);
    }

    private void markOrderComplete() {
        int selectedIdx = ordersListView.getSelectionModel().getSelectedIndex();
        if (selectedIdx < 0) {
            showAlert("Selection Error", "Please select an order to mark as complete");
            return;
        }
        
        String order = ordersListView.getItems().get(selectedIdx);
        ordersListView.getItems().remove(selectedIdx);
        
        addLogMessage("Order marked as complete: " + order);
        showAlert("Completed", "Order has been marked as completed!");
    }

    private void addLogMessage(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String logEntry = "[" + timestamp + "] " + message + "\n";
        statusArea.appendText(logEntry);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
