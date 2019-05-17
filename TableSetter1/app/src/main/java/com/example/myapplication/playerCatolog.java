package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class playerCatolog extends AppCompatActivity {

    private ArrayList<Player> catalog;
    private RecyclerView mCatalog;
    private  playernoclickAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    final DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_catolog);
        //playerView
        catalog = createPlayerArrayList();
        if (catalog == null){
            catalog = new ArrayList<>();
        }

        createrecycler();



    }

    public void createrecycler()
    {
        //recycleview is here

        mCatalog = findViewById(R.id.playerView);
        mCatalog.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new  playernoclickAdapter(catalog);

        mCatalog.setLayoutManager(mLayout);
        mCatalog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new playernoclickAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {


            }
        });


    }

    public ArrayList<Player> createPlayerArrayList()
    {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        String playerIDString = dbHelper.loadPlayerData();

        String[] playerIDArray = playerIDString.split(";");

        for(int i = 0; i < playerIDArray.length; i++)
        {
            playerArrayList.add(dbHelper.fetchPlayerData(playerIDArray[i]));
        }

        return playerArrayList;
    }

    public void open(Class des)
    {
        Intent intent = new Intent(this, des);
        startActivity(intent);
    }
}
