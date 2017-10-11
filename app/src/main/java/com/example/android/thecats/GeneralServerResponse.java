package com.example.android.thecats;

/**
 * Created by fbrsw on 11.10.2017.
 */

public class GeneralServerResponse {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ClassPojo [response = " + response + "]";
    }

}
