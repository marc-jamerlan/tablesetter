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

public class Add_andor_edit extends AppCompatActivity
{
    private static int RESULT_LOAD_IMAGE = 1;
    private RecyclerView mCatalog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;
    private Game gameEntry;
    private ArrayList<Tag> TagObjectHolder;
    private ArrayList<Integer> tagIDHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_andor_edit);

        final EditText edit = (EditText) findViewById(R.id.editText);
        final EditText edit2 = (EditText) findViewById(R.id.editText2);
        final GameNameList gameNameList = (GameNameList) getApplicationContext();
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        final ImageButton addImage = (ImageButton) findViewById(R.id.addImage1);

        addImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edit.getText().clear();
            }
        });
        edit2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edit2.getText().clear();
            }
        });

        Intent intent = getIntent();
        this.gameEntry = intent.getParcelableExtra("Game");
        this.TagObjectHolder = intent.getParcelableArrayListExtra("Tag");

        if(gameEntry == null)
        {
            tagIDHolder = new ArrayList<>();
        }

        else
        {
            edit.setText(this.gameEntry.getName());
            edit2.setText(this.gameEntry.getNotes());
            addImage.setImageBitmap(this.gameEntry.decodeGameImage());
            tagIDHolder = gameEntry.getTagIDList();

            if(TagObjectHolder == null)
            {
                TagObjectHolder = new ArrayList<>();

                for (int i = 0; i < tagIDHolder.size(); i++)
                {
                    TagObjectHolder.add(dbHelper.fetchTagData(tagIDHolder.get(i).toString()));
                }
            }
        }

        createrecycler();

        Button submit = findViewById(R.id.editSub);

        Button addTag = findViewById(R.id.addTags);

        addTag.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(gameEntry == null)
                {
                    gameEntry = new Game();
                }

                gameEntry.setName(edit.getText().toString());
                gameEntry.setGameImage(imageToString(((BitmapDrawable)addImage.getDrawable()).getBitmap()));
                gameEntry.setNotes(edit2.getText().toString());



                openGame(Tag_Add.class, gameEntry);
            }
        });

        // Add new game
        if (this.gameEntry == null || (this.gameEntry.hasTagsAdded() == 1 && this.gameEntry.wasEdited() == 0))
        {
            submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Game newGame = new Game();
                    String gameName = edit.getText().toString();

                    newGame.setName(gameName);
                    gameNameList.appendList(gameName);
                    newGame.setGameImage(imageToString(((BitmapDrawable)addImage.getDrawable()).getBitmap()));
                    newGame.setNotes(edit2.getText().toString());
                    if(tagIDHolder.size() != 0)
                    {
                        newGame.setTagsAdded(1);
                    }
                    newGame.setTagIDList(tagIDHolder);
                    newGame.setEdited(1);

                    dbHelper.addGameData(newGame);

                    Toast.makeText(getApplicationContext(), "Game Added", Toast.LENGTH_SHORT).show();

                    open(MainActivity.class);
                }
            });
        }

        // update game info
        else
        {
            submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Game gameUpdate = gameEntry;
                    String oldName = gameUpdate.getName();

                    gameUpdate.setName(edit.getText().toString());
                    gameUpdate.setGameImage(imageToString(((BitmapDrawable)addImage.getDrawable()).getBitmap()));
                    gameUpdate.setNotes(edit2.getText().toString());
                    gameUpdate.setTagIDList(tagIDHolder);

                    for (int i = 0; i < gameNameList.getLength(); i++)
                    {
                        if (oldName.equals(gameNameList.getGameNameList().get(i)))
                        {
                            gameNameList.getGameNameList().add(gameUpdate.getName());
                            gameNameList.getGameNameList().remove(i);
                            break;
                        }
                    }

                    boolean updateFlag = dbHelper.updateGameData(gameUpdate);

                    if (updateFlag)
                    {
                        Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_SHORT).show();
                        open(MainActivity.class);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Update failed, data unchanged", Toast.LENGTH_SHORT).show();
                        open(MainActivity.class);
                    }
                }
            });
        }

    }

    @Override
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

    public String imageToString(Bitmap bm)
    {
        ByteArrayOutputStream  baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public void createrecycler()
    {
        //recylceview is here
        mCatalog = findViewById(R.id.recyclerViewtag);
        mCatalog.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new tagAdapter(this.TagObjectHolder);

        mCatalog.setLayoutManager(mLayout);
        mCatalog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                Tag removedTag = TagObjectHolder.remove(itemPos);
                tagIDHolder.remove(tagIDHolder.indexOf(removedTag.getID()));
                mAdapter.notifyDataSetChanged();
                Toast.makeText(Add_andor_edit.this, "Removed Tag", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void open(Class des)
    {
        Intent intent = new Intent(this, des);
        startActivity(intent);
    }

    public void openGame(Class des, Game game)
    {
        Intent intent = new Intent(this, des);
        intent.putExtra("Game", game);
        startActivity(intent);
    }
}
