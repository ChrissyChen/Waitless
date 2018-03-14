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
import android.widget.ImageButton;
import android.widget.TextView;

import project.csc895.sfsu.waitless.R;

public class HomeFragment extends Fragment{

    private static final String TAG = "Home Fragment";
    public final static String EXTRA_SEARCH = "Pass Search Tag";
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

        return view;
    }

    private void showSearchPageOnClick(View mView) {
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                String searchTag = v.getTag().toString();
                Log.d(TAG, searchTag);
                intent.putExtra(EXTRA_SEARCH, searchTag);
                startActivity(intent);
            }
        });
    }
}
