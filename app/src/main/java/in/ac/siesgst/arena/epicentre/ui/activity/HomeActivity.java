package in.ac.siesgst.arena.epicentre.ui.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.database.entity.Auth;
import in.ac.siesgst.arena.epicentre.ui.fragment.AboutFragment;
import in.ac.siesgst.arena.epicentre.ui.fragment.DatabaseFragment;
import in.ac.siesgst.arena.epicentre.ui.fragment.HerokuFragment;
import in.ac.siesgst.arena.epicentre.ui.fragment.HomeFragment;
import in.ac.siesgst.arena.epicentre.ui.fragment.LogsFragment;
import in.ac.siesgst.arena.epicentre.ui.fragment.TravisFragment;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;
import in.ac.siesgst.arena.epicentre.utils.InjectorUtils;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModelFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();
    // Butterknife
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.nav_about)
    TextView aboutView;
    private AuthViewModel mAuthViewModel;
    private Auth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        // UI Initialization
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // Click Actions
        navigationView.getHeaderView(0).setOnClickListener(view -> {
            FragmentUtils.replaceFragment(this, new HomeFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        aboutView.setOnClickListener(view -> {
            FragmentUtils.replaceFragment(this, new AboutFragment());
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        // Load default HomeFragment in HomeActivity
        FragmentUtils.replaceFragment(this, new HomeFragment());

        // Get the AuthRequest ViewModel from the AuthRequest ViewModelFactory
        AuthViewModelFactory authViewModelFactory = InjectorUtils
                .provideAuthViewModelFactory(this.getApplicationContext());
        mAuthViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel.class);

        // Observer for Updating AuthRequest Information
        mAuthViewModel.getAuth().observe(this, auth -> {
            if (auth == null) {
                Intent intent = new Intent(HomeActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            } else {
                mAuth = auth;
                // Update Nav Drawer with updated information
                TextView lastUpdated = (TextView) navigationView.getHeaderView(0)
                        .findViewById(R.id.nav_header_subtitle);
                String updatedAt = "Last Updated: " + mAuth.getUpdatedAt();
                lastUpdated.setText(updatedAt);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Sign Out Action
        if (id == R.id.action_signout) {
            // Delete entries from database and sign out
            mAuthViewModel.deleteAuth();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_app_database) {
            FragmentUtils.replaceFragment(this, new DatabaseFragment());
        } else if (id == R.id.nav_app_logs) {
            FragmentUtils.replaceFragment(this, new LogsFragment());
        } else if (id == R.id.nav_heroku) {
            FragmentUtils.replaceFragment(this, new HerokuFragment());
        } else if (id == R.id.nav_travis_ci) {
            FragmentUtils.replaceFragment(this, new TravisFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
