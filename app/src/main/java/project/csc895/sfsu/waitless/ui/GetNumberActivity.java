package project.csc895.sfsu.waitless.ui;

import android.content.Context;
import android.os.Build;
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

import project.csc895.sfsu.waitless.R;

public class GetNumberActivity extends AppCompatActivity {

    private static final String TAG = "GetNumber Activity";
    private LinearLayout mLinearLayout;
    private EditText mFirstNameField, mLastNameField, mTelephoneField, mPartyNumberField;
    private Button mConfirmButton;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mLinearLayout = (LinearLayout) findViewById(R.id.getNumberLinearLayout);
        mFirstNameField = (EditText) findViewById(R.id.firstName);
        mLastNameField = (EditText) findViewById(R.id.lastName);
        mTelephoneField = (EditText) findViewById(R.id.telephone);
        mPartyNumberField= (EditText) findViewById(R.id.partyNumber);
        mConfirmButton = (Button) findViewById(R.id.confirmButton);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmPopupWindow();
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

        Button closeButton = (Button) customView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
