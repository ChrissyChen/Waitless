package project.csc895.sfsu.waitless.ui;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.Test_sendDataToFirebase;
import project.csc895.sfsu.waitless.model.Dish;
import project.csc895.sfsu.waitless.model.Restaurant;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigationView();
        Log.d(TAG, "1");


//        ArrayList<String> cuisineTags = new ArrayList<>();
//        cuisineTags.add("Chinese");
//        cuisineTags.add("Hot Pot");
//        Restaurant restaurant = new Restaurant("Fashion Wok", cuisineTags);
//        mDatabase.child("restaurants").child("1").setValue(restaurant);

        Dish dish = new Dish("1", "duck meat", 12.95, "001");
        mDatabase.child("dishes").push().setValue(dish).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "!!!!!!!!!!!!!!!!!!!");
            }
        });

        //isGooglePlayServicesAvailable(this);

        Log.d(TAG, "test to write 1");

//        ArrayList<String> cuisineTags2 = new ArrayList<>();
//        cuisineTags2.add("American");
//        cuisineTags2.add("Burger");
//        test.writeNewRestaurant("2", "iHop", cuisineTags);
//
//        Log.d(TAG, "test to write 2");



//        textInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout);
//        editText = (EditText) findViewById(R.id.edit_text);
//
//        textInputLayout.setHint(getString(R.string.hint));
//        editText.setOnEditorActionListener(ActionListener.newInstance(this));

//    private boolean shouldShowError() {
//        int textLength = editText.getText().length();
//        return textLength > 0 && textLength < MIN_TEXT_LENGTH;
//    }
//
//    private void showError() {
//        textInputLayout.setError(getString(R.string.error));
//    }
//
//    private void hideError() {
//        textInputLayout.setError(EMPTY_STRING);
//    }
//
//    private static final class ActionListener implements TextView.OnEditorActionListener {
//        private final WeakReference<MainActivity> mainActivityWeakReference;
//
//        public static ActionListener newInstance(MainActivity mainActivity) {
//            WeakReference<MainActivity> mainActivityWeakReference = new WeakReference<>(mainActivity);
//            return new ActionListener(mainActivityWeakReference);
//        }
//
//        private ActionListener(WeakReference<MainActivity> mainActivityWeakReference) {
//            this.mainActivityWeakReference = mainActivityWeakReference;
//        }
//
//        @Override
//        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            MainActivity mainActivity = mainActivityWeakReference.get();
//            if (mainActivity != null) {
//                if (actionId == EditorInfo.IME_ACTION_GO && mainActivity.shouldShowError()) {
//                    mainActivity.showError();
//                } else {
//                    mainActivity.hideError();
//                }
//            }
//            return true;
//        }
//    }

    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        if (bottomNavigationView != null) {

            // Select first menu item by default and show Fragment accordingly.
            Menu menu = bottomNavigationView.getMenu();
            selectFragment(menu.getItem(0));

            // Set action to perform when any menu-item is selected.
            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            selectFragment(item);
                            return false;
                        }
                    });
        }
    }

    protected void selectFragment(MenuItem item) {
        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.action_home:
                // Action to perform when Home Menu item is selected.
                pushFragment(new HomeFragment());
                break;
            case R.id.action_nearby:
                pushFragment(new NearbyFragment());
                break;
            case R.id.action_profile:
                pushFragment(new ProfileFragment());
                break;
        }
    }

    protected void pushFragment(Fragment fragment) {
        if (fragment == null) return;

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (transaction != null) {
                transaction.replace(R.id.rootLayout, fragment);
                transaction.commit();
            }
        }
    }

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
}
