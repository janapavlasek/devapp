package com.janapavlasek.devbotapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DevBotHome extends AppCompatActivity implements View.OnClickListener{

    TextView mainTextView, responseTextView;
    RequestQueue queue;
    String url ="http://192.168.0.110:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_bot_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Main activity buttons.
        Button forwardButton = (Button) findViewById(R.id.forward_button);
        forwardButton.setOnClickListener(this);
        Button leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnClickListener(this);
        Button rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnClickListener(this);
        Button stopButton = (Button) findViewById(R.id.stop_button);
        stopButton.setOnClickListener(this);

        // Text for testing.
        mainTextView = (TextView) findViewById(R.id.text);
        mainTextView.setText("Hello there.");
        responseTextView = (TextView) findViewById(R.id.response_text);

        // Request queue.
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v){
        // On any button click, react based on which button was clicked.
        switch (v.getId()){
            case R.id.forward_button:
                mainTextView.setText("Forward!");
                addRequest("forward");
                break;
            case R.id.left_button:
                mainTextView.setText("Left!");
                addRequest("left");
                break;
            case R.id.right_button:
                mainTextView.setText("Right!");
                addRequest("right");
                break;
            case R.id.stop_button:
                mainTextView.setText("Stop!");
                addRequest("stop");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dev_bot_home, menu);
        return true;
    }

    public void addRequest(String url_ext) {
        // Request a string response from the provided URL.
        System.out.println(url+url_ext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url + url_ext,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the first 500 characters of the response string.
                    responseTextView.setText("Response is: " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error.networkResponse);
                    System.out.println(error.getMessage());
                    responseTextView.setText("That didn't work! ");
                    error.printStackTrace();
                }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("velocity", "0");
                params.put("angle", "0");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
