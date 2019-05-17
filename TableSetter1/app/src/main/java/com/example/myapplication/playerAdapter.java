package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class playerAdapter  extends RecyclerView.Adapter<catalogAdapter.catalogAdapterHolder> {

    private ArrayList<Player> playerArrayList;
    private catalogAdapter.onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int itemPos);
    }

    public void setOnItemClickListener(catalogAdapter.onItemClickListener listener){
        this.mListener = listener;
    }

    public static class catalogAdapterHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView playerName;

        public catalogAdapterHolder(@NonNull View itemView, final catalogAdapter.onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView11);
            playerName = itemView.findViewById(R.id.playerName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            listener.onItemClick(postion);
                        }
                    }
                }
            });
        }
    }

    playerAdapter(ArrayList<Player> a){
        this.playerArrayList = a;
    }


    public catalogAdapter.catalogAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item,viewGroup,false);

        catalogAdapter.catalogAdapterHolder cah = new catalogAdapter.catalogAdapterHolder(v,mListener);
        return cah;
    }


    public void onBindViewHolder(@NonNull catalogAdapter.catalogAdapterHolder catalogAdapterHolder, int i) {
        Player currentplayer = playerArrayList.get(i);

        catalogAdapterHolder.mImageView.setImageBitmap(currentplayer.decodeGameImage());
        catalogAdapterHolder.gameName.setText(currentplayer.getName());
    }


    public int getItemCount() {
        return playerArrayList.size();
    }


}
