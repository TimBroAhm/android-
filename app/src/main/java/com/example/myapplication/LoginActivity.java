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

public class LoginActivity extends AppCompatActivity {

    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tableLayout = findViewById(R.id.tableLayout);

        // Fetch data from the server
        new FetchItemsTask().execute();
    }

    // AsyncTask to fetch data from PHP API
    private class FetchItemsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                // PHP endpoint that returns the items in JSON format
                URL url = new URL("http://10.1.36.186/fetch_items.php");  // Update with your actual server URL
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
                TableRow headerRow = new TableRow(LoginActivity.this);
                String[] headers = {"ID", "Serial Number", "Model", "Category", "Description", "Shelf", "Request ID", "Supplier ID", "Stock Clerk ID", "Price", "Date"};
                for (String header : headers) {
                    TextView textView = new TextView(LoginActivity.this);
                    textView.setText(header);
                    textView.setPadding(8, 8, 8, 8);
                    headerRow.addView(textView);
                }
                tableLayout.addView(headerRow);  // Add the header row to TableLayout

                // Loop through the JSON data and add rows to the table
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject itemObject = jsonArray.getJSONObject(i);

                    // Get the data from each column
                    String itemRegisterID = itemObject.getString("item_Register_ID");
                    String serialNumber = itemObject.getString("serial_numbre");
                    String itemModel = itemObject.getString("item_model");
                    String category = itemObject.getString("catagory");
                    String description = itemObject.getString("description");
                    String shelfNumber = itemObject.getString("shelf_number");
                    String requestID = itemObject.getString("request_id");
                    String supplierID = itemObject.getString("supplier_id");
                    String stockClerkID = itemObject.getString("stockclerk_id");
                    String price = itemObject.getString("price");
                    String date = itemObject.getString("date");

                    // Create a new row for each item and add TextViews with the fetched data
                    TableRow tableRow = new TableRow(LoginActivity.this);

                    addColumnToRow(tableRow, itemRegisterID);
                    addColumnToRow(tableRow, serialNumber);
                    addColumnToRow(tableRow, itemModel);
                    addColumnToRow(tableRow, category);
                    addColumnToRow(tableRow, description);
                    addColumnToRow(tableRow, shelfNumber);
                    addColumnToRow(tableRow, requestID);
                    addColumnToRow(tableRow, supplierID);
                    addColumnToRow(tableRow, stockClerkID);
                    addColumnToRow(tableRow, price);
                    addColumnToRow(tableRow, date);

                    tableLayout.addView(tableRow);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Helper method to add a TextView to a TableRow
    private void addColumnToRow(TableRow row, String data) {
        TextView textView = new TextView(LoginActivity.this);
        textView.setText(data);
        textView.setPadding(8, 8, 8, 8);
        row.addView(textView);
    }
}
