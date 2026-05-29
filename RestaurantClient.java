

import java.util.Scanner;

import util.SocketWrapper;

public class RestaurantClient  {

    public RestaurantClient(String serverAddress, int serverPort) {
        try {
            System.out.print("Enter name of the Restaurant: ");
            Scanner scanner = new Scanner(System.in);
            //scanner.nextLine();
            String clientName = scanner.nextLine();
            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(clientName);
            Restaurant res=(Restaurant)socketWrapper.read();
            res.ShowRestaurant();
            for(FoodItems x: res.foodList)
            {
                x.ShowFood();
            }
            new ReadThreadClient(socketWrapper);
            new WriteThreadClient(socketWrapper, clientName);
              //scanner.close();
        } catch (Exception e) {
            System.out.println(e);
        }
      
    }

    public static void main(String args[]) {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        new  RestaurantClient(serverAddress, serverPort);
    }
}
