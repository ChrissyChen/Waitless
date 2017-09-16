package project.csc895.sfsu.waitless.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import project.csc895.sfsu.waitless.R;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "Search Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String searchTag = intent.getStringExtra(HomeFragment.EXTRA_MESSAGE);
        Log.d(TAG, searchTag);

        EditText editText = (EditText) findViewById(R.id.searchBarEditText);
        editText.setText(searchTag);

        LinearLayout searchLinearLayout = (LinearLayout) findViewById(R.id.searchLinearLayout);
        searchLinearLayout.setFocusable(true);
        searchLinearLayout.setFocusableInTouchMode(true);


    }
}
