package com.groomandbride.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.groomandbride.R;
import com.groomandbride.data.models.FavoritesModel;
import com.groomandbride.data.models.Hall;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapLocationActivity extends BaseActivity implements OnMapReadyCallback {

    @BindView(R.id.hallNameText)
    TextView hallNameText;
    @BindView(R.id.hallAddressText)
    TextView hallAddressText;
    @BindView(R.id.dirButton)
    Button dirButton;
    @BindView(R.id.back_button_bar)
    ImageView back_button_bar;

    private GoogleMap mMap;
    Hall.DataBean itemHall;
    FavoritesModel.DataBean itemHallFav;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);
        ButterKnife.bind(this);
        back_button_bar.setOnClickListener(view -> finish());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (getIntent().getStringExtra("flag") != null) {
            //coming from Fav
            itemHallFav = getIntent().getParcelableExtra("hall");
        } else {
            itemHall = getIntent().getParcelableExtra("hall");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (getIntent().getStringExtra("flag") != null) {
            showMarkerFromFav(itemHallFav);
        } else {
            showMarkerFromHome(itemHall);
        }

    }


    private void showMarkerFromFav(FavoritesModel.DataBean itemHallFav) {
        checkLatLngValidation(itemHallFav.getHallLocationLat(), itemHallFav.getHallLocationLong());
        LatLng mBranchLatLng = new LatLng(Double.parseDouble(itemHallFav.getHallLocationLat()), Double.parseDouble(itemHallFav.getHallLocationLong()));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_low);
        mMap.addMarker(new MarkerOptions().position(mBranchLatLng).title(itemHallFav.getHallName()).snippet(itemHallFav.getHallAdress())
                .icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mBranchLatLng, 16));
        setupMarkerTexts();

        hallNameText.setText(itemHallFav.getHallName());
        hallAddressText.setText(itemHallFav.getHallAdress());
    }

    private void showMarkerFromHome(Hall.DataBean itemHall) {
        checkLatLngValidation(itemHall.getHallLocationLat(), itemHall.getHallLocationLong());
        LatLng mBranchLatLng = new LatLng(Double.parseDouble(itemHall.getHallLocationLat()), Double.parseDouble(itemHall.getHallLocationLong()));
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker_low);
        mMap.addMarker(new MarkerOptions().position(mBranchLatLng).title(itemHall.getHallName()).snippet(itemHall.getHallAdress())
                .icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mBranchLatLng, 16));
        setupMarkerTexts();

        hallNameText.setText(itemHall.getHallName());
        hallAddressText.setText(itemHall.getHallAdress());
    }


    private void setupMarkerTexts() {
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(MapLocationActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(MapLocationActivity.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.START);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(MapLocationActivity.this);
                snippet.setTextColor(Color.BLACK);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }


    @OnClick(R.id.dirButton)
    void onDirBtnClicked(){
        if (getIntent().getStringExtra("flag") != null) {
            goToMapsDirctions(itemHallFav.getHallLocationLat(), itemHallFav.getHallLocationLong());
        } else {
            goToMapsDirctions(itemHall.getHallLocationLat(), itemHall.getHallLocationLong());
        }

    }

    @OnClick(R.id.mapsButton)
    void onMapsBtnClicked(){
        if (getIntent().getStringExtra("flag") != null) {
            goToMaps(itemHallFav.getHallLocationLat(), itemHallFav.getHallLocationLong());
        } else {
            goToMaps(itemHall.getHallLocationLat(), itemHall.getHallLocationLong());
        }

    }

    private void goToMapsDirctions(String hallLocationLat, String hallLocationLong) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + hallLocationLat + "," + hallLocationLong);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private void goToMaps(String hallLocationLat, String hallLocationLong){
        String uri = "http://maps.google.com/maps?q=loc:" + hallLocationLat + "," + hallLocationLong;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void checkLatLngValidation(String latStr, String lngStr) {
        double lat = Double.parseDouble(latStr);
        double lng = Double.parseDouble(lngStr);

        if (lat < -90 || lat > 90 || lng < -180 || lng > 180) {
            showNoteDialogFinishActivity("Error", "Invalid region");
        }
    }
}
