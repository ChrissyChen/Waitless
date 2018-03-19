package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;
import project.csc895.sfsu.waitless.model.Waitlist;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "Restaurant Activity";
    private static final String RESTAURANT_CHILD = "restaurants";
    public static final String EXTRA_RESTAURANT_NAME = "Pass Restaurant Name";
    private static final String WAITLIST_CHILD = "waitlists";
    private static final String RESTAURANT_ID_CHILD = "restaurantID";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String restaurantID, restaurantName, waitlistID;
    private ImageView mRestaurantImage;
    private TextView mRestaurantName, mCuisines, mOperateStatus, mRestaurantAddress, mRestaurantPhone,
                    mTableAWaitingParty, mTableBWaitingParty, mTableCWaitingParty, mTableDWaitingParty,
                    mTableAEstimateTime, mTableBEstimateTime, mTableCEstimateTime, mTableDEstimateTime;
    private Button mGetNumberButton;
    private static FirebaseStorage sStorage = FirebaseStorage.getInstance();

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

        initViews();
        loadRestaurantValue();
        loadWaitlistInfo();

        mGetNumberButton = (Button) findViewById(R.id.getNumberButton);
        mGetNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, GetNumberActivity.class);
                intent.putExtra(SearchActivity.EXTRA_RESTAURANT_ID, restaurantID);
                intent.putExtra(EXTRA_RESTAURANT_NAME, restaurantName);
                v.getContext().startActivity(intent);
            }
        });

    }

    private void initViews() {
        mRestaurantImage = (ImageView) findViewById(R.id.restaurantImageView);
        mRestaurantName = (TextView) findViewById(R.id.name);
        mCuisines = (TextView) findViewById(R.id.cuisine);
        mOperateStatus = (TextView) findViewById(R.id.operateStatusTextView);
        mRestaurantAddress = (TextView) findViewById(R.id.restaurantAddress);
        mRestaurantPhone = (TextView) findViewById(R.id.restaurantTelephone);

        mTableAWaitingParty = (TextView) findViewById(R.id.tableAWaitingParty);
        mTableBWaitingParty = (TextView) findViewById(R.id.tableBWaitingParty);
        mTableCWaitingParty = (TextView) findViewById(R.id.tableCWaitingParty);
        mTableDWaitingParty = (TextView) findViewById(R.id.tableDWaitingParty);
        mTableAEstimateTime = (TextView) findViewById(R.id.tableAEstimateTime);
        mTableBEstimateTime = (TextView) findViewById(R.id.tableBEstimateTime);
        mTableCEstimateTime = (TextView) findViewById(R.id.tableCEstimateTime);
        mTableDEstimateTime = (TextView) findViewById(R.id.tableDEstimateTime);
    }

    private void loadRestaurantValue() {
        DatabaseReference ref = mDatabase.child(RESTAURANT_CHILD).child(restaurantID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Restaurant restaurant = dataSnapshot.getValue(Restaurant.class);
                if (restaurant != null) {
                    restaurantName = restaurant.getName();
                    String address = restaurant.getAddress();
                    String phone = restaurant.getTelephone();
                    String cuisine = restaurant.getCuisine();
                    String imageUrl = restaurant.getImageUrl();

                    mRestaurantName.setText(restaurantName);
                    mCuisines.setText(cuisine);
                    mRestaurantAddress.setText(address);
                    mRestaurantPhone.setText(phone);

                    // get image
                    if (imageUrl != null) {  // if has value in imageUrl, then load the uri into logo ImageView
                        final StorageReference gsReference = sStorage.getReferenceFromUrl(imageUrl);
                        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(RestaurantActivity.this)
                                        .load(uri)
                                        .into(mRestaurantImage);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.e(TAG, "Could not load image for message", exception);
                            }
                        });
                    } // if imageUrl is null, display the default image
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadWaitlistInfo() {
        Query query = mDatabase.child(WAITLIST_CHILD)
                .orderByChild(RESTAURANT_ID_CHILD)
                .equalTo(restaurantID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Log.d("Error", "NO WAITLIST SHOWS");
                } else {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        Waitlist waitlist = objSnapshot.getValue(Waitlist.class);
                        if (waitlist != null) {
                            waitlistID = waitlist.getWaitlistID();
                            Log.d("waitlistID inside", waitlistID);

                            int waitNumTableA = waitlist.getWaitNumTableA();
                            int waitNumTableB = waitlist.getWaitNumTableB();
                            int waitNumTableC = waitlist.getWaitNumTableC();
                            int waitNumTableD = waitlist.getWaitNumTableD();

                            mTableAWaitingParty.setText(String.valueOf(waitNumTableA));
                            mTableBWaitingParty.setText(String.valueOf(waitNumTableB));
                            mTableCWaitingParty.setText(String.valueOf(waitNumTableC));
                            mTableDWaitingParty.setText(String.valueOf(waitNumTableD));

                            String estimateTimeTableA = waitNumTableA * 5 + " min";
                            String estimateTimeTableB = waitNumTableB * 5 + " min";
                            String estimateTimeTableC = waitNumTableC * 5 + " min";
                            String estimateTimeTableD = waitNumTableD * 5 + " min";

                            mTableAEstimateTime.setText(estimateTimeTableA);
                            mTableBEstimateTime.setText(estimateTimeTableB);
                            mTableCEstimateTime.setText(estimateTimeTableC);
                            mTableDEstimateTime.setText(estimateTimeTableD );
                        }
                    }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeAction:
                startActivity(new Intent(RestaurantActivity.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}