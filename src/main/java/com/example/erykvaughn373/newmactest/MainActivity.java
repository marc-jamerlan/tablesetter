package com.example.erykvaughn373.newmactest;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class catalogAdapter extends RecyclerView.Adapter<catalogAdapter.catalogAdapterHolder> {

    private ArrayList<Game> catalog;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int itemPos);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.mListener = listener;
    }

    public static class catalogAdapterHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView gameName;
        public TextView tag1;
        public TextView tag2;
        public TextView tag3;

        public catalogAdapterHolder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            gameName = itemView.findViewById(R.id.gameName);
            tag1 = itemView.findViewById(R.id.textView1);
            tag2 = itemView.findViewById(R.id.textView2);
            tag3 = itemView.findViewById(R.id.textView3);

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

    catalogAdapter(ArrayList<Game> a){
        this.catalog = a;
    }

    @NonNull
    @Override
    public catalogAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item,viewGroup,false);

        catalogAdapterHolder cah = new catalogAdapterHolder(v,mListener);
        return cah;
    }

    @Override
    public void onBindViewHolder(@NonNull catalogAdapterHolder catalogAdapterHolder, int i) {
        Game currentGame = catalog.get(i);

        catalogAdapterHolder.mImageView.setImageResource(currentGame.getGameImage());
        catalogAdapterHolder.gameName.setText(currentGame.getName());
        catalogAdapterHolder.tag1.setText(currentGame.getTag1());
        catalogAdapterHolder.tag2.setText(currentGame.getTag2());
        catalogAdapterHolder.tag3.setText(currentGame.getTag3());

    }

    @Override
    public int getItemCount() {
        return catalog.size();
    }
}

