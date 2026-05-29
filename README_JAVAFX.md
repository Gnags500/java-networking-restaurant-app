# Restaurant Ordering System with JavaFX GUI
## Complete Terminal Setup & Running Guide

This project is a multi-threaded Java networking application with JavaFX GUI for a restaurant ordering system that supports multiple customers and restaurants.

---

## PROJECT STRUCTURE
```
2105062/
├── Server.java                          (Main server - handles multiple clients)
├── ClientGUI.java                       (Customer GUI for ordering)
├── RestaurantClientGUI.java             (Restaurant GUI for receiving orders)
├── RestaurantManager.java               (Business logic - search, filtering)
├── Restaurant.java                      (Restaurant data model)
├── FoodItems.java                       (Food item data model)
├── RestaurantData.java                  (Order data model)
├── ReadThread.java                      (Server-side thread for reading orders)
├── WriteThreadServer.java               (Server-side thread for writing)
├── WriteThreadClient.java               (Client-side thread)
├── ReadThreadClient.java                (Client-side thread)
├── util/
│   └── SocketWrapper.java               (Socket communication wrapper)
├── menu.txt                             (Food items database)
├── restaurant.txt                       (Restaurants database)
└── README.md                            (This file)
```

---

## SYSTEM REQUIREMENTS

1. **Java Development Kit (JDK)**: JDK 11 or higher
2. **JavaFX SDK**: JavaFX 11 or higher
3. **Operating System**: Windows, macOS, or Linux
4. **Network**: Local network (127.0.0.1)

---

## JAVAFX INSTALLATION STEPS

### Step 1: Download JavaFX SDK
- Visit: https://gluonhq.com/products/javafx/
- Download JavaFX SDK for your OS
- Extract it to a known location (e.g., `C:\javafx-sdk-21` on Windows)

### Step 2: Set JAVAFX_HOME environment variable (optional but recommended)
**On Windows (PowerShell):**
```powershell
[Environment]::SetEnvironmentVariable("JAVAFX_HOME", "C:\javafx-sdk-21", "User")
```

**On macOS/Linux (Terminal):**
```bash
export JAVAFX_HOME=/path/to/javafx-sdk-21
```

---

## COMPILATION STEPS

### Step 1: Navigate to project directory
```bash
cd "d:\asssignments and offlines\1-2\1-2 JAVA NETWORKING PROJECT\Java project part 2\2105062"
```

### Step 2: Compile ALL Java files (Non-GUI + GUI)

**Option A: Without JAVAFX_HOME set (specify path directly)**
```bash
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . util/SocketWrapper.java FoodItems.java Restaurant.java RestaurantManager.java RestaurantData.java Server.java Client.java RestaurantClient.java ReadThread.java ReadThreadClient.java WriteThreadServer.java WriteThreadClient.java ClientGUI.java RestaurantClientGUI.java
```

**Option B: With JAVAFX_HOME set**
```bash
javac --module-path %JAVAFX_HOME%\lib --add-modules javafx.controls,javafx.fxml -cp . util/SocketWrapper.java FoodItems.java Restaurant.java RestaurantManager.java RestaurantData.java Server.java Client.java RestaurantClient.java ReadThread.java ReadThreadClient.java WriteThreadServer.java WriteThreadClient.java ClientGUI.java RestaurantClientGUI.java
```

---

## RUNNING THE APPLICATION

### STEP 1: Start the Server

Open Terminal 1 and run:
```bash
cd "d:\asssignments and offlines\1-2\1-2 JAVA NETWORKING PROJECT\Java project part 2\2105062"
java -cp . Server
```

**Expected Output:**
```
Enter the name of the client to send, a message to send:
```

The server is now listening on port 33333 and ready to accept connections.

---

### STEP 2: Launch Customer Client (GUI)

Open Terminal 2 and run:
```bash
cd "d:\asssignments and offlines\1-2\1-2 JAVA NETWORKING PROJECT\Java project part 2\2105062"
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . ClientGUI
```

**Or with JAVAFX_HOME:**
```bash
java --module-path %JAVAFX_HOME%\lib --add-modules javafx.controls,javafx.fxml -cp . ClientGUI
```

A JavaFX window opens with:
- Restaurant search panel on the left
- Food menu in the middle
- Shopping cart on the right

---

### STEP 3: Launch Second Customer Client

Open Terminal 3 and run the same command:
```bash
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . ClientGUI
```

---

### STEP 4: Launch Restaurant Client (GUI)

Open Terminal 4 and run:
```bash
cd "d:\asssignments and offlines\1-2\1-2 JAVA NETWORKING PROJECT\Java project part 2\2105062"
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . RestaurantClientGUI
```

**Or with JAVAFX_HOME:**
```bash
java --module-path %JAVAFX_HOME%\lib --add-modules javafx.controls,javafx.fxml -cp . RestaurantClientGUI
```

A dialog box appears asking for restaurant name. Enter:
- `KFC`
- `IHOP`
- `Starbucks`
- `McDonalds`

The restaurant GUI opens and waits for incoming orders.

---

### STEP 5: Launch Additional Restaurant Clients

Repeat STEP 4 for other restaurants:

**Terminal 5 (IHOP):**
```bash
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . RestaurantClientGUI
```
(Enter "IHOP" when prompted)

**Terminal 6 (Starbucks):**
```bash
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . RestaurantClientGUI
```
(Enter "Starbucks" when prompted)

---

## TERMINAL LAYOUT EXAMPLE

You should have these terminals open simultaneously:

| Terminal | Purpose | Command |
|----------|---------|---------|
| 1 | Server | `java -cp . Server` |
| 2 | Customer 1 | `java --module-path ... ClientGUI` |
| 3 | Customer 2 | `java --module-path ... ClientGUI` |
| 4 | Restaurant (KFC) | `java --module-path ... RestaurantClientGUI` |
| 5 | Restaurant (IHOP) | `java --module-path ... RestaurantClientGUI` |
| 6 | Restaurant (Starbucks) | `java --module-path ... RestaurantClientGUI` |

---

## USAGE GUIDE

### Customer GUI (ClientGUI)

1. **Search Restaurants**
   - Select search type: "All", "By Name", "By Score", "By Price", "By Zipcode"
   - Enter search term (if needed)
   - Click "Search" button

2. **View Restaurant Details**
   - Click on a restaurant in the list
   - Restaurant details and menu items display

3. **Add Items to Cart**
   - Click on a food item in the menu
   - Item details display
   - Click "Add to Cart" button

4. **Manage Cart**
   - View all items in "Shopping Cart" on the right
   - Remove individual items or clear entire cart
   - See total price

5. **Place Order**
   - Review your cart
   - Click "Place Order" button
   - Order sent to restaurant

---

### Restaurant GUI (RestaurantClientGUI)

1. **Connect to Server**
   - When launched, prompted to enter restaurant name
   - Enter one of: KFC, IHOP, Starbucks, McDonalds

2. **Receive Orders**
   - Incoming customer orders appear in list
   - Notifications popup for each new order

3. **View Order Details**
   - Click on order in list
   - Details display in "Order Details" section
   - Activity log updates with timestamps

4. **Complete Orders**
   - Select order from list
   - Click "Mark as Completed"
   - Order removed from list

---

## CONSOLE COMMANDS REFERENCE

### Compile All Files
```bash
javac --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . util/SocketWrapper.java FoodItems.java Restaurant.java RestaurantManager.java RestaurantData.java Server.java Client.java RestaurantClient.java ReadThread.java ReadThreadClient.java WriteThreadServer.java WriteThreadClient.java ClientGUI.java RestaurantClientGUI.java
```

### Run Server
```bash
java -cp . Server
```

### Run Customer GUI
```bash
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . ClientGUI
```

### Run Restaurant GUI
```bash
java --module-path "C:\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml -cp . RestaurantClientGUI
```

---

## FEATURES DEMONSTRATED

✅ **Multi-threaded Server** - Handles multiple concurrent clients
✅ **Socket Communication** - Serialized object transmission over network
✅ **Thread-Safe Collections** - HashMap manages client connections
✅ **JavaFX GUI** - Modern graphical interface for customers and restaurants
✅ **Search & Filtering** - Multiple search criteria for restaurants and food
✅ **Order Routing** - Orders routed from customers to specific restaurants
✅ **Real-time Notifications** - Instant order delivery and alerts
✅ **Activity Logging** - Timestamped event tracking

---

## DATA FILES

### restaurant.txt
Contains restaurant data in format:
```
ID,Name,Score,Price,Zipcode,Category1,Category2,...
```

Example:
```
1,KFC,4.3,$$$,98531,Chicken,Fast Food,Family Meals
2,IHOP,4.3,$$,77494,Breakfast and Brunch,Family Meals,Burgers
3,Starbucks,4.9,$,99218,Coffee and Tea,Breakfast and Brunch,Bakery
4,McDonalds,4.7,$,98346,Burgers,Fast Food
```

### menu.txt
Contains food items in format:
```
RestaurantID,Category,FoodName,Price
```

Example:
```
1,A la Carte,Large Popcorn Nuggets,5.99
1,A la Carte,A La Carte Wing,2.75
3,Bakery,Cinnamon Raisin Bagel,2.65
```

---

## TROUBLESHOOTING

### Issue: "Module not found: javafx.controls"
**Solution:** Ensure JavaFX path is correct:
```bash
javac --module-path "C:\javafx-sdk-21\lib" ...
java --module-path "C:\javafx-sdk-21\lib" ...
```

### Issue: "Port 33333 already in use"
**Solution:** Kill the existing Server process or wait 60 seconds for port to release

### Issue: "Connection refused" on clients
**Solution:** Ensure Server is running first before launching clients

### Issue: Orders not appearing at restaurant
**Solution:** 
1. Ensure restaurant client is connected and waiting
2. Enter correct restaurant name (case-insensitive, but must match database)
3. Check that customer selected correct restaurant

### Issue: GUI window doesn't appear
**Solution:** Check if --add-modules parameter is included in command

---

## TESTING SCENARIO

1. Start Server (Terminal 1)
2. Start Customer 1 (Terminal 2)
3. Start Customer 2 (Terminal 3)
4. Start KFC Restaurant (Terminal 4)
5. Customer 1: Search restaurants → Select KFC → Add items → Place Order
6. Observe: Order appears immediately in KFC Restaurant GUI
7. Customer 2: Place order from IHOP
8. KFC Restaurant: Mark orders as completed

---

## NOTES

- All communications happen over localhost (127.0.0.1:33333)
- Maximum connections limited by system resources
- Each restaurant can have multiple clients connecting as the same restaurant
- Orders are in-memory (not persisted to disk)
- Close GUI windows to disconnect clients cleanly

---

## CONTACT & SUPPORT

For questions or issues, check:
- Server logs in Terminal 1
- Client console output
- Ensure all files are compiled in the same directory
- Verify JavaFX SDK is properly installed
