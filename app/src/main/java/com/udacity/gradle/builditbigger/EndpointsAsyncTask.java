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
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

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

            // Option to use local app server or deployed instance
            // Edit the value "shouldUseLocalAppServer" in bools.xml to change configuration
            Boolean shouldUseLocalAppServer = context.getResources().getBoolean(R.bool.shouldUseLocalAppServer);
            if (shouldUseLocalAppServer) {

                // To use the development App Server with an Emulator
                builder.setRootUrl("http://10.0.2.2:8080/_ah/api/");
                builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });

            } else {

                // To use deployed App Server with any device or emulator
                String endpointRootUrl = context.getResources().getString(R.string.endpiont_root_url);
                builder.setRootUrl(endpointRootUrl);

            }

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
