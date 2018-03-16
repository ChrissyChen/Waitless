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
import project.csc895.sfsu.waitless.model.Number;
import project.csc895.sfsu.waitless.model.User;
import project.csc895.sfsu.waitless.model.Waitlist;

public class NumberDetailedActivity extends AppCompatActivity {

    private static final String TAG = "Number detailed Activity";
    private static final String USER_CHILD = "users";
    private static final String NUMBER_CHILD = "numbers";
    private static final String RESTAURANT_ID_CHILD = "restaurantID";
    private LinearLayout mLinearLayout;
    private PopupWindow mPopupWindowComplete, mPopupWindowCancel;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_detail);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        Number number = (Number) intent.getSerializableExtra(WaitlistHistoryFragment.EXTRA_NUMBER);  // get number obj

        initViews(number);

        Button completeButton = (Button) findViewById(R.id.completeButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO popup window and trigger table to be available
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO popup window and trigger cancel the number
            }
        });

    }

    private void initViews(Number number) {
        mLinearLayout = (LinearLayout) findViewById(R.id.numberDetailLinearLayout);
        TextView restaurantName = (TextView) findViewById(R.id.restaurant);
        TextView numberName = (TextView) findViewById(R.id.number);
        TextView status = (TextView) findViewById(R.id.status);
        TextView customerName = (TextView) findViewById(R.id.customerName);
        TextView customerPhone = (TextView) findViewById(R.id.customerTelephone);
        TextView partyNumber = (TextView) findViewById(R.id.customerPartyNumber);
        TextView createdTime = (TextView) findViewById(R.id.numberCreatedTime);

        restaurantName.setText(number.getRestaurantName());
        numberName.setText(number.getNumberName());
        status.setText(number.getStatus());
        customerName.setText(number.getUsername());
        customerPhone.setText(number.getPhone());
        partyNumber.setText(String.valueOf(number.getPartyNumber()));
        createdTime.setText(number.getTimeCreated());
    }


//    public void showConfirmPopupWindow() {
//        LayoutInflater inflater = (LayoutInflater) NumberDetailedActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (inflater != null) {
//            View customView = inflater.inflate(R.layout.popup_window_number, null);
//            mPopupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
//
//            TextView number = (TextView) customView.findViewById(R.id.number);
//            TextView restaurant = (TextView) customView.findViewById(R.id.restaurant);
//            TextView customerName = (TextView) customView.findViewById(R.id.customerName);
//            TextView telephone = (TextView) customView.findViewById(R.id.customerTelephone);
//            TextView partyNumberTextView = (TextView) customView.findViewById(R.id.customerPartyNumber);
//            TextView createdTime = (TextView) customView.findViewById(R.id.numberCreatedTime);
//            number.setText(numberName);
//            restaurant.setText(restaurantName);
//            customerName.setText(username);
//            telephone.setText(phone);
//            partyNumberTextView.setText(String.valueOf(partyNumber));
//            createdTime.setText(currentTime);
//
//            Button closeButton = (Button) customView.findViewById(R.id.closeButton);
//            closeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPopupWindow.dismiss();
//
//                    // lead to restaurant activity again. but can't startActivity. maybe need to go back.
//                    onBackPressed();
//                }
//            });
//        }
//    }

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
                startActivity(new Intent(NumberDetailedActivity.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
