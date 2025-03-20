package com.example.sqlite_program.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sqlite_program.R;
import com.example.sqlite_program.optimization.CommandM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Setting_Activity extends AppCompatActivity {

    FloatingActionButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        btn_back = findViewById(R.id.btn_setting_back);

        btn_back.setOnClickListener(view -> CommandM.Change(Setting_Activity.this,Main_Activity.class));
    }
    @Override
    public void onBackPressed() {

    }


}
