package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class playerpage extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;

    private ArrayList<Game> catalog;
    private ArrayList<Tag> tagList;

    // currently empty, pass from tag and game addition pages
    private ArrayList<String> gameNameHolder;
    private ArrayList<Integer> tagIDHolder;

    private RecyclerView mCatalogPlayer;
    private catalogAdapter mAdapterPlayer;
    private RecyclerView.LayoutManager mlayoutPlayer;

    private RecyclerView mCatalogTag;
    private tagAdapter mAdapterTag;
    private RecyclerView.LayoutManager mlayoutTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playerpage);

        gameNameHolder = new ArrayList<>();
        tagIDHolder = new ArrayList<>();
        catalog = new ArrayList<>();
        tagList = new ArrayList<>();


        final EditText name = findViewById(R.id.editText3);
        final ImageButton addImage = findViewById(R.id.addImage2);
        final EditText notes = findViewById(R.id.editText4);
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        RecyclerView games = findViewById(R.id.playergame);
        RecyclerView tags = findViewById(R.id.playertag);
        Button addTag = findViewById(R.id.button);
        Button addGame = findViewById(R.id.button2);
        Button submit = findViewById(R.id.button3);

        addTag.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open(playerTagAdd.class);
            }
        });

        addGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open(player_Game_Add.class);
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            // add new player
            @Override
            public void onClick(View v)
            {
               Player player = new Player();
               player.setName(name.getText().toString());
               player.setPlayerImage((imageToString(((BitmapDrawable)addImage.getDrawable()).getBitmap())));
               player.setNotes(notes.getText().toString());
               player.setGameNameList(gameNameHolder);
               player.setTagIDList(tagIDHolder);

                dbHelper.addPlayerData(player);

                Toast.makeText(getApplicationContext(), "Player Added", Toast.LENGTH_SHORT).show();

               open(TitleScreen.class);
            }
        });

        addImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });





        Intent intent = getIntent();

        this.tagList = intent.getParcelableArrayListExtra("Tag");
        this.catalog = intent.getParcelableArrayListExtra("Game");

        if(this.catalog == null){
            this.catalog = new ArrayList<>();
        }
        if(this.tagList == null){
            this.tagList = new ArrayList<>();
        }

        createrecyclerPlayer();
        createrecyclerTag();
    }

    public void createrecyclerPlayer()
    {
        //recylceview is here
        mCatalogPlayer = findViewById(R.id.playergame);
        mCatalogPlayer.setHasFixedSize(true);
        mlayoutPlayer = new LinearLayoutManager(this);
        mAdapterPlayer = new catalogAdapter(this.catalog);

        mCatalogPlayer.setLayoutManager(mlayoutPlayer);
        mCatalogPlayer.setAdapter(mAdapterPlayer);

        mAdapterPlayer.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                catalog.remove(itemPos);
                mAdapterPlayer.notifyDataSetChanged();
            }
        });


    }

    public void createrecyclerTag()
    {
        //recylceview is here
        mCatalogTag = findViewById(R.id.playertag);
        mCatalogTag.setHasFixedSize(true);
        mlayoutTag = new LinearLayoutManager(this);
        mAdapterTag = new tagAdapter(this.tagList);

        mCatalogTag.setLayoutManager(mlayoutTag);
        mCatalogTag.setAdapter(mAdapterTag);

        mAdapterTag.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                tagList.remove(itemPos);
                mAdapterTag.notifyDataSetChanged();

            }
        });


    }

    public void open(Class des)
    {
        Intent intent = new Intent(this, des);
        intent.putParcelableArrayListExtra("Tag",this.tagList);
        intent.putParcelableArrayListExtra("Game",this.catalog);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            //Uri selectedImage = data.getData();
            Bundle extras = data.getExtras();

            try
            {
                //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Bitmap bitmap = (Bitmap) extras.get("data");
                Toast.makeText(playerpage.this, "Uploaded Image", Toast.LENGTH_SHORT).show();
                ImageButton addImage = (ImageButton) findViewById(R.id.addImage2);
                addImage.getLayoutParams().height = 350;
                addImage.getLayoutParams().width = 350;
                addImage.requestLayout();

                addImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 350, 350, false));

            } catch (NullPointerException e) //IOException for gallery image creation
            {
                e.printStackTrace();
                Toast.makeText(playerpage.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String imageToString(Bitmap bm)
    {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

}
