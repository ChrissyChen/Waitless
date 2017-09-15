package project.csc895.sfsu.waitless.model;


import java.util.ArrayList;

public class User {
    private String userID;
    private String username;
    private String email;
    private String password;
    private String telephone;
    private String photoUrl;
    private ArrayList<String> favorites; // store restaurant_ids

    public User(String userID, String username, String email, String password,
                String telephone, String photoUrl, ArrayList<String> favorites) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.photoUrl = photoUrl;
        this.favorites = favorites;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }
}
