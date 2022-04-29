package edu.uncc.itcs4180.inclass11;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.uncc.itcs4180.inclass11.databinding.ActivityMapsBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * InClass11
 * MapsActivity.java
 * CJ D'Autorio, Mason Pipkin
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private final String TAG = "inclass11-MapsActivity";
    private final OkHttpClient client = new OkHttpClient();
    private ArrayList<Point> path = new ArrayList<>();
    private String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void getPoints() {
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/map/route")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "getPoints-response: " + response.toString());
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                ResponseBody responseBody = response.body();

                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("path");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject currentJson = jsonArray.getJSONObject(i);
                        double latitude = currentJson.getDouble("latitude");
                        double longitude = currentJson.getDouble("longitude");
                        Log.d(TAG, "getPoints-currentPoint lat: " + latitude + ", longitude: " + longitude);
                        Point currentPoint = new Point(latitude, longitude);
                        path.add(currentPoint);
                    }

                    title = jsonObject.getString("title");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            createPath();
                            LatLng startPoint = new LatLng(path.get(0).latitude, path.get(0).longitude);
                            mMap.addMarker(new MarkerOptions().position(startPoint).title(title));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(startPoint));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(getLatLngBounds(), 20));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private LatLng getNorthEast() {
        double northMost = Collections.max(path, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return Double.compare(point.longitude, point.longitude);
            }
        }).longitude;

        double eastMost = Collections.max(path, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return Double.compare(point.latitude, point.latitude);
            }
        }).latitude;

        return new LatLng(northMost, eastMost);
    }

    private LatLng getSouthWest() {
        double southMost = Collections.min(path, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return Double.compare(point.longitude, point.longitude);
            }
        }).longitude;

        double westMost = Collections.min(path, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                return Double.compare(point.latitude, point.latitude);
            }
        }).latitude;

        return new LatLng(southMost, westMost);
    }

    private LatLngBounds getLatLngBounds() {
        //return new LatLngBounds(getSouthWest(), getNorthEast());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < path.size(); i++) {
            builder.include(new LatLng(path.get(i).latitude, path.get(i).longitude));
        }

        return new LatLngBounds(builder.build().southwest, builder.build().northeast);
    }

    private void createPath() {
        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < path.size(); i++) {
            polylineOptions.add(new LatLng(path.get(i).latitude, path.get(i).longitude))
                    .width(5)
                    .color(Color.BLUE)
                    .geodesic(true);
        }

        mMap.addPolyline(polylineOptions);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getPoints();


//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}