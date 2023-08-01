package com.example.login_register_sqlite_db.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }
    public Long insertRecord(String name,String address,String image,String email,String phone ){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        //insert Data
        contentValues.put("name",name);
        contentValues.put("address",address);
        contentValues.put("image",image);
        contentValues.put("email",email);
        contentValues.put("phone ", phone);

        long id=sqLiteDatabase.insert(Constants.TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return id;
    }
}
