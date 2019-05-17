package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class TitleScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        CardView gamesCard = findViewById(R.id.gamesCard);
        gamesCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open(MainActivity.class);
            }
        });

        CardView playersCard = findViewById(R.id.playersCard);
        playersCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open(playerpage.class);
            }
        });

        CardView recommendCard = findViewById(R.id.recommendCard);
        recommendCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO - recommend page
            }
        });
    }

    public void open(Class des)
    {
        Intent intent = new Intent(this, des);
        startActivity(intent);
    }
}


