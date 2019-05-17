package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener
{
    private ArrayList<Game> catalog;
    private RecyclerView mCatalog;
    private catalogAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    final DatabaseHelper dbHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createlist();
        createrecycler();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createlist()
    {
        //this is for the catalog

        GameNameList gameNameList = new GameNameList();

        String tableData = dbHelper.loadGameData();

        if (!tableData.equals(""))
        {
            String[] tableArray = tableData.split(";");

            for (int i = 0; i < tableArray.length; i++)
            {
                gameNameList.appendList(tableArray[i]);
            }
        }

        catalog = new ArrayList<>();

        for (int i = 0; i < gameNameList.getLength(); i++)
        {
            Game currentGame = dbHelper.fetchGameData(gameNameList.getGameNameList().get(i));
            catalog.add(currentGame);
        }
    }

    public void createrecycler()
    {
        //recycleview is here

        mCatalog = findViewById(R.id.recylceview);
        mCatalog.setHasFixedSize(true);
        mLayout = new LinearLayoutManager(this);
        mAdapter = new catalogAdapter(catalog);

        mCatalog.setLayoutManager(mLayout);
        mCatalog.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new catalogAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int itemPos)
            {
                openGame(GameView.class, catalog.get(itemPos));

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
        if(game != null)
        {
            game.setEdited(1);
        }
        intent.putExtra("Game", game);
        startActivity(intent);
    }

    public void openPopup(View v)
    {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.custom_menu);
        popupMenu.show();
    }

    public void openSearchPopup()
    {
        final AlertDialog.Builder searchPopupBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog searchPopup = searchPopupBuilder.create();

        LayoutInflater inflater = this.getLayoutInflater();
        final View search = inflater.inflate(R.layout.search_bar, null);

        ImageButton submit = search.findViewById(R.id.searchSubmit);
        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText query = search.findViewById(R.id.searchbar);
                createSearchResults(query.getText().toString());

                searchPopup.dismiss();
            }
        });

        searchPopup.setView(search);
        searchPopup.show();
    }

    public void createSearchResults(String query)
    {
        String tableData = dbHelper.loadGameData();
        Game currentGame;
        String name, notes;

        catalog = new ArrayList<>();

        if (!tableData.equals(""))
        {
            String[] tableArray = tableData.split(";");

            for (int i = 0; i < tableArray.length; i++)
            {
                currentGame = dbHelper.fetchGameData(tableArray[i]);
                name = currentGame.getName();
                notes = currentGame.getNotes();

                if(name.toUpperCase().contains(query.toUpperCase()) || notes.toUpperCase().contains(query.toUpperCase()))
                {
                    catalog.add(currentGame);
                }
            }
        }

        createrecycler();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //createlist();
        //createrecycler();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.new_game:
                openGame(Add_andor_edit.class, null);
                return true;

            case R.id.searchGame:
                openSearchPopup();
                return true;

            case R.id.home:
                open(TitleScreen.class);
                return true;

            case R.id.clear:
                Toast.makeText(this, "Cleared the catalog", Toast.LENGTH_SHORT).show();
                dbHelper.clearGameData();
                createlist();
                createrecycler();
                mAdapter.notifyDataSetChanged();
                return true;


            default:
                return false;
        }
    }
}
