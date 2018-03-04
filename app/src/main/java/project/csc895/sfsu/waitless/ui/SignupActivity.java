package project.csc895.sfsu.waitless.ui;


import android.content.Intent;
import android.os.Bundle;
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
import project.csc895.sfsu.waitless.model.Restaurant;
import project.csc895.sfsu.waitless.model.User;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "Signup Activity";
    private static final String USER_CHILD = "users";
    private EditText inputEmail, inputPassword, inputFirstName, inputLastName, inputPhone;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Log.d(TAG, "sign up page");

        //Get Firebase mFirebaseAuth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputFirstName = (EditText) findViewById(R.id.first_name);
        inputLastName = (EditText) findViewById(R.id.last_name);
        inputPhone = (EditText) findViewById(R.id.user_phone);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstName = inputFirstName.getText().toString().trim();
                final String lastName = inputLastName.getText().toString().trim();
                final String phone = inputPhone.getText().toString().trim();
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(firstName)) {
                    inputFirstName.requestFocus();
                    inputFirstName.setError(getString(R.string.empty_first_name));
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    inputLastName.requestFocus();
                    inputLastName.setError(getString(R.string.empty_last_name));
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    inputPhone.requestFocus();
                    inputPhone.setError(getString(R.string.empty_phone));
                    return;
                }

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

                if (password.length() < 6) {
                    inputPassword.requestFocus();
                    inputPassword.setError(getString(R.string.minimum_password));
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the mFirebaseAuth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed. " + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    // NOTE: If the task succeeded, the user is signed in
                                    mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                    createNewUserRecord(firstName, lastName, phone, email);
                                    sendVerificationEmail();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void createNewUserRecord(String firstName, String lastName, String phone, String email) {
        if (mFirebaseUser != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(USER_CHILD);
            String key = ref.push().getKey();
            Log.d("key:  ", key); // generated random id

            User user = new User(firstName, lastName, phone, email);
            ref.child(key).setValue(user);
        }

        // TODO do in background?
        Log.d(TAG, "create a new User in db");
    }

    private void sendVerificationEmail() {
        if (mFirebaseUser != null) {
            mFirebaseUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {  // email sent
                                Toast.makeText(SignupActivity.this, getString(R.string.send_verified_email), Toast.LENGTH_LONG).show();
                                // after email is sent just logout the user and finish this activity
                                mFirebaseAuth.signOut();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                //finish();
                            } else {
                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                //restart this activity
                                overridePendingTransition(0, 0);
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                            }
                        }
                    });
        }
    }
}
