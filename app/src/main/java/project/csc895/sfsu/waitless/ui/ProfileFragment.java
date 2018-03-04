package project.csc895.sfsu.waitless.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import project.csc895.sfsu.waitless.R;

public class ProfileFragment extends Fragment {

    private TextView mNameTextView;
    private LinearLayout historyLinearLayout, favoriteLinearLayout;
    private Button btn_signout;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
        historyLinearLayout= (LinearLayout) view.findViewById(R.id.historyLinearLayout);
        btn_signout = (Button) view.findViewById(R.id.btn_signout);
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).signOut();
            }
        });

        return view;
    }


}