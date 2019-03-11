package com.example.myapplication;

import com.example.myapplication.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.Array;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private  ArrayList<Game> catolog;
    private RecyclerView mCatolog;
    private catalogAdapter mAdapter;
    private RecyclerView.LayoutManager mlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       createlist();

       createrecycler();


       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(Add_andor_edit.class);
            }
        });
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

    public void createlist(){
        //this is for the catolog
        catolog = new ArrayList<>();
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game1","Cool","Shit Game",
                "Yolo"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game2","Cool","Shit Game",
                "Yolo"));
        catolog.add(new Game(R.drawable.ic_launcher_background,"New Game3","Cool","Shit Game",
                "Yolo"));

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
                open(GameView.class);
            }
        });



    }

    public void open(Class des){
        Intent intent = new Intent(this,des);
        startActivity(intent);
    }
}
