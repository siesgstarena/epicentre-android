package in.ac.siesgst.arena.epicentre.api.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SIESGSTarena on 05/12/18.
 * This is used for both successful and error response
 * Note: Success Response will have additional data JSONObject
 */
public class Base {

    @SerializedName("status")
    String status;

    @SerializedName("code")
    int code;

    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
