package project.csc895.sfsu.waitless;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import project.csc895.sfsu.waitless.model.Restaurant;

public class Test_sendDataToFirebase {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public void writeNewRestaurant(String id, String name, ArrayList<String> cuisineTags) {
        Restaurant restaurant = new Restaurant(name, cuisineTags);
        mDatabase.child("restaurants").child(id).setValue(restaurant);
    }

}
