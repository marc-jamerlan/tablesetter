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
    private ArrayList<Game> allGames;
    private ArrayList<Game> recomenedGames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_recommend);

        recomenedGames = new ArrayList<>();
        recomenedpeople = new ArrayList<>();

        createlist();

        final Button recomend = findViewById(R.id.button4);

        listOfplayers = createPlayerArrayList();

        if( listOfplayers == null){
            listOfplayers = new ArrayList<>();
        }

        createPlayerArrayList();



        createrecyclerplayer();

        recomend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Game Recomend = null;
                int score = -1;
                Game temp;
                int tempScore;
                for(int i = 0; i < allGames.size(); i++){
                    tempScore = 0;
                    temp = allGames.get(i);
                    for(int j = 0; j < listOfplayers.size(); j++){
                        if(listOfplayers.get(j).getGameNameList().contains(temp.getName())){
                            tempScore += 5;
                        }
                        for(int k = 0;k <listOfplayers.get(j).getTagIDList().size(); k++){
                            if(temp.getTagIDList().contains(listOfplayers.get(j).getSingleTag(k))){
                                tempScore += 3;
                            }
                        }

                    }

                    if(tempScore >= score){
                        score = tempScore;
                        Recomend = temp;
                    }



                }

                if(score != -1 && Recomend != null){
                    recomenedGames.add(Recomend);
                    createrecyclergame();
                }




            }
        });




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

        allGames = new ArrayList<>();

        for (int i = 0; i < gameNameList.getLength(); i++)
        {
            Game currentGame = dbHelper.fetchGameData(gameNameList.getGameNameList().get(i));
            allGames.add(currentGame);
        }
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
