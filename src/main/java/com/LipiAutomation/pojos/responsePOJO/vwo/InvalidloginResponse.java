package com.LipiAutomation.pojos.responsePOJO.vwo;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")

public class InvalidloginResponse {
    @SerializedName("message")
    private String mMessage;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
