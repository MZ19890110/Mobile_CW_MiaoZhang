package com.example.miao.mobile_cw;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class ItemDetails_Current extends AppCompatActivity implements OnMapReadyCallback {


    TextView title,Des,pubDate,GesPoint;
    private GoogleMap myGoogleMap;
    private String gesPoint;
    private double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_item_details__current);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = (TextView)findViewById( R.id.txtTitle );
        Des = (TextView)findViewById( R.id.txtDes );
        pubDate = (TextView)findViewById( R.id.txtPubDate );
        title.setText(getIntent().getStringExtra( "Title" ) );
        pubDate.setText( getIntent().getStringExtra( "PubDate" ) );
        Des.setText(getIntent().getStringExtra( "Des" ));
        gesPoint = (getIntent().getStringExtra("GesPoint"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment2);
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
        myGoogleMap.addMarker(new MarkerOptions().position(ll).title("Marker in Sydney"));
        myGoogleMap.moveCamera(update);
    }
}
