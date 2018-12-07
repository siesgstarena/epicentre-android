package in.ac.siesgst.arena.epicentre.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;

public class DatabaseFragment extends Fragment {

    private static final String LOG_TAG = DatabaseFragment.class.getSimpleName();

    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_database, container, false);

        // Update Title
        FragmentUtils.updateTitle((AppCompatActivity) rootView.getContext(), "Database");

        return rootView;
    }

}
