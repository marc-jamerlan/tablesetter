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
    private ArrayList<Tag> tagsArrayList;

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

            name = itemView.findViewById(R.id.tagnameA);

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

    tagAdapter(){
        this.tagsArrayList = new ArrayList<>();
    }

    tagAdapter(ArrayList<Tag> a){
        this.tagsArrayList = a;
    }


    @NonNull
    @Override
    public tagAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tagvieweritem,viewGroup,false);

        tagAdapterHolder cah = new tagAdapterHolder(v,mListener);
        return cah;
    }

    @Override
    public void onBindViewHolder(@NonNull tagAdapterHolder tagAdapterHolder, int i) {
        Tag tag = tagsArrayList.get(i);
        tagAdapterHolder.name.setText(tag.getName());
    }

    @Override
    public int getItemCount() {
        if(tagsArrayList != null)
        {
            return this.tagsArrayList.size();
        }
        else
        {
            return 0;
        }
    }
}
