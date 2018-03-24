package project.csc895.sfsu.waitless.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Number;
import project.csc895.sfsu.waitless.model.Table;
import project.csc895.sfsu.waitless.model.Waitlist;

public class NumberDetailedActivity extends AppCompatActivity {

    private static final String NUMBER_CHILD = "numbers";
    private static final String WAITLIST_CHILD = "waitlists";
    private static final String TABLE_CHILD = "tables";
    private static final String TABLE_ID_CHILD = "tableID";
    private static final String NUMBER_ID_CHILD = "numberID";
    private static final String RESTAURANT_ID_CHILD = "restaurantID";
    private static final String STATUS_CHILD = "status";
    private static final String NUMBER_STATUS_WAITING = "Waiting";
    private static final String NUMBER_STATUS_DINING = "Dining";
    private static final String NUMBER_STATUS_CANCELLED = "Cancelled";
    private static final String NUMBER_STATUS_COMPLETED = "Completed";
    private static final String TABLE_STATUS_DIRTY = "Dirty";
    private static final String WAIT_NUM_TABLE_A_CHILD = "waitNumTableA";
    private static final String WAIT_NUM_TABLE_B_CHILD = "waitNumTableB";
    private static final String WAIT_NUM_TABLE_C_CHILD = "waitNumTableC";
    private static final String WAIT_NUM_TABLE_D_CHILD = "waitNumTableD";
    private LinearLayout mLinearLayout;
    private TextView restaurantName, numberNameField, statusField, customerName, customerPhone, partyNumber, createdTime;
    private Button completeButton, cancelButton;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String numberID, restaurantID, waitlistID, tableID;
    private String numberName;
    private int waitNumTableA, waitNumTableB, waitNumTableC, waitNumTableD;
    private TextView tableName;
    private ImageView tableIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_detail);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        Intent intent = getIntent();
        //Number number = (Number) intent.getSerializableExtra(WaitlistHistoryFragment.EXTRA_NUMBER);  // get number obj
        numberID = intent.getStringExtra(WaitlistHistoryFragment.EXTRA_NUMBER_ID);
        restaurantID = intent.getStringExtra(WaitlistHistoryFragment.EXTRA_RESTAURANT_ID);
        tableID = intent.getStringExtra(WaitlistHistoryFragment.EXTRA_TABLE_ID);  // if not null, indicate number is dining status.
        // NOTE: get tableID both real time and got it as extra. May get changed

        initViews();
        loadNumberInfo();
        getWaitlistInfo();
    }

    private void initViews() {
        mLinearLayout = (LinearLayout) findViewById(R.id.numberDetailLinearLayout);
        restaurantName = (TextView) findViewById(R.id.restaurant);
        numberNameField = (TextView) findViewById(R.id.number);
        statusField = (TextView) findViewById(R.id.status);
        tableIcon = (ImageView) findViewById(R.id.tableIcon);
        tableName = (TextView) findViewById(R.id.tableName);
        customerName = (TextView) findViewById(R.id.customerName);
        customerPhone = (TextView) findViewById(R.id.customerTelephone);
        partyNumber = (TextView) findViewById(R.id.customerPartyNumber);
        createdTime = (TextView) findViewById(R.id.numberCreatedTime);

        completeButton = (Button) findViewById(R.id.completeButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmCompleteAlertDialog();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // popup window and trigger cancel the number
                showCancelPopupWindow();
            }
        });
    }

    private void loadNumberInfo() {
        DatabaseReference ref = mDatabase.child(NUMBER_CHILD).child(numberID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Number number = dataSnapshot.getValue(Number.class);
                if (number != null) {
                    String status = number.getStatus();
                    numberName = number.getNumberName();
                    restaurantName.setText(number.getRestaurantName());
                    String displayNumberName = "Number "+ numberName;
                    numberNameField.setText(displayNumberName);
                    statusField.setText(status);
                    tableID = number.getTableID();  // get tableID real time
                    customerName.setText(number.getUsername());
                    customerPhone.setText(number.getPhone());
                    partyNumber.setText(String.valueOf(number.getPartyNumber()));
                    createdTime.setText(number.getTimeCreated());

                    if (status.equals(NUMBER_STATUS_WAITING)) {         //show cancel button. hide complete button
                        completeButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.VISIBLE);
                    } else if (status.equals(NUMBER_STATUS_DINING)) {   // show complete button. hide cancel button
                        completeButton.setVisibility(View.VISIBLE);
                        cancelButton.setVisibility(View.GONE);
                    } else if (status.equals(NUMBER_STATUS_CANCELLED) || status.equals(NUMBER_STATUS_COMPLETED)) { // hide both buttons
                        completeButton.setVisibility(View.GONE);
                        cancelButton.setVisibility(View.GONE);
                    }

                    if (tableID != null) {
                        tableIcon.setVisibility(View.VISIBLE);
                        tableName.setVisibility(View.VISIBLE);
                        loadTableName();
                    } else {
                        tableIcon.setVisibility(View.GONE);
                        tableName.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getWaitlistInfo() {
        Query query = mDatabase.child(WAITLIST_CHILD)
                .orderByChild(RESTAURANT_ID_CHILD)
                .equalTo(restaurantID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChildren()) {
                    Log.d("Error", "NO WAITLIST SHOWS");
                } else {
                    for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                        Waitlist waitlist = objSnapshot.getValue(Waitlist.class);
                        if (waitlist != null) {
                            waitlistID = waitlist.getWaitlistID();
                            Log.d("waitlistID inside", waitlistID);
                            waitNumTableA = waitlist.getWaitNumTableA();
                            waitNumTableB = waitlist.getWaitNumTableB();
                            waitNumTableC = waitlist.getWaitNumTableC();
                            waitNumTableD = waitlist.getWaitNumTableD();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showCancelPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) NumberDetailedActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            View customView = inflater.inflate(R.layout.popup_window_cancel, null);
            final PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            popupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);

            Button yesButton = (Button) customView.findViewById(R.id.yesButton);
            Button noButton = (Button) customView.findViewById(R.id.noButton);
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelNumber();
                    popupWindow.dismiss();

                }
            });
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }
    }

    private void cancelNumber() {
        //cancel number: set number status to cancelled. update db. update textview ui (auto)
        //waitNumTable -1
        //toast
        DatabaseReference numberRef = mDatabase.child(NUMBER_CHILD).child(numberID);
        numberRef.child(STATUS_CHILD).setValue(NUMBER_STATUS_CANCELLED);

        updateWaitlistInfo();
        Toast.makeText(NumberDetailedActivity.this, getString(R.string.cancel_succeed), Toast.LENGTH_SHORT).show();
    }

    private void updateWaitlistInfo() {
        DatabaseReference ref = mDatabase.child(WAITLIST_CHILD).child(waitlistID);
        if (numberName.charAt(0) == 'A') {
            waitNumTableA -= 1;
            ref.child(WAIT_NUM_TABLE_A_CHILD).setValue(waitNumTableA);
        } else if (numberName.charAt(0) == 'B') {
            waitNumTableB -= 1;
            ref.child(WAIT_NUM_TABLE_B_CHILD).setValue(waitNumTableB);
        } else if (numberName.charAt(0) == 'C') {
            waitNumTableC -= 1;
            ref.child(WAIT_NUM_TABLE_C_CHILD).setValue(waitNumTableC);
        } else if (numberName.charAt(0) == 'D') {
            waitNumTableD -= 1;
            ref.child(WAIT_NUM_TABLE_D_CHILD).setValue(waitNumTableD);
        }
    }

    private void loadTableName() {
        DatabaseReference ref = mDatabase.child(TABLE_CHILD).child(tableID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Table table = dataSnapshot.getValue(Table.class);
                if (table != null) {
                    String displayTableName = "Table " + table.getTableName();
                    tableName.setText(displayTableName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showConfirmCompleteAlertDialog() {
        String message = "Are you ready to check and let us clean " + tableName.getText() + "?";

        AlertDialog.Builder builder = new AlertDialog.Builder(NumberDetailedActivity.this);
        builder.setMessage(message);
        builder.setCancelable(false); // Disallow cancel of AlertDialog on click of back button and outside touch

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        completeNumber();
                    }
                });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void completeNumber() {
        //Update Table status: from Seated to Dirty
        //set table's numberID to null
        DatabaseReference tableRef = mDatabase.child(TABLE_CHILD).child(tableID);
        tableRef.child(STATUS_CHILD).setValue(TABLE_STATUS_DIRTY);
        tableRef.child(NUMBER_ID_CHILD).setValue(null);

        //Update Number status: from Dining to Completed
        //set number's tableID to null
        DatabaseReference numberRef = mDatabase.child(NUMBER_CHILD).child(numberID);
        numberRef.child(STATUS_CHILD).setValue(NUMBER_STATUS_COMPLETED);
        numberRef.child(TABLE_ID_CHILD).setValue(null);

        Toast.makeText(NumberDetailedActivity.this, "Number Completed and Table is under cleaning!", Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(NumberDetailedActivity.this, MainActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
