package com.example.miao.mobile_cw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Miao Zhang on 12/03/2018.
 * StudentID: S1402087
 */

public class Item {

    private String title;
    private String description;
    private String link;
    private String georssPoint;
    private String author;
    private String comments;
    private String pubDateString;

    private Date publishDate;
    private Date startDate;
    private Date EndDate;
    private int dateRmain;
    private String TypeAndLocation;



    public String getTypeAndLocation() {
        String[] dateParts = description.split("<br />");
        TypeAndLocation = dateParts[2];
        return TypeAndLocation;
    }

    public Date getPublishDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        try {
            publishDate = sdf.parse(pubDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return publishDate;
    }

    public Date getStartDate() {

        String[] dateParts = description.split("<br />");
        String start = dateParts[0];
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMMM yyyy - HH:mm");
        try {
            startDate = sdf.parse(start.split(": ")[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startDate;
    }

    public Date getEndDate() {
          //Split Original Description (Planned Road Work ) to 3 parts

        String[] dateParts = description.split("<br />");
        String end = dateParts[1];
        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMMM yyyy - HH:mm");

        //Split first part of Description and transform to Date
        //Exaample first part = "Start Date: Tues 18 March 2017"
        try {
            EndDate = sdf.parse(end.split(": ")[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return EndDate;
    }
      //Split the StartDate String by (": ") to get the Date String and then parse to DateTime then calculate the days left (startDate - now)
    //Oringinal String = Start Date: Saturday, 17 March 2018 - 00:00 for example
    public int getDateRmain() throws ParseException {
        String[] dateParts = description.split("<br />");


        SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMMMM yyyy - HH:mm");
        String start = dateParts[0];
        Date st = sdf.parse(start.split(": ")[1]);
        String end = dateParts[1];
        Date ed = sdf.parse(end.split(": ")[1]);
        Date now = Calendar.getInstance().getTime();

        //long daysRemain1=(e.getTime()-s.getTime()+1000000)/(3600*24*1000);
        long diffInMillies = Math.abs(ed.getTime() - st.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        dateRmain = (int)diff;
        return dateRmain;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGeorssPoint() {
        return georssPoint;
    }

    public void setGeorssPoint(String georssPoint) {
        this.georssPoint = georssPoint;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPubDateString() {
        return pubDateString;
    }

    public void setPubDateString(String pubDateString) {
        this.pubDateString = pubDateString;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", georss_point='" + georssPoint + '\'' +
                ", pubDateString='" + pubDateString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item Item = (Item) o;

        if (!getTitle().equals(Item.getTitle())) return false;
        return getPubDateString().equals(Item.getPubDateString());

    }

    @Override
    public int hashCode() {
        int result = getTitle().hashCode();
        result = 31 * result + getPubDateString().hashCode();
        return result;
    }




}
