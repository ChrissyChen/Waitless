package project.csc895.sfsu.waitless.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.User;
import project.csc895.sfsu.waitless.model.Number;
import project.csc895.sfsu.waitless.model.Waitlist;

public class GetNumberActivity extends AppCompatActivity {

    private static final String TAG = "GetNumber Activity";
    private static final String USER_CHILD = "users";
    private static final String EMAIL_CHILD = "email";
    private static final String WAITLIST_CHILD = "waitlists";
    private static final String WAIT_NUM_TABLE_A_CHILD = "waitNumTableA";
    private static final String WAIT_NUM_TABLE_B_CHILD = "waitNumTableB";
    private static final String WAIT_NUM_TABLE_C_CHILD = "waitNumTableC";
    private static final String WAIT_NUM_TABLE_D_CHILD = "waitNumTableD";
    private static final String COUNTER_TABLE_A_CHILD = "counterTableA";
    private static final String COUNTER_TABLE_B_CHILD = "counterTableB";
    private static final String COUNTER_TABLE_C_CHILD = "counterTableC";
    private static final String COUNTER_TABLE_D_CHILD = "counterTableD";
    private static final String NUMBER_CHILD = "numbers";
    private static final String RESTAURANT_ID_CHILD = "restaurantID";
    private static final int TABLE_A_SIZE = 2;
    private static final int TABLE_B_SIZE = 4;
    private static final int TABLE_C_SIZE = 6;
    private static final int TABLE_D_SIZE = 10;
    private LinearLayout mLinearLayout;
    private EditText mFirstNameField, mLastNameField, mTelephoneField, mEmailField, mPartySizeField;
    private PopupWindow mPopupWindow;
    private String currentTime;
    private String restaurantID, restaurantName, userID, waitlistID;
    private String numberName, username, phone, email;
    private int partySize;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private int waitNumTableA, waitNumTableB, waitNumTableC, waitNumTableD;
    private int counterTableA, counterTableB, counterTableC, counterTableD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        restaurantID = intent.getStringExtra(SearchActivity.EXTRA_RESTAURANT_ID);
        restaurantName = intent.getStringExtra(RestaurantActivity.EXTRA_RESTAURANT_NAME);
        Log.d("restaurant id:", restaurantID);
        Log.d("restaurant name:", restaurantName);

        initFields();
        loadUserInfo();
        getWaitNumTables();

        Button confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNumberToDatabase();
            }
        });
    }

    private void initFields() {
        mLinearLayout = (LinearLayout) findViewById(R.id.getNumberLinearLayout);
        TextView mRestaurantName = (TextView) findViewById(R.id.restaurantName);
        mRestaurantName.setText(restaurantName);
        mFirstNameField = (EditText) findViewById(R.id.firstName);
        mLastNameField = (EditText) findViewById(R.id.lastName);
        mTelephoneField = (EditText) findViewById(R.id.telephone);
        mTelephoneField.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mEmailField = (EditText) findViewById(R.id.email);
        mPartySizeField = (EditText) findViewById(R.id.partySize);
    }

    private void loadUserInfo() {
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        String loginEmail = "";
        if (mFirebaseUser != null) {
            loginEmail = mFirebaseUser.getEmail();
            Log.d("user email: ", loginEmail);
        }

        Query query = mDatabase.child(USER_CHILD)
                .orderByChild(EMAIL_CHILD)
                .equalTo(loginEmail);

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
                            String phone = user.getTelephone();
                            String email = user.getEmail();
                            mFirstNameField.setText(firstName);
                            mLastNameField.setText(lastName);
                            mTelephoneField.setText(phone);
                            mEmailField.setText(email);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getWaitNumTables() {
        Query query = mDatabase.child(WAITLIST_CHILD)
                .orderByChild(RESTAURANT_ID_CHILD)
                .equalTo(restaurantID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Log.d(TAG, "NO WAITLIST FOUND!");
                } else {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        Waitlist waitlist = objSnapshot.getValue(Waitlist.class);
                        if (waitlist != null) {
                            waitlistID = waitlist.getWaitlistID();
                            Log.d("waitlistID", waitlistID);
                            waitNumTableA = waitlist.getWaitNumTableA();
                            waitNumTableB = waitlist.getWaitNumTableB();
                            waitNumTableC = waitlist.getWaitNumTableC();
                            waitNumTableD = waitlist.getWaitNumTableD();
                            Log.d("num a", String.valueOf(waitNumTableA));
                            Log.d("num b", String.valueOf(waitNumTableB));
                            Log.d("num c", String.valueOf(waitNumTableC));
                            Log.d("num d", String.valueOf(waitNumTableD));
                            counterTableA = waitlist.getCounterTableA();
                            counterTableB = waitlist.getCounterTableB();
                            counterTableC = waitlist.getCounterTableC();
                            counterTableD = waitlist.getCounterTableD();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveNumberToDatabase() {
        String firstName = mFirstNameField.getText().toString().trim();
        String lastName = mLastNameField.getText().toString().trim();
        phone = mTelephoneField.getText().toString().trim();
        email = mEmailField.getText().toString().trim();
        String partyNum = mPartySizeField.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            mFirstNameField.requestFocus();
            mFirstNameField.setError(getString(R.string.empty_first_name));
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            mLastNameField.requestFocus();
            mLastNameField.setError(getString(R.string.empty_last_name));
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            mTelephoneField.requestFocus();
            mTelephoneField.setError(getString(R.string.empty_phone));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailField.requestFocus();
            mEmailField.setError(getString(R.string.empty_email));
            return;
        }
        if (TextUtils.isEmpty(partyNum)) {
            mPartySizeField.requestFocus();
            mPartySizeField.setError(getString(R.string.empty_party));
            return;
        }

        partySize = Integer.valueOf(partyNum);
        if (partySize > TABLE_D_SIZE) {
            Toast.makeText(GetNumberActivity.this, getString(R.string.large_party), Toast.LENGTH_SHORT).show();
            return;
        }

        createNewNumberRecord(firstName, lastName);
        updateWaitlistNum();
        showConfirmPopupWindow();
    }

    private void createNewNumberRecord(String firstName, String lastName) {
        DatabaseReference numberRef = mDatabase.child(NUMBER_CHILD);
        String key = numberRef.push().getKey();  // newly generated numberID
        username = firstName + " " + lastName;
        if (partySize <= TABLE_A_SIZE) {
            waitNumTableA += 1;
            counterTableA += 1;
            numberName = "# A" + counterTableA;
        } else if (partySize <= TABLE_B_SIZE) {
            waitNumTableB += 1;
            counterTableB += 1;
            numberName = "# B" + counterTableB;
        } else if (partySize <= TABLE_C_SIZE) {
            waitNumTableC += 1;
            counterTableC += 1;
            numberName = "# C" + counterTableC;
        } else if (partySize <= TABLE_D_SIZE) {
            waitNumTableD += 1;
            counterTableD += 1;
            numberName = "# D" + counterTableD;
        }
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
        currentTime = format.format(Calendar.getInstance().getTime());

        Number number = new Number(key, restaurantID, restaurantName, null, userID, username, phone, email, currentTime, numberName, partySize, "Waiting", false);
        numberRef.child(key).setValue(number);
        Toast.makeText(GetNumberActivity.this, "Number Created! ", Toast.LENGTH_SHORT).show();
    }

    private void updateWaitlistNum() {
        DatabaseReference ref = mDatabase.child(WAITLIST_CHILD).child(waitlistID);
        ref.child(WAIT_NUM_TABLE_A_CHILD).setValue(waitNumTableA);
        ref.child(WAIT_NUM_TABLE_B_CHILD).setValue(waitNumTableB);
        ref.child(WAIT_NUM_TABLE_C_CHILD).setValue(waitNumTableC);
        ref.child(WAIT_NUM_TABLE_D_CHILD).setValue(waitNumTableD);

        ref.child(COUNTER_TABLE_A_CHILD).setValue(counterTableA);
        ref.child(COUNTER_TABLE_B_CHILD).setValue(counterTableB);
        ref.child(COUNTER_TABLE_C_CHILD).setValue(counterTableC);
        ref.child(COUNTER_TABLE_D_CHILD).setValue(counterTableD);
    }

    public void showConfirmPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) GetNumberActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View customView = inflater.inflate(R.layout.popup_window_number, null);
            mPopupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

            TextView number = (TextView) customView.findViewById(R.id.number);
            TextView restaurant = (TextView) customView.findViewById(R.id.restaurant);
            TextView customerName = (TextView) customView.findViewById(R.id.customerName);
            TextView telephone = (TextView) customView.findViewById(R.id.customerTelephone);
            TextView partySizeTextView = (TextView) customView.findViewById(R.id.customerPartySize);
            TextView createdTime = (TextView) customView.findViewById(R.id.numberCreatedTime);
            number.setText(numberName);
            restaurant.setText(restaurantName);
            customerName.setText(username);
            telephone.setText(phone);
            partySizeTextView.setText(String.valueOf(partySize));
            createdTime.setText(currentTime);

            Button closeButton = (Button) customView.findViewById(R.id.closeButton);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();

                    // lead to restaurant activity again. but can't startActivity. maybe need to go back.
                    onBackPressed();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeAction:
                startActivity(new Intent(GetNumberActivity.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
