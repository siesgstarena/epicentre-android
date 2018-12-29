package in.ac.siesgst.arena.epicentre.api.requests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import in.ac.siesgst.arena.epicentre.api.Constants;
import in.ac.siesgst.arena.epicentre.api.responses.Base;
import in.ac.siesgst.arena.epicentre.database.entity.Heroku;
import in.ac.siesgst.arena.epicentre.viewmodel.heroku.HerokuViewModel;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
public class HerokuRequest {

    private static final String LOG_TAG = HerokuRequest.class.getSimpleName();

    /**
     * Tries to load heroku health information of dyno and app
     *
     * @param herokuViewModel View Model to interact with Heroku Repository
     * @param context         Application Context
     * @param url             Application Endpoint
     * @param token           JSON web tokens
     */
    public static void LoadHealth(HerokuViewModel herokuViewModel, Context context, String url, String token) {
        AndroidNetworking.get(url + Constants.HEROKU_URL)
                .addHeaders("authorization", token)
                .setTag("herokuHealth")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optString("status").equals("OK")) {
                            JSONObject dataObject = response.optJSONObject("data");
                            JSONObject infoObject = dataObject.optJSONObject("info");
                            JSONObject dynoObject = dataObject.optJSONObject("dyno");
                            // App Information
                            String buildpack = infoObject.optString("buildpack_provided_description");
                            String createdAt = infoObject.optString("created_at");
                            String releasedAt = infoObject.optString("released_at");
                            String updatedAt = infoObject.optString("updated_at");
                            String name = infoObject.optString("name");
                            String url = infoObject.optString("web_url");
                            Boolean maintenance = infoObject.optBoolean("maintenance");
                            JSONObject stackObject = infoObject.optJSONObject("stack");
                            String stack = stackObject.optString("name");
                            JSONObject regionObject = infoObject.optJSONObject("region");
                            String region = regionObject.optString("name");
                            // Dyno Information
                            String command = dynoObject.optString("command");
                            String dynoCreatedAt = dynoObject.optString("created_at");
                            String dynoUpdatedAt = dynoObject.optString("updated_at");
                            String state = dynoObject.optString("state");
                            String type = dynoObject.optString("type");
                            String dynoName = dynoObject.optString("name");
                            String size = dynoObject.optString("size");
                            JSONObject releaseObject = dynoObject.optJSONObject("release");
                            int version = releaseObject.optInt("version");
                            // Insert into database
                            herokuViewModel.insert(new Heroku(1, buildpack, stack, createdAt, name, region,
                                    releasedAt, updatedAt, url, command, dynoCreatedAt, dynoName, size,
                                    state, type, dynoUpdatedAt, maintenance, version));
                        } else {
                            Toast.makeText(context, "Some error. Try again", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Tries to update maintenance mode of the heroku app
     *
     * @param herokuViewModel View Model to interact with Heroku Repository
     * @param context         Application Context
     * @param url             Application Endpoint
     * @param token           JSON web tokens
     * @param value           Boolean value for setting the mode
     */
    public static void UpdateMaintenance(HerokuViewModel herokuViewModel, Context context, String url, String token, String value) {
        JSONObject jsonObject = new JSONObject();
        value = value.equals("true") ? "false" : "true";
        try {
            jsonObject.put("maintenance", value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String finalValue = value;
        AndroidNetworking.patch(url + Constants.HEROKU_MAINTENANCE_URL)
                .addHeaders("authorization", token)
                .addJSONObjectBody(jsonObject)
                .setTag("herokuMaintenance")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optString("status").equals("OK")) {
                            herokuViewModel.updateMaintenance(Boolean.parseBoolean(finalValue));
                            Toast.makeText(context, response.optString("message"), Toast.LENGTH_LONG).show();
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

    /**
     * Tries to add or update config variables given as json for the heroku app
     *
     * @param herokuViewModel View Model to interact with Heroku Repository
     * @param context         Application Context
     * @param url             Application Endpoint
     * @param token           JSON web tokens
     * @param jsonObject      Boolean value for setting the mode
     */
    public static void AddOrUpdateConfig(HerokuViewModel herokuViewModel, Context context, String url, String token, JSONObject jsonObject) {
        AndroidNetworking.patch(url + Constants.HEROKU_CONFIG_URL)
                .addHeaders("authorization", token)
                .addJSONObjectBody(jsonObject)
                .setTag("herokuConfig")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optString("status").equals("OK")) {
                            // TODO: Refresh Fragment with new data showing latest configs
                            Toast.makeText(context, response.optString("message"), Toast.LENGTH_LONG).show();
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
