package in.ac.siesgst.arena.epicentre.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.api.requests.HerokuRequest;
import in.ac.siesgst.arena.epicentre.api.requests.TravisRequest;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;
import in.ac.siesgst.arena.epicentre.utils.InjectorUtils;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModelFactory;
import in.ac.siesgst.arena.epicentre.viewmodel.heroku.HerokuViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.heroku.HerokuViewModelFactory;
import in.ac.siesgst.arena.epicentre.viewmodel.travis.TravisViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.travis.TravisViewModelFactory;

public class HomeFragment extends Fragment {

    private static final String LOG_TAG = HomeFragment.class.getSimpleName();
    @BindView(R.id.home_progress_layout)
    RelativeLayout progressBarLayout;
    @BindView(R.id.home_heroku_layout)
    CardView herokuLayout;
    @BindView(R.id.home_travis_layout)
    CardView travisLayout;
    @BindView(R.id.home_heroku_app_name)
    TextView herokuAppName;
    @BindView(R.id.home_heroku_app_state)
    TextView herokuAppState;
    @BindView(R.id.home_travis_branch_name)
    TextView travisBranchName;
    @BindView(R.id.home_travis_branch_state)
    TextView travisBranchState;
    private AuthViewModel mAuthViewModel;
    private HerokuViewModel mHerokuViewModel;
    private TravisViewModel mTravisViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        // Get the View Models
        AuthViewModelFactory authViewModelFactory = InjectorUtils
                .provideAuthViewModelFactory(container.getContext());
        mAuthViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel.class);
        HerokuViewModelFactory herokuViewModelFactory = InjectorUtils
                .provideHerokuViewModelFactory(container.getContext());
        mHerokuViewModel = ViewModelProviders.of(this, herokuViewModelFactory).get(HerokuViewModel.class);
        TravisViewModelFactory travisViewModelFactory = InjectorUtils
                .provideTravisViewModelFactory(container.getContext());
        mTravisViewModel = ViewModelProviders.of(this, travisViewModelFactory).get(TravisViewModel.class);

        // Observer
        mAuthViewModel.getAuth().observe(this, auth -> {
            if (auth != null) {
                String API_URL = auth.getUrl();
                String API_TOKEN = auth.getToken();
                HerokuRequest.LoadHealth(mHerokuViewModel, container.getContext(), API_URL, API_TOKEN);
                TravisRequest.LoadHealth(mTravisViewModel, container.getContext(), API_URL, API_TOKEN);
            }
        });
        mHerokuViewModel.get().observe(this, heroku -> {
            if (heroku != null) {
                // Set on View
                progressBarLayout.setVisibility(View.INVISIBLE);
                herokuLayout.setVisibility(View.VISIBLE);
                herokuAppName.setText(heroku.getName());
                String state = heroku.getState().toUpperCase();
                herokuAppState.setText(state);
                if (state.equals("DOWN") || state.equals("CRASHED")) {
                    herokuAppState.setTextColor(container.getContext().getResources().getColor(R.color.buildFailed));
                } else if (state.equals("UP")) {
                    herokuAppState.setTextColor(container.getContext().getResources().getColor(R.color.buildPassed));
                }
            }
        });
        mTravisViewModel.getSingle().observe(this, travis -> {
            if (travis != null) {
                // Set on View
                progressBarLayout.setVisibility(View.INVISIBLE);
                travisLayout.setVisibility(View.VISIBLE);
                travisBranchName.setText(travis.getBranch());
                String state = travis.getState().toUpperCase();
                travisBranchState.setText(state);
                if (state.equals("ERRORED") || state.equals("FAILED")) {
                    travisBranchState
                            .setTextColor(container.getContext().getResources().getColor(R.color.buildFailed));
                } else if (state.equals("PASSED")) {
                    travisBranchState
                            .setTextColor(container.getContext().getResources().getColor(R.color.buildPassed));
                }
            }
        });

        // Update Title
        FragmentUtils.updateTitle((AppCompatActivity) rootView.getContext(), "Home");

        // Click Actions to visit individual fragment of its own
        herokuLayout.setOnClickListener(view -> {
            FragmentUtils.replaceFragment(rootView.getContext(), new HerokuFragment());
        });
        travisLayout.setOnClickListener(view -> {
            FragmentUtils.replaceFragment(rootView.getContext(), new TravisFragment());
        });

        return rootView;
    }

}
