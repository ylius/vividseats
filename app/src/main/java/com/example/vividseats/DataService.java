package com.example.vividseats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private LruCache<String, Bitmap> bitmapLruCache;
    private Context context;

    public DataService(Context context) {
        final int memorySize = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = memorySize / 8;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
        this.context = context;
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        Bitmap bitmap = null;
        if (bitmapLruCache != null) {
            bitmap = bitmapLruCache.get(imageUrl);
        }
        if (bitmap == null) {
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmapLruCache != null) {
                    bitmapLruCache.put(imageUrl, bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public List<Item> getItems(String startDate, String endDate) {
        String jsonResponse = null;
        VividSeatsApi vs = new VividSeatsApi();
        try {
            jsonResponse = vs.searchForEventsByDates(startDate, endDate);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parseResponse(jsonResponse);
    }

    private List<Item> parseResponse(String jsonResponse) {
        List<Item> items = new ArrayList<>();
        try {
            JSONArray events = new JSONArray(jsonResponse);
            for (int i = 0; i < events.length(); i++) {
                JSONObject event = events.getJSONObject(i);
                String imageUrl = event.optString("image");
                Bitmap image = imageUrl.isEmpty() ?
                        BitmapFactory.decodeResource(context.getResources(), R.drawable.blackhole)
                        : getBitmapFromUrl(imageUrl);
                String title = event.getString("topLabel");
                String subtitle = event.getString("middleLabel");
                String description = event.getString("bottomLabel");
                String count = event.getString("eventCount");
                Item item = new Item(image, title, subtitle, description, count);
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }
}
