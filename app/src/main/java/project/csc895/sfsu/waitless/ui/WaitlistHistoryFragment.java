package project.csc895.sfsu.waitless.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import project.csc895.sfsu.waitless.R;
import project.csc895.sfsu.waitless.model.Number;

public class WaitlistHistoryFragment extends Fragment {

    private static final String TAG = "Search Activity";
    private static final String ARGS_USER_ID = "userID";
    private static final String NUMBER_CHILD = "numbers";
    private static final String TIME_CREATED_CHILD = "timeCreated";
    private static final String USER_ID_CHILD = "userID";
    public final static String EXTRA_NUMBER = "Pass Number";
    private RecyclerView mRecyclerView;
    private TextView mNoRecordTextView;
    private FirebaseRecyclerAdapter mAdapter;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String userID;
    private LinearLayout mLinearLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waitlist_history, container, false);

        // Retrieve data from main activity
        Bundle args = getArguments();
        userID = args.getString(ARGS_USER_ID);
        Log.d("userID", userID);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.waitlistHistory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNoRecordTextView = (TextView) view.findViewById(R.id.noRecord);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.waitlistHistoryLinearLayout);

        showWaitlistHistory();

        return view;
    }

    private void showWaitlistHistory() {
        Query query = mDatabase.child(NUMBER_CHILD)
                .orderByChild(USER_ID_CHILD)
                .equalTo(userID);

        mAdapter = new FirebaseRecyclerAdapter<Number, NumberViewHolder>(
                Number.class,
                R.layout.item_waitlist_number_info,
                NumberViewHolder.class,
                query) {
            @Override
            protected void populateViewHolder(NumberViewHolder viewHolder, Number number, int position) {
                viewHolder.setRestaurantName(number.getRestaurantName());
                viewHolder.setNumberName(number.getNumberName());
                viewHolder.setCustomerName(number.getUsername());
                viewHolder.setCreatedTime(number.getTimeCreated());

                viewHolder.onClick(number);
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // show no result view
                if (!dataSnapshot.hasChildren()) {
                    mRecyclerView.setVisibility(View.GONE);
                    mNoRecordTextView.setVisibility(View.VISIBLE);
                    Log.d(TAG, "NO RECORD SHOWS");
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNoRecordTextView.setVisibility(View.GONE);
                    Log.d(TAG, "RESULT VIEW SHOWS");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class NumberViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;
        private TextView mRestaurantName, mNumberName, mCustomerName, mCreatedTime;

        public NumberViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.waitlistInfoCardView);
            mRestaurantName = (TextView) itemView.findViewById(R.id.restaurantName);
            mNumberName = (TextView) itemView.findViewById(R.id.numberName);
            mCustomerName = (TextView) itemView.findViewById(R.id.customerName);
            mCreatedTime = (TextView) itemView.findViewById(R.id.numberCreatedTime);
        }

        public void setRestaurantName(String restaurantName) {
            mRestaurantName.setText(restaurantName);
        }

        public void setNumberName(String numberName) {
            mNumberName.setText(numberName);
        }

        public void setCustomerName(String customerName) {
            mCustomerName.setText(customerName);
        }

        public void setCreatedTime(String createdTime) {
            mCreatedTime.setText(createdTime);
        }

        public void onClick(final Number number) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // popup a window
                    //showDetailedPopupWindow(number);

                    Context context = v.getContext();
                    Intent intent = new Intent(context, NumberDetailedActivity.class);
                    intent.putExtra(EXTRA_NUMBER, number);// pass number obj
                    context.startActivity(intent);
                }
            });
        }
    }

//    public void showDetailedPopupWindow(Number number) {
//        final PopupWindow mPopupWindow;
//
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (inflater != null) {
//            View customView = inflater.inflate(R.layout.popup_window_detailed_number, null);
//            mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            mPopupWindow.showAtLocation(mLinearLayout, Gravity.CENTER, 0, 0);
//
//            TextView numberName = (TextView) customView.findViewById(R.id.number);
//            TextView restaurant = (TextView) customView.findViewById(R.id.restaurant);
//            TextView customerName = (TextView) customView.findViewById(R.id.customerName);
//            TextView telephone = (TextView) customView.findViewById(R.id.customerTelephone);
//            TextView partyNumberTextView = (TextView) customView.findViewById(R.id.customerPartyNumber);
//            TextView createdTime = (TextView) customView.findViewById(R.id.numberCreatedTime);
//
//            numberName.setText(number.getNumberName());
//            restaurant.setText(number.getRestaurantID()); //TODO
//            customerName.setText(number.getUsername());
//            telephone.setText(number.getPhone());
//            partyNumberTextView.setText(String.valueOf(number.getPartyNumber()));
//            createdTime.setText(number.getTimeCreated());
//
//            Button completeButton = (Button) customView.findViewById(R.id.completeButton);
//            Button cancelButton = (Button) customView.findViewById(R.id.cancelButton);
//            Button closeButton = (Button) customView.findViewById(R.id.closeButton);
//
//            completeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // TODO trigger table to be available
//                    mPopupWindow.dismiss();
//                }
//            });
//            cancelButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // TODO trigger cancel the number
//                    mPopupWindow.dismiss();
//                }
//            });
//            closeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mPopupWindow.dismiss();
//                }
//            });
//        }
//    }
}
