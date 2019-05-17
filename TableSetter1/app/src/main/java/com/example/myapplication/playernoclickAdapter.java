package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class playernoclickAdapter extends RecyclerView.Adapter<playerAdapter.playerAdapterHolder>  {

    private ArrayList<Player> playerArrayList;
    private playernoclickAdapter.onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int itemPos);
    }

    public void setOnItemClickListener(playernoclickAdapter.onItemClickListener listener){
        this.mListener = listener;
    }

    public static class playernoclickAdapterHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView playerName;
        public CheckBox box;

        public playernoclickAdapterHolder(@NonNull View itemView, final playernoclickAdapter.onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView11);
            playerName = itemView.findViewById(R.id.playerName);
            box = itemView.findViewById(R.id.checkBox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onItemClick(postion);
                        }
                        if(box.isChecked()){
                            box.setChecked(false);
                        } else {
                            box.setChecked(true);
                        }
                    }
                }
            });
        }


    }

    playernoclickAdapter(ArrayList<Player> a){
        this.playerArrayList = a;
    }


    public playernoclickAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.playeritem,viewGroup,false);

        playernoclickAdapterHolder pap = new playernoclickAdapterHolder(v,mListener);
        return pap;
    }



    public void onBindViewHolder(@NonNull playernoclickAdapter.playernoclickAdapterHolder playernoclickAdapterHolder, int i) {
        Player currentplayer = playerArrayList.get(i);

        if(currentplayer != null){
            playernoclickAdapterHolder.mImageView.setImageBitmap(currentplayer.decodeGameImage());
            playernoclickAdapterHolder.playerName.setText(currentplayer.getName());
        }


    }


    public int getItemCount() {
        return playerArrayList.size();
    }

}
