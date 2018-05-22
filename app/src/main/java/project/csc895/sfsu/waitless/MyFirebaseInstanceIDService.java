package project.csc895.sfsu.waitless;

import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import project.csc895.sfsu.waitless.model.User;

/**
 * Created by Chrissy on 3/30/18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseIDService";
    private static int SPLASH_TIME_OUT = 3000;
    private static final String USER_CHILD = "users";
    private static final String EMAIL_CHILD = "email";
    private static final String TOKEN_FCM_CHILD = "tokenFCM";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String userID;

    @Override
    public void onTokenRefresh() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            getUserID(user.getEmail());
            Log.d(TAG, "user ID: " + userID);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Get updated InstanceID token.
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Refreshed token: " + refreshedToken);
                Log.d(TAG, "user ID: " + userID);

                if (userID != null) {
                    updateTokenInUserDatabase(refreshedToken);
                } else {
                    Log.d(TAG, "user ID is null!");
                }
            }
        }, SPLASH_TIME_OUT); // Showing splash screen with a timer
    }

    private void updateTokenInUserDatabase(String token) {
        DatabaseReference userRef = mDatabase.child(USER_CHILD).child(userID);
        userRef.child(TOKEN_FCM_CHILD).setValue(token);
    }

    private void getUserID(String email) {
        Query query = mDatabase.child(USER_CHILD)
                .orderByChild(EMAIL_CHILD)
                .equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Log.d(TAG, "NO USER FOUND!");
                } else {
                    for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                        User user = objSnapshot.getValue(User.class);
                        if (user != null) {
                            userID = user.getUserID();
                            Log.d(TAG, "user ID inside callback " + userID);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}