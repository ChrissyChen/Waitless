package project.csc895.sfsu.waitless.model;

public class Dish {
    private String name;
    private double price;
    private String restaurantID;
//    private String description;
//    private String imageUrl;


    public Dish(String name, double price, String restaurantID) {
        this.name = name;
        this.price = price;
        this.restaurantID = restaurantID;
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

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }
}
