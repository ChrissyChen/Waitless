package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "Restaurant Activity";
    private Restaurant restaurant;
    private ImageView mRestaurantImage;
    private TextView mRestaurantName, mCuisines, mOperateStatus;
    private Button mGetNumberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        restaurant = (Restaurant)intent.getSerializableExtra(SearchActivity.EXTRA_RESTAURANT);
        Log.d(TAG, restaurant.toString());

        mRestaurantImage = (ImageView) findViewById(R.id.restaurantImageView);
        mRestaurantName = (TextView) findViewById(R.id.name);
        mCuisines = (TextView) findViewById(R.id.cuisine);
        mOperateStatus = (TextView) findViewById(R.id.operateStatusTextView);
        mGetNumberButton = (Button) findViewById(R.id.getNumberButton);

        mRestaurantName.setText(restaurant.getName());
        mCuisines.setText(restaurant.getCuisineTagsString());
    }
}