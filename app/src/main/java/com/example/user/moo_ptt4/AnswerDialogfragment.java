package com.example.user.moo_ptt4;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

import user.Moo_ptt.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerDialogfragment extends DialogFragment {


    public AnswerDialogfragment() {
        // Required empty public constructor

    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_all_group, container, false);
//
//        return view;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_answer_dialogragment, null);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                //  builder.setView(inflater.inflate(R.layout.dialoglayout, null)) // 也能這樣寫
                // Add action buttons
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText number_editText = (EditText) view.findViewById(R.id.number_editext);
                        EditText answer_editText = (EditText) view.findViewById(R.id.answer_editext); //取得輸入的答案
                        String number_result = number_editText.getText().toString();
                        String answer_result = answer_editText.getText().toString();
//                        Log.i("SQLiteException","number_result " +number_result);
//                        Log.i("SQLiteException","answer_result " +answer_result);
//                        Toast toast = Toast.makeText(getActivity(), editText.getText(), Toast.LENGTH_SHORT);
//                        toast.show();
                        //將答案寫入db
                        SQLiteOpenHelper faqDatabaseHelper =
                                new FAQDatabaseHelper(getActivity());
                        try {
                            SQLiteDatabase db = faqDatabaseHelper.getWritableDatabase();
                            ContentValues resultedit = new ContentValues();
                            resultedit.put("NUMBER", number_result);
                            resultedit.put("ANSWER", answer_result);
                            db.insert("FAQ", null, resultedit);
                            Toast toast = Toast.makeText(getActivity(), "寫入成功", Toast.LENGTH_SHORT);
                            toast.show();
                            db.close();
                        } catch (SQLiteException e) {
//                            Log.i("SQLiteException","寫入失敗" + e.getMessage().toString());
                            Toast toast = Toast.makeText(getActivity(), "寫入失敗", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });

        return builder.create();
    }

}
