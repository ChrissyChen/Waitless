package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "Restaurant Activity";
    public final static String EXTRA_RESTAURANT_NAME = "Pass Restaurant Name";
    private static final String RESTAURANT_CHILD = "restaurants";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String restaurantID;
    private ImageView mRestaurantImage;
    private TextView mRestaurantName, mCuisines, mOperateStatus, mRestaurantAddress, mRestaurantPhone;
    private Button mGetNumberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        /* Pass a restaurant object. but the obj info will get changed at real time. eg available tables. so change to pass id */
        //Restaurant restaurant = (Restaurant)intent.getSerializableExtra(SearchActivity.EXTRA_RESTAURANT);
        //Log.d(TAG, restaurant.toString());
        restaurantID = intent.getStringExtra(SearchActivity.EXTRA_RESTAURANT_ID);
        Log.d(TAG + " ID:", restaurantID);

        mRestaurantImage = (ImageView) findViewById(R.id.restaurantImageView);
        mRestaurantName = (TextView) findViewById(R.id.name);
        mCuisines = (TextView) findViewById(R.id.cuisine);
        mOperateStatus = (TextView) findViewById(R.id.operateStatusTextView);
        mRestaurantAddress = (TextView) findViewById(R.id.restaurantAddress);
        mRestaurantPhone = (TextView) findViewById(R.id.restaurantTelephone);

        mGetNumberButton = (Button) findViewById(R.id.getNumberButton);
        mGetNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, GetNumberActivity.class);
                intent.putExtra(SearchActivity.EXTRA_RESTAURANT_ID, restaurantID);
                //intent.putExtra(EXTRA_RESTAURANT_NAME, restaurant.getName());
                v.getContext().startActivity(intent);
            }
        });

        loadRestaurantValue();
    }

    private void loadRestaurantValue() {
        DatabaseReference ref = mDatabase.child(RESTAURANT_CHILD).child(restaurantID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                if (restaurant != null) {
                    String name = restaurant.getName();
                    String address = restaurant.getAddress();
                    String phone = restaurant.getTelephone();
                    String cuisine = restaurant.getCuisine();
                    // TODO get image
                    Log.d("name: ", name);
                    Log.d("address: ", address);
                    Log.d("phone: ", phone);
                    Log.d("cuisine: ", cuisine);
                    mRestaurantName.setText(name);
                    mCuisines.setText(cuisine);
                    mRestaurantAddress.setText(address);
                    mRestaurantPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}