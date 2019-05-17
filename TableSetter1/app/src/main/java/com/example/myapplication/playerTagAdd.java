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



public class playerTagAdd extends AppCompatActivity {
    private ArrayList<Integer> tagIDList;
    private RecyclerView mCatalog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private ArrayList<Tag> TagObjectHolder;
    private ArrayList<Tag> tranferTags;
    private ArrayList<Game> transferGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_tag_add);

        Intent intent = getIntent();
        this.tranferTags = intent.getParcelableArrayListExtra("Tag");
        this.transferGame = intent.getParcelableArrayListExtra("Game");

        if(tranferTags == null){
            tranferTags = new ArrayList<>();
        }

        if(transferGame == null){
            transferGame = new ArrayList<>();
        }

        TagObjectHolder = new ArrayList<>();

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        String tagData = dbHelper.loadTagData();

        String[] tagArray = tagData.split(";");

        for (int i = 0; i < tagArray.length; i++)
        {
            this.TagObjectHolder.add(dbHelper.fetchTagData(tagArray[i]));
        }

        createrecycler();

        Button addtag = findViewById(R.id.addTagButton2);
        final EditText name = findViewById(R.id.tagNameAdd2);
        final EditText summary = findViewById(R.id.tagSummaryAdd2);

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
                mAdapter.notifyDataSetChanged();
            }
        });


    }


    public void openGame(Class des,Tag tag)
    {
        tranferTags.add(tag);
        Intent intent = new Intent(this, des);
        intent.putParcelableArrayListExtra("Tag",tranferTags);
        intent.putParcelableArrayListExtra("Game",transferGame);
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
                    openGame(playerpage.class,TagObjectHolder.get(itemPos));
                }
            });


        }


}
