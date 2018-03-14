package project.csc895.sfsu.waitless.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.User;

public class ProfileFragment extends Fragment {

//    private TextView mNameTextView;
//    private LinearLayout historyLinearLayout, favoriteLinearLayout;
//    private Button btn_signout;
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;

    private static final String ARGS_USER_ID = "userID";
    private static final String USER_CHILD = "users";
    private static final String FIRST_NAME_CHILD = "firstName";
    private static final String LAST_NAME_CHILD = "lastName";
    private static final String TELEPHONE_CHILD = "telephone";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String userID;
    private EditText inputFirstName, inputLastName, inputPhone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        mFirebaseAuth = FirebaseAuth.getInstance();
//        mFirebaseUser = mFirebaseAuth.getCurrentUser();
//
//        mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
//        historyLinearLayout= (LinearLayout) view.findViewById(R.id.historyLinearLayout);
//        btn_signout = (Button) view.findViewById(R.id.btn_signout);
//        btn_signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((MainActivity)getActivity()).signOut();
//            }
//        });


        // Retrieve data from main activity
        Bundle args = getArguments();
        userID = args.getString(ARGS_USER_ID);
        Log.d("userID", userID);

        initEditText(view);
        loadUserInfoWithUserID();

        Button btnSave = (Button) view.findViewById(R.id.save_button);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfoToDatabase();
            }
        });

        return view;
    }

    private void initEditText(View view) {
        inputFirstName = (EditText) view.findViewById(R.id.first_name);
        inputLastName = (EditText) view.findViewById(R.id.last_name);
        inputPhone = (EditText) view.findViewById(R.id.phone);
        inputPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    private void loadUserInfoWithUserID() {
        DatabaseReference ref = mDatabase.child(USER_CHILD).child(userID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();
                    String phone = user.getTelephone();
                    inputFirstName.setText(firstName);
                    inputLastName.setText(lastName);
                    inputPhone.setText(phone);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveUserInfoToDatabase() {
        DatabaseReference ref = mDatabase.child(USER_CHILD).child(userID);
        String firstName = inputFirstName.getText().toString().trim();
        String lastName = inputLastName.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();

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

        ref.child(FIRST_NAME_CHILD).setValue(firstName);
        ref.child(LAST_NAME_CHILD).setValue(lastName);
        ref.child(TELEPHONE_CHILD).setValue(phone);

        Toast.makeText(getActivity(), "User Information Saved! ", Toast.LENGTH_SHORT).show();
    }
}