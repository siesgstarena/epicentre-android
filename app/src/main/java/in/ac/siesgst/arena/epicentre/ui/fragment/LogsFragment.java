package in.ac.siesgst.arena.epicentre.ui.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;

public class LogsFragment extends Fragment {

    private static final String LOG_TAG = LogsFragment.class.getSimpleName();

    public LogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_log, container, false);

        // Update Title
        FragmentUtils.updateTitle((AppCompatActivity) rootView.getContext(), "Logs");

        return rootView;
    }

}
