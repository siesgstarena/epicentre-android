package in.ac.siesgst.arena.epicentre.ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;

public class AboutFragment extends Fragment {

    private static final String LOG_TAG = AboutFragment.class.getSimpleName();

    public AboutFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        // Update Title
        FragmentUtils.updateTitle((AppCompatActivity) rootView.getContext(), "About");
        Button git = (Button) rootView.findViewById(R.id.gitlink);
        git.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/siesgstarena/epicentre-android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
