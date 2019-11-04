package in.ac.siesgst.arena.epicentre.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
@Entity(tableName = "heroku")
public class Heroku {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String buildpack, stack, createdAt, name, region, releasedAt, updatedAt, url;
    private String command, dynoCreatedAt, dynoName, size, state, type, dynoUpdatedAt;
    private Boolean maintenance;
    private int version;

    public Heroku(int id, String buildpack, String stack, String createdAt, String name, String region, String releasedAt, String updatedAt, String url, String command, String dynoCreatedAt, String dynoName, String size, String state, String type, String dynoUpdatedAt, Boolean maintenance, int version) {
        this.id = id;
        this.buildpack = buildpack;
        this.stack = stack;
        this.createdAt = createdAt;
        this.name = name;
        this.region = region;
        this.releasedAt = releasedAt;
        this.updatedAt = updatedAt;
        this.url = url;
        this.command = command;
        this.dynoCreatedAt = dynoCreatedAt;
        this.dynoName = dynoName;
        this.size = size;
        this.state = state;
        this.type = type;
        this.dynoUpdatedAt = dynoUpdatedAt;
        this.maintenance = maintenance;
        this.version = version;
    }

    public ArrayList<Pair<String, String>> getAppInfo() {
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

    public ArrayList<Pair<String, String>> getDynoInfo() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildpack() {
        return buildpack;
    }

    public void setBuildpack(String buildpack) {
        this.buildpack = buildpack;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getReleasedAt() {
        return releasedAt;
    }

    public void setReleasedAt(String releasedAt) {
        this.releasedAt = releasedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDynoCreatedAt() {
        return dynoCreatedAt;
    }

    public void setDynoCreatedAt(String dynoCreatedAt) {
        this.dynoCreatedAt = dynoCreatedAt;
    }

    public String getDynoName() {
        return dynoName;
    }

    public void setDynoName(String dynoName) {
        this.dynoName = dynoName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDynoUpdatedAt() {
        return dynoUpdatedAt;
    }

    public void setDynoUpdatedAt(String dynoUpdatedAt) {
        this.dynoUpdatedAt = dynoUpdatedAt;
    }

    public Boolean getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
