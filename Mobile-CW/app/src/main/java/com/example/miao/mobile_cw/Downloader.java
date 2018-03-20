package com.example.miao.mobile_cw;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */

public class Downloader {

    String xmlData;

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public Downloader() {
        xmlData = "";
    }

    public void downloadfromUrl(String url) {
    URL aurl;
    URLConnection yc;
    BufferedReader in = null;
    String inputLine = "";


    Log.e( "MyTag", "Start Dowloading !" );

    try {
        Log.e( "MyTag", "Tring to dowload" );
        aurl = new URL( url );
        yc = aurl.openConnection();
        in = new BufferedReader( new InputStreamReader( yc.getInputStream() ) );
        //
        // Throw away the first 2 header lines before parsing
        //
        //
        //
        while ((inputLine = in.readLine()) != null) {
            xmlData = xmlData + inputLine;
            Log.e( "MyTag", inputLine );

        }
        Log.e( "MyTag", "Finish Dowloading !" );
        in.close();
    } catch (IOException ae) {
        Log.e( "MyTag", "ioexception" );
    }

}
}
