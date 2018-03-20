package com.example.miao.mobile_cw;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */

public class ItemDetails_Planned extends AppCompatActivity implements OnMapReadyCallback{


    TextView title,startTime,endTime,des,ges,GesPoint;;
    private GoogleMap myGoogleMap;
    private String gesPoint,titleOnMap;
    private double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_details__planned);

        title = (TextView)findViewById( R.id.textTitlePlanned );
        startTime = (TextView)findViewById( R.id.starTimePlanned );
        endTime = (TextView)findViewById( R.id.endTimePlanned );
        des = (TextView)findViewById( R.id.LocationPlanned );


        title.setText(getIntent().getStringExtra( "Title" ) );
        startTime.setText("Start Date : "+getIntent().getStringExtra( "StartTime" ) );
        endTime.setText("End Date : "+getIntent().getStringExtra( "EndTime" ) );
        des.setText(getIntent().getStringExtra( "Location" ) );
        gesPoint =getIntent().getStringExtra( "Geo" );
        titleOnMap = getIntent().getStringExtra( "Title" );



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map_panned);
        mapFragment.getMapAsync(this);
    }

    private void getLatlngValue(String GesPoint)
    {

        String[] gespoint = GesPoint.split(" ");
        lat = Double.parseDouble(gespoint[0]);
        lng = Double.parseDouble(gespoint[1]);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myGoogleMap = googleMap;
        getLatlngValue(gesPoint);
        goTolocationZoom(lat   , lng,16);
    }
    private void goTolocationZoom(double lat,double lng, float zoom)
    {

        LatLng ll = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
        myGoogleMap.addMarker(new MarkerOptions().position(ll).title(titleOnMap));
        myGoogleMap.moveCamera(update);
    }
}


