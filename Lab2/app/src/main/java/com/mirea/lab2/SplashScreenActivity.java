package com.mirea.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SplashScreenActivity extends AppCompatActivity {

    private String TAG = SplashScreenActivity.class.getSimpleName();
    private ArrayList<HashMap<String, String>> technologyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new ReaderJSON().execute();
    }

    private class ReaderJSON extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String url = "https://raw.githubusercontent.com/wesleywerner/" +
                    "ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/data/techs.ruleset.json";

            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);

                    for (int i = 1; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String image = c.getString("graphic");
                        String name = c.getString("name");
                        String helptext = "";

                        if (!c.isNull("helptext")) {
                            helptext = c.getString("helptext");
                        }

                        HashMap<String, String> contact = new HashMap<>();

                        contact.put("graphic", image);
                        contact.put("name", name);
                        contact.put("helptext", helptext);

                        technologyList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            intent.putExtra("ARRAY_LIST", technologyList);

            startActivity(intent);
            finish();
        }
    }
}
