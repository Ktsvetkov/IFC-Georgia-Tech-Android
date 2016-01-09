package com.kamtechs.ifcgeorgiatech;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kamenator on 10/27/15.
 */
public class JsonHelper {

    private final static String USER_AGENT = "Mozilla/5.0";
    static String facebookUrl = "https://graph.facebook.com/v2.3/295206497199797/feed?access_token=488056298015418|ev5kTyKcCZU5sQD0s3za_nB9HhU";


    public static ArrayList<NewsPost> getNewsPosts() {
        ArrayList<NewsPost> toReturn = new ArrayList<NewsPost>();

        String jsonToParse = getJsonString(facebookUrl);
        Log.i("JsonOutput", "" + "example");
        Log.i("JsonOutput", jsonToParse);

        try {
            JSONObject jsonObject = new JSONObject(jsonToParse);
            Log.i("Json: ", jsonObject.toString());

            JSONArray rootArray = jsonObject.getJSONArray("data");
            int len = rootArray.length();


            for(int i = 0; i < len; i++) {
                JSONObject obj = rootArray.getJSONObject(i);

                String message = "";

                //Get message
                if (obj.has("message")) {
                    message = obj.getString("message").replaceAll("\\n", " ");
                } else if (obj.has("story")){
                    message = obj.getString("story").replaceAll("\\n", " ");
                }

                //Get date
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                cal.setTime(sdf.parse(obj.getString("created_time")));
                Log.i("Date:", obj.getString("created_time"));

                String picURL = "";
                boolean hasPicture = false;

                if (obj.has("object_id")) {
                    picURL = obj.getString("object_id");
                    picURL = "https://graph.facebook.com/" + picURL + "/picture?height=620&width=620";
                    Log.i("Has URL", "HAS URL");
                    hasPicture = true;
                }

                NewsPost toAdd = new NewsPost(cal, message, picURL, hasPicture);
                toReturn.add(toAdd);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Log.i("Real Date:", format1.format(cal.getTime()));
                Log.i("Posts: ", "the message is: " + toAdd.message);

            }
        } catch (Exception e) {
            Log.i("Exception: ", e.toString());
        }


        return toReturn;
    }




    // HTTP GET
    // Taken from - http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
    public static String getJsonString(String url){
        StringBuffer response = new StringBuffer();

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {
            //do nothing
        }

        //print result
        return response.toString();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
