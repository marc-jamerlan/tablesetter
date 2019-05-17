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

public class playerAdapter  extends RecyclerView.Adapter<playerAdapter.playerAdapterHolder> {

    private ArrayList<Player> playerArrayList;
    private playerAdapter.onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int itemPos);
    }

    public void setOnItemClickListener(playerAdapter.onItemClickListener listener){
        this.mListener = listener;
    }

    public static class playerAdapterHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView playerName;

        public playerAdapterHolder(@NonNull View itemView, final playerAdapter.onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            playerName = itemView.findViewById(R.id.playerName);
            final ImageView check = itemView.findViewById(R.id.check);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isVisible = false;
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onItemClick(postion);
                        }
                    }

                    if(isVisible){
                        check.setVisibility(View.INVISIBLE);
                        isVisible = false;
                    }
                    else
                    {
                        check.setVisibility(View.VISIBLE);
                        isVisible = true;
                    }

                }
            });
        }


    }

    playerAdapter(ArrayList<Player> a){
        this.playerArrayList = a;
    }


    public playerAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.playeritem,viewGroup,false);

        playerAdapter.playerAdapterHolder pap = new playerAdapter.playerAdapterHolder(v,mListener);
        return pap;
    }


    public void onBindViewHolder(@NonNull playerAdapter.playerAdapterHolder playerAdapterHolder, int i) {
        Player currentplayer = playerArrayList.get(i);

        if(currentplayer != null){
            playerAdapterHolder.mImageView.setImageBitmap(currentplayer.decodeGameImage());
            playerAdapterHolder.playerName.setText(currentplayer.getName());
        }


    }


    public int getItemCount() {
        return playerArrayList.size();
    }






}
