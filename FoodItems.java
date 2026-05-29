import java.io.Serializable;

public class FoodItems implements Serializable {
    private int restaurantId;
    private String category;
    private String name;
    private double price;


    public FoodItems(int restaurantId, String category, String name, double price) {
        this.restaurantId = restaurantId;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void ShowFood(){
        System.out.println("Restaurant ID : "+restaurantId);
        System.out.println("Category : "+category);
        System.out.println("Name : "+name);
        System.out.println("Price : "+price);
        System.out.println("");
    }
}
