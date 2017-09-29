package project.csc895.sfsu.waitless.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import project.csc895.sfsu.waitless.R;

public class GetNumberActivity extends AppCompatActivity {

    private static final String TAG = "GetNumber Activity";
    private static final int USA_TELEPHONE_DIGIT = 10;
    private LinearLayout mLinearLayout;
    private EditText mFirstNameField, mLastNameField, mTelephoneField, mPartyNumberField;
    private Button mConfirmButton;
    private PopupWindow mPopupWindow;
    private Date currentTime;
    private String restaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        restaurantName = intent.getStringExtra(RestaurantActivity.EXTRA_RESTAURANT_NAME);

        mLinearLayout = (LinearLayout) findViewById(R.id.getNumberLinearLayout);
        mFirstNameField = (EditText) findViewById(R.id.firstName);
        mLastNameField = (EditText) findViewById(R.id.lastName);
        mTelephoneField = (EditText) findViewById(R.id.telephone);
        mPartyNumberField= (EditText) findViewById(R.id.partyNumber);
        mConfirmButton = (Button) findViewById(R.id.confirmButton);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFieldsValidation()) {
                    showConfirmPopupWindow();
                } else {
                    //toast
                }
            }
        });
    }

    public void showConfirmPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) GetNumberActivity.this.
                                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.popup_window_number, null);
        mPopupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
//        if(Build.VERSION.SDK_INT >= 21){
//            mPopupWindow.setElevation(5.0f);
//        }

        TextView number = (TextView) customView.findViewById(R.id.number);
        TextView restaurant = (TextView) customView.findViewById(R.id.restaurant);
        TextView customerName = (TextView) customView.findViewById(R.id.customerName);
        TextView telephone = (TextView) customView.findViewById(R.id.customerTelephone);
        TextView partyNumber = (TextView) customView.findViewById(R.id.customerPartyNumber);
        TextView createdTime = (TextView) customView.findViewById(R.id.numberCreatedTime);
        number.setText("Number: "); //// TODO: 9/28/17 retrieved from db and edit
        restaurant.setText("Restaurant: " + restaurantName);
        customerName.setText("Customer Name: " + mFirstNameField.getText() + " " + mLastNameField.getText());
        String display = "Telephone: " + mTelephoneField.getText();
        telephone.setText(display);
        partyNumber.setText("Party Number: " + mPartyNumberField.getText());
        currentTime = Calendar.getInstance().getTime();
        createdTime.setText("Created Time: " + currentTime.toString());

        Button closeButton = (Button) customView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                // lead to restaurant activity again. but can't startActivity. maybe need to go back.
            }
        });
    }

    public boolean checkFieldsValidation() {
        if (mFirstNameField.getText().length() < 0 || mLastNameField.getText().length() < 0
                || mTelephoneField.getText().length() != USA_TELEPHONE_DIGIT) {
            return false;
        }
        //if (mPartyNumberField.getText() > 10)  // TODO: 9/28/17
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
