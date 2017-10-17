package project.csc895.sfsu.waitless.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "Search Activity";
    public final static String EXTRA_RESTAURANT = "Pass Restaurant";
    public static final String RESTAURANT_CHILD = "restaurants";
    public static final String CUISINE_TAG_CHILD = "cuisineTags";
    public static final String NAME_CHILD = "name";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter mAdapter;
    private EditText mSearchBarEditText;
    private Button mSearchButton;
    private TextView mNoResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        String searchTag = intent.getStringExtra(HomeFragment.EXTRA_SEARCH);
        Log.d(TAG, searchTag);

        mSearchBarEditText = (EditText) findViewById(R.id.searchBarEditText);
        mSearchBarEditText.setText(searchTag);


        LinearLayout searchLinearLayout = (LinearLayout) findViewById(R.id.searchLinearLayout);
        searchLinearLayout.setFocusable(true);    // set editText not on focus
        searchLinearLayout.setFocusableInTouchMode(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.restaurantList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!searchTag.equals("")) {
            searchRestaurantByCuisine(CUISINE_TAG_CHILD);
        } else {
            //display all restaurants
        }

        mSearchButton = (Button) findViewById(R.id.searchButton);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchRestaurantByName(NAME_CHILD);
            }
        });

        mNoResultTextView = (TextView) findViewById(R.id.noResult);
    }

    private void searchRestaurantByCuisine(String cuisine) {
        final String queryText = mSearchBarEditText.getText().toString();
        Log.d(TAG, "Search By Cuisine: user types the keyword: " + queryText);
        Query query = mDatabase.child(RESTAURANT_CHILD)
                    .orderByChild(cuisine);

        mAdapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(
                Restaurant.class,
                R.layout.item_restaurant_brief_info,
                RestaurantViewHolder.class,
                query) {
            @Override
            protected void populateViewHolder(RestaurantViewHolder viewHolder, Restaurant restaurant, int position) {
                //// TODO: 9/25/17 image
                Log.d(TAG, "Search By Cuisine: inside adapter");
                if (restaurant.getCuisineTags().contains(queryText)) {
                    Log.d(TAG, "Search By Cuisine: contains searched cuisine" );
                    viewHolder.setName(restaurant.getName());
                    viewHolder.setCuisine(restaurant.getCuisineTagsString());
                    Log.d(TAG, "Search By Cuisine: set done" );

                    viewHolder.onClick(restaurant);
                } else {
                    viewHolder.mImageField.setVisibility(View.GONE);
                    viewHolder.mNameField.setVisibility(View.GONE);
                    viewHolder.mCuisineField.setVisibility(View.GONE);
                    viewHolder.mCardView.setVisibility(View.GONE);
                    Log.d(TAG, "Search By Cuisine: doesn't contain searched cuisine" );
                }
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // show no result view
                if (!dataSnapshot.hasChildren()) {
                    mRecyclerView.setVisibility(View.GONE);
                    mNoResultTextView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "NO RESULT VIEW SHOWS");
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNoResultTextView.setVisibility(View.GONE);
                    Log.d(TAG, "RESULT VIEW SHOWS");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void searchRestaurantByName(String name) {
        String queryText = mSearchBarEditText.getText().toString();
        Log.d(TAG, "user types the keyword: " + queryText);
        Query query = mDatabase.child(RESTAURANT_CHILD)
                .orderByChild(name)
                .startAt(queryText)
                .endAt(queryText + "\uf8ff");

        mAdapter = new FirebaseRecyclerAdapter<Restaurant, RestaurantViewHolder>(
                Restaurant.class,
                R.layout.item_restaurant_brief_info,
                RestaurantViewHolder.class,
                query) {
            @Override
            protected void populateViewHolder(RestaurantViewHolder viewHolder, Restaurant restaurant, int position) {
                //// TODO: 9/25/17 image
                viewHolder.setName(restaurant.getName());
                viewHolder.setCuisine(restaurant.getCuisineTagsString());

                viewHolder.onClick(restaurant);
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // show no result view
                if (!dataSnapshot.hasChildren()) {
                    mRecyclerView.setVisibility(View.GONE);
                    mNoResultTextView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "NO RESULT VIEW SHOWS");
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNoResultTextView.setVisibility(View.GONE);
                    Log.d(TAG, "RESULT VIEW SHOWS");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageField;
        private TextView mNameField;
        private TextView mCuisineField;
        private CardView mCardView;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            mImageField = (ImageView) itemView.findViewById(R.id.restaurantImage);
            mNameField = (TextView) itemView.findViewById(R.id.restaurantName);
            mCuisineField = (TextView) itemView.findViewById(R.id.restaurantCuisineTags);
            mCardView = (CardView) itemView.findViewById(R.id.briefInfoCardView);
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

        public void onClick(final Restaurant restaurant) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, RestaurantActivity.class);
                    intent.putExtra(EXTRA_RESTAURANT, restaurant);
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }
}

