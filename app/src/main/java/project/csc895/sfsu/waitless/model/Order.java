package project.csc895.sfsu.waitless.model;

import java.util.Date;
import java.util.HashMap;

public class Order {
    private String orderID;
    private String userID;
    private String restaurantID;
    private HashMap<String, Integer> oderDetails; //dishID: quantity
    private double totalCost;
    private Date createDate;

    public Order(String orderID, String userID, String restaurantID,
                 HashMap<String, Integer> oderDetails, double totalCost,
                 Date createDate) {
        this.orderID = orderID;
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.oderDetails = oderDetails;
        this.totalCost = totalCost;
        this.createDate = createDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public HashMap<String, Integer> getOderDetails() {
        return oderDetails;
    }

    public void setOderDetails(HashMap<String, Integer> oderDetails) {
        this.oderDetails = oderDetails;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
