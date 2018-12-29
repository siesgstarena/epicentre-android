package in.ac.siesgst.arena.epicentre.api.responses;

import android.util.Pair;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SIESGSTarena on 05/12/18.
 */
public class HerokuInfo {

    private String buildpack, stack, createdAt, name, region, releasedAt, updatedAt, url;
    private Boolean maintenance;

    public HerokuInfo(JSONObject jsonObject) {
        buildpack = jsonObject.optString("buildpack_provided_description");
        createdAt = jsonObject.optString("created_at");
        releasedAt = jsonObject.optString("released_at");
        updatedAt = jsonObject.optString("updated_at");
        name = jsonObject.optString("name");
        url = jsonObject.optString("web_url");
        maintenance = jsonObject.optBoolean("maintenance");
        JSONObject stackObject = jsonObject.optJSONObject("stack");
        stack = stackObject.optString("name");
        JSONObject regionObject = jsonObject.optJSONObject("region");
        region = regionObject.optString("name");
    }

    public String getBuildpack() {
        return buildpack;
    }

    public String getStack() {
        return stack;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getReleasedAt() {
        return releasedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getMaintenance() {
        return maintenance;
    }

    public ArrayList<Pair<String, String>> getArrayList() {
        ArrayList<Pair<String, String>> items = new ArrayList<Pair<String, String>>();
        items.add(Pair.create("BUILD PACK", buildpack));
        items.add(Pair.create("CREATED AT", createdAt));
        items.add(Pair.create("MAINTENANCE", maintenance.toString()));
        items.add(Pair.create("NAME", name));
        items.add(Pair.create("REGION", region));
        items.add(Pair.create("RELEASED AT", releasedAt));
        items.add(Pair.create("STACK", stack));
        items.add(Pair.create("UPDATED AT", updatedAt));
        items.add(Pair.create("URL", url));
        return items;
    }
}
