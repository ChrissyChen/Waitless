package project.csc895.sfsu.waitless.model;

public class Table {
    private String restaurantID;
    private String orderID;
    private int tableSize;
    boolean isOccupied;
    private String tableName;

    public Table(String restaurantID, String orderID, int tableSize, boolean isOccupied, String tableName) {
        this.restaurantID = restaurantID;
        this.orderID = orderID;
        this.tableSize = tableSize;
        this.isOccupied = isOccupied;
        this.tableName = tableName;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
