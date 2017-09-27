package project.csc895.sfsu.waitless.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "Search Activity";
    public static final String RESTAURANT_CHILD = "restaurants";
    public static final String CUISINE_TAG_CHILD = "cuisineTags";
    public static final String NAME_CHILD = "name";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    //private SearchRestaurantAdapter mAdapter;
    private EditText mSearchBarEditText;
    private Button mSearchButton;
    private TextView mNoResultTextView;
    private ArrayList<Restaurant> restaurantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        String searchTag = intent.getStringExtra(HomeFragment.EXTRA_MESSAGE);
        Log.d(TAG, searchTag);

        mSearchBarEditText = (EditText) findViewById(R.id.searchBarEditText);
        mSearchBarEditText.setText(searchTag);

        LinearLayout searchLinearLayout = (LinearLayout) findViewById(R.id.searchLinearLayout);
        // set editText not on focus
        searchLinearLayout.setFocusable(true);
        searchLinearLayout.setFocusableInTouchMode(true);


        //displayInfo();


//        mSearchBarEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().trim().length() > 0) {
//                    mSearchButton.setEnabled(true);
//                    Log.d(TAG, "search button is enabled");
//                } else {
//                    mSearchButton.setEnabled(false);
//                    Log.d(TAG, "search button is disabled");
//                    // clear the list view
//                    mRecyclerView.setAdapter(null);
//                    mNoResultTextView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });


        mNoResultTextView = (TextView) findViewById(R.id.noResult);

        mRecyclerView = (RecyclerView) findViewById(R.id.restaurantList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSearchButton = (Button) findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryText = mSearchBarEditText.getText().toString();
                Log.d(TAG, "user types the keyword: " + queryText);
                Query query = mDatabase.child(RESTAURANT_CHILD)
                        .orderByChild(NAME_CHILD)
                        .startAt(queryText)
                        .endAt(queryText + "\uf8ff");

                mAdapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(
                        Restaurant.class,
                        R.layout.item_restaurant_brief_info,
                        RestaurantViewHolder.class,
                        query) {
                    @Override
                    protected void populateViewHolder(RestaurantViewHolder viewHolder, Restaurant restaurant, int position) {
                        //// TODO: 9/25/17
                        viewHolder.setName(restaurant.getName());
                        viewHolder.setCuisine(restaurant.getCuisineTagsString());
                    }
                };

                mRecyclerView.setAdapter(mAdapter);
            }
        });


//        mRecyclerView = (RecyclerView) findViewById(R.id.restaurantList);
//
//
//
//        mSearchButton = (Button) findViewById(R.id.searchButton);
//        mSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchRestaurant();
//                Log.d(TAG, "search button is clicked: search message");
//            }
//        });
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
//        mAdapter.cleanup();
    }

    private void searchRestaurant() {
        String queryText = mSearchBarEditText.getText().toString();
        Log.d(TAG, "user types the keyword: " + queryText);
        Query query = mDatabase.child(RESTAURANT_CHILD)
                .orderByChild(NAME_CHILD)
                .startAt(queryText);

        mAdapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(
                Restaurant.class,
                R.layout.item_restaurant_brief_info,
                RestaurantViewHolder.class,
                query) {
            @Override
            protected void populateViewHolder(RestaurantViewHolder viewHolder, Restaurant restaurant, int position) {
                //// TODO: 9/25/17
                viewHolder.setName(restaurant.getName());
                viewHolder.setCuisine(restaurant.getCuisineTagsString());
            }
        };
        mRecyclerView.setAdapter(mAdapter);

    }
}

