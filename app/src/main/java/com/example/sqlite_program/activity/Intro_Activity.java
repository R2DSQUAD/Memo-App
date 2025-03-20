package com.example.sqlite_program.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_program.optimization.CommandM;
import com.example.sqlite_program.optimization.PrefM;
import com.example.sqlite_program.R;

public class Intro_Activity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mContext = this;

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            boolean checkFirst = PrefM.getBoolean(PrefM.getPrivatePref(mContext), "checkFirst",false);
            if(!checkFirst){
                CommandM.Change(this,Tutorial_Activity.class);
            }else{
                boolean isSwitchChecked = PrefM.getBoolean(PrefM.getDefaultPref(mContext),"on_off_password",true);
                 //켜져있음
                if(isSwitchChecked){
                    CommandM.Change(this,Password_Activity.class);
                } else {
                    CommandM.Change(this,Main_Activity.class);
                }
            }

        },3000);
    }


    @Override
    public void onBackPressed() {

    }
}
