

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import util.SocketWrapper;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String, SocketWrapper> clientMap; // HashMap of client's name and socket information
    RestaurantManager manager=new RestaurantManager();

    Server() {
        clientMap = new HashMap<>();
        try {
            manager.FoodListInput();
            manager.ResInput();
            manager.addFoodInRestaurant();
//            Restaurant res=manager.searchRestaurentByName ("kfc").get(0);
//            res.ShowRestaurant();
           /* FoodItems item= manager.searchFoodByName("chicken").get(0);
            item.ShowFood();*/


            serverSocket = new ServerSocket(33333);
            new WriteThreadServer(clientMap, "Server");
            while (true) {
               /* System.out.println("Press your desired option(if any)");
                System.out.println("1.Add restaurant");
                System.out.println("2.Add food ");
                System.out.println("3.None");
                Scanner scanner=new Scanner(System.in);
                int option=scanner.nextInt();
                if(option==1){
                    System.out.println("Enter Restaurant Id: ");
                    int id= scanner.nextInt();
                    System.out.println("Restaurant Name :");
                    String name=scanner.next();
                    System.out.println("Enter Restaurant Score : ");
                    double score=scanner.nextDouble();
                    System.out.println("Enter Restaurant Price : ");
                    String price=scanner.next();
                    System.out.println("Enter Restaurant Zipcode : ");
                    String zipcode=scanner.next();
                    System.out.println("Enter Restaurant Categories : ");
                    String Line=scanner.nextLine();
                    String[] arr= Line.split("," ,-1);
                    ArrayList<String> Category=new ArrayList<>();
                    for(int i=0;i<arr.length;i++){
                        Category.add(arr[i]);
                    }


                    Restaurant r= new Restaurant(id, name, score, price, zipcode, Category);
                    manager.addRestaurant(r);
                    manager.addFoodInRestaurant();
                }

                else if(option==2)
                {
                    System.out.println("Enter Restaurant Name : ");
                    String restaurantname=scanner.next();
                    boolean b= manager.isRestaurantPresent(restaurantname);
                    if(!b){
                        System.out.println("There is no such restaurant with this name");
                    }
                    else{
                        int id=-1;
                        for(Restaurant r: manager.getRestaurantList()){
                            if(r.getName()==restaurantname){
                                id=r.getId();
                                break;
                            }
                        }
                        scanner.nextLine();
                        System.out.println("Enter Food Category : ");
                        String Category=scanner.nextLine();
                        scanner.nextLine();
                        System.out.println("Enter Food Name : ");
                        //scanner2.nextLine();
                        String Name= scanner.nextLine();
                        System.out.println("Enter Food Price : ");
                        double price= scanner.nextDouble();
                        FoodItems f=new FoodItems(id, Category, Name, price);
                        manager.addFood(f);
                    }
                    manager.addFoodInRestaurant();
                }
                else if(option==3){
                    System.out.println("No restaurants or foods added");
                }
                else{
                    System.out.println("Enter vallid option");
                }*/

                Socket clientSocket = serverSocket.accept();
               // System.out.println("Restaurant Client accepted");
                SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
                String clientName = (String) socketWrapper.read();
                if(clientName.equalsIgnoreCase("Customer"))
                {
                   // System.out.println("serving customer");
                    serveCustomer(clientSocket,socketWrapper);

                }
               // serve(clientSocket,clientName,socketWrapper);
                serve(clientName,socketWrapper);

            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

//    public void serve(Socket clientSocket,String clientName,SocketWrapper socketWrapper) throws Exception, ClassNotFoundException {
//        //SocketWrapper socketWrapper = so;
//       // String clientName = (String) socketWrapper.read();
//       // Restaurant temp=manager.searchRestaurentByName (clientName).get(0);
//        Restaurant temp=manager.searchbyname(clientName);
//        socketWrapper.write(temp);
//        clientMap.put(clientName, socketWrapper);
//        new ReadThread(socketWrapper,clientMap);
//    }

    public void serve(String clientName,SocketWrapper socketWrapper) throws Exception, ClassNotFoundException {
        //SocketWrapper socketWrapper = so;
        // String clientName = (String) socketWrapper.read();
        // Restaurant temp=manager.searchRestaurentByName (clientName).get(0);
        Restaurant temp=manager.searchbyname(clientName);
        socketWrapper.write(temp);
        clientMap.put(clientName, socketWrapper);
      //  new ReadThread(socketWrapper,clientMap);
    }
    public void serveCustomer(Socket clientSocket,SocketWrapper socketWrapper) throws Exception, ClassNotFoundException {
       // SocketWrapper socketWrapper1 = socketWrapper;
        //String clientName = (String) socketWrapper.read();
        // Restaurant temp=manager.searchRestaurentByName (clientName).get(0);
       // Restaurant temp=manager.searchbyname(clientName);
       // socketWrapper.write(temp);
        //clientMap.put(clientName, socketWrapper);
        socketWrapper.write(manager);
        new ReadThread(socketWrapper,clientMap);

    }

    public static void main(String args[]) {
        new Server();
    }
}
