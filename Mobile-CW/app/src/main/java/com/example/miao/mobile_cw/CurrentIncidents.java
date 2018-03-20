package com.example.miao.mobile_cw;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */

public class CurrentIncidents extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private String url1 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    private String url2="http://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    Downloader myDownloader;
    XmlParserHandler myParser;
    LinkedList<Item> itemList;
    RecyclerView recyclerView;
    RecylerViewAdapter_Current adapter;
    LinkedList<Item> arrayOfTitle;
    private ProgressDialog progress;
    Calendar myCalendar;
    int day,month,year;
    String syear,smonth,sday;
    private Date pickedDate;
    LinkedList<Item> arrayOfPickedDate;
    static final int DIALOG_ID = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Log.i( "StackSites", "OnCreate()" );
        setContentView( R.layout.activity_current_incidents );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress =ProgressDialog.show(this,"Current Works","On Loading");
        DownloadAndProcessData dAP = new DownloadAndProcessData();
        dAP.execute(  );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);
        MenuItem menuItemSearch = menu.findItem(R.id.search_for_road);
        MenuItem menuItemCalendar = menu.findItem(R.id.calendarView);
        MenuItem menuItemRefresh = menu.findItem(R.id.refresh);
        SearchView searchView = (SearchView) menuItemSearch.getActionView();
        searchView.setOnQueryTextListener(this);
        menuItemCalendar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                   showDialog(DIALOG_ID);
                return false;
            }
        });
        menuItemRefresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DownloadAndProcessData dAP = new DownloadAndProcessData();
                dAP.execute(  );
                return false;
            }
        });
        return super.onCreateOptionsMenu( menu );
    }

    /**
     * @param id
     * @deprecated
     */
    @Override
    protected Dialog onCreateDialog(int id) {



        return new DatePickerDialog(this,dpickerListner,year,month,day);
       // return super.onCreateDialog(id);
    }

    private DatePickerDialog.OnDateSetListener dpickerListner = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year = i;
            month = i1;
            day = i2;

            syear = String.valueOf(i);
            smonth = String.valueOf(i1+1);
            sday = String.valueOf(i2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                pickedDate = sdf.parse(sday+"/"+smonth+"/"+syear);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            arrayOfPickedDate = new LinkedList<Item>();
            for(Item item:itemList)
            {
                  String terst =  sdf.format(item.getPublishDate());

                Date publish = null;
                try {
                    publish = sdf.parse(terst);
                    if(publish.equals(pickedDate)) {
                        arrayOfPickedDate.add(item);
                        Toast.makeText(CurrentIncidents.this,terst, Toast.LENGTH_LONG).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

             }
              adapter.setFilter(arrayOfPickedDate);
          //  Toast.makeText(CurrentIncidents.this,sdf.format(pickedDate),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {



        newText = newText.toLowerCase();
        arrayOfTitle = new LinkedList<>();
        for(Item item : itemList)
        {
            String title = "";
            title = item.getTitle().toLowerCase();
            if(title.contains(newText)) {
                arrayOfTitle.add(item);

            }

        }
        adapter.setFilter(arrayOfTitle);


        return true;
    }

    private class DownloadAndProcessData extends AsyncTask<String, Integer,LinkedList<Item> >{

        @Override
        protected LinkedList<Item> doInBackground(String... params) {
            String xmlData;
            myDownloader = new Downloader();
            myDownloader.downloadfromUrl( url1 );
            xmlData = myDownloader.getXmlData();
            myParser = new XmlParserHandler();
            myParser.parseData( xmlData );
            return myParser.getItemList();
        }


        @Override
        protected void onPostExecute(LinkedList<Item> list) {
            super.onPostExecute( list );
            itemList = list;
            progress.dismiss();
            processView();

        }
    }


    private void processView() {
        myCalendar= Calendar.getInstance();
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        month = myCalendar.get(Calendar.MONTH);
        year = myCalendar.get(Calendar.YEAR);
        recyclerView = (RecyclerView) findViewById( R.id.reView );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
        adapter = new RecylerViewAdapter_Current( this,itemList );
        recyclerView.setAdapter( adapter );


    }


}