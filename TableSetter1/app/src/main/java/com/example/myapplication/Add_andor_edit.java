package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Add_andor_edit extends AppCompatActivity {
    private RecyclerView mCatolog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mlayout;
    private Game gameEntery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_andor_edit);

        Button submit = findViewById(R.id.editSub);

        Intent intent = getIntent();
        this.gameEntery = intent.getParcelableExtra("Game");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(MainActivity.class);
            }
        });

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
}
