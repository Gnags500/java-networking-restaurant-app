import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantData implements Serializable {
    String restaurantName;
    ArrayList<FoodItems>flist=new ArrayList<>();
    public RestaurantData(String name,ArrayList<FoodItems>flist)
    {
        this.restaurantName=name;
        this.flist=flist;
    }

    public RestaurantData() {

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public ArrayList<FoodItems> getFlist() {
        return flist;
    }

    public void setFlist(ArrayList<FoodItems> flist) {
        this.flist = flist;
    }
}
