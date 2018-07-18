package com.example.user.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter {

    Context mContext;
    ArrayList<Music> mSongName;
    public MainAdapter(Context context,ArrayList<Music> songname){
        mContext = context;
        mSongName = songname;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.song,parent,false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ItemHolder)holder).textView.setText(String.valueOf(position+1)+"."+mSongName.get(position).mSongname);
    }

    @Override
    public int getItemCount() {
        return mSongName.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public ItemHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.Song);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,Main2Activity.class);
                    Bundle songidKeeper = new Bundle();
                    songidKeeper.putInt("songCount",getItemCount());
                    songidKeeper.putInt("countSong",getAdapterPosition());
                    songidKeeper.putInt("songId",mSongName.get(getAdapterPosition()).resId);

                    ArrayList<String>songName = new ArrayList<>();
                    for(int i=0;i<getItemCount();i++){
                        songName.add(mSongName.get(i).mSongname);
                    }
                    songidKeeper.putStringArrayList("songnamelist",songName);

                    intent.putExtras(songidKeeper);
                    mContext.startActivity(intent);
                }
            });
        }
    }
}
