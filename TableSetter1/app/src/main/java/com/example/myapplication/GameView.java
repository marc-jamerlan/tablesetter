package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameView extends AppCompatActivity {
    private RecyclerView mCatalog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private Game gameEntry;
    private ArrayList<Tag> tagObjectHolder;

    final DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        Intent intent = getIntent();
        this.gameEntry = intent.getParcelableExtra("Game");
        this.tagObjectHolder = createTagList(this.gameEntry.getTagIDList());

        createrecycler();

        ImageButton edit = findViewById(R.id.editButton);

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edit();
            }
        });

        ImageButton deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteGame();
            }
        });

        ImageView pic = findViewById(R.id.imageView5);

        TextView name = findViewById(R.id.textView5);

        TextView summary = findViewById(R.id.textView8);

        pic.setImageBitmap(this.gameEntry.decodeGameImage());

        name.setText(this.gameEntry.getName());

        summary.setText(this.gameEntry.getNotes());
    }

    public ArrayList<Tag> createTagList(ArrayList<Integer> ids)
    {
        ArrayList<Tag> tagList = new ArrayList<>();

        if(ids != null)
        {
            for (int i = 0; i < ids.size(); i++)
            {
                tagList.add(dbHelper.fetchTagData((ids.get(i).toString())));
            }
        }
        return tagList;
    }

    public void createrecycler(){
        //recylceview is here
        mCatalog = findViewById(R.id.tagList);
        mCatalog.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new tagAdapter(tagObjectHolder);

        mCatalog.setLayoutManager(mLayout);
        mCatalog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int itemPos) {

                //Toast.makeText(this,gameEntry.getTagIDList().get(itemPos).getNotes(),Toast.LENGTH_LONG).show();

            }
        });
    }

    public void open(Class des){
        Intent intent = new Intent(this,des);
        startActivity(intent);
    }


    public void close(){
        //open(MainActivity.class);
        finish();
    }

    public void edit(){
        Intent intent = new Intent(this,Add_andor_edit.class);
        intent.putExtra("Tag",this.tagObjectHolder);
        intent.putExtra("Game", this.gameEntry);
        startActivity(intent);
    }

    public void deleteGame(){
        Intent intent = new Intent(this,MainActivity.class);
        if(dbHelper.deleteGameData(gameEntry.getID()))
        {
            Toast.makeText(this, "Game Deleted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Deletion Failed, data unchanged", Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }
}
