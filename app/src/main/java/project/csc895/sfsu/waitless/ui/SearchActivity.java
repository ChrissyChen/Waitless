package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "Search Activity";
    public static final String RESTAURANT_CHILD = "restaurants";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        String searchTag = intent.getStringExtra(HomeFragment.EXTRA_MESSAGE);
        Log.d(TAG, searchTag);

        EditText editText = (EditText) findViewById(R.id.searchBarEditText);
        editText.setText(searchTag);

        LinearLayout searchLinearLayout = (LinearLayout) findViewById(R.id.searchLinearLayout);
        // set editText not on focus
        searchLinearLayout.setFocusable(true);
        searchLinearLayout.setFocusableInTouchMode(true);


        //displayInfo();

        mRecyclerView = (RecyclerView) findViewById(R.id.restaurantList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(
                Restaurant.class,
                R.layout.item_restaurant_brief_info,
                RestaurantViewHolder.class,
                mDatabase.child(RESTAURANT_CHILD)) {
            @Override
            public void populateViewHolder(RestaurantViewHolder holder, Restaurant restaurant, int position) {
                //// TODO: 9/25/17 set restaurant image
                holder.setName(restaurant.getName());
                holder.setCuisine(restaurant.getCuisineTagsString());
            }
        };

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

//    public void displayInfo() {
//        mDatabase.limitToLast(5).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot msgSnapshot: snapshot.getChildren()) {
//                    Restaurant restaurant  = msgSnapshot.getValue(Restaurant.class);
//                    Log.i("Restaurant", restaurant.getName()+": "+restaurant.getCuisineTags());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                Log.e("Restaurant", "The read failed: " + error.getDetails());
//            }
//        });
//    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private ImageButton mImageField;
        private TextView mNameField;
        private TextView mCuisineField;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            mImageField = (ImageButton) itemView.findViewById(R.id.restaurantImage);
            mNameField = (TextView) itemView.findViewById(R.id.restaurantName);
            mCuisineField = (TextView) itemView.findViewById(R.id.restaurantCuisineTags);
        }

        public void setImage() {
            //// TODO: 9/25/17
        }
        public void setName(String name) {
            mNameField.setText(name);
        }

        public void setCuisine(String message) {
            mCuisineField.setText(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
