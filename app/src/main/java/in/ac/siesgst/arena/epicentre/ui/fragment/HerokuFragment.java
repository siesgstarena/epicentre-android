package in.ac.siesgst.arena.epicentre.ui.fragment;


import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.ac.siesgst.arena.epicentre.R;
import in.ac.siesgst.arena.epicentre.api.Constants;
import in.ac.siesgst.arena.epicentre.api.requests.HerokuRequest;
import in.ac.siesgst.arena.epicentre.api.responses.Base;
import in.ac.siesgst.arena.epicentre.ui.adapters.HerokuAdapter;
import in.ac.siesgst.arena.epicentre.utils.EditConfigDialogFragment;
import in.ac.siesgst.arena.epicentre.utils.FragmentUtils;
import in.ac.siesgst.arena.epicentre.utils.InjectorUtils;
import in.ac.siesgst.arena.epicentre.utils.ViewUtils;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModelFactory;
import in.ac.siesgst.arena.epicentre.viewmodel.heroku.HerokuViewModel;
import in.ac.siesgst.arena.epicentre.viewmodel.heroku.HerokuViewModelFactory;

public class HerokuFragment extends Fragment {

    private static final String LOG_TAG = HerokuFragment.class.getSimpleName();
    @BindView(R.id.heroku_progress_bar_layout)
    RelativeLayout progressBarLayout;
    @BindView(R.id.heroku_info_layout)
    CardView infoCardView;
    @BindView(R.id.heroku_dyno_layout)
    CardView dynoCardView;
    @BindView(R.id.heroku_config_layout)
    CardView configCardView;
    @BindView(R.id.heroku_info_layout_list)
    ListView infoListView;
    @BindView(R.id.heroku_dyno_layout_list)
    ListView dynoListView;
    @BindView(R.id.heroku_config_layout_list)
    ListView configListView;
    @BindView(R.id.heroku_config_add)
    TextView addConfigView;
    private AuthViewModel mAuthViewModel;
    private HerokuViewModel mHerokuViewModel;
    private String API_URL, API_TOKEN;

    public HerokuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_heroku, container, false);
        ButterKnife.bind(this, rootView);

        // Get the ViewModel from the factory
        AuthViewModelFactory authViewModelFactory = InjectorUtils
                .provideAuthViewModelFactory(container.getContext());
        mAuthViewModel = ViewModelProviders.of(this, authViewModelFactory).get(AuthViewModel.class);
        HerokuViewModelFactory herokuViewModelFactory = InjectorUtils
                .provideHerokuViewModelFactory(container.getContext());
        mHerokuViewModel = ViewModelProviders.of(this, herokuViewModelFactory).get(HerokuViewModel.class);

        // Observer
        mAuthViewModel.getAuth().observe(this, auth -> {
            if (auth != null) {
                API_URL = auth.getUrl();
                API_TOKEN = auth.getToken();
                // Reload the app information and dyno information from network
                HerokuRequest.LoadHealth(mHerokuViewModel, container.getContext(), API_URL, API_TOKEN);
                // Load App Dyno Information
                loadConfig(container.getContext(), API_URL, API_TOKEN);
            }
        });
        mHerokuViewModel.get().observe(this, heroku -> {
            if (heroku != null) {
                // Set on App Info View
                HerokuAdapter infoAdapter = new HerokuAdapter(container.getContext(), heroku.getAppInfo());
                infoListView.setAdapter(infoAdapter);
                ViewUtils.setListViewHeightBasedOnChildren(infoListView);
                progressBarLayout.setVisibility(View.INVISIBLE);
                infoCardView.setVisibility(View.VISIBLE);
                // Set on Dyno Info View
                HerokuAdapter dynoAdapter = new HerokuAdapter(container.getContext(), heroku.getDynoInfo());
                dynoListView.setAdapter(dynoAdapter);
                ViewUtils.setListViewHeightBasedOnChildren(dynoListView);
                progressBarLayout.setVisibility(View.INVISIBLE);
                dynoCardView.setVisibility(View.VISIBLE);
            }
        });

        // Update Title
        FragmentUtils.updateTitle((AppCompatActivity) rootView.getContext(), "Heroku");

        // Update Maintenance Mode
        infoListView.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView headingView = (TextView) view.findViewById(R.id.list_item_heading);
            String heading = headingView.getText().toString();
            if (heading.equals("MAINTENANCE")) {
                TextView subHeadingView = (TextView) view.findViewById(R.id.list_item_subheading);
                String subHeading = subHeadingView.getText().toString();
                new AlertDialog.Builder(container.getContext())
                        .setTitle(
                                subHeading.equals("false") ? "Enable Maintenance Mode" : "Disable Maintenance Mode")
                        .setPositiveButton(android.R.string.yes, (dialog, whichButton) ->
                                HerokuRequest.UpdateMaintenance(mHerokuViewModel, container.getContext(), API_URL, API_TOKEN, subHeading))
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        // Update a Config
        configListView.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView headingView = (TextView) view.findViewById(R.id.list_item_heading);
            String heading = headingView.getText().toString();
            TextView subHeadingView = (TextView) view.findViewById(R.id.list_item_subheading);
            String subHeading = subHeadingView.getText().toString();
            if (!heading.isEmpty() && !subHeading.isEmpty()) {
                // Ask for new config
                LayoutInflater li = LayoutInflater.from(container.getContext());
                View promptsView = li.inflate(R.layout.config_edit_item, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(container.getContext());
                alertDialogBuilder.setView(promptsView);
                // Process
                final TextView configKey = (TextView) promptsView
                        .findViewById(R.id.heroku_edit_config_name);
                final EditText configValue = (EditText) promptsView
                        .findViewById(R.id.heroku_edit_config);
                configKey.setText("Edit " + heading);
                configValue.setText(subHeading);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("UPDATE",
                                (dialog, id) -> {
                                    String value = configValue.getText().toString();
                                    Log.v("GotValue", value);
                                    JSONObject jsonObject = new JSONObject();
                                    JSONObject keyValue = new JSONObject();
                                    try {
                                        keyValue.put(heading, value);
                                        jsonObject.put("config", keyValue);
                                        HerokuRequest.AddOrUpdateConfig(mHerokuViewModel, container.getContext(), API_URL, API_TOKEN, jsonObject);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                })
                        .setNegativeButton("CANCEL",
                                (dialog, id) -> dialog.cancel());

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        // Add a Config
        addConfigView.setOnClickListener(view -> {
            LayoutInflater li = LayoutInflater.from(container.getContext());
            View promptsView = li.inflate(R.layout.config_add_item, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(container.getContext());
            alertDialogBuilder.setView(promptsView);
            // Process
            final EditText configKey = (EditText) promptsView
                    .findViewById(R.id.heroku_new_config_key);
            final EditText configValue = (EditText) promptsView
                    .findViewById(R.id.heroku_new_config_value);
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("ADD",
                            (dialog, id) -> {
                                if (configKey.getText().length() > 0 && configValue.getText().length() > 0) {
                                    String key = configKey.getText().toString();
                                    String value = configValue.getText().toString();
                                    JSONObject jsonObject = new JSONObject();
                                    JSONObject keyValue = new JSONObject();
                                    try {
                                        keyValue.put(key, value);
                                        jsonObject.put("config", keyValue);
                                        HerokuRequest.AddOrUpdateConfig(mHerokuViewModel, container.getContext(), API_URL, API_TOKEN, jsonObject);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                    .setNegativeButton("CANCEL",
                            (dialog, id) -> dialog.cancel());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        });

        return rootView;
    }

    private void showEditDialog(Context context) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        EditConfigDialogFragment editConfigDialogFragment = EditConfigDialogFragment
                .newInstance("Enter New Value");
        editConfigDialogFragment.show(fragmentManager, "fragment_edit_name");
    }

    /**
     * Only allowed in current fragment load, not stored in database for security reasons
     *
     * @param context Application Context
     * @param url     Application Endpoint URL
     * @param token   JSON web token
     */
    void loadConfig(Context context, String url, String token) {
        AndroidNetworking.get(url + Constants.HEROKU_CONFIG_URL)
                .addHeaders("authorization", token)
                .setTag("herokuConfigVars")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optString("status").equals("OK") &&
                                response.optInt("code") == 200) {
                            JSONObject dataObject = response.optJSONObject("data");
                            Iterator<String> keys = dataObject.keys();
                            ArrayList<Pair<String, String>> arrayList = new ArrayList<Pair<String, String>>();
                            try {
                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    arrayList.add(Pair.create(key, dataObject.get(key).toString()));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // Set on View
                            HerokuAdapter dynoAdapter = new HerokuAdapter(context, arrayList);
                            configListView.setAdapter(dynoAdapter);
                            ViewUtils.setListViewHeightBasedOnChildren(configListView);
                            progressBarLayout.setVisibility(View.INVISIBLE);
                            configCardView.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context, "Some error. Try again", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.v("ErrorResp", anError.getErrorDetail());
                        String errorMessage = "Some error. Try again";
                        try {
                            Base apiError = anError.getErrorAsObject(Base.class);
                            errorMessage = apiError.getMessage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

}
