package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Tag_Add extends AppCompatActivity {
    private ArrayList<Integer> tagIDList;
    private RecyclerView mCatalog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private Game gameEntry;
    private ArrayList<Tag> TagObjectHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag__add);

        Intent intent = getIntent();
        this.gameEntry = intent.getParcelableExtra("Game");
        this.tagIDList = intent.getParcelableExtra("Tag");
        this.TagObjectHolder = new ArrayList<>();

        if(gameEntry == null){
            gameEntry = new Game();
        }

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        String tagData = dbHelper.loadTagData();

        String[] tagArray = tagData.split(";");

        for (int i = 0; i < tagArray.length; i++)
        {
            this.TagObjectHolder.add(dbHelper.fetchTagData(tagArray[i]));
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
                Tag newTag = new Tag(name.getText().toString(), summary.getText().toString());
                name.setText("  ");
                summary.setText("  ");
                TagObjectHolder.add(newTag);
                dbHelper.addTagData(newTag);
                // layout need to be let out
                createrecycler();
                mAdapter.notifyDataSetChanged();
            }
        });


    }


    public void openGame(Class des, Game game)
    {
        Intent intent = new Intent(this, des);
        intent.putExtra("Tag",this.tagIDList);
        intent.putExtra("Game", game);
        startActivity(intent);
    }

    public void createrecycler()
    {
        //recylceview is here
        mCatalog = findViewById(R.id.recyclerView);
        mCatalog.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        mAdapter = new tagAdapter(TagObjectHolder);

        mCatalog.setLayoutManager(mLayout);
        mCatalog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                gameEntry.setTagIDList(TagObjectHolder.get(itemPos));
                gameEntry.setTagsAdded(1);
                openGame(Add_andor_edit.class,gameEntry);

            }
        });


    }
}


