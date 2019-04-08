package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class tagAdapter extends RecyclerView.Adapter<tagAdapter.tagAdapterHolder> {

    private catalogAdapter.onItemClickListener mListener;
    private Game gameEntry;

    public interface onItemClickListener{
        void onItemClick(int itemPos);
    }

    public void setOnItemClickListener(catalogAdapter.onItemClickListener listener){
        this.mListener = listener;
    }

    public static class tagAdapterHolder extends RecyclerView.ViewHolder {

        public TextView name;

        public tagAdapterHolder(@NonNull View itemView, final catalogAdapter.onItemClickListener listener) {
            super(itemView);

             name = itemView.findViewById(R.id.textView10);

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

    tagAdapter(Game a){
        this.gameEntry = a;
    }


    @NonNull
    @Override
    public tagAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_item,viewGroup,false);

        tagAdapter.tagAdapterHolder cah = new tagAdapter.tagAdapterHolder(v,mListener);
        return cah;
    }

    @Override
    public void onBindViewHolder(@NonNull tagAdapterHolder tagAdapterHolder, int i) {
        Tags tag = gameEntry.getTagArray().get(i);
        tagAdapterHolder.name.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        return this.gameEntry.getTagArray().size();
    }
}
