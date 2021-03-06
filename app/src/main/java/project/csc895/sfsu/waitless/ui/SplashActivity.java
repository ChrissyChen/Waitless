package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static project.csc895.sfsu.waitless.ui.SearchActivity.RESTAURANT_CHILD;

/**
 * Created by Chrissy on 03/03/18.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "Splash Activity";
    private static final String USER_CHILD = "users";
    private static final String EMAIL_CHILD = "email";
    public final static String EXTRA_USER_ID = "Pass User id";
    public final static String EXTRA_EMAIL = "Pass User email";
    private static int SPLASH_TIME_OUT = 3000;  // Splash screen timer
    private String loginEmail, userID;
    private String dataTitle, dataMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //SharedPreference to Store API Result
        SharedPreferences pref = getApplicationContext().getSharedPreferences("CachedResponse", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.apply();

//        // Handle possible data accompanying notification message.
//        if (getIntent().getExtras() != null) {   // include all intent extras
//            Log.d(TAG, "Handle possible data accompanying notification message.");
//            for (String key : getIntent().getExtras().keySet()) {
//                if (key.equals("title")) {
//                    Log.d(TAG, "data title " + dataTitle);
//                    dataTitle=(String)getIntent().getExtras().get(key);
//                }
//                if (key.equals("message")) {
//                    Log.d(TAG, "data message " + dataMessage);
//                    dataMessage = (String)getIntent().getExtras().get(key);
//                }
//            }
//        }

        loginEmail = pref.getString("loginEmail", null);

        if (loginEmail != null) {   //It means User is already Logged in so the user will be taken to MainActivity
            // get userID
            loadUserIDWithEmail();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // This method will be executed once the timer is over. Start the MainActivity
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra(EXTRA_EMAIL, loginEmail);
                    intent.putExtra(EXTRA_USER_ID, userID);
                    Log.d(TAG, "email: " + loginEmail);
                    Log.d(TAG, "user ID: " + userID);

//                    //add notification extra to main activity
//                    if (dataTitle != null && dataMessage != null) {
//                        intent.putExtra("title", dataTitle);
//                        intent.putExtra("message", dataMessage);
//                        Log.d(TAG, "title: " + dataTitle);
//                        Log.d(TAG, "message: " + dataMessage);
//                    }

                    startActivity(intent);
                    // close splash activity
                    finish();
                }
            }, SPLASH_TIME_OUT); // Showing splash screen with a timer

        } else {
            //It means User is not Logged in so the user will be taken to Login Screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void loadUserIDWithEmail() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child(USER_CHILD)
                .orderByChild(EMAIL_CHILD)
                .equalTo(loginEmail);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // show no result view
                if (!dataSnapshot.hasChildren()) {
                    Log.d(TAG, "NO USER FOUND!");
                } else {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        userID = objSnapshot.getKey();
                        Log.d(TAG, "user ID inside callback: " + userID);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
