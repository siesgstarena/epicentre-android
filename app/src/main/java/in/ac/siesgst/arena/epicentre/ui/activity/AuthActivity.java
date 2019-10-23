package in.ac.siesgst.arena.epicentre.ui.activity;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.api.requests.AuthRequest;
import in.ac.siesgst.arena.epicentre.utils.InjectorUtils;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModelFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AuthActivity extends AppCompatActivity {

    private static final String LOG_TAG = AuthActivity.class.getSimpleName();
    // Butterknife
    @BindView(R.id.auth_url)
    EditText urlEditText;
    @BindView(R.id.auth_code)
    EditText codeEditText;
    @BindView(R.id.auth_submit_button)
    Button submitButton;
    @BindView(R.id.auth_activity_root)
    View view;
    private AuthViewModel mAuthViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        // Get the AuthRequest ViewModel from the AuthRequest ViewModelFactory
        AuthViewModelFactory authViewModelFactory = InjectorUtils
                .provideAuthViewModelFactory(this.getApplicationContext());
        mAuthViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel.class);

        // After sign in, if entry is added into database, it will switch activity
        mAuthViewModel.getAuth().observe(this, auth -> {
            if (auth != null) {
                // If user is inserted into database, switch to HomeActivity
                Intent intent = new Intent(AuthActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Sign In Action on Click
        submitButton.setOnClickListener(view -> {
            // Some validation
            if (urlEditText.getText().length() == 0) {
                Snackbar.make(view, "Enter your application endpoint", Snackbar.LENGTH_LONG).show();
                urlEditText.requestFocus();
            } else if (codeEditText.getText().length() == 0) {
                Snackbar.make(view, "Enter your secret code", Snackbar.LENGTH_LONG).show();
                codeEditText.requestFocus();
            } else {
                // Extract information from signIn form
                String url = urlEditText.getText().toString();
                String code = codeEditText.getText().toString();
                // Validate endpoint as HTTP URL
                if (URLUtil.isHttpUrl(url)) {
                    if (url.charAt(url.lastIndexOf(url)) != '/') {
                        url = url.concat("/");
                    }
                    AuthRequest.SignIn(mAuthViewModel, view, url, code);
                } else {
                    Snackbar.make(view, "Enter a valid application endpoint", Snackbar.LENGTH_LONG).show();
                    urlEditText.requestFocus();
                }
            }
        });
    }

    @Override
    public void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
