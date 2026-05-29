import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import util.SocketWrapper;
import java.util.ArrayList;

public class ClientGUI extends Application {
    private RestaurantManager restaurantManager;
    private SocketWrapper socketWrapper;
    private Stage primaryStage;
    
    private Label titleLabel;
    private ComboBox<String> restaurantSearchType;
    private TextField searchField;
    private ListView<String> restaurantListView;
    private TextArea restaurantDetailsArea;
    private ListView<String> foodListView;
    private TextArea orderDetailsArea;
    private ListView<String> cartListView;
    private Label totalPriceLabel;
    
    private ArrayList<FoodItems> selectedFoods = new ArrayList<>();
    private String selectedRestaurant = "";
    private double totalPrice = 0;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Restaurant Ordering System - Customer");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(800);
        
        connectToServer();
        
        Scene scene = new Scene(createMainLayout());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void connectToServer() {
        try {
            socketWrapper = new SocketWrapper("127.0.0.1", 33333);
            socketWrapper.write("Customer");
            restaurantManager = (RestaurantManager) socketWrapper.read();
            
            new Thread(() -> {
                try {
                    while (true) {
                        Object o = socketWrapper.read();
                        if (o instanceof RestaurantData) {
                            RestaurantData data = (RestaurantData) o;
                            Platform.runLater(() -> {
                                showAlert("Order Confirmation", 
                                    "Your order has been sent to " + data.getRestaurantName());
                            });
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Connection closed: " + e);
                }
            }).start();
        } catch (Exception e) {
            showAlert("Connection Error", "Failed to connect to server: " + e.getMessage());
        }
    }

    private VBox createMainLayout() {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1;");

        // Title
        titleLabel = new Label("Welcome to Restaurant Ordering System");
        titleLabel.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        
        // Main content with two columns
        HBox contentPane = new HBox(15);
        contentPane.setPrefHeight(700);
        
        // Left panel - Restaurant search
        VBox leftPanel = createLeftPanel();
        leftPanel.setPrefWidth(300);
        
        // Middle panel - Food selection
        VBox middlePanel = createMiddlePanel();
        middlePanel.setPrefWidth(400);
        
        // Right panel - Shopping cart
        VBox rightPanel = createRightPanel();
        rightPanel.setPrefWidth(300);
        
        contentPane.getChildren().addAll(leftPanel, middlePanel, rightPanel);
        
        mainLayout.getChildren().addAll(titleLabel, contentPane);
        return mainLayout;
    }

    private VBox createLeftPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: #dddddd; -fx-border-width: 1; -fx-padding: 10;");
        
        Label searchLabel = new Label("Search Restaurants");
        searchLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        HBox searchBox = new HBox(5);
        restaurantSearchType = new ComboBox<>();
        restaurantSearchType.getItems().addAll("By Name", "By Score", "By Price", "By Zipcode", "All");
        restaurantSearchType.setValue("All");
        
        searchField = new TextField();
        searchField.setPromptText("Enter search term...");
        
        Button searchBtn = new Button("Search");
        searchBtn.setOnAction(e -> searchRestaurants());
        searchBtn.setStyle("-fx-font-size: 11;");
        
        searchBox.getChildren().addAll(restaurantSearchType, searchField, searchBtn);
        
        restaurantListView = new ListView<>();
        restaurantListView.setPrefHeight(400);
        restaurantListView.setOnMouseClicked(e -> showRestaurantDetails());
        
        restaurantDetailsArea = new TextArea();
        restaurantDetailsArea.setWrapText(true);
        restaurantDetailsArea.setPrefHeight(150);
        restaurantDetailsArea.setEditable(false);
        
        panel.getChildren().addAll(
            searchLabel, searchBox, 
            new Label("Available Restaurants:"),
            restaurantListView,
            new Label("Details:"),
            restaurantDetailsArea
        );
        
        return panel;
    }

    private VBox createMiddlePanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: #dddddd; -fx-border-width: 1; -fx-padding: 10;");
        
        Label foodLabel = new Label("Menu Items");
        foodLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        foodListView = new ListView<>();
        foodListView.setPrefHeight(400);
        foodListView.setOnMouseClicked(e -> showFoodDetails());
        
        orderDetailsArea = new TextArea();
        orderDetailsArea.setWrapText(true);
        orderDetailsArea.setPrefHeight(150);
        orderDetailsArea.setEditable(false);
        
        Button addToCartBtn = new Button("Add to Cart");
        addToCartBtn.setPrefWidth(100);
        addToCartBtn.setStyle("-fx-font-size: 11;");
        addToCartBtn.setOnAction(e -> addToCart());
        
        panel.getChildren().addAll(
            foodLabel,
            new Label("Select Restaurant First:"),
            foodListView,
            new Label("Item Details:"),
            orderDetailsArea,
            addToCartBtn
        );
        
        return panel;
    }

    private VBox createRightPanel() {
        VBox panel = new VBox(10);
        panel.setStyle("-fx-border-color: #dddddd; -fx-border-width: 1; -fx-padding: 10;");
        
        Label cartLabel = new Label("Shopping Cart");
        cartLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
        
        cartListView = new ListView<>();
        cartListView.setPrefHeight(350);
        
        totalPriceLabel = new Label("Total: $0.00");
        totalPriceLabel.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");
        
        Button removeBtn = new Button("Remove");
        removeBtn.setPrefWidth(80);
        removeBtn.setStyle("-fx-font-size: 11;");
        removeBtn.setOnAction(e -> removeFromCart());
        
        Button clearBtn = new Button("Clear All");
        clearBtn.setPrefWidth(80);
        clearBtn.setStyle("-fx-font-size: 11;");
        clearBtn.setOnAction(e -> clearCart());
        
        Button submitBtn = new Button("Place Order");
        submitBtn.setPrefWidth(80);
        submitBtn.setStyle("-fx-font-size: 11; -fx-font-weight: bold; -fx-padding: 8;");
        submitBtn.setOnAction(e -> submitOrder());
        
        HBox buttonBox = new HBox(5);
        buttonBox.getChildren().addAll(removeBtn, clearBtn, submitBtn);
        
        panel.getChildren().addAll(
            cartLabel,
            cartListView,
            totalPriceLabel,
            buttonBox
        );
        
        return panel;
    }

    private void searchRestaurants() {
        restaurantListView.getItems().clear();
        String searchType = restaurantSearchType.getValue();
        String searchTerm = searchField.getText();
        ArrayList<Restaurant> results = new ArrayList<>();
        
        if (searchType.equals("All")) {
            results = restaurantManager.getRestaurantList();
        } else if (searchType.equals("By Name")) {
            results = restaurantManager.searchRestaurentByName(searchTerm);
        } else if (searchType.equals("By Score")) {
            try {
                double score = Double.parseDouble(searchTerm);
                results = restaurantManager.searchRestaurentByScore(score);
            } catch (Exception e) {
                showAlert("Input Error", "Please enter a valid score");
            }
        } else if (searchType.equals("By Price")) {
            results = restaurantManager.searchRestaurantByPrice(searchTerm);
        } else if (searchType.equals("By Zipcode")) {
            results = restaurantManager.searchRestaurentByZipcode(searchTerm);
        }
        
        for (Restaurant r : results) {
            restaurantListView.getItems().add(r.getName() + " (Score: " + r.getScore() + ")");
        }
    }

    private void showRestaurantDetails() {
        int selectedIdx = restaurantListView.getSelectionModel().getSelectedIndex();
        if (selectedIdx < 0) return;
        
        ArrayList<Restaurant> allRestaurants = restaurantManager.getRestaurantList();
        String selectedName = restaurantListView.getItems().get(selectedIdx);
        String restaurantName = selectedName.substring(0, selectedName.indexOf(" ("));
        
        Restaurant selected = null;
        for (Restaurant r : allRestaurants) {
            if (r.getName().equalsIgnoreCase(restaurantName)) {
                selected = r;
                selectedRestaurant = r.getName();
                break;
            }
        }
        
        if (selected != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Name: ").append(selected.getName()).append("\n");
            sb.append("Score: ").append(selected.getScore()).append("\n");
            sb.append("Price Level: ").append(selected.getPrice()).append("\n");
            sb.append("Zipcode: ").append(selected.getZipcode()).append("\n");
            sb.append("Categories: ").append(String.join(", ", selected.getCategories())).append("\n");
            
            restaurantDetailsArea.setText(sb.toString());
            
            // Load food items for this restaurant
            foodListView.getItems().clear();
            for (FoodItems f : selected.foodList) {
                foodListView.getItems().add(f.getName() + " - $" + String.format("%.2f", f.getPrice()));
            }
        }
    }

    private void showFoodDetails() {
        if (selectedRestaurant.isEmpty()) {
            showAlert("Selection Error", "Please select a restaurant first");
            return;
        }
        
        int selectedIdx = foodListView.getSelectionModel().getSelectedIndex();
        if (selectedIdx < 0) return;
        
        Restaurant selected = null;
        for (Restaurant r : restaurantManager.getRestaurantList()) {
            if (r.getName().equalsIgnoreCase(selectedRestaurant)) {
                selected = r;
                break;
            }
        }
        
        if (selected != null && selectedIdx < selected.foodList.size()) {
            FoodItems food = selected.foodList.get(selectedIdx);
            StringBuilder sb = new StringBuilder();
            sb.append("Item: ").append(food.getName()).append("\n");
            sb.append("Category: ").append(food.getCategory()).append("\n");
            sb.append("Price: $").append(String.format("%.2f", food.getPrice())).append("\n");
            sb.append("Restaurant: ").append(selected.getName()).append("\n");
            
            orderDetailsArea.setText(sb.toString());
        }
    }

    private void addToCart() {
        if (selectedRestaurant.isEmpty()) {
            showAlert("Selection Error", "Please select a restaurant first");
            return;
        }
        
        int selectedIdx = foodListView.getSelectionModel().getSelectedIndex();
        if (selectedIdx < 0) {
            showAlert("Selection Error", "Please select a food item");
            return;
        }
        
        Restaurant selected = null;
        for (Restaurant r : restaurantManager.getRestaurantList()) {
            if (r.getName().equalsIgnoreCase(selectedRestaurant)) {
                selected = r;
                break;
            }
        }
        
        if (selected != null && selectedIdx < selected.foodList.size()) {
            FoodItems food = selected.foodList.get(selectedIdx);
            selectedFoods.add(food);
            totalPrice += food.getPrice();
            updateCart();
            showAlert("Added", food.getName() + " added to cart!");
        }
    }

    private void removeFromCart() {
        int selectedIdx = cartListView.getSelectionModel().getSelectedIndex();
        if (selectedIdx >= 0) {
            FoodItems removed = selectedFoods.get(selectedIdx);
            totalPrice -= removed.getPrice();
            selectedFoods.remove(selectedIdx);
            updateCart();
        }
    }

    private void clearCart() {
        selectedFoods.clear();
        totalPrice = 0;
        updateCart();
    }

    private void updateCart() {
        cartListView.getItems().clear();
        for (FoodItems f : selectedFoods) {
            cartListView.getItems().add(f.getName() + " - $" + String.format("%.2f", f.getPrice()));
        }
        totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
    }

    private void submitOrder() {
        if (selectedFoods.isEmpty()) {
            showAlert("Empty Cart", "Please add items before ordering");
            return;
        }
        
        RestaurantData data = new RestaurantData();
        data.setFlist(selectedFoods);
        data.setRestaurantName(selectedRestaurant);
        
        try {
            socketWrapper.write(data);
            showAlert("Order Placed", "Your order has been sent to " + selectedRestaurant);
            clearCart();
        } catch (Exception e) {
            showAlert("Order Error", "Failed to place order: " + e.getMessage());
        }
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
