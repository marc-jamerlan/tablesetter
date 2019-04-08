package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameView extends AppCompatActivity {
    private RecyclerView mCatolog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mlayout;
    private Game gameEntery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        Intent intent = getIntent();
        this.gameEntery = intent.getParcelableExtra("Game");


        ImageView edit = findViewById(R.id.imageView3);
        ImageView  Delete = findViewById(R.id.imageView4);

        ImageView pic = findViewById(R.id.imageView5);

        TextView name = findViewById(R.id.textView5);

        TextView summary = findViewById(R.id.textView8);

        name.setText(this.gameEntery.getName());

        summary.setText(this.gameEntery.getNotes());





    }

    public void createrecycler(){
        //recylceview is here
        mCatolog = findViewById(R.id.tagList);
        mCatolog.setHasFixedSize(true);
        mlayout = new LinearLayoutManager(this);
        mAdapter = new tagAdapter(gameEntery) ;

        mCatolog.setLayoutManager(mlayout);
        mCatolog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int itemPos) {

                //Toast.makeText(this,gameEntery.getTagArray().get(itemPos).getNotes(),Toast.LENGTH_LONG).show();

            }
        });



    }

    public void open(Class des){
        Intent intent = new Intent(this,des);
        startActivity(intent);
    }

    public void close(){
        open(MainActivity.class);
    }

    public void edit(){
        Intent intent = new Intent(this,Add_andor_edit.class);
        intent.putExtra("Game",gameEntery);
        startActivity(intent);
    }
}
