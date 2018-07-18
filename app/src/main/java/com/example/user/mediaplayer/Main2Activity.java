package com.example.user.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button playBtn;
    Button nextBtn;
    Button previousBtn;
    Button closeBtn;
    SeekBar positionBar;
    SeekBar volumeBar;
    TextView elapsedTimeLabel;
    TextView remainingTimeLabel;
    TextView songNameTitle;
    MediaPlayer mp;
    int totalTime;
    Bundle songIdKeeper;
    int id;
    int songCount;
    int countSong;
    int positioncountSong;
    ArrayList<String> songName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        playBtn = findViewById(R.id.playBtn);
        nextBtn = findViewById(R.id.nextBtn);
        previousBtn = findViewById(R.id.previousBtn);
        closeBtn = findViewById(R.id.closeBtn);
        elapsedTimeLabel = findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel);
        songNameTitle = findViewById(R.id.songName);

        songIdKeeper = getIntent().getExtras();
        id = songIdKeeper.getInt("songId");
        songCount = songIdKeeper.getInt("songCount");
        countSong = songIdKeeper.getInt("countSong");
        songName = songIdKeeper.getStringArrayList("songnamelist");
        positioncountSong = countSong;

        songNameTitle.setText(songName.get(countSong));
        /*for(int i=0;i<songName.size();i++){
            System.out.println(songName.get(i));
        }
        System.out.println(id);
        System.out.println(songCount);
        System.out.println(countSong);*/

        mp = MediaPlayer.create(this,id);
        mp.setLooping(false);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        totalTime = mp.getDuration();
        changePlayandStop();
        positionBar = findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    mp.seekTo(i);
                    positionBar.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        volumeBar = findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float volumeNum = i / 100f;
                mp.setVolume(volumeNum,volumeNum);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            int currentPosition = msg.what;
            positionBar.setProgress(currentPosition);

            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);

            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    public String createTimeLabel(int time){
        String timeLabel = "";
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if(sec < 10){
            timeLabel += "0";
        }
        timeLabel += sec;

        return timeLabel;
    }

    public void changePlayandStop(){
        if(mp != null && !mp.isPlaying()){
            mp.start();
            playBtn.setBackgroundResource(R.drawable.ic_pause_black_24dp);
        }
        else if(mp != null && mp.isPlaying()){
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    public void playBtnClick(View view){
        changePlayandStop();
    }

    public void closeBtnClick(View view){
        mp.stop();
        finish();
    }

    public void nextBtnClick(View view){
        mp.stop();
        mp.reset();

        countSong = (countSong+1)%songCount;
        if(countSong>=0){
            songNameTitle.setText(songName.get(countSong));
            id = songIdKeeper.getInt("songId");
            id += (countSong-positioncountSong);

           /*System.out.println(id);
            System.out.println(countSong);*/

            mp = MediaPlayer.create(this,id);
            mp.setLooping(false);
            mp.seekTo(0);
            mp.setVolume(0.5f,0.5f);
            totalTime = mp.getDuration();
            changePlayandStop();
        }
    }

    public void previousBtnClick(View view){
        mp.stop();
        mp.reset();

        countSong = (countSong-1)%songCount;
        if(countSong>=0) {
            songNameTitle.setText(songName.get(countSong));
            id = songIdKeeper.getInt("songId");
            id += (countSong-positioncountSong);

            /*System.out.println(id);
            System.out.println(countSong);*/

            mp = MediaPlayer.create(this, id);
            mp.setLooping(false);
            mp.seekTo(0);
            mp.setVolume(0.5f, 0.5f);
            totalTime = mp.getDuration();
            changePlayandStop();
        }
        else{
            countSong = songCount-1;
            songNameTitle.setText(songName.get(countSong));
            id = songIdKeeper.getInt("songId");
            id += (countSong-positioncountSong);

          /*System.out.println(id);
            System.out.println(countSong);*/

            mp = MediaPlayer.create(this, id);
            mp.setLooping(false);
            mp.seekTo(0);
            mp.setVolume(0.5f, 0.5f);
            totalTime = mp.getDuration();
            changePlayandStop();
        }
    }
}

