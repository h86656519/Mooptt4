package com.example.user.moo_ptt4;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FAQDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "FAQ"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    FAQDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private static void insertFAQ(SQLiteDatabase db, String number, String answer) {
        ContentValues faqValues = new ContentValues();
        faqValues.put("NUMBER", number);
        faqValues.put("ANSWER", answer);
        db.insert("FAQ", null, faqValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE FAQ" +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NUMBER TEXT, "
                    + "ANSWER TEXT );");
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE FAQ ADD COLUMN FAVORITE NUMERIC;");
        }
    }
}
