package com.example.sqlite_program.activity;

import static com.example.sqlite_program.optimization.CommandM.Tos;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite_program.MemoItem;
import com.example.sqlite_program.R;
import com.example.sqlite_program.db.DBHelper;
import com.example.sqlite_program.optimization.CommandM;

import java.text.SimpleDateFormat;
import java.util.Date;

public class New_Activity extends AppCompatActivity {


    int id_v;
    EditText et_title, et_content;
    Button btn_edit, btn_cancel;

    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        btn_cancel = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);

        int id = -1;
        MemoItem memo = (MemoItem) getIntent().getSerializableExtra(DBHelper.NOTE_INTENT);

        if (getIntent() != null) {

            id = getIntent().getIntExtra("ID", 0);
        }

        if (id > 0) {

            id_v = memo.getId();

            String title = memo.getTitle();
            et_title.setText(title);

            String content = memo.getContent();
            et_content.setText(content);
        }


        btn_cancel = findViewById(R.id.btn_back);
        btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(view -> {
            //update
            String title = et_title.getText().toString();
            String content = et_content.getText().toString();
            String currentTime = new SimpleDateFormat("yyyy/MM/dd HH시 mm분 ss초").format(new Date()); //현재 시간 연-월-일 시:분:초 받아오기

            String beforeTime = memo.getWriteDate();
            mDBHelper= new DBHelper(New_Activity.this);

            mDBHelper.UpdateMemo(title, content, currentTime, beforeTime);

            memo.setTitle(et_title.getText().toString());
            memo.setContent(et_content.getText().toString());
            memo.setWriteDate(currentTime);
            Tos(New_Activity.this, "메모가 수정되었습니다!");
            CommandM.Change(New_Activity.this,Main_Activity.class);
        });

        btn_cancel.setOnClickListener(view -> {
            New_Activity.super.onBackPressed();
            CommandM.Change(New_Activity.this,Main_Activity.class);
            finish();
        });
    }


    //다른 화면 터치시 키보드가 내려가는 구문
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {

    }
}