package in.ac.siesgst.arena.epicentre.ui.fragment;


import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.api.requests.TravisRequest;
import in.ac.siesgst.arena.epicentre.ui.adapters.TravisAdapter;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;
import in.ac.siesgst.arena.epicentre.utils.InjectorUtils;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModelFactory;
import in.ac.siesgst.arena.epicentre.viewmodel.travis.TravisViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.travis.TravisViewModelFactory;

public class TravisFragment extends Fragment {

    private static final String LOG_TAG = TravisFragment.class.getSimpleName();
    @BindView(R.id.travis_progress_bar_layout)
    RelativeLayout progressBarLayout;
    @BindView(R.id.travis_builds_layout)
    CardView buildLayout;
    @BindView(R.id.travis_builds_layout_list)
    RecyclerView buildListView;
    private AuthViewModel mAuthViewModel;
    private TravisViewModel mTravisViewModel;

    public TravisFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_travis, container, false);
        ButterKnife.bind(this, rootView);

        // Get the ViewModel from the factory
        AuthViewModelFactory authViewModelFactory = InjectorUtils
                .provideAuthViewModelFactory(container.getContext());
        mAuthViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel.class);
        TravisViewModelFactory travisViewModelFactory = InjectorUtils
                .provideTravisViewModelFactory(container.getContext());
        mTravisViewModel = ViewModelProviders.of(this, travisViewModelFactory).get(TravisViewModel.class);

        // Observer
        mAuthViewModel.getAuth().observe(this, auth -> {
            if (auth != null) {
                String API_URL = auth.getUrl();
                String API_TOKEN = auth.getToken();
                TravisRequest.LoadBuilds(mTravisViewModel, container.getContext(), API_URL, API_TOKEN);
            }
        });
        mTravisViewModel.get().observe(this, travisList -> {
            // Set on View
            if (travisList != null) {
                TravisAdapter travisAdapter = new TravisAdapter(travisList);
                progressBarLayout.setVisibility(View.INVISIBLE);
                buildLayout.setVisibility(View.VISIBLE);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                        buildListView.getContext(),
                        LinearLayoutManager.VERTICAL);
                buildListView.addItemDecoration(dividerItemDecoration);
                buildListView.setAdapter(travisAdapter);
                buildListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
            }
        });

        // Update Title
        FragmentUtils.updateTitle((AppCompatActivity) rootView.getContext(), "Travis CI");

        return rootView;
    }
}
