package project.csc895.sfsu.waitless.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import project.csc895.sfsu.waitless.R;

public class HomeFragment extends Fragment{

    private static final String TAG = "Home Fragment";
    public final static String EXTRA_MESSAGE = "Extra Message_" + TAG;
    private TextView mSearchTextView;
    private ImageButton mChineseButton, mItalianButton, mJapaneseButton,
            mAmericanButton, mThaiButton, mViewAllButton;
    private BottomNavigationView mBottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mSearchTextView = (TextView) view.findViewById(R.id.searchTextView);
        showSearchPageOnClick(mSearchTextView);
//        mSearchButton = (Button) view.findViewById(R.id.searchButton);
//        showSearchPageOnClick(mSearchButton);

//        mSearchEditText = (EditText)view.findViewById(R.id.searchEditText);
//
//        mSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//
//                }
//            }
//        });
//
//        showSearchPageOnClick(mSearchEditText);
        Log.d(TAG, "3");

        mChineseButton = (ImageButton)view.findViewById(R.id.chineseCuisineButton);
        mItalianButton = (ImageButton)view.findViewById(R.id.italianCuisineButton);
        mJapaneseButton = (ImageButton)view.findViewById(R.id.japaneseCuisineButton);
        mAmericanButton = (ImageButton)view.findViewById(R.id.americanCuisineButton);
        mThaiButton = (ImageButton)view.findViewById(R.id.thaiCuisineButton);
        mViewAllButton = (ImageButton)view.findViewById(R.id.viewAllCuisineButton);
        showSearchPageOnClick(mChineseButton);
        showSearchPageOnClick(mItalianButton);
        showSearchPageOnClick(mJapaneseButton);
        showSearchPageOnClick(mAmericanButton);
        showSearchPageOnClick(mThaiButton);
        showSearchPageOnClick(mViewAllButton);
        Log.d(TAG, "4");

        return view;
    }

    private void showSearchPageOnClick(View mView) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                String searchTag = v.getTag().toString();
                Log.d(TAG, searchTag);
                intent.putExtra(EXTRA_MESSAGE, searchTag);
                startActivity(intent);

//                Fragment  searchFragment = new SearchFragment();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.rootLayout, searchFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }
}
