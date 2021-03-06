package project.csc895.sfsu.waitless.model;

public class Number {

    private String numberID;
    private String restaurantID;
    private String restaurantName;
    private String tableID; // if number status is dining, then tableID would indicate which table is seated. Other status would be null
    private String userID;
    private String username;
    private String phone;
    private String email;
    private String timeCreated;
    private String numberName; // based on tableSize, A1, B2...
    private int partySize;
    private String status;   // waiting, dining, canceled, completed
    private boolean isHidden; //when set to true, then load list will not show this number in the list

    public Number() {
    }

    public Number(String numberID, String restaurantID, String restaurantName, String tableID, String userID, String username, String phone, String email, String timeCreated, String numberName, int partySize, String status, boolean isHidden) {
        this.numberID = numberID;
        this.restaurantID = restaurantID;
        this.restaurantName = restaurantName;
        this.tableID = tableID;
        this.userID = userID;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.timeCreated = timeCreated;
        this.numberName = numberName;
        this.partySize = partySize;
        this.status = status;
        this.isHidden = isHidden;
    }

    public String getNumberID() {
        return numberID;
    }

    public void setNumberID(String numberID) {
        this.numberID = numberID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getNumberName() {
        return numberName;
    }

    public void setNumberName(String numberName) {
        this.numberName = numberName;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}