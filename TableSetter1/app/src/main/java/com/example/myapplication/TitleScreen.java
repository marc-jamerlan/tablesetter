package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TitleScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        Button gamesButton = findViewById(R.id.gamesButton);
        gamesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open(MainActivity.class);
            }
        });
    }

    public void open(Class des)
    {
        Intent intent = new Intent(this, des);
        startActivity(intent);
    }
}


