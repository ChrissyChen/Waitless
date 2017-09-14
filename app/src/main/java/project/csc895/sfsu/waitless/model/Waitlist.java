package project.csc895.sfsu.waitless.model;

public class Waitlist {
    private String restaurantID;
    private String userID;
    private String waitlistNumber; // based on tableSize, A1, B2...
    private String partyNumber; // tableSize TODO: 9/14/17
    private boolean hasShownUp;  //// TODO: 9/14/17

    public Waitlist(String restaurantID, String userID,
                    String waitlistNumber, String partyNumber, boolean showUp) {
        this.restaurantID = restaurantID;
        this.userID = userID;
        this.waitlistNumber = waitlistNumber;
        this.partyNumber = partyNumber;
        this.hasShownUp = showUp;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getWaitlistNumber() {
        return waitlistNumber;
    }

    public void setWaitlistNumber(String waitlistNumber) {
        this.waitlistNumber = waitlistNumber;
    }

    public String getPartyNumber() {
        return partyNumber;
    }

    public void setPartyNumber(String partyNumber) {
        this.partyNumber = partyNumber;
    }

    public boolean isHasShownUp() {
        return hasShownUp;
    }

    public void setHasShownUp(boolean hasShownUp) {
        this.hasShownUp = hasShownUp;
    }
}


