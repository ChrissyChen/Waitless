package project.csc895.sfsu.waitless.ui;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private static final String USER_CHILD = "users";
    private static final String EMAIL_CHILD = "email";
    private static final String ARGS_USER_ID = "userID";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DrawerLayout mDrawerLayout;
    private TextView drawerName, drawerEmail;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        Log.d(TAG + " UserEmail", mFirebaseUser.getEmail());

        //setupBottomNavigationView();

        // set Navigation Drawer icon
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // show the home fragment when app first launches
        selectFragment(R.id.nav_home);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View navHeader = navigationView.getHeaderView(0);
        drawerName = (TextView) navHeader.findViewById(R.id.drawer_name);
        drawerEmail = (TextView) navHeader.findViewById(R.id.drawer_email);

        // Load name and email in drawer header and get userID
        loadUserInfo(mFirebaseUser.getEmail());

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // Set action bar title
                        setTitle(menuItem.getTitle());
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        selectFragment(menuItem.getItemId());

                        return true;
                    }
                });

    }

    private void loadUserInfo(String email) {
        Query query = mDatabase.child(USER_CHILD)
                .orderByChild(EMAIL_CHILD)
                .equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Log.d(TAG, "NO USER FOUND!");
                } else {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        User user = objSnapshot.getValue(User.class);
                        if (user != null) {
                            userID = user.getUserID();
                            Log.d("userID", userID);
                            String firstName = user.getFirstName();
                            String lastName = user.getLastName();
                            String name = firstName + " " + lastName;
                            drawerName.setText(name);
                            drawerEmail.setText(user.getEmail());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void selectFragment(int menuItemID) {
        switch (menuItemID) {
            case R.id.nav_home:
                pushFragment(new HomeFragment());
                break;
            case R.id.nav_profile:
                pushFragment(new ProfileFragment());
                break;
            case R.id.nav_waitlist_history:
                pushFragment(new WaitlistHistoryFragment());
                break;
            case R.id.nav_signout:
                signOut();
                break;
        }
    }

    private void pushFragment(Fragment fragment) {
        if (fragment == null) return;

        // pass data from activity to fragment
        Bundle args = new Bundle();
        args.putString(ARGS_USER_ID, userID);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (transaction != null) {
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        }
    }

//    private void setupBottomNavigationView() {
//        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
//        if (bottomNavigationView != null) {
//
//            // Select first menu item by default and show Fragment accordingly.
//            Menu menu = bottomNavigationView.getMenu();
//            selectFragment(menu.getItem(0));
//
//            // Set action to perform when any menu-item is selected.
//            bottomNavigationView.setOnNavigationItemSelectedListener(
//                    new BottomNavigationView.OnNavigationItemSelectedListener() {
//                        @Override
//                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                            selectFragment(item);
//                            return false;
//                        }
//                    });
//        }
//    }
//
//    protected void selectFragment(MenuItem item) {
//        item.setChecked(true);
//        switch (item.getItemId()) {
//            case R.id.action_home:
//                // Action to perform when Home Menu item is selected.
//                pushFragment(new HomeFragment());
//                break;
//            case R.id.action_nearby:
//                pushFragment(new NearbyFragment());
//                break;
//            case R.id.action_profile:
//                pushFragment(new ProfileFragment());
//                break;
//        }
//    }
//
//    protected void pushFragment(Fragment fragment) {
//        if (fragment == null) return;
//
//        // pass data from activity to fragment
////        Bundle args = new Bundle();
////        args.putString(ARGS_USER_ID, restaurantID);
////        fragment.setArguments(args);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        if (fragmentManager != null) {
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            if (transaction != null) {
//                transaction.replace(R.id.rootLayout, fragment);
//                transaction.commit();
//            }
//        }
//    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    public void signOut() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("CachedResponse", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        mFirebaseAuth.signOut();    // check??
        finish();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
