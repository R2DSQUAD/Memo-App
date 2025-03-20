package com.example.sqlite_program.activity;

import static com.example.sqlite_program.optimization.CommandM.Tol;


import android.os.Bundle;
import android.widget.ImageButton;
import com.example.sqlite_program.optimization.CommandM;
import com.example.sqlite_program.optimization.PrefM;
import com.example.sqlite_program.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class old_password_Activity extends NewSetting_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_password);
        ButterKnife.bind(this);
        TRUE_CODE = PrefM.getString(PrefM.getPrivatePref(mContext),"pinnum","");
    }

    @OnClick({R.id.num0, R.id.num1, R.id.num2, R.id.num3, R.id.num4, R.id.num5, R.id.num6, R.id.num7, R.id.num8, R.id.num9})
    public void onClick(ImageButton imageButton) {
        getStringCode(imageButton.getId());
        if (codeString.length() == MAX_LENGHT) {
            if (codeString.equals(TRUE_CODE)) {
                setIsPass();
                CommandM.Change(old_password_Activity.this,NewSetting_Activity.class);
                finish();
            } else {
                Tol(this,"잘못된 암호 입니다");
                //vibrate the dots layout
                shakeAnimation();
            }
        } else if (codeString.length() > MAX_LENGHT){
            //reset the input code
            codeString = "";
            getStringCode(imageButton.getId());
        }
        setDotImagesState();
    }
}
