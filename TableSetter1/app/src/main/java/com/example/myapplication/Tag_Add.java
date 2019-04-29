package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Tag_Add extends AppCompatActivity {
    private ArrayList<Tags> tagList;
    private RecyclerView mCatolog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mlayout;
    private Game gameEntry;
    private ArrayList<Tags> listoftags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag__add);

        Intent intent = getIntent();
        this.gameEntry = intent.getParcelableExtra("Game");
        //this.listoftags = intent.getParcelableArrayListExtra("Tags");
        this.listoftags = new ArrayList<>();

        if(gameEntry == null){
            gameEntry = new Game();
        }

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        String tagData = dbHelper.loadTagData();

        String[] tagArray = tagData.split(";");

        for (int i = 0; i < tagArray.length; i++)
        {
            this.listoftags.add(dbHelper.fetchTagData(tagArray[i]));
        }

        createrecycler();

        Button addtag = findViewById(R.id.addTagButton);
        final EditText name = findViewById(R.id.tagNameAdd);
        final EditText summary = findViewById(R.id.tagSummaryAdd);

        addtag.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Tags newTag = new Tags(name.getText().toString(), summary.getText().toString());
                name.setText("  ");
                summary.setText("  ");
                listoftags.add(newTag);
                dbHelper.addTagData(newTag);
                mAdapter.notifyDataSetChanged();
            }
        });


    }


    public void openGame(Class des, Game game)
    {
        Intent intent = new Intent(this, des);
        intent.putExtra("Tags",this.tagList);
        intent.putExtra("Game", game);
        startActivity(intent);
    }

    public void createrecycler()
    {
        //recylceview is here
        mCatolog = findViewById(R.id.recyclerView);
        mCatolog.setHasFixedSize(true);
        mlayout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new tagAdapter(listoftags);

        mCatolog.setLayoutManager(mlayout);
        mCatolog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {

                gameEntry.setTagArray(listoftags.get(itemPos));
                gameEntry.setTagsAdded(1);
                openGame(Add_andor_edit.class,gameEntry);

            }
        });


    }
}


