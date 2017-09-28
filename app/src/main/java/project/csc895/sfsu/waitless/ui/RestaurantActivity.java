package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "Restaurant Activity";
    private Restaurant restaurant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        restaurant = (Restaurant)intent.getSerializableExtra(SearchActivity.EXTRA_RESTAURANT);
        Log.d(TAG, restaurant.toString());
    }
}