package com.mycompany.myfirstapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstApp.message";
    public static final String LOG_TAG = "SoundScavenger";
    private int mSoundCount = 8;
    private static boolean g_mIsRecording = false;
    public int getSoundCount(){ return mSoundCount; }
    public void setSoundCount(int count){ mSoundCount = count; }

    static final String STORAGE_STATES_COUNT = "count_to_save";
    static final String STORAGE_STATES = "saved_states";
    private int[] mStorageStates = new int[mSoundCount];

    public static String mFileDirectory = null;

    private boolean m__________use____layout_____buttons = true;


    private SoundTrack[] mTracks = new SoundTrack[mSoundCount];

    public MainActivity() {
        mFileDirectory = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    private String getFilename(int index) {
        return mFileDirectory + "/androidRecordTest" + String.valueOf(index).toString() + ".3gp";
    }

    class SoundTrack {
        MediaRecorder mRecorder;
        MediaPlayer mPlayer;
        String filename;
        final int mIndex;
        boolean mIsRecording = false;
        boolean mIsPlaying = false;

        public SoundTrack(int index) {
            mIndex = index;
        }

        String getFilename() {
            return mFileDirectory + "/androidRecordTest" + String.valueOf(mIndex).toString() + ".3gp";
        }

        boolean isRecording() {
            return mIsRecording;
        }

        boolean isPlaying() {
            return mIsPlaying;
        }

        MediaPlayer.OnCompletionListener mPlayCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.e(LOG_TAG, "on completion listener called." + mIndex);
                updateButton(mIndex, false, false);
                mIsPlaying = false;
            }
        };

        void timeToDie(){
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            mIsPlaying = false;
            if (null != mRecorder) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
            mIsRecording = false;
        }

        boolean stopPlaying() {
            if (mPlayer != null) {
                mPlayer.release();
                mPlayer = null;
            }
            mIsPlaying = false;
            return true;
        }
        boolean startPlaying() {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setOnCompletionListener(mPlayCompletionListener);
                mPlayer.setDataSource(getFilename());
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed.");
                return false;
            }
            mIsPlaying = true;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    stopPlaying();
                    updateButton(mIndex, false, false);
                }
            }, 3000);
            return true;
        }

        boolean stopRecording() {
            if (null != mRecorder) {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
            }
            mIsRecording = false;
            Log.e(LOG_TAG, "*** save before setting was: " + mStorageStates[mIndex]);
            mStorageStates[mIndex] = 1;
            Log.e(LOG_TAG, "*** save after  setting was: " + mStorageStates[mIndex]);
            return true;
        }

        boolean startRecording() {
            if (mIsRecording) return false;
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(getFilename());
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed.");
                return false;
            }

            try {
                mRecorder.start();
                mIsRecording = true;
            } catch (RuntimeException e) {
                Log.e(LOG_TAG, "start() failed with exception.");
                return false;
            }
            mIsRecording = true;
            return true;
        }
    }

    boolean getIsRecordButtonFromId(int id) {
        boolean isRecord = false;
        switch (id) {
            case R.id.record_button_0:
            case R.id.record_button_1:
            case R.id.record_button_2:
            case R.id.record_button_3:
            case R.id.record_button_4:
            case R.id.record_button_5:
            case R.id.record_button_6:
            case R.id.record_button_7:
                isRecord = true;
                break;
        }
        return isRecord;
    }

    int getIndexFromButton(int id) {
        int index = -1;
        switch (id) {
            case R.id.record_button_0:
            case R.id.play_button_0:
                index = 0;
                break;
            case R.id.record_button_1:
            case R.id.play_button_1:
                index = 1;
                break;
            case R.id.record_button_2:
            case R.id.play_button_2:
                index = 2;
                break;
            case R.id.record_button_3:
            case R.id.play_button_3:
                index = 3;
                break;
            case R.id.record_button_4:
            case R.id.play_button_4:
                index = 4;
                break;
            case R.id.record_button_5:
            case R.id.play_button_5:
                index = 5;
                break;
            case R.id.record_button_6:
            case R.id.play_button_6:
                index = 6;
                break;
            case R.id.record_button_7:
            case R.id.play_button_7:
                index = 7;
                break;
        }
        return index;
    }

    int getButtonId(boolean isRecord, int index) {
        int id = 0;
        if (!isRecord) {
            switch (index) {
                case 0:
                    id = R.id.play_button_0;
                    break;
                case 1:
                    id = R.id.play_button_1;
                    break;
                case 2:
                    id = R.id.play_button_2;
                    break;
                case 3:
                    id = R.id.play_button_3;
                    break;
                case 4:
                    id = R.id.play_button_4;
                    break;
                case 5:
                    id = R.id.play_button_5;
                    break;
                case 6:
                    id = R.id.play_button_6;
                    break;
                case 7:
                    id = R.id.play_button_7;
                    break;
            }
        } else {
            switch (index) {
                case 0:
                    id = R.id.record_button_0;
                    break;
                case 1:
                    id = R.id.record_button_1;
                    break;
                case 2:
                    id = R.id.record_button_2;
                    break;
                case 3:
                    id = R.id.record_button_3;
                    break;
                case 4:
                    id = R.id.record_button_4;
                    break;
                case 5:
                    id = R.id.record_button_5;
                    break;
                case 6:
                    id = R.id.record_button_6;
                    break;
                case 7:
                    id = R.id.record_button_7;
                    break;
            }
        }
        return id;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt(STORAGE_STATES_COUNT, mSoundCount);
        savedInstanceState.putIntArray(STORAGE_STATES, mStorageStates);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(LOG_TAG, "***************** on create enter ");

        for (int i = 0 ; i < getSoundCount() ; i++) {
            mStorageStates[i] = 0;
        }
        int savedCount = 0;
        if( savedInstanceState != null){
            savedCount = savedInstanceState.getInt(STORAGE_STATES_COUNT);
            if (savedCount > getSoundCount()) {
                setSoundCount( savedCount );
            }
            for (int i = 0 ; i < savedCount ; i++) {
                mStorageStates = savedInstanceState.getIntArray(STORAGE_STATES);
            }
        } else {
            // bad, do not save here.
            // savedInstanceState.putInt(STORAGE_STATES_COUNT, getSoundCount());
        }

        Log.e(LOG_TAG, "***************** on create e ");

        if (m__________use____layout_____buttons) {
            setContentView(R.layout.activity_main);
            for (int i = 0; i < getSoundCount(); i++) {
                int idRec = getButtonId(true, i);
                Button bRec = (Button)findViewById(idRec);
                int idPlay = getButtonId(false, i);
                Button bPlay = (Button)findViewById(idPlay);
                if (bRec != null && bRec.getBackground() != null) {
                    if (mStorageStates[i] == 0) {
                        bRec.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                    } else {
                        bRec.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
                    }
                    bRec.setText("+rec " + i);
                } else {
                    Log.e(LOG_TAG, "***** null button, id: " + i);
                }
                if (bPlay != null && bPlay.getBackground() != null) {
                    bPlay.setText("+play " + i);
                } else {
                    Log.e(LOG_TAG, "***** null button, id: " + i);
                }
                mTracks[i] = new SoundTrack(i);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(LOG_TAG, "************** on pause enter ");
        for (int i = 0; i < getSoundCount(); i++) {
            SoundTrack track = mTracks[i];
            track.timeToDie();
        }
        Log.e(LOG_TAG, "************** on pause completed ok");
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
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(EXTRA_MESSAGE, "goober.");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        if (view.getId() == R.id.send_button_0) {
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            EditText editText = (EditText) findViewById(R.id.edit_message);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        } else {
            buttonClicked(view);
        }
    }

    public void updateButton(int index, boolean isRecord, boolean isActive) {
        int buttonId = getButtonId(isRecord, index);
        String buttonText = "not set";
        int color = Color.BLACK;

        if (isRecord) {
            if (isActive) {
                buttonText = "-rec " + index;
                color = Color.BLUE;
            } else {
                buttonText = "+rec " + index;
                color = Color.GREEN;
            }
        } else {
            if (isActive) {
                buttonText = "-play " + index;
                color = Color.BLUE;
            } else {
                buttonText = "+play " + index;
                color = Color.LTGRAY;
            }
        }
        Button b = (Button) findViewById(buttonId);
        b.setText(buttonText);
        b.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        // button.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
    }

    public void buttonClicked(View view) {
        int id = view.getId();

        Button b = (Button) view;
        int indexFromId = getIndexFromButton(id);
        boolean isRecorder = getIsRecordButtonFromId(id);
        boolean isActive = false;

        SoundTrack track = mTracks[indexFromId];

        if (isRecorder) {
            if (track.isRecording()) {
                if (track.stopRecording()){
                    isActive = false;
                    g_mIsRecording = false;
                } else {
                    isActive = true;
                }
            } else {
                if (g_mIsRecording) {
                    // click on a second record button while recording... do nothing.
                    return;
                }
                isActive = track.startRecording();
                g_mIsRecording = true;
            }
        } else {
            if (track.isPlaying()) {
                isActive = !track.stopPlaying();
            } else {
                isActive = track.startPlaying();
            }
        }

        updateButton(indexFromId, isRecorder, isActive);
    }

}