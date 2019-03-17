package com.example.user.moo_ptt4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnswerDialogfragment dialogFragment = new AnswerDialogfragment();
        dialogFragment.show(getSupportFragmentManager(),"AnswerDialogfragment");
    }

}
