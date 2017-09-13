package project.csc895.sfsu.waitless;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class HomeFragment extends Fragment{

    private static final String TAG = "Home Fragment";
    private EditText mSearchEditText;
    private ImageButton mChineseButton, mItalianButton, mJapaneseButton,
            mAmericanButton, mThaiButton, mViewAllButton;
    private BottomNavigationView mBottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mSearchEditText = (EditText)view.findViewById(R.id.searchEditText);

        mSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.d(TAG, "2");
                    showSearchPageOnClick(mSearchEditText);
                }
            }
        });

        showSearchPageOnClick(mSearchEditText);
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
                startActivity(new Intent(getActivity(), SearchActivity.class));

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
