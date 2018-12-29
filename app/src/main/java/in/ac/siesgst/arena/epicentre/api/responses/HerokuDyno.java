package in.ac.siesgst.arena.epicentre.api.responses;

import android.util.Pair;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SIESGSTarena on 05/12/18.
 */
public class HerokuDyno {

    private String command, createdAt, name, size, state, type, updatedAt;
    private int version;

    public HerokuDyno(JSONObject jsonObject) {
        command = jsonObject.optString("command");
        createdAt = jsonObject.optString("created_at");
        updatedAt = jsonObject.optString("updated_at");
        state = jsonObject.optString("state");
        type = jsonObject.optString("type");
        name = jsonObject.optString("name");
        size = jsonObject.optString("size");
        JSONObject releaseObject = jsonObject.optJSONObject("release");
        version = releaseObject.optInt("version");
    }

    public String getCommand() {
        return command;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public int getVersion() {
        return version;
    }

    public ArrayList<Pair<String, String>> getArrayList() {
        ArrayList<Pair<String, String>> items = new ArrayList<Pair<String, String>>();
        items.add(Pair.create("COMMAND", command));
        items.add(Pair.create("CREATED AT", createdAt));
        items.add(Pair.create("NAME", name));
        items.add(Pair.create("SIZE", size));
        items.add(Pair.create("STATE", state));
        items.add(Pair.create("TYPE", type));
        items.add(Pair.create("VERSION", Integer.toString(version)));
        items.add(Pair.create("UPDATED AT", updatedAt));
        return items;
    }
}
