package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Add_andor_edit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_andor_edit);

        Button submit = findViewById(R.id.editSub);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open(MainActivity.class);
            }
        });

    }

    public void open(Class des){
        Intent intent = new Intent(this,des);
        startActivity(intent);
    }
}
