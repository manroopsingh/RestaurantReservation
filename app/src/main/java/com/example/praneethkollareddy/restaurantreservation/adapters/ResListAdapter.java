package com.example.praneethkollareddy.restaurantreservation.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praneethkollareddy.restaurantreservation.R;
import com.example.praneethkollareddy.restaurantreservation.ResData;
import com.example.praneethkollareddy.restaurantreservation.activities.Main_Activity;
import com.firebase.client.Query;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * Created by INSPIRON on 16-Feb-16.
 */
public class ResListAdapter extends ResListFirebase<ResData> {

    String res_key;
    Location myLocation= new Location(LocationManager.NETWORK_PROVIDER), resLocation= new Location(LocationManager.NETWORK_PROVIDER);

    public ResListAdapter(Query ref, Activity activity, int layout) {
        super(ref, ResData.class, layout, activity);
        // TODO Auto-generated constructor stub


    }

    @Override
    protected void populateView(View v, ResData resData) {

        String myLat = Main_Activity.Latitude;
        String myLong = Main_Activity.Longitude;
        myLocation = Main_Activity.myLoc;
        float distance;
        int time;




        Double resLat = Double.valueOf(resData.getLatitude());
        Double resLng = Double.valueOf((resData.getLongitude()));

        resLocation.setLatitude(resLat);
        resLocation.setLongitude(resLng);
        distance = myLocation.distanceTo(resLocation);
        distance=distance/1000;

        distance = (float) (distance/ 1.6);
        distance= (float) ((double)Math.round(distance * 10d) / 10d);

        time = (int) (distance*1.2);




        // Map a Chat object to an entry in our listview
        String waittime = resData.getWaittime();
        String dollarrange = resData.getDollar_range();
        String rating = resData.getRating();
        String cuisine = resData.getCuisine();
        String name = resData.getName();
        String image = resData.getImage();

        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //image.setImageBitmap(decodedByte);



        TextView res_dis = (TextView) v.findViewById(R.id.textview_distance);
        TextView res_name = (TextView) v.findViewById(R.id.text_res_name);
        TextView res_cuisine = (TextView) v.findViewById(R.id.text_res_cuisine);
        TextView res_dollarrange = (TextView) v.findViewById(R.id.text_dollar_range);
        Button btn_waittime = (Button) v.findViewById(R.id.btn_wait_time);
        RatingBar res_rating = (RatingBar)v.findViewById(R.id.res_rating);
        ImageView img_res = (ImageView) v.findViewById(R.id.img_res);

        res_name.setText(name);
        res_cuisine.setText(cuisine);
        res_dollarrange.setText(dollarrange);
        btn_waittime.setText(waittime);
        res_rating.setRating(Float.parseFloat(rating));
        res_dis.setText(String.valueOf(distance) + " mi, " + time + " mins");

        Main_Activity.googleMap.addMarker(new MarkerOptions().position(new LatLng(resLat, resLng)).title(name));


        int wt =  Integer.parseInt(waittime);
        if(wt<=15)btn_waittime.setBackgroundResource(R.drawable.wait_green);
        if(wt>15 && wt<=30)btn_waittime.setBackgroundResource(R.drawable.wait_yellow);
        if(wt>30 && wt<=45)btn_waittime.setBackgroundResource(R.drawable.wait_orange);
        if(wt>45)btn_waittime.setBackgroundResource(R.drawable.wait_red);

        img_res.setImageBitmap(decodedByte);


    }
    }



