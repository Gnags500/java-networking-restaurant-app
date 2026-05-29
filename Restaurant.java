import java.io.Serializable;
import java.util.ArrayList;


public class Restaurant implements Serializable {
    private int id;
    private String name;
    private double score;
    private String price;
    private String zipcode;
    private ArrayList<String> categories;
    public ArrayList<FoodItems>  foodList;
   // private int noOfCategories;

    public Restaurant(int  id, String name, double score, String price,String zipcode,ArrayList<String>category) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.price = price;
        this.zipcode=zipcode;
        categories=new ArrayList<String>();
        categories.addAll(category);
        foodList=new ArrayList<FoodItems>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void addCategory(String s)
    {
        categories.add(s);
    }
    public String getZipcode(){
        return zipcode;
    }
    public boolean isCategory(String S){
        for(String s:categories){
            if(s.toUpperCase().contains(S)){
                return true;

            }
        }
        return false;
    }

    public ArrayList<FoodItems> getFoodList() {
        return foodList;
    }

    public ArrayList<String> getCategories(){
        return categories;
    }
    public void ShowRestaurant(){
        System.out.println("ID : "+id);
        System.out.println("Name : "+name);
        System.out.println("Score : "+score);
        System.out.println("Price : "+price);
        System.out.println("Zipcode : "+zipcode);
       // System.out.println();
        for(String s: categories){
            System.out.print(s+" , ");
        }
        System.out.println("The foods present in the restaurant "+ getName());
        for(FoodItems f: foodList){
            System.out.println(f.getName()+ " ");
        }
        System.out.println();
    }

}