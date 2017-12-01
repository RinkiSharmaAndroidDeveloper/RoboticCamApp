package com.emtcontrols.cam.communication;

import android.net.Uri;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by i3 on 06 06 2016.
 */
public class HTTPConnection {

    public String CallHardwareApiSendData(String callUrl ) {
        String response="";
        try {
            URL url = new URL(callUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000); //set timeout to 2 seconds
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            response =  (connection.getResponseCode() == HttpURLConnection.HTTP_OK)?"1" : "0";
        } catch (MalformedURLException ex) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String CallHardwareApiGetData(String callUrl,String command  ) {
        StringBuilder responseOutput = new StringBuilder();
        try {
            Uri.Builder builder;

            builder = Uri.parse("http://" + callUrl).buildUpon();
            builder.appendPath("camera").appendPath("headController").appendPath("status");
            builder.appendPath("command");//status_rd
            URL url = new URL(builder.toString() + "=" + command);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(2000);
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setDoOutput(true);
            int responseCode = connection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";

            System.out.println("output===============" + br);
            while((line = br.readLine()) != null ) {
                responseOutput.append(line);
            }
        } catch (MalformedURLException ex) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseOutput.toString();

    }

}
