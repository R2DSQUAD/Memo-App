package com.example.sqlite_program.activity;

import static com.example.sqlite_program.optimization.CommandM.Tos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlite_program.MemoItem;
import com.example.sqlite_program.R;
import com.example.sqlite_program.adapter.CustomAdapter;
import com.example.sqlite_program.db.DBHelper;
import com.example.sqlite_program.optimization.BackPressHandler;
import com.example.sqlite_program.optimization.CommandM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main_Activity extends AppCompatActivity {

    EditText et_title;
    EditText et_content;
    Button btn_edit;
    Button btn_cancel;

    RecyclerView mRv_memo;
    FloatingActionButton mBtn_set;
    FloatingActionButton mBtn_write;
    ArrayList<MemoItem> mMemoItems;
    DBHelper mDBHelper;
    CustomAdapter mAdapter;

    private BackPressHandler backPressHandler = new BackPressHandler(this);

    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInit();
    }

    private void setInit() {
        mDBHelper = new DBHelper(this);
        mMemoItems = new ArrayList<>();
        mRv_memo = findViewById(R.id.rv_memo);
        mBtn_write = findViewById(R.id.btn_write);
        mBtn_set = findViewById(R.id.btn_setting_back);

        // DB불러오기
        loadRecentDB();

        mBtn_write.setOnClickListener(view -> {
            //새로운 창 띄우기
            setContentView(R.layout.activity_new);
            et_title = findViewById(R.id.et_title);
            et_content = findViewById(R.id.et_content);
            btn_cancel = findViewById(R.id.btn_back);
            btn_edit = findViewById(R.id.btn_edit);

            btn_cancel.setOnClickListener(view12 -> {
                Main_Activity.super.onBackPressed();
                CommandM.Change(Main_Activity.this,Main_Activity.class);
            });

            btn_edit.setOnClickListener(view1 -> {
                // Insert 데이터베이스
                String currentTime = new SimpleDateFormat("yyyy/MM/dd HH시 mm분 ss초").format(new Date()); //현재 시간 연-월-일 시:분:초 받아오기
                mDBHelper.InsertMemo(et_title.getText().toString(), et_content.getText().toString(), currentTime);
                loadRecentDB();

                //add Insert
                Tos(Main_Activity.this, "메모가 추가되었습니다");
                MemoItem item = new MemoItem();
                item.setTitle(et_title.getText().toString());
                item.setContent(et_content.getText().toString());
                item.setWriteDate(currentTime);
                CommandM.Change(Main_Activity.this,Main_Activity.class);
                mAdapter.addItem(item);
                mRv_memo.smoothScrollToPosition(0);
                finish();
            });
        });

        mBtn_set.setOnClickListener(view -> {
            setContentView(R.layout.activity_setting);
            CommandM.Change(Main_Activity.this,Setting_Activity.class);
            finish();
        });
    }

    public void loadRecentDB()
    {
        //저장된 DB를 가져온다
        mMemoItems = mDBHelper.getMemoList();
        if(mAdapter == null) {
            mAdapter = new CustomAdapter(mMemoItems, this);
            mRv_memo.setHasFixedSize(true);
            mRv_memo.setAdapter(mAdapter);
        }
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
        if (System.currentTimeMillis() > backKeyPressedTime + 1000) {
            backKeyPressedTime = System.currentTimeMillis();
            backPressHandler.onBackPressed("뒤로가기 버튼 한번 더 누르면 종료");
        }
        else {
            moveTaskToBack(true);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}