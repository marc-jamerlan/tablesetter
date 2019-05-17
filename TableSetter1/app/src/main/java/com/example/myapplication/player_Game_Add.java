package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class player_Game_Add extends AppCompatActivity {

    private ArrayList<Game> catalog;
    private RecyclerView mCatalog;
    private catalogAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<Tag> tranferTags;
    private ArrayList<Game> transferGame;

    final DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player__game__add);

        Intent intent = getIntent();
        this.tranferTags = intent.getParcelableArrayListExtra("Tag");
        this.transferGame = intent.getParcelableArrayListExtra("Game");

        if(tranferTags == null){
            tranferTags = new ArrayList<>();
        }

        if(transferGame == null){
            transferGame = new ArrayList<>();
        }

        createlist();
        createrecycler();

    }

    public void createlist()
    {
        //this is for the catalog

        GameNameList gameNameList = new GameNameList();

        String tableData = dbHelper.loadGameData();

        if (!tableData.equals(""))
        {
            String[] tableArray = tableData.split(";");

            for (int i = 0; i < tableArray.length; i++)
            {
                gameNameList.appendList(tableArray[i]);
            }
        }

        catalog = new ArrayList<>();

        for (int i = 0; i < gameNameList.getLength(); i++)
        {
            Game currentGame = dbHelper.fetchGameData(gameNameList.getGameNameList().get(i));
            catalog.add(currentGame);
        }
    }

    public void createrecycler()
    {
        //recycleview is here

        mCatalog = findViewById(R.id.recylceview);
        mCatalog.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new catalogAdapter(catalog);

        mCatalog.setLayoutManager(mLayout);
        mCatalog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                openGame(playerpage.class,catalog.get(itemPos));

            }
        });
    }

    public void openGame(Class des,Game game)
    {
        transferGame.add(game);
        Intent intent = new Intent(this, des);
        intent.putParcelableArrayListExtra("Tag",tranferTags);
        intent.putParcelableArrayListExtra("Game",transferGame);
        startActivity(intent);
    }
}
