package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        //Button submit = findViewById(R.id.button);
        //Button Delete = findViewById(R.id.button2);

    }

    public void open(Class des){
        Intent intent = new Intent(this,des);
        startActivity(intent);
    }

    public void closePopup(){
        open(MainActivity.class);
        closePopup();
    }
}
