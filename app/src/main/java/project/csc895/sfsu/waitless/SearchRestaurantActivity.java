package project.csc895.sfsu.waitless;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchRestaurantActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
//    private final String MESSAGES_CHILD = "messages";
//    private DatabaseReference mFirebaseDatabaseReference =
//            FirebaseDatabase.getInstance().getReference();
//    private EditText mSearchBarEditText;
//    private Button mSearchButton;
//    private Button mCancelButton;
//    private ListView mSearchResultListView;
//    private TextView mNoResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_restaurant);


        Log.d(TAG, "3");

    }
}