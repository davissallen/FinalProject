package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import com.example.davis.myapplication.backend.myApi.MyApi;

import java.io.IOException;

import me.davisallen.showtextactivity.ShowTextActivity;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {

            MyApi.Builder builder = new MyApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            );
            builder.setRootUrl("https://1-dot-joke-endpoint.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent showTextActivityIntent = new Intent(context, ShowTextActivity.class);
        showTextActivityIntent.putExtra(Intent.EXTRA_TEXT, result);
        context.startActivity(showTextActivityIntent);
    }
}
