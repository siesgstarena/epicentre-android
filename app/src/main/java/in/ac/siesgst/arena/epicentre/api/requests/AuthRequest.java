package in.ac.siesgst.arena.epicentre.api.requests;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import in.ac.siesgst.arena.epicentre.api.Constants;
import in.ac.siesgst.arena.epicentre.api.responses.Base;
import in.ac.siesgst.arena.epicentre.viewmodel.auth.AuthViewModel;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
public class AuthRequest {

    private static final String LOG_TAG = AuthRequest.class.getSimpleName();

    /**
     * Tries Sign In to given application endpoint
     * Server generated token is to be saved in the database
     *
     * @param authViewModel View Model to interact with Auth Repository
     * @param view          Base View to show Snackbars or Messages
     * @param url           Server URL
     * @param code          Secret Code
     */
    public static void SignIn(AuthViewModel authViewModel, View view, String url, String code) {
        Snackbar.make(view, "Signing in...", Snackbar.LENGTH_INDEFINITE).show();
        AndroidNetworking.post(url + Constants.AUTH_URL)
                .addBodyParameter("code", code)
                .setTag("auth")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(LOG_TAG, response.toString());
                        if (response.optString("status").equals("OK")) {
                            JSONObject dataObject = response.optJSONObject("data");
                            String token = dataObject.optString("token");
                            // Insert into database
                            authViewModel.insertAuth(url, code, token);
                        } else {
                            Snackbar.make(view, "Some error. Try again", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        String errorMessage = "Some error. Try again";
                        try {
                            Base apiError = anError.getErrorAsObject(Base.class);
                            errorMessage = apiError.getMessage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show();
                    }
                });
    }

}
