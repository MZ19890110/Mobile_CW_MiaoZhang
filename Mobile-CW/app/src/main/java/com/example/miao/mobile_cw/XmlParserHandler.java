package com.example.miao.mobile_cw;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

/**
 * Created by Miao Zhang on 12/03/2018.
         * StudentID: S1402087
         */

public class XmlParserHandler {

    LinkedList<Item> itemList;
    Item currentItem;

    public LinkedList<Item> getItemList() {
        return itemList;
    }

    public void parseData(String dataToParse)
    {
        itemList = new LinkedList<Item>();
        currentItem = new Item();
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( dataToParse ) );
            int eventType = xpp.getEventType();

            //used for Jumping the first few tags that not wraped in item tage for example the first title and description
            boolean itemFound = false;
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(eventType == XmlPullParser.START_TAG)
                {

                     if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        itemFound = true;
                        Log.e("MyTag","Item Start Tag found");
                        currentItem = new Item();
                    }
                    else
                    if (itemFound&&xpp.getName().equalsIgnoreCase("title"))
                    {
                        // Now just get the associated text
                        String temp = xpp.nextText();
                        // Do something with text
                        Log.e("MyTag","title is " + temp);
                        currentItem.setTitle(temp);
                    }
                    else
                        // Check which Tag we have
                        if (itemFound&&xpp.getName().equalsIgnoreCase("description"))
                        {
                            // Now just get the associated text
                            String temp = xpp.nextText();
                            // Do something with text
                            Log.e("MyTag","description is " + temp);
                            currentItem.setDescription(temp);
                        }
                        else
                            // Check which Tag we have
                            if (itemFound&&xpp.getName().contains("point"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                Log.e("MyTag","georss:point is " + temp);
                                currentItem.setGeorssPoint(temp);
                            }
                        else
                            // Check which Tag we have
                            if (itemFound&&xpp.getName().equalsIgnoreCase("pubDate"))
                            {
                                // Now just get the associated text
                                String temp = xpp.nextText();
                                // Do something with text
                                Log.e("MyTag","pubDate is " + temp);
                                currentItem.setPubDateString(temp);
                            }
                }
                else
                if(eventType == XmlPullParser.END_TAG)
                {
                    if (xpp.getName().equalsIgnoreCase("item"))
                    {
                        Log.e("MyTag","widget is " + currentItem.toString());
                        itemList.add(currentItem);
                        itemFound = false;
                    }

                }


                // Get the next event
                eventType = xpp.next();

            } // End of while

        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }

        Log.e("MyTag","End document");


    }
}
