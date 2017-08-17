package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    // The Idling Resource which will be null in production.
    @Nullable
    private MyIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is run when the button is pressed.
     *
     * It will send of an AsyncTask that:
     *      1. Gets a joke from endpoint server
     *      2. Starts a new activity to show the joke
     */
    public void tellJoke(View view) {
        EndpointsTaskParams params = new EndpointsTaskParams(this, mIdlingResource);
        // send off new AsyncTask to get joke from GCE server
        new EndpointsAsyncTask().execute(params);
    }

    /**
     * Custom object to hold params to send to EndpointsAsyncTask
     */
    public class EndpointsTaskParams {
        private Context mContext;
        private MyIdlingResource mIdlingResource;

        // constructor for param object
        public EndpointsTaskParams(Context context, @Nullable final MyIdlingResource idlingResource) {
            mContext = context;
            mIdlingResource = idlingResource;
        }

        // getters for each param
        public Context getContext() {
            return mContext;
        }
        public MyIdlingResource getIdlingResource() {
            return mIdlingResource;
        }
    }

    /**
     * Only called from test, creates and returns a new MyIdlingResource
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new MyIdlingResource();
        }
        return mIdlingResource;
    }

}
