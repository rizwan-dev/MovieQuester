package com.systemplus.webservice.view;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.maps.android.PolyUtil;
import com.systemplus.webservice.R;
import com.systemplus.webservice.api.ApiClient;
import com.systemplus.webservice.api.ApiInterface;
import com.systemplus.webservice.model.polylines.PolyLineResponse;
import com.systemplus.webservice.util.AppConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapDemoActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final LatLng DRIVER_LOCATION = new LatLng(18.540556, 73.904048);

    private static final LatLng MID_LOCATION = new LatLng(18.544752, 73.905965);

    private static final LatLng CURBEE_LOCATION = new LatLng(18.543440, 73.905482);

    private static final int PATTERN_DASH_LENGTH_PX = 20;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    private static final List<PatternItem> PATTERN_POLYGON_ALPHA = Arrays.asList(GAP, DASH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {


                CircleOptions circleOptions = new CircleOptions();
                circleOptions.center(new LatLng(18.545052, 73.906048));
                circleOptions.radius(100);
                circleOptions.strokeColor(Color.BLUE);
                circleOptions.strokeWidth(5);
                circleOptions.fillColor(Color.RED);

                PolygonOptions polygonOptions = new PolygonOptions();
                polygonOptions.add(new LatLng(18.545975, 73.904735));
                polygonOptions.add(new LatLng(18.546987, 73.904891));
                polygonOptions.add(new LatLng(18.546130, 73.905301));
                polygonOptions.add(new LatLng(18.546514, 73.905293));
                polygonOptions.strokeColor(Color.BLUE);
                polygonOptions.strokeWidth(5);
                polygonOptions.add(new LatLng(18.545975, 73.904735));
                polygonOptions.fillColor(Color.RED);

                mMap.addPolygon(polygonOptions);


                mMap.addCircle(circleOptions);




                mMap.addMarker(new MarkerOptions().position(DRIVER_LOCATION).snippet(""));

                mMap.addMarker(new MarkerOptions().position(CURBEE_LOCATION));

                PolylineOptions polylineOptions = new PolylineOptions().width(10).color(Color.CYAN);
                List<LatLng> points = new ArrayList<>();
                points.add(CURBEE_LOCATION);
                points.add(MID_LOCATION);
                points.add(DRIVER_LOCATION);

                polylineOptions.addAll(points);

                Polyline polyline = mMap.addPolyline(polylineOptions);

                polyline.setEndCap(new RoundCap());
                polyline.setStartCap(new RoundCap());
                polyline.setPattern(PATTERN_POLYGON_ALPHA);

                callPolyLine();
            }
        });

    }

    private void callPolyLine() {
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String url = AppConstants.URL_GOOGLE_DIRECTION_API + "18.568407,73.907410&destination=18.560410,73.945016&mode=car&alternatives=true&key=" + AppConstants.GOOGLE_DIRECTION_API_KEY;
        Call<PolyLineResponse> polyLineResponseCall = apiService.getPolyLine(url);

        polyLineResponseCall.enqueue(new Callback<PolyLineResponse>() {
            @Override
            public void onResponse(Call<PolyLineResponse> call, Response<PolyLineResponse> response) {
                PolyLineResponse polyLineResponse = response.body();

                String polyLineString = polyLineResponse.getRoutes().get(0).getOverviewPolyline().getPoints();

                List<LatLng> latLngs = PolyUtil.decode(polyLineString);

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.addAll(latLngs);

                mMap.addPolyline(polylineOptions);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (LatLng latLng : latLngs) {
                    builder.include(latLng);
                }
                LatLngBounds bounds = builder.build();

                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);

                mMap.animateCamera(cu);
            }

            @Override
            public void onFailure(Call<PolyLineResponse> call, Throwable t) {

            }
        });

    }

}
