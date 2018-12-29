package in.ac.siesgst.arena.epicentre.api.requests;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.ac.siesgst.arena.epicentre.api.Constants;
import in.ac.siesgst.arena.epicentre.api.responses.Base;
import in.ac.siesgst.arena.epicentre.database.entity.Travis;
import in.ac.siesgst.arena.epicentre.viewmodel.travis.TravisViewModel;

/**
 * Created by Omkar Prabhu on 07/12/18.
 */
public class TravisRequest {

    private static final String LOG_TAG = TravisRequest.class.getSimpleName();

    /**
     * Tries loading latest build status from Travis CI
     *
     * @param travisViewModel View Model to interact with TravisRepository
     * @param context         Application Context
     * @param url             Application Endpoint
     * @param token           JSON web tokens
     */
    public static void LoadHealth(TravisViewModel travisViewModel, Context context, String url, String token) {
        AndroidNetworking.get(url + Constants.TRAVIS_URL)
                .addHeaders("authorization", token)
                .setTag("travisHealth")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optString("status").equals("OK")) {
                            JSONObject dataObject = response.optJSONObject("data");
                            String number = dataObject.optString("number");
                            String state = dataObject.optString("state");
                            String startedAt = dataObject.optString("started_at");
                            String finishedAt = dataObject.optString("finished_at");
                            String branch = dataObject.optString("branch");
                            String message = dataObject.optString("message");
                            int duration = dataObject.optInt("duration");
                            // Insert into database
                            travisViewModel.insert(new Travis(number, state, startedAt, finishedAt, branch, message, duration));
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
     * Tries loading a list of builds with full information from Travis CI
     *
     * @param travisViewModel View Model to interact with TravisRepository
     * @param context         Application Context
     * @param url             Application Endpoint
     * @param token           JSON web tokens
     */
    public static void LoadBuilds(TravisViewModel travisViewModel, Context context, String url, String token) {
        AndroidNetworking.get(url + Constants.TRAVIS_BUILDS_URL)
                .addHeaders("authorization", token)
                .setTag("travisBuilds")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.optString("status").equals("OK")) {
                            JSONArray dataArray = response.optJSONArray("data");
                            List<Travis> travisList = new ArrayList<Travis>();
                            try {
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject travisObject = dataArray.getJSONObject(i);
                                    String number = travisObject.optString("number");
                                    String state = travisObject.optString("state");
                                    String startedAt = travisObject.optString("started_at");
                                    String finishedAt = travisObject.optString("finished_at");
                                    JSONObject branchObject = travisObject.optJSONObject("branch");
                                    String branch = branchObject.optString("name");
                                    JSONObject commitObject = travisObject.optJSONObject("commit");
                                    String message = commitObject.optString("message");
                                    int duration = travisObject.optInt("duration");
                                    travisList.add(new Travis(number, state, startedAt, finishedAt, branch, message, duration));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                // Insert into database
                                travisViewModel.insert(travisList);
                            }
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
