package project.csc895.sfsu.waitless.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Restaurant;

public class GetNumberActivity extends AppCompatActivity {

    private static final String TAG = "GetNumber Activity";
    private EditText mFirstNameField, mLastNameField, mTelephoneField, mPartyNumberField;
    private Button mConfirmButton;
    private PopupWindow popUpWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        mFirstNameField = (EditText) findViewById(R.id.firstName);
        mLastNameField = (EditText) findViewById(R.id.lastName);
        mTelephoneField = (EditText) findViewById(R.id.telephone);
        mPartyNumberField= (EditText) findViewById(R.id.partyNumber);
        mConfirmButton = (Button) findViewById(R.id.confirmButton);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
