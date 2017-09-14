package project.csc895.sfsu.waitless.model;


import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private String password;
    private String telephone;
    private String photoUrl;
    private ArrayList<String> favorites; // store restaurant_ids

    public User(String username, String email, String password,
                String telephone, String photoUrl, ArrayList<String> favorites) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.telephone = telephone;
        this.photoUrl = photoUrl;
        this.favorites = favorites;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public String getTelephone() {
        return this.telephone;
    }
    public String getPhotoUrl() {
        return this.photoUrl;
    }
    public ArrayList<String> getFavorites() {
        return this.favorites;
    }

}
