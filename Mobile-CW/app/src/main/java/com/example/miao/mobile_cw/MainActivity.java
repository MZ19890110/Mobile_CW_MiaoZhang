package com.example.miao.mobile_cw;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miao.mobile_cw.R;

/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button current;
    private Button planned;
    private String url1 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String url2 = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String url3 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private TextView urlInput;
    private Button startButton;
    private String result = "";
ImageView img;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        current = (Button)findViewById(R.id.currentButton);
        current.setOnClickListener(this);
        planned = (Button)findViewById(R.id.plannedButton);
        planned.setOnClickListener(this);

        img = (ImageView)findViewById(R.id.imageView2);
    }
    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.currentButton)
        {


            Intent i = new Intent(MainActivity.this, CurrentIncidents.class);
            startActivity(i);



        }
        if(v.getId() == R.id.plannedButton)
        {
            Intent i = new Intent( MainActivity.this, PlannedRoadWork.class);
            startActivity(i);

        }

    }

}
