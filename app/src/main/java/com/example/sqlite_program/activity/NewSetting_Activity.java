package com.example.sqlite_program.activity;

import static com.example.sqlite_program.optimization.CommandM.Tol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite_program.MemoItem;
import com.example.sqlite_program.db.DBHelper;
import com.example.sqlite_program.optimization.CommandM;
import com.example.sqlite_program.optimization.PrefM;
import com.example.sqlite_program.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewSetting_Activity extends AppCompatActivity {

    Context mContext;
    public static String TRUE_CODE;

    //변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_set);
        ButterKnife.bind(this);
        mContext = this;


    }

    @BindViews({R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9})
    List<View> btnNumPads;

    @BindViews({R.id.input1, R.id.input2, R.id.input3, R.id.input4})
    List<ImageView> dots;

    int MAX_LENGHT = 4;
    String codeString = "";

    @OnClick(R.id.cancel_set)
    public void onClear(){
        if (codeString.length() > 0) {
            codeString = removeLastChar(codeString);

            setDotImagesState();
        }
    }

    @OnClick({R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9})
    public void onClick(ImageButton imageButton) {
        getStringCode(imageButton.getId());
        //입력한 pin번호의 길이가 최대 4의 길이와 같을 때
        if (codeString.length() != MAX_LENGHT) {
            if (codeString.length() > MAX_LENGHT) {
            //reset the input code
            codeString = "";
            getStringCode(imageButton.getId());
        }
        } else {
            PrefM.setString(PrefM.getPrivatePref(mContext), "pinnum", codeString);

            if(getIntent().getBooleanExtra("Tutorial",false)){
                codeString = "";
                PrefM.setBoolean(PrefM.getPrivatePref(mContext), "checkFirst", true);
            }
            else {
                Tol(this, "암호가 설정되었습니다.");
            }

            CommandM.Change(NewSetting_Activity.this, Main_Activity.class);
            overridePendingTransition(0, 0);
            finish();
        }
        setDotImagesState();
    }

    public void getStringCode(int buttonId) {
        switch (buttonId) {
            case R.id.num0:
                codeString += "0";
                break;
            case R.id.num1:
                codeString += "1";
                break;
            case R.id.num2:
                codeString += "2";
                break;
            case R.id.num3:
                codeString += "3";
                break;
            case R.id.num4:
                codeString += "4";
                break;
            case R.id.num5:
                codeString += "5";
                break;
            case R.id.num6:
                codeString += "6";
                break;
            case R.id.num7:
                codeString += "7";
                break;
            case R.id.num8:
                codeString += "8";
                break;
            case R.id.num9:
                codeString += "9";
                break;
        }
    }

    public void setDotImagesState() {
        for (int i = 0; i < codeString.length(); i++) {
            dots.get(i).setImageResource(R.drawable.dots_enable);
        }
        if (codeString.length()<4) {
            for (int j = codeString.length(); j<4; j++) {
                dots.get(j).setImageResource(R.drawable.dots_disable);
            }
        }
    }

    public String removeLastChar(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length() - 1);
    }

    public void setIsPass() {
        PrefM.setBoolean(PrefM.getPrivatePref(mContext),"is_pass",true);
    }

    public void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_anim);
        findViewById(R.id.dot_layout).startAnimation(shake);
        Toast.makeText(this, "잘못된 암호 입니다", Toast.LENGTH_SHORT).show();
    }
}
