
# Restaurant Ordering System

A Java networking project that simulates a restaurant ordering system with a client-server architecture. This application uses sockets for communication and provides a GUI interface for both customers and restaurant managers.

## Project Overview

This system allows customers to browse restaurant menus and place food orders, while restaurant managers can receive and manage incoming orders through a separate interface. The architecture uses multi-threaded socket communication to handle multiple clients simultaneously.

## Components

### Server Side
- **`Server.java`** - The main server that handles incoming connections and manages client communication
- **`SocketWrapper.java`** (in `util/` package) - Helper class for socket-based object serialization
- **`ReadThread.java`** - Thread that reads incoming data from clients (like orders from customers)

### Client Side (Console Application)
- **`Client.java`** - Console-based customer client with menu navigation
- **`RestaurantClient.java`** - Console-based restaurant client for order management

### GUI Clients (JavaFX)
- **`ClientGUI.java`** - JavaFX-based customer interface (needs JavaFX to run)
- **`RestaurantClientGUI.java`** - JavaFX-based restaurant interface (needs JavaFX to run)

### Data Models
- **`Restaurant.java`** - Restaurant data model (id, name, score, price, zipcode, categories, food items)
- **`FoodItems.java`** - Food item data model (restaurantId, category, name, price)
- **`RestaurantManager.java`** - Manages restaurant list and provides search functionality
- **`RestaurantData.java`** - Data transfer object for order information

## Data Files

The system loads data from two text files:
- **`restaurant.txt`** - Contains restaurant information (format: id,name,score,price,zipcode,categories...)
- **`menu.txt`** - Contains food menu items (format: restaurantId,category,name,price)

## How to Run

### Prerequisites
- Java JDK 20+ (as configured in the project)
- **JavaFX is required** for GUI applications (not included in standard JDK)

### Running the Server
```bash
javac -d out *.java util/*.java
java Server
```

### Running Console Clients
```bash
# Customer client
java Client

# Restaurant client (enter restaurant name when prompted)
java RestaurantClient
```


## User Workflows

### Customer Side (ClientGUI)
1. Connects to the server and loads the restaurant list
2. Search restaurants by name, score, price level, or zipcode
3. Select a restaurant to view its menu
4. Add menu items to the shopping cart
5. Place order - sends the cart to the selected restaurant

### Restaurant Side (RestaurantClientGUI)
1. Enter your restaurant name to connect
2. The restaurant receives the menu data for that restaurant
3. Incoming orders appear in the orders list with notifications
4. Can mark orders as completed when done

## Known Issues

- **GUI Compilation Error**: The JavaFX GUI files cannot compile without JavaFX libraries. The error "package javafx.* does not exist" indicates missing JavaFX dependencies.
- To use the GUI, you'll need to either:
  - Install JavaFX SDK and add it to your module path
  - Convert the GUI to Swing (which is built into Java)
  
## Network Configuration

- Server listens on port **33333**
- Default server address: **127.0.0.1** (localhost)

## Features

- Multi-threaded server for handling multiple clients
- Real-time order notifications
- Restaurant and food search capabilities
- Shopping cart functionality
- Socket-based communication using object serialization
=======
# java-networking-restaurant-app
A Java‑based client‑server application for a restaurant ordering system, demonstrating socket programming, multithreading, and a simple GUI.

