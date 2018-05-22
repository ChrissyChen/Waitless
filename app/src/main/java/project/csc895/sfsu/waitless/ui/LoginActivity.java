package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login Activity";
    private static final String USER_CHILD = "users";
    private static final String EMAIL_CHILD = "email";
    public final static String EXTRA_USER_ID = "Pass User id";
    public final static String EXTRA_EMAIL = "Pass User email";
    private static int SPLASH_TIME_OUT = 3000;  // Splash screen timer
    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Log.d(TAG, "login page");

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        //SharedPreference to Store API Result. don't need to login again when launch the app if pref has loginEmail value
        pref = getApplicationContext().getSharedPreferences("CachedResponse", 0);
        editor = pref.edit();
        editor.apply();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    inputEmail.requestFocus();
                    inputEmail.setError(getString(R.string.empty_email));
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.requestFocus();
                    inputPassword.setError(getString(R.string.empty_password));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputPassword.requestFocus();
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    loadUserIDWithEmail(email);
                                    checkIfEmailVerified(email);
                                }
                            }
                        });
            }
        });
    }

    private void checkIfEmailVerified(final String email)
    {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            if (user.isEmailVerified())
            {
                // user is verified, so you can finish this activity or send user to activity which you want.
                Toast.makeText(LoginActivity.this, getString(R.string.login_succeed), Toast.LENGTH_SHORT).show();

                // don't need to login again when launch the app if pref has loginEmail value
                editor.putString("loginEmail", email);
                editor.apply();

                // delay in order to get userID
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // This method will be executed once the timer is over. Start the MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(EXTRA_EMAIL, email);
                        intent.putExtra(EXTRA_USER_ID, userID);
                        startActivity(intent);
                        finish();
                    }
                }, SPLASH_TIME_OUT); // Showing splash screen with a timer
            }
            else
            {
                // email is not verified, so just prompt the message to the user and restart this activity.
                // NOTE: don't forget to log out the user.
                Toast.makeText(LoginActivity.this, getString(R.string.login_fail), Toast.LENGTH_LONG).show();
                auth.signOut();

                //restart this activity

            }
        }
    }

    private void loadUserIDWithEmail(String email) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query query = mDatabase.child(USER_CHILD)
                .orderByChild(EMAIL_CHILD)
                .equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // show no result view
                if (!dataSnapshot.hasChildren()) {
                    Log.d(TAG, "NO USER FOUND!");
                } else {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        userID = objSnapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
