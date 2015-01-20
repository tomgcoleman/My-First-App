package com.mycompany.myfirstapp;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class DisplayMessageActivity extends ActionBarActivity {

    static final String STATE_COUNT = "count_to_save";
    int myCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( savedInstanceState != null){
            myCount = savedInstanceState.getInt(STATE_COUNT);
        } else {
            myCount = 0;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // setContentView(R.layout.activity_display_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message + " : " + String.valueOf(myCount).toString() );
        setContentView(textView);
    }

    @Override
    public void onPause(){
        EditText editText = (EditText) findViewById(R.id.edit_message);
        Intent intent = getIntent();
        // get the current text view, change the text.
        // TextView textView = intent.text
/*
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("on pause was called.");
        setContentView(textView);
*/
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        myCount++;
        savedInstanceState.putInt(STATE_COUNT, myCount);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        if (id == R.id.action_search) {
            // openSearch();
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // openSettings();
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment(){}
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // View rootView = inflater.inflate( R.layout.fragment_display_message,
            View rootView = inflater.inflate( R.layout.activity_display_message,
                    container, false);
            return rootView;
        }
    }
}

