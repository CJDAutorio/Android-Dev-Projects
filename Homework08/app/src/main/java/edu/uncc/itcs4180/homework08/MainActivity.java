package edu.uncc.itcs4180.homework08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    final String TAG = "homework08-MainActivity";
    Place startPlace;
    Place endPlace;
    ArrayList<LatLng> route = new ArrayList<>();
    ArrayList<LatLng> gasStations = new ArrayList<>();
    ArrayList<LatLng> restaurants = new ArrayList<>();
    ArrayList<LatLng> snappedRoute = new ArrayList<>();
    LatLng northEast;
    LatLng southWest;
    GoogleMap googleMap;
    OkHttpClient client;
    Button gas_button;
    Button restaurants_button;
    Button submit_button;
    Button reset_button;
    AutocompleteSupportFragment startAutocompleteFragment;
    AutocompleteSupportFragment endAutocompleteFragment;
    SupportMapFragment map;
    PolylineOptions polylineOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize places
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.api_key), Locale.US);
        }


        // Initialize globals
        gas_button = findViewById(R.id.gas_button);
        restaurants_button = findViewById(R.id.restaurants_button);
        submit_button = findViewById(R.id.submit_button);
        reset_button = findViewById(R.id.reset_button);

        startAutocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.start_autocomplete_fragment);
        startAutocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));

        endAutocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.end_autocomplete_fragment);
        endAutocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.LAT_LNG, Place.Field.ID, Place.Field.NAME));

        map = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        map.getMapAsync(this);


        // On submit_button press
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startPlace == null || endPlace == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.fields_unfilled), Toast.LENGTH_SHORT).show();
                } else {
                    googleMap.clear();
                    buildPath();
                }
            }
        });

        // On reset_button press
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMap.clear();
                route.clear();
            }
        });
    }

    private void autocompleteFragmentListeners() {
        Log.d(TAG, "autocompleteFragmentListeners running");
        // startAutocompleteFragment listener
        startAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                startPlace = place;
                Log.d(TAG, "startAutocompleteFragment: startPlace: " +
                        startPlace.getName() + ", "
                        + startPlace.getId() + ", "
                        + startPlace.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e(TAG, "startAutocompleteFragment error: " + status.getStatusMessage());
            }
        });


        // endAutocompleteFragment listener
        endAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                endPlace = place;
                Log.d(TAG, "startAutocompleteFragment: endPlace: " +
                        endPlace.getName() + ", " +
                        endPlace.getId() + ", " +
                        endPlace.getLatLng());
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e(TAG, "endAutocompleteFragment error: " + status.getStatusMessage());
            }
        });
    }

    private void addMarker(Place place) {
        Log.d(TAG, "addMarker function running");
        polylineOptions = new PolylineOptions();
        googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
        Log.d(TAG, "addMarker: added marker " + place.getName() + " at " + place.getLatLng());
    }

    private void buildPath() {
        client = new OkHttpClient().newBuilder().build();
        HttpUrl url = HttpUrl.parse("https://maps.googleapis.com/maps/api/directions/json")
                .newBuilder()
                .addQueryParameter("origin", "place_id:" + startPlace.getId())
                .addQueryParameter("destination", "place_id:" + endPlace.getId())
                .addQueryParameter("key", getResources().getString(R.string.api_key))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "buildPath error: " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d(TAG, "buildPath response: " + response.toString());
                if (!response.isSuccessful()) {
                    Log.e(TAG, "buildPath response error code: " + response.code());
                }

                ResponseBody responseBody = response.body();

                try {
                    JSONObject jsonObject = new JSONObject(responseBody.string());

                    // Gets steps and bounds JSON objects
                    JSONObject bounds = jsonObject.getJSONArray("routes")
                            .getJSONObject(0)
                            .getJSONObject("bounds");
                    JSONArray steps = jsonObject.getJSONArray("routes")
                            .getJSONObject(0)
                            .getJSONArray("legs")
                            .getJSONObject(0)
                            .getJSONArray("steps");
                    Log.d(TAG, "bounds: " + bounds.toString());
                    Log.d(TAG, "steps: " + steps.toString());

                    northEast = new LatLng((Double) bounds.getJSONObject("northeast").get("lat"),
                            (Double) bounds.getJSONObject("northeast").get("lng"));
                    southWest = new LatLng((Double) bounds.getJSONObject("southwest").get("lat"),
                            (Double) bounds.getJSONObject("southwest").get("lng"));
                    Log.d(TAG, "northEast: " + northEast.toString());
                    Log.d(TAG, "southWest: " + southWest.toString());


                    route.add(new LatLng(startPlace.getLatLng().latitude, startPlace.getLatLng().longitude));
                    for (int i = 0; i < steps.length(); i++) {
                        LatLng latLng = new LatLng(steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat"),
                                steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));
                        route.add(latLng);
                        latLng = new LatLng(steps.getJSONObject(i).getJSONObject("end_location").getDouble("lat"),
                                steps.getJSONObject(i).getJSONObject("end_location").getDouble("lng"));
                        route.add(latLng);
                        Log.d(TAG, "onResponse: point added: " + latLng.toString());
                    }
                    route.add(new LatLng(endPlace.getLatLng().latitude, endPlace.getLatLng().longitude));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateCamera();
                            buildPolylines();
                        }
                    });
                } catch (JSONException e) {
                    Log.e(TAG, "buildPath: error: " + e.getLocalizedMessage());
                }
            }
        });
    }

//    private void snapToRoad() {
//        StringBuilder formattedPath = new StringBuilder();
//        for (int i = 0; i < route.size() - 1; i++) {
//            formattedPath.append(route.get(i).latitude + "," + route.get(i).longitude + "|");
//        }
//        formattedPath.append(route.get(route.size() - 1).latitude + "," + route.get(route.size() - 1).longitude);
//        Log.d(TAG, "snapToRoad: formattedPath: " + formattedPath.toString());
//
//        client = new OkHttpClient().newBuilder().build();
//
//        HttpUrl url = HttpUrl.parse("https://roads.googleapis.com/v1/snapToRoads")
//                .newBuilder()
//                .addQueryParameter("path", formattedPath.toString())
//                .addQueryParameter("key", getResources().getString(R.string.api_key))
//                .build();
//        Request request = new Request.Builder()
//                .url(url)
//                .method("GET", null)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e(TAG, "snapToRoad error: " + e.getLocalizedMessage());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                Log.d(TAG, "snapToRoad response: " + response.toString());
//                if (!response.isSuccessful()) {
//                    Log.e(TAG, "snapToRoad response error code: " + response.code());
//                }
//
//                ResponseBody responseBody = response.body();
//
//                try {
//                    JSONObject jsonObject = new JSONObject(responseBody.string());
//                } catch (JSONException e) {
//                    Log.e(TAG, "snapToRoad JSON error: " + e.getLocalizedMessage());
//                }
//            }
//        });
//    }

    private void updateCamera() {
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(startPlace.getLatLng()));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(southWest, northEast), 75));
        addMarker(startPlace);
        addMarker(endPlace);
    }

    private void buildPolylines() {
        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < route.size(); i++) {
            polylineOptions.add(new LatLng(route.get(i).latitude, route.get(i).longitude))
                    .width(5)
                    .color(Color.BLUE)
                    .geodesic(true);
        }

        googleMap.addPolyline(polylineOptions);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: running");
        this.googleMap = googleMap;
        autocompleteFragmentListeners();
    }
}