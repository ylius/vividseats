package com.example.vividseats;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VividSeatsApi {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String URL = "https://webservices.vividseats.com/rest/mobile/v1/home/cards";
    private final OkHttpClient client = new OkHttpClient();

    public String searchForEventsByDates(String startDate, String endDate) throws IOException {
        String jsonRequest = "{\n" +
                "  \"startDate\": \"" + startDate + "\",\n" +
                "  \"endDate\": \"" + endDate + "\",\n" +
                "  \"includeSuggested\": \"true\"\n" +
                "}";
        RequestBody requestBody = RequestBody.create(JSON, jsonRequest);
        Request request = new Request.Builder().url(URL).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();
        response.close();
        Log.e("VividSeatsApi", jsonResponse);
        return jsonResponse;
    }
}
