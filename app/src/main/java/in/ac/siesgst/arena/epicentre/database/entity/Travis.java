package in.ac.siesgst.arena.epicentre.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by SIESGSTarena on 07/12/18.
 */
@Entity(tableName = "travis")
public class Travis {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private String number, state, startedAt, finishedAt, branch, message;
    private int duration;


    public Travis(String number, String state, String startedAt, String finishedAt, String branch, String message, int duration) {
        this.number = number;
        this.state = state;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.branch = branch;
        this.message = message;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
