package com.example.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;

import java.util.ArrayList;

public class Add_andor_edit extends AppCompatActivity
{
    private static int RESULT_LOAD_IMAGE = 1;
    private RecyclerView mCatolog;
    private tagAdapter mAdapter;
    private RecyclerView.LayoutManager mlayout;
    private Game gameEntry;
    private ArrayList<Tags> listoftags;
    private ArrayList<Tags> holdingtags;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_andor_edit);

        final EditText edit = (EditText) findViewById(R.id.editText);
        final EditText edit2 = (EditText) findViewById(R.id.editText2);
        final GameNameList gameNameList = (GameNameList) getApplicationContext();
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        ImageButton addImage = (ImageButton) findViewById(R.id.addImage);

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
        this.listoftags = intent.getParcelableArrayListExtra("Tags");

        if(gameEntry == null){
            holdingtags = new ArrayList<>();
        } else {
            holdingtags = gameEntry.getTagArray();
        }

        createrecycler();

        Button submit = findViewById(R.id.editSub);

        Button addTag = findViewById(R.id.addTags);

        addTag.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openGame(Tag_Add.class, gameEntry);
            }
        });

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
                    newGame.setNotes(edit2.getText().toString());
                    newGame.setTagArray(holdingtags);
                    newGame.setEdited(1);

                    dbHelper.addGameData(newGame);

                    Toast.makeText(getApplicationContext(), "Game added", Toast.LENGTH_SHORT).show();

                    open(MainActivity.class);
                }
            });
        }

        // update game info
        else
        {
            edit.setText(this.gameEntry.getName());
            edit2.setText(this.gameEntry.getNotes());

            submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Game gameUpdate = getGameEntry();
                    String oldName = gameUpdate.getName();

                    gameUpdate.setName(edit.getText().toString());
                    gameUpdate.setNotes(edit2.getText().toString());
                    gameUpdate.setTagArray(holdingtags);

                    for (int i = 0; i < gameNameList.getLength(); i++)
                    {
                        if (oldName.equals(gameNameList.getGameNameList().get(i)))
                        {
                            gameNameList.getGameNameList().add(gameUpdate.getName());
                            gameNameList.getGameNameList().remove(i);
                            break;
                        }
                    }

                    boolean updateFlag = dbHelper.updateGameData(gameUpdate.getID(),
                            gameUpdate.getName(),
                            gameUpdate.getGameImage(),
                            gameUpdate.getNotes());

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
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageButton addImage = (ImageButton) findViewById(R.id.addImage);

            Bitmap gameImage = BitmapFactory.decodeFile(picturePath);

            //TODO - figure out how to show image

            addImage.setImageBitmap(gameImage);
        }
    }


    public void createrecycler()
    {
        //recylceview is here
        mCatolog = findViewById(R.id.recyclerViewtag);
        mCatolog.setHasFixedSize(true);
        mlayout = new LinearLayoutManager(this);
        mAdapter = new tagAdapter(holdingtags);

        mCatolog.setLayoutManager(mlayout);
        mCatolog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                holdingtags.remove(itemPos);
                mAdapter.notifyDataSetChanged();

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
        //intent.putExtra("Tags",this.listoftags);
        intent.putExtra("Game", game);
        startActivity(intent);
    }


    public Game getGameEntry()
    {
        return gameEntry;
    }
}
