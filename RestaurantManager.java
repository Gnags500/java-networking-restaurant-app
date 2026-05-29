import java.io.*;
import java.util.ArrayList;



public class RestaurantManager implements Serializable {
    private ArrayList<Restaurant>restaurantList;
    private ArrayList <FoodItems>foodlists;
    public RestaurantManager(ArrayList<Restaurant>r,ArrayList<FoodItems>f)
    {
       restaurantList=new ArrayList<Restaurant>();
       foodlists=new ArrayList<FoodItems>();
       restaurantList=r;
       foodlists=f;
    }
    public RestaurantManager()
    {
          restaurantList=new ArrayList<Restaurant>();
       foodlists=new ArrayList<FoodItems>();

    }
    public void ResInput() throws IOException{
        
     String INPUT_FILE_NAME = "restaurant.txt";
    //private static final String OUTPUT_FILE_NAME = "out.txt";

   // public static void main(String[] args) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                //System.out.println(line);
                String[] array = line.split(",", -1);
           /* for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }*/
                int id = Integer.parseInt(array[0]);
                String name = array[1];
                double score = Double.parseDouble(array[2]);
                String price = array[3];
                String zipcode = array[4];
                ArrayList<String> Category = new ArrayList<String>();
                for (int i = 5; i < array.length; i++) {
                    Category.add(array[i]);
                }
                Restaurant r = new Restaurant(id, name, score, price, zipcode, Category);
                restaurantList.add(r);
            }
            br.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

        //String text = "Hello World";
        //BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
       // bw.write(text);
        //bw.write(System.lineSeparator());
       // bw.close();
    }
        public void FoodListInput() throws IOException{
        
     String INPUT_FILE_NAME = "menu.txt";
    //private static final String OUTPUT_FILE_NAME = "out.txt";

   // public static void main(String[] args) throws Exception {
            try {
                BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
                while (true) {
                    String line = br.readLine();
                    if (line == null) break;
                    // System.out.println(line);
                    String[] array = line.split(",", -1);
           /* for (int i = 0; i < array.length; i++) {
                System.out.println(array[i]);
            }*/
                    int id = Integer.parseInt(array[0]);
                    String Category = array[1];
                    String name = array[2];
                    double price = Double.parseDouble(array[3]);

                    FoodItems f = new FoodItems(id, Category, name, price);
          /*  Restaurant r = null;
           // Restaurant r=getRestaurant(id);
            for(Restaurant x:restaurantList)
            {
                if(x.getId()==id)
                {
                    r=x;
                }
            }
            r.foodList.add(f);*/
                    foodlists.add(f);
                }
                br.close();
            }catch (Exception e){
                System.out.println(e);
            }

        //String text = "Hello Wo
    }

    public ArrayList<Restaurant> getRestaurantList(){
        return restaurantList;
    }
    public ArrayList<FoodItems> getfoodtList(){
        return foodlists;
    }
    public ArrayList<Restaurant> searchRestaurentByName (String S){
        ArrayList<Restaurant>p=new ArrayList<Restaurant>();
        for(Restaurant r:restaurantList){
            if(r.getName().toUpperCase().contains(S.toUpperCase())){
                p.add(r);
            }
        }
        return p;
    }
    public Restaurant searchbyname(String name)
    {
        for(Restaurant r:restaurantList){
            if(r.getName().equalsIgnoreCase(name)){
                return r;
            }
        }
        return null;

    }


    public ArrayList<Restaurant> searchRestaurentByScore(double S){
        ArrayList<Restaurant>p=new ArrayList<Restaurant>();
        for(Restaurant r:restaurantList){
            if(r.getScore()==S){
                p.add(r);
            }
        }
        return p;
    }


    public ArrayList<Restaurant> searchRestaurentByZipcode(String S){
        ArrayList<Restaurant>p=new ArrayList<Restaurant>();
        for(Restaurant r:restaurantList){
            if(r.getZipcode().equals(S)){
                p.add(r);
            }
        }
        return p;
    }
    
    public ArrayList<Restaurant> searchRestaurantByPrice(String S){
        ArrayList<Restaurant> list=new ArrayList<Restaurant>();
        for(Restaurant r:restaurantList){
            if(r.getPrice().equals(S))
            list.add(r);
        }
        return list;
    }


     public ArrayList<Restaurant> searchRestaurantByCategory(String S){
        ArrayList<Restaurant>p=new ArrayList<Restaurant>();
        for(Restaurant r:restaurantList){
            if(r.isCategory(S))
            p.add(r);
        }
        return p;
    }


    public ArrayList<String> getListOfCategories(){
        ArrayList<String>UniqueCategory= new ArrayList<String>();
        for(Restaurant r:restaurantList){
            for(String s:r.getCategories()){
                if(!(UniqueCategory.contains(s)))
                UniqueCategory.add(s);
            }
        }
        return UniqueCategory;
    }

    

    public ArrayList<FoodItems> searchFoodByName(String S){
        ArrayList<FoodItems>list=new ArrayList<FoodItems>();
        for(FoodItems f:foodlists){
            if(f.getName().toUpperCase().contains(S.toUpperCase()))
            list.add(f);
        }
        return list;
    }


    public ArrayList<FoodItems> searchFoodByRestaurantName (String S, String RestaurantName ){
        ArrayList<FoodItems>list=new ArrayList<FoodItems>();

        for(Restaurant r:restaurantList){
            if(r.getName().equalsIgnoreCase(RestaurantName)){
                for(FoodItems f:foodlists){
                    if(f.getName().toLowerCase().contains(S)&&f.getRestaurantId()==r.getId())
                    list.add(f);
                }
            }
        }
        return list;
    }


    public ArrayList<FoodItems> searchFoodByCategory(String S){
        ArrayList<FoodItems>list=new ArrayList<FoodItems>();
        for(FoodItems f: foodlists){
            if(f.getCategory().toUpperCase().contains(S))
            list.add(f);
        }
        return list;
    }

    public void addFoodInRestaurant()
    {
        for(FoodItems f: foodlists){
          for(Restaurant r: restaurantList) {
              if (f.getRestaurantId() == r.getId()) {
                  r.foodList.add(f);
                  break;
              }
          }
        }
    }

    public Restaurant getRestaurant(int id)
    {
        for(Restaurant x:restaurantList)
        {
            if(x.getId()==id)
            {
                return  x;
            }
        }
        return null;
    }


     public ArrayList<FoodItems> searchFoodByRestaurantCategory(String S, String RestaurantName ){
        ArrayList<FoodItems>list=new ArrayList<FoodItems>();

        for(Restaurant r:restaurantList){
            if(r.getName().equalsIgnoreCase(RestaurantName)){
                for(FoodItems f:foodlists){
                    if(f.getCategory().toLowerCase().contains(S)&&f.getRestaurantId()==r.getId())
                    list.add(f);
                }
            }
        }
        return list;
    }


   public ArrayList<FoodItems> searchFoodByPrice(double n1,double n2){
    ArrayList<FoodItems>list= new ArrayList<FoodItems>();
    for(FoodItems f: foodlists){
        if(f.getPrice()>n1 && f.getPrice()<n2){
            list.add(f);
        }
    }
    return list;
   }

   public ArrayList<FoodItems> searchFoodByPriceInRestaurant(double n1,double n2,String RestaurantName){
    ArrayList<FoodItems>list= new ArrayList<FoodItems>();
    for(Restaurant r:restaurantList){
        if(r.getName().toUpperCase().contains(RestaurantName)){
    for(FoodItems f: foodlists){
        if(f.getPrice()>n1 && f.getPrice()<n2 &&f.getRestaurantId()==r.getId()){
            list.add(f);
        }
    }
}
    }
    return list;
   }


   public ArrayList<FoodItems> Items(String RestaurantName){
    ArrayList<FoodItems>list=new ArrayList<FoodItems>();
    int resId=-1 ;
    for(Restaurant r: restaurantList){
        if(r.getName().equalsIgnoreCase(RestaurantName))
        resId=r.getId();
    }
    for(FoodItems f: foodlists){
        if(f.getRestaurantId()==resId){
            list.add(f);
        }
    }
    return list;
   }

   public ArrayList<FoodItems> CostlyItem(ArrayList<FoodItems>Restaurantfood){
    ArrayList<FoodItems> list= new ArrayList<FoodItems>();
    double max= Restaurantfood.get(0).getPrice();
    for(FoodItems f:Restaurantfood ){
        if(max<f.getPrice())
       
        max=f.getPrice();
    }
    for(FoodItems f:Restaurantfood){
        if(f.getPrice()==max)
        list.add(f);
    }
    return list;

   }
   public void addRestaurant(Restaurant r){
    restaurantList.add(r);
   }


   public boolean isRestaurantPresent(String RestaurantName){
    for(Restaurant r: restaurantList){
        if(r.getName().equalsIgnoreCase(RestaurantName))
        return true;
    }
    return false;
   }


   public void addFood(FoodItems f ){
    foodlists.add(f);
   }

   public void FileWriter() throws IOException{
    BufferedWriter bw = new BufferedWriter(new FileWriter("restaurant.txt"));
                    ArrayList<Restaurant> r =new ArrayList<Restaurant>();
                    r=restaurantList;
                    for(Restaurant p: r){
                        bw.write(p.getId()+","+p.getName()+","+p.getScore()+","+p.getPrice()+","+p.getZipcode()+",");
                        for(String s: p.getCategories()){
                            bw.write(s+",");
                        }
                        bw.write(System.lineSeparator());
                    }
                    BufferedWriter bw2=new BufferedWriter(new FileWriter("menu.txt"));
                    ArrayList<FoodItems>f= new ArrayList<FoodItems>();
                    f= foodlists;
                    for(FoodItems q:f){
                        bw2.write(q.getRestaurantId()+","+q.getCategory()+","+q.getName()+","+q.getPrice());
                        bw2.write(System.lineSeparator());
                    }
                    bw.close();
                    bw2.close();
   }

}
   




   

   


