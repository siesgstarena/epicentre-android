package in.ac.siesgst.arena.epicentre.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by SIESGSTarena on 02/12/18.
 */
@Entity(tableName = "auth")
public class Auth {

    // Stores the server secret code
    // TODO: Hash using some algorithm
    public String code;
    @PrimaryKey(autoGenerate = true)
    private int id;
    // Stores the application endpoint to request for data
    private String url;
    // Stores the JSON web tokens
    private String token;

    // Stores the ID remote token stored in Firebase Realtime Database
    // Note: Delete procedure is called to remove the token from Remote Database to stop receiving notifications
    private String remoteId;

    private String createdAt;

    // Used for updating after API responses and to check if to refresh token or not
    private String updatedAt;

    // Constructor
    public Auth(int id, String url, String code, String token, String remoteId, String createdAt, String updatedAt) {
        this.id = id;
        this.url = url;
        this.code = code;
        this.token = token;
        this.remoteId = remoteId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(String remoteId) {
        this.remoteId = remoteId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
