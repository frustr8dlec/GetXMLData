package com.example.getxmldata;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG =
            NetworkUtils.class.getSimpleName();

    // Base URL for Vehicle API.
    // https://vpic.nhtsa.dot.gov/api/
    private static final String CAR_BASE_URL =  "https://vpic.nhtsa.dot.gov/api/vehicles/getmanufacturerdetails/989?";
    // Parameter to specify XML return data.
    private static final String FORMAT = "format";

    static String getBookInfo(String queryString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String carXMLString = null;

        try {
            Uri builtURI = Uri.parse(CAR_BASE_URL).buildUpon()
                    .appendQueryParameter(FORMAT, "XML")
                    .build();

            URL requestURL = new URL(builtURI.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }

            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            carXMLString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, carXMLString);
        return carXMLString;
    }
}
