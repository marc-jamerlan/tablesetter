package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class playerpage extends AppCompatActivity {

    private ArrayList<Game> catalog;
    private ArrayList<Tag> tagList;

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

        EditText name = findViewById(R.id.editText3);
        ImageButton addImage = findViewById(R.id.addImage2);
        EditText notes = findViewById(R.id.editText4);
        RecyclerView games = findViewById(R.id.playergame);
        RecyclerView tags = findViewById(R.id.playertag);
        Button addTag = findViewById(R.id.addTags2);
        Button addGame = findViewById(R.id.addGame);




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
                open(Tag_Add.class);
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


    public String imageToString(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void open(Class des)
    {
        Intent intent = new Intent(this, des);
        intent.putParcelableArrayListExtra("Tag",this.tagList);
        intent.putParcelableArrayListExtra("Game",this.catalog);
        startActivity(intent);
    }

    // TODO - use startActivityforResult() when requesting image, see add_andor_edit.class
    /*
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();

            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                Toast.makeText(Add_andor_edit.this, "Uploaded Image", Toast.LENGTH_SHORT).show();
                ImageButton addImage = (ImageButton) findViewById(R.id.addImage1);
                addImage.getLayoutParams().height = 350;
                addImage.getLayoutParams().width = 350;
                addImage.requestLayout();

                addImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 350, 350, false));

            } catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(Add_andor_edit.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    */
}
