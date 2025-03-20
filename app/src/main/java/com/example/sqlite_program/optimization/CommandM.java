package com.example.sqlite_program.optimization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CommandM {
    public static void Change (Activity activity, Class c){
        Intent intent = new Intent(activity, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    public static void Change_ (Context context, Class c){
        Intent intent = new Intent(context, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void Tos (Context context, String text) {
        Toast.makeText(context, (CharSequence) text,Toast.LENGTH_SHORT).show();
    }

    public static void Tol (Context context, String text) {
        Toast.makeText(context, (CharSequence) text,Toast.LENGTH_LONG).show();
    }

}



