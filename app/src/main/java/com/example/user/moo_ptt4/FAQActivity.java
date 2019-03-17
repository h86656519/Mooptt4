package com.example.user.moo_ptt4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FAQActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    CursorAdapter faqAdapter;
    ListView listFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        listFavorites = (ListView) findViewById(R.id.list_faq);
        setupFAQListView();
    }

    private void setupFAQListView() {
        updatelistview();
        listFavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Integer.valueOf(cursor.getString(1)); //取得item 裡的number 資料
                db.delete("FAQ", "NUMBER = " + Integer.valueOf(cursor.getString(1)), null);
//              更新一次listview
                updatelistview();

                Toast toast = Toast.makeText(FAQActivity.this, "刪除資料", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }

        });
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    //Close the cursor and database in the onDestroy() method
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //讀取全部的資料內容
    public void updatelistview() {
        try {
            SQLiteOpenHelper faqDatabaseHelper = new FAQDatabaseHelper(this);
            db = faqDatabaseHelper.getReadableDatabase();
            cursor = db.query("FAQ",
                    new String[]{"_id", "NUMBER", "ANSWER"}, // 抓特定欄位，用null表示抓全部
                    null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
//                Log.i("資料表欄位總數", ":" + cursor.getColumnCount());
//                Log.i("資料 _id", ":" + cursor.getString(0).toString()); 對應query第二個參數的陣列
//                Log.i("資料 NUMBER", ":" + cursor.getString(1).toString());
//                Log.i("資料 ANSWER", ":" + cursor.getString(2).toString());
                //查詢資料庫時 用 SimpleCursorAdapter
                faqAdapter = new SimpleCursorAdapter(
                        FAQActivity.this,
                        android.R.layout.simple_list_item_2,
                        cursor,
                        new String[]{"NUMBER", "ANSWER"}, //顯示資料內容
                        new int[]{android.R.id.text1, android.R.id.text2}, 0);
                listFavorites.setAdapter(faqAdapter);
                // faqAdapter.notifyDataSetChanged();
            } else {
                Toast toast = Toast.makeText(this, "資料讀取失敗", Toast.LENGTH_SHORT);
                toast.show();
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
