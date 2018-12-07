package in.ac.siesgst.arena.epicentre.api.responses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SIESGSTarena on 05/12/18.
 */
public class TravisBuild {

    private String number, state, startedAt, finishedAt, branch, message;
    private int duration;

    private TravisBuild(String number, String state, String startedAt, String finishedAt,
                        String branch, String message, int duration) {
        this.number = number;
        this.state = state;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.branch = branch;
        this.message = message;
        this.duration = duration;
    }


    public static ArrayList<TravisBuild> getArrayList(JSONArray jsonArray) {
        ArrayList<TravisBuild> travisBuildList = new ArrayList<TravisBuild>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String number = jsonObject.optString("number");
                String state = jsonObject.optString("state");
                String startedAt = jsonObject.optString("started_at");
                String finishedAt = jsonObject.optString("finished_at");
                JSONObject branchObject = jsonObject.optJSONObject("branch");
                String branch = branchObject.optString("name");
                JSONObject commitObject = jsonObject.optJSONObject("commit");
                String message = commitObject.optString("message");
                int duration = jsonObject.optInt("duration");
                travisBuildList
                        .add(new TravisBuild(number, state, startedAt, finishedAt, branch, message, duration));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return travisBuildList;
    }


    public String getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public String getBranch() {
        return branch;
    }

    public String getMessage() {
        return message;
    }

    public int getDuration() {
        return duration;
    }
}
