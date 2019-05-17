package com.example.myapplication;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class gameRecommend extends AppCompatActivity {

    final DatabaseHelper dbHelper = new DatabaseHelper(this);

    private RecyclerView PlayerCatalog;
    private playerAdapter PlayerAdapter;
    private RecyclerView.LayoutManager PlayerLayout;

    private RecyclerView gameCatalog;
    private catalogAdapter gameAdapter;
    private RecyclerView.LayoutManager gameLayout;

    private ArrayList<Player> listOfplayers;
    private ArrayList<Player> recomenedpeople;
    private ArrayList<Game> recomenedGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_recommend);

        listOfplayers = new ArrayList<>();
        recomenedGames = new ArrayList<>();
        recomenedpeople = new ArrayList<>();

        Button recomend = findViewById(R.id.button4);

        listOfplayers = createPlayerArrayList();

        createPlayerArrayList();

        recomend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });




    }

    public void createrecyclerplayer()
    {
        //recylceview is here
        PlayerCatalog = findViewById(R.id.playerlist);
        PlayerCatalog.setHasFixedSize(true);
        PlayerLayout = new LinearLayoutManager(this);
        PlayerAdapter = new playerAdapter(this.listOfplayers);

        PlayerCatalog.setLayoutManager(PlayerLayout);
        PlayerCatalog.setAdapter(PlayerAdapter);

        PlayerAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                if(recomenedpeople.contains(listOfplayers.get(itemPos))){
                    recomenedpeople.remove(listOfplayers.get(itemPos));
                }else {
                    recomenedpeople.add(listOfplayers.get(itemPos));
                }

            }
        });


    }

    public void createrecyclergame()
    {
        //recylceview is here
        gameCatalog = findViewById(R.id.gamerec);
        gameCatalog.setHasFixedSize(true);
        gameLayout = new LinearLayoutManager(this);
        gameAdapter = new catalogAdapter(this.recomenedGames);

        gameCatalog.setLayoutManager(gameLayout);
        gameCatalog.setAdapter(gameAdapter);

        gameAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
               openGameView(recomenedGames.get(itemPos));

            }
        });


    }

    // creates an ArrayList of player objects fetched from database
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
    public void openGameView(Game game){
        Intent intent = new Intent(this,GameView.class);
        intent.putExtra("Game", game);
        startActivity(intent);
    }
}
