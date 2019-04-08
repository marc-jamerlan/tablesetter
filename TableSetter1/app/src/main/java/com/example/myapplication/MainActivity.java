package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private  ArrayList<Game> catolog; // needs to be public perhaps?
    private  ArrayList<Tags> tagList;
    private RecyclerView mCatolog;
    private catalogAdapter mAdapter;
    private RecyclerView.LayoutManager mlayout;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);

       createlist();

       createrecycler();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createtagList(){
        tagList = new ArrayList<>();

        tagList.add(new Tags(0,"Casual"," "));

        tagList.add(new Tags(1,"Hardcore"," "));

        tagList.add(new Tags(2,"Card Game"," "));

        tagList.add(new Tags(3,"Hex and Tile"," "));

        tagList.add(new Tags(4,"Deck Builder"," "));

        tagList.add(new Tags(5,"Co-op"," "));

        tagList.add(new Tags(6,"Competitive"," "));
    }

    //TODO - MODIFY TO PUT INTO DB


    public void createlist(){
        //this is for the catolog
        catolog = new ArrayList<>();

        /*
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game1","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game2","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game3","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game4","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game5","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game6","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game7","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game8","tag","tag",
                "tag"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"Please","Help","They are",
                "Watching"));
        */
    }

    public void createrecycler(){
        //recylceview is here
        mCatolog = findViewById(R.id.recylceview);
        mCatolog.setHasFixedSize(true);
        mlayout = new LinearLayoutManager(this);
        mAdapter = new catalogAdapter(catolog);

        mCatolog.setLayoutManager(mlayout);
        mCatolog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int itemPos) {
                openGame(GameView.class,catolog.get(itemPos));

            }
        });



    }

    public void openGame(Class des,Game game){
        Intent intent = new Intent(this,des);
        intent.putExtra("Game",game);
        startActivity(intent);
    }

    public void open(Class des){
        Intent intent = new Intent(this,des);
        Game game = new Game(0,R.drawable.ic_launcher_background,"New Game1",tagList);

        intent.putExtra("Game",game);
        startActivity(intent);
    }

    public void openPopup (View v){
//        myDialog.setContentView(R.layout.custom_popup);
//
//        Button exit = (Button)myDialog.findViewById(R.id.exitbutton);
//        Button edit = (Button)myDialog.findViewById(R.id.editbutton);
//
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myDialog.dismiss();
//            }
//        });
//        myDialog.show();


        PopupMenu popupMenu = new PopupMenu(this,v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.custom_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_game:
                open(Add_andor_edit.class);

                return true;

            default:
                return false;

        }
    }
}
