import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import util.SocketWrapper;

public class Client {
    public RestaurantManager restaurantManager;

    public Client(String serverAddress, int serverPort) throws Exception {
        //try {
           // System.out.print("Enter name of the client: ");
            //Scanner scanner = new Scanner(System.in);
            String clientName = "Customer";
            SocketWrapper socketWrapper = new SocketWrapper(serverAddress, serverPort);
            socketWrapper.write(clientName);
            restaurantManager=(RestaurantManager)socketWrapper.read();
         //   System.out.println("Today is my birthday");
            Scanner scanner2=new Scanner(System.in);
        RestaurantData data= new RestaurantData();
            while(true){
                //System.out.println();
                System.out.println();
                System.out.println("Main Menu ");
                System.out.println("1.Search Restaurants ");
                System.out.println("2.Search Food items");
              //  System.out.println("3.Add Restaurant ");
              //  System.out.println("4.Add Food Item to The Menu");
                System.out.println("3.Order Food");
                System.out.println("4.Exit System");

                int option1= scanner2.nextInt();
                if(!(option1>=1&&option1<=5)){
                    System.out.println("Enter Valid Option Of Main Menu(1-5)");
                }

                if(option1==1)
                {

                    while(true){
                        // System.out.println();
                        System.out.println();
                        System.out.println("Restaurant Searching Options");
                        System.out.println("1.By name");
                        System.out.println("2.By Score");
                        System.out.println("By Category");
                        System.out.println("4.By Price");
                        System.out.println("5.By Zipcode");
                        System.out.println("6.Different Category Wise List of Restaurants");
                        System.out.println("7.Back to Main Menu");
                        int option2=scanner2.nextInt();
                        if(!(option2>=1&&option2<=7))
                            System.out.println("Enter valid Option for Option 1 of The Main Menu");
                        else{

                            if(option2==1){
                                System.out.println("Enter Restaurant Name: ");
                                String Name=scanner2.next();
                                ArrayList<Restaurant> p=new ArrayList<Restaurant>();
                                p= restaurantManager.searchRestaurentByName(Name);
                                if(p.isEmpty()){
                                    System.out.println("No such restaurant with this name");
                                }
                                else{
                                    for(Restaurant r: p){
                                        r.ShowRestaurant();
                                    }
                                }

                            }
                            else if(option2==2)
                            {
                                System.out.println("Enter Restaurant Score: ");
                                double score =scanner2.nextDouble();
                                ArrayList<Restaurant>p=new ArrayList<Restaurant>();
                                p= restaurantManager.searchRestaurentByScore(score);
                                if(p.isEmpty()){
                                    System.out.println("No such restaurant with this score");
                                }
                                else{
                                    for(Restaurant r: p){
                                        r.ShowRestaurant();
                                    }
                                }

                            }
                            else if(option2==3)
                            {
                                System.out.println("Enter Restaurant Category: ");
                                String Category=scanner2.next();
                                ArrayList<Restaurant>p=new ArrayList<Restaurant>();
                                p= restaurantManager.searchRestaurantByCategory(Category);
                                if(p.isEmpty()){
                                    System.out.println("No such restaurant with this category");
                                }
                                else{
                                    for(Restaurant r: p){
                                        r.ShowRestaurant();
                                    }
                                }
                            }
                            else if(option2==4)
                            {
                                System.out.println("Enter Restaurant Price: ");
                                String price=scanner2.next();
                                ArrayList<Restaurant>p=new ArrayList<Restaurant>();
                                p= restaurantManager.searchRestaurantByPrice(price);
                                if(p.isEmpty()){
                                    System.out.println("No such restaurant with this price");
                                }
                                else{
                                    for(Restaurant r: p){
                                        r.ShowRestaurant();
                                    }
                                }

                            }
                            else if(option2==5)
                            {
                                System.out.println("Enter Restaurant Zipcode: ");
                                String Zipcode=scanner2.next();
                                ArrayList<Restaurant>p=new ArrayList<Restaurant>();
                                p= restaurantManager.searchRestaurentByZipcode(Zipcode);

                                if(p.isEmpty()){
                                    System.out.println("No such restaurant with this Zipcode");
                                }
                                else{
                                    for(Restaurant r: p){
                                        r.ShowRestaurant();
                                    }
                                }
                            }
                            else if(option2==6)
                            {
                                ArrayList<String>p=new ArrayList<String>();
                                p=restaurantManager.getListOfCategories();
                                for(String s: p){
                                    ArrayList<Restaurant>a=new ArrayList<Restaurant>();
                                    a=restaurantManager.searchRestaurantByCategory(s);
                                    //System.out.println();
                                    System.out.print(s+": ");
                                    for(Restaurant r:a){
                                        System.out.print(r.getName()+",");
                                    }
                                    System.out.println();

                                }
                            }
                            else if(option2==7)
                            {
                                break;
                            }

                        }
                    }
                }
                else if(option1==2)
                {
                    while(true){
                        // System.out.println();
                        System.out.println();
                        System.out.println("Food Item Searching Options: ");
                        System.out.println("1.By name");
                        System.out.println("2.By Name in a Given Restaurant");
                        System.out.println("3.By Category");
                        System.out.println("4.By Category in a Given Restaurant");
                        System.out.println("5.By Price Range");
                        System.out.println("6.By Price Range in a Given Restaurant");
                        System.out.println("7. Costliest Food Item(s) on the Menu in a Given Restaurant");
                        System.out.println("8. List of Restaurants and Total Food Item on the Menu");
                        System.out.println("9. Back to Main Menu");
                        int option2= scanner2.nextInt();
                        if(option2>=1&&option2<=9){

                            if(option2==1)
                            {
                                System.out.println("Enter Food Name :");
                                String name= scanner2.next();
                                ArrayList<FoodItems> p=new ArrayList<FoodItems>();
                                p= restaurantManager.searchFoodByName(name);
                                if(p.isEmpty())
                                {
                                    System.out.println("No such food item with this name");
                                }
                                else{
                                    System.out.println();
                                    for(FoodItems f: p){
                                        f.ShowFood();
                                    }
                                }
                            }
                            else if(option2==2){
                                System.out.println("Enter Food Name :");
                                String foodname=scanner2.next();
                                System.out.println("Enter Restaurant Name :");
                                String restaurantname=scanner2.next();
                                ArrayList<FoodItems>p=new ArrayList<FoodItems>();
                                p=restaurantManager.searchFoodByRestaurantName(foodname,restaurantname);
                                if(p.isEmpty()){
                                    System.out.println("No such food item with this name on the menu of the restaurant");
                                }
                                else{
                                    System.out.println();
                                    for(FoodItems f:p){
                                        f.ShowFood();
                                    }
                                }
                            }
                            else if(option2==3)
                            {
                                System.out.println("Enter Food Category :");

                                String Category= scanner2.next();
                                ArrayList<FoodItems> p=new ArrayList<FoodItems>();
                                p= restaurantManager.searchFoodByCategory(Category);
                                if(p.isEmpty())
                                {
                                    System.out.println("No such food item with this category");
                                }
                                else{
                                    System.out.println();
                                    for(FoodItems f: p){
                                        f.ShowFood();
                                    }
                                }
                            }
                            else if(option2==4)
                            {
                                System.out.println("Enter Food Category :");

                                String foodcategory=scanner2.next();
                                System.out.println("Enter Restaurant Name: ");
                                String restaurantname=scanner2.next();
                                ArrayList<FoodItems>p=new ArrayList<FoodItems>();
                                p=restaurantManager.searchFoodByRestaurantCategory(foodcategory,restaurantname);
                                if(p.isEmpty()){
                                    System.out.println("No such food item with this category on the menu of the restaurant");
                                }
                                else{
                                    System.out.println();
                                    for(FoodItems f:p){
                                        f.ShowFood();
                                    }
                                }

                            }
                            else if(option2==5)
                            {
                                System.out.println("Enter Price Range  :");
                                double n1=scanner2.nextDouble();
                                double n2=scanner2.nextDouble();

                                ArrayList<FoodItems>p=new ArrayList<FoodItems>();
                                p=restaurantManager.searchFoodByPrice(n1,n2);
                                if(p.isEmpty()){
                                    System.out.println("No such food item with this price range");
                                }
                                else{
                                    System.out.println();
                                    for(FoodItems f:p){
                                        f.ShowFood();
                                    }
                                }

                            }
                            else if(option2==6)
                            {
                                System.out.println("Enter Price Range  :");

                                double n1=scanner2.nextDouble();
                                double n2=scanner2.nextDouble();
                                System.out.println("Enter Restaurant Name");
                                String restaurantname=scanner2.next();

                                ArrayList<FoodItems>p=new ArrayList<FoodItems>();
                                p=restaurantManager.searchFoodByPriceInRestaurant(n1,n2,restaurantname);
                                if(p.isEmpty()){
                                    System.out.println("No such food item with this price range on the menu of this restaurant");
                                }
                                else{
                                    System.out.println();
                                    for(FoodItems f:p){
                                        f.ShowFood();
                                    }
                                }
                            }
                            else if(option2==7)
                            {
                                System.out.println("Enter Restaurant Name");
                                String restaurantname=scanner2.next();
                                if(!(restaurantManager.isRestaurantPresent(restaurantname)))
                                    System.out.println("There is no such restaurant with this name");
                                else{
                                    ArrayList<FoodItems>p=new ArrayList<FoodItems>();
                                    p=restaurantManager.Items(restaurantname);
                                    ArrayList<FoodItems>q=new ArrayList<FoodItems>();
                                    q=restaurantManager.CostlyItem(p);
                                    System.out.println("Costliest food items in the restaurant "+restaurantname);
                                    for(FoodItems r: q){
                                        r.ShowFood();
                                    }

                                }
                            }
                            else if(option2==8)
                            {
                                ArrayList<Restaurant>p=new ArrayList<Restaurant>();
                                p=restaurantManager.getRestaurantList();
                                for(Restaurant r:p){
                                    ArrayList<FoodItems>q=new ArrayList<FoodItems>();
                                    q=restaurantManager.Items(r.getName());
                                    System.out.print(r.getName()+" : "+q.size());
                                    System.out.println();
                                }
                            }
                            else if(option2==9)
                            {
                                break;
                            }

                        }
                        else
                            System.out.println("Enter options between 1 and 9");
                    }
                }


               /* else if(option1==3)
                {
                    System.out.println("Enter Restaurant Id: ");
                    int id= scanner2.nextInt();
                    System.out.println("Restaurant Name :");
                    String name=scanner2.next();
                    System.out.println("Enter Restaurant Score : ");
                    double score=scanner2.nextDouble();
                    System.out.println("Enter Restaurant Price : ");
                    String price=scanner2.next();
                    System.out.println("Enter Restaurant Zipcode : ");
                    String zipcode=scanner2.next();
                    scanner2.nextLine();
                    System.out.println("Enter Restaurant Categories : ");
                    String Line=scanner2.nextLine();
                    String[] arr= Line.split("," ,-1);
                    ArrayList<String> Category=new ArrayList<>();
                    for(int i=0;i<arr.length;i++){
                        Category.add(arr[i]);
                    }


                    Restaurant r= new Restaurant(id, name, score, price, zipcode, Category);
                    restaurantManager.addRestaurant(r);

                }*/
               /*else if(option1==4)
                {
                    System.out.println("Enter Restaurant Name : ");
                    String restaurantname=scanner2.next();
                    boolean b= restaurantManager.isRestaurantPresent(restaurantname);
                    if(!b){
                        System.out.println("There is no such restaurant with this name");
                    }
                    else{
                        int id=-1;
                        for(Restaurant r: restaurantManager.getRestaurantList()){
                            if(r.getName()==restaurantname){
                                id=r.getId();
                                break;
                            }
                        }
                        scanner2.nextLine();
                        System.out.println("Enter Food Category : ");
                        String Category=scanner2.nextLine();
                        scanner2.nextLine();
                        System.out.println("Enter Food Name : ");
                        //scanner2.nextLine();
                        String Name= scanner2.nextLine();
                        System.out.println("Enter Food Price : ");
                        double price= scanner2.nextDouble();
                        FoodItems f=new FoodItems(id, Category, Name, price);
                        restaurantManager.addFood(f);
                    }
                }*/
                else if(option1==4)
                {
                    //restaurantManager.FileWriter();
                    break;



                }

                else if (option1==3)
                {

                    System.out.println("Enter the name of restaurant : ");
                    scanner2.nextLine();

                    String resName=scanner2.nextLine();
                    Restaurant p = restaurantManager.searchbyname(resName);
                    ArrayList<FoodItems>OrderedFoods=new ArrayList<>();
                    ArrayList<FoodItems>RestaurantFoods=p.foodList;
                    int i=1;
                    for(FoodItems f:RestaurantFoods){
                      System.out.println("Item no "+i);
                      f.ShowFood();
                      i++;
                  }
                    System.out.println("Enter the serial no of desired foods: and  -1 to termainate");
                  int a=-1;
                    while(true)
                    {
                        a=scanner2.nextInt();
                        if(a==-1) break;
                        else
                        OrderedFoods.add(RestaurantFoods.get(a-1));

                    }

                    data.setFlist(OrderedFoods);
                    data.setRestaurantName(resName);
                    socketWrapper.write(data);



                }









            }scanner2.close();

        }
//        catch (ClassNotFoundException ex) {
//            throw new RuntimeException(ex);
//        }


        // new ReadThread(socketWrapper);
          //  new WriteThreadClient(socketWrapper, clientName);

//        } catch (Exception e) {
//            System.out.println(e);
//        }



    public static void main(String args[]) throws Exception {
        String serverAddress = "127.0.0.1";
        int serverPort = 33333;
        new Client(serverAddress, serverPort);
    }
}