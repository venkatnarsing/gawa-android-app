package org.gawa.owner;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DisplayOwnersActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_owners);
        setContentView(R.layout.activity_show_owners);

        //textView = findViewById(R.id.textView);

        new ExecuteTask().execute("http://192.168.1.9:8081/gawa/owners");


    }

    class ExecuteTask extends AsyncTask<String, Void, String>
    {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            /*super.onPreExecute();
            progressDialog = ProgressDialog.show(getApplicationContext(),
                    "ProgressDialog",
                    "Wait for a moment...");*/
            //textView.setText("Wait for a moment...");
        }

        @Override
        protected String doInBackground(String... params) {

            String res = getOwners(params);

            //String res1 = "[{\"id\":\"1\",\"name\":\"Kiran Ch\",\"houseNumber\":\"9-3-1\",\"phone\":\"1234567890\"},{\"id\":\"2\",\"name\":\"Jaggaiah\",\"houseNumber\":\"9-3-2\",\"phone\":\"1234567890\"},{\"id\":\"3\",\"name\":\"Uday\",\"houseNumber\":\"9-3-3\",\"phone\":\"1234567890\"}]";

            //return "Showing Owners list";
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            //progressDialog.dismiss();
            //progess_msz.setVisibility(View.GONE);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

            try {
                loadIntoTableView(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //textView.setText("Owners List... \n" + result);
        }

    }

    public String getOwners(String[] params){
        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        builder.url(params[0]);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void loadIntoTableView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] heroes = new String[jsonArray.length()];
        int count = 0;
        TableLayout tl = (TableLayout) findViewById(R.id.tableOwners);

        int ownerNameWidth = findViewById(R.id.ownerName).getWidth();
        int phoneNumberWidth = findViewById(R.id.phoneNumber).getWidth();
        int houseNumberWidth = findViewById(R.id.houseNumber).getWidth();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            // Create the table row
            TableRow tr = new TableRow(this);
            if(i%2!=0) {
                tr.setBackgroundColor(Color.LTGRAY);
            }else{
                tr.setBackgroundColor(Color.GRAY);
            }
            tr.setId(100+i);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            tr.setPadding(5,5,5,5);

            //Create two columns to add as table data
            // Create a TextView to add Owner Name
            TextView labelOwnerName = new TextView(this);
            labelOwnerName.setId(200+i);
            labelOwnerName.setText(obj.getString("name"));
            labelOwnerName.setPadding(5, 0, 0, 0);
            labelOwnerName.setWidth(ownerNameWidth);
            //labelNAME.setTextColor(Color.WHITE);
            tr.addView(labelOwnerName);

            // Create a TextView to add Phone Number
            TextView labelPhoneNumber = new TextView(this);
            labelPhoneNumber.setId(300+i);
            labelPhoneNumber.setText(obj.getString("phone"));
            labelPhoneNumber.setWidth(phoneNumberWidth);
            labelPhoneNumber.setPadding(5, 0, 0, 0);
            //labelPhoneNumber.setTextColor(Color.WHITE);
            tr.addView(labelPhoneNumber);

            // Create a TextView to add Plot Number
            TextView labelHouseNumber = new TextView(this);
            labelHouseNumber.setId(400+i);
            labelHouseNumber.setText(obj.getString("houseNumber"));
            labelHouseNumber.setWidth(houseNumberWidth);
            labelHouseNumber.setPadding(5, 0, 0, 0);
            //labelHouseNumber.setTextColor(Color.WHITE);
            tr.addView(labelHouseNumber);

            // finally add this to the table row
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //listView.setAdapter(arrayAdapter);
    }

}
