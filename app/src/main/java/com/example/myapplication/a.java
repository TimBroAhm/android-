package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class a extends AsyncTask<String, Void, String> {
    Context context;
    AlertDialog alertDialog;

    a(Context ctx) {
        context = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Processing");
        alertDialog.setMessage("Please wait...");
        alertDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String register_url = "http://192.168.13.251/register.php"; // Replace with your actual registration URL

        try {
            URL url = new URL(register_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(15000);  // Timeout
            httpURLConnection.setReadTimeout(15000);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            // Fetching data from params
            String firstName = params[1];
            String fatherName = params[2];
            String lastName = params[3];
            String dob = params[4];
            String phone = params[5];
            String kebele = params[6];
            String qualification = params[7];
            String role = params[8];
            String sex = params[9];  // Added sex as the 10th parameter

            // Sending data through POST request
            String post_data = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&"
                    + URLEncoder.encode("fatherName", "UTF-8") + "=" + URLEncoder.encode(fatherName, "UTF-8") + "&"
                    + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&"
                    + URLEncoder.encode("dob", "UTF-8") + "=" + URLEncoder.encode(dob, "UTF-8") + "&"
                    + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8") + "&"
                    + URLEncoder.encode("kebele", "UTF-8") + "=" + URLEncoder.encode(kebele, "UTF-8") + "&"
                    + URLEncoder.encode("qualification", "UTF-8") + "=" + URLEncoder.encode(qualification, "UTF-8") + "&"
                    + URLEncoder.encode("role", "UTF-8") + "=" + URLEncoder.encode(role, "UTF-8") + "&"
                    + URLEncoder.encode("sex", "UTF-8") + "=" + URLEncoder.encode(sex, "UTF-8");

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            // Reading the response from the server
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Invalid URL: " + e.getMessage();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return "Network Error: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected Error: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        // Debugging: Log the response
        Log.d("Registration Result", result);

        // Check if the registration result contains the success message

            // If registration failed, show an error alert with the server's response message
            alertDialog.setTitle("Registration success");
            alertDialog.setMessage(result);  // Show the server's response as error message
            alertDialog.show();
        }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
