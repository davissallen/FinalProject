package me.davisallen.showtextactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        TextView showJokeTextView = (TextView) findViewById(R.id.tv_show_joke);

        Intent receivedIntent = getIntent();
        String joke = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);

        if (joke != null) {
            showJokeTextView.setText(joke);
        } else {
            showJokeTextView.setText(getString(R.string.no_joke_found));
        }

    }
}
