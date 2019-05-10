package com.example.myapplication;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
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

        this.catalog = new ArrayList<>();
        this.tagList = new ArrayList<>();

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
