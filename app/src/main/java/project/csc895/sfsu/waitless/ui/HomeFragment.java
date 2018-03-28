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
import android.widget.ImageView;
import android.widget.TextView;

import project.csc895.sfsu.waitless.R;

public class HomeFragment extends Fragment{

    private static final String TAG = "Home Fragment";
    public final static String EXTRA_SEARCH = "Pass Search Tag";
    public final static String EXTRA_RESTAURANT_ID = "Pass Restaurant ID";

    // hard coded restaurant ids
    public final static String RESTAURANT_ID_I_HOP = "-L8hpJLasRFNJrBeNPEc";
    public final static String RESTAURANT_ID_DIN_TAI_FENG = "-L8hmS2FsDYyLG4Hy16_";
    public final static String RESTAURANT_ID_BBQ = "-L8htVBpvFJBOm37GpGg";
    public final static String RESTAURANT_ID_RESTAURANT_PARK = "-L8VXoROF8kamwgyvG3q";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView mSearchTextView = (TextView) view.findViewById(R.id.searchTextView);
        showSearchPageOnClick(mSearchTextView);

        ImageButton mChineseButton = (ImageButton)view.findViewById(R.id.chineseCuisineButton);
        ImageButton mItalianButton = (ImageButton)view.findViewById(R.id.italianCuisineButton);
        ImageButton mJapaneseButton = (ImageButton)view.findViewById(R.id.japaneseCuisineButton);
        ImageButton mAmericanButton = (ImageButton)view.findViewById(R.id.americanCuisineButton);
        ImageButton mThaiButton = (ImageButton)view.findViewById(R.id.thaiCuisineButton);
        ImageButton mViewAllButton = (ImageButton)view.findViewById(R.id.viewAllCuisineButton);
        showSearchPageOnClick(mChineseButton);
        showSearchPageOnClick(mItalianButton);
        showSearchPageOnClick(mJapaneseButton);
        showSearchPageOnClick(mAmericanButton);
        showSearchPageOnClick(mThaiButton);
        showSearchPageOnClick(mViewAllButton);

        ImageView iHopLogo = (ImageView) view.findViewById(R.id.logo_iHop);
        ImageView DinTaiFengLogo = (ImageView) view.findViewById(R.id.logo_DinTaiFeng);
        ImageView bbqLogo = (ImageView) view.findViewById(R.id.logo_bbq);
        ImageView RestaurantParkLogo = (ImageView) view.findViewById(R.id.logo_RestaurantPark);

        iHopLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_ID, RESTAURANT_ID_I_HOP);
                startActivity(intent);
            }
        });

        DinTaiFengLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_ID, RESTAURANT_ID_DIN_TAI_FENG);
                startActivity(intent);
            }
        });

        bbqLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_ID, RESTAURANT_ID_BBQ);
                startActivity(intent);
            }
        });

        RestaurantParkLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_ID, RESTAURANT_ID_RESTAURANT_PARK);
                startActivity(intent);
            }
        });

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
