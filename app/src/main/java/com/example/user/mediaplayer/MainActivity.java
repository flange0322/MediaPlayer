package com.example.user.mediaplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerSongList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<Music> song = new ArrayList<>();
        song.add(new Music("明日の君さえいればいい",R.raw.ashita));
        song.add(new Music("hoshikuzu venus",R.raw.hoshikuzuvenus));
        song.add(new Music("いいのに",R.raw.iinoni));
        song.add(new Music("Lucid Dream",R.raw.luciddream));
        song.add(new Music("To see the future",R.raw.toseethefuture));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(new MainAdapter(this,song));
    }
}