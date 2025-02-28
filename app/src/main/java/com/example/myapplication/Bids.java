package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Bids extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tableLayout = findViewById(R.id.tableLayout);

        // Fetch bids data from the server
        new FetchBidsTask().execute();
    }

    // AsyncTask to fetch data from PHP API
    private class FetchBidsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                // PHP endpoint that returns the bids in JSON format
                URL url = new URL("http://10.1.36.186/fetch_bids.php"); // Update with your actual server URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                // Parse the JSON response
                JSONArray jsonArray = new JSONArray(result);

                // Create a header row dynamically
                TableRow headerRow = new TableRow(Bids.this);
                String[] headers = {"Bid ID", "Subject", "Content", "Start Date", "End Date", "Status"};
                for (String header : headers) {
                    TextView textView = new TextView(Bids.this);
                    textView.setText(header);
                    textView.setPadding(8, 8, 8, 8);
                    headerRow.addView(textView);
                }
                tableLayout.addView(headerRow);  // Add the header row to TableLayout

                // Loop through the JSON data and add rows to the table
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject bidObject = jsonArray.getJSONObject(i);

                    // Get the data from each column
                    String bidID = bidObject.getString("bid_id");
                    String subject = bidObject.getString("subject");
                    String content = bidObject.getString("content");
                    String startDate = bidObject.getString("start_date");
                    String endDate = bidObject.getString("end_date");
                    String status = bidObject.getString("status");

                    // Create a new row for each bid and add TextViews with the fetched data
                    TableRow tableRow = new TableRow(Bids.this);

                    addColumnToRow(tableRow, bidID);
                    addColumnToRow(tableRow, subject);
                    addColumnToRow(tableRow, content);
                    addColumnToRow(tableRow, startDate);
                    addColumnToRow(tableRow, endDate);
                    addColumnToRow(tableRow, status);

                    tableLayout.addView(tableRow);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Bids.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Helper method to add a TextView to a TableRow
    private void addColumnToRow(TableRow row, String data) {
        TextView textView = new TextView(Bids.this);
        textView.setText(data);
        textView.setPadding(8, 8, 8, 8);
        row.addView(textView);
    }
}
