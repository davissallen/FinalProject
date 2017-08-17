package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import com.example.davis.myapplication.backend.myApi.MyApi;

import java.io.IOException;

import me.davisallen.showtextactivity.ShowTextActivity;

class EndpointsAsyncTask extends AsyncTask<MainActivity.EndpointsTaskParams, Void, String> {
    private static MyApi myApiService = null;

    private Context context;
    private MyIdlingResource mIdlingResource;

    @Override
    protected String doInBackground(MainActivity.EndpointsTaskParams... params) {
        // Get params from custom param object
        context = params[0].getContext();
        mIdlingResource = params[0].getIdlingResource();

        if(myApiService == null) {

            MyApi.Builder builder = new MyApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            );

            String endpointRoorUrl = context.getResources().getString(R.string.endpiont_root_url);
            builder.setRootUrl(endpointRoorUrl);

            myApiService = builder.build();
        }

        // The IdlingResource is null in production.
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }

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

        // let the IdlingResource know it's done
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }
}
