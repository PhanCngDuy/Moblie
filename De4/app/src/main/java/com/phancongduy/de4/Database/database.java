package com.phancongduy.de4.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {

    public static final String DB_NAME = "db.sqlite";
    public static final int VERSION = 1;
    public static final String TBL_NAME = "C2";
    public static final String COL_CODE = "colCode";
    public static final String COL_NAME =  "colName";
    public static final String COL_PRICE =  "colPrice";
    public database(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " ( " + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " NVARCHAR(50), " + COL_PRICE + " REAL) ";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TBL_NAME;

        db.execSQL(sql);
        onCreate(db);
    }
    public Cursor getData(String sql){
        try{
            SQLiteDatabase db = getReadableDatabase();
            return db.rawQuery(sql, null);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }


    public boolean execSql(String sql){
        try{
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public int getNumberOfRows(){
        Cursor cursor = getData("SELECT * FROM " + TBL_NAME);
        int numberOfRows = cursor.getCount();
        cursor.close();

        return numberOfRows;
    }

    public void createSampleData(){
        if(getNumberOfRows() == 0){
            try {
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Thuốc trừ sâu', 10000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Thuốc diệt bug', 10000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Bỉ chấp 0.5', 500000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Pháp chấp 1.5', 200000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Thuốc abc 1.5', 210000)");
                execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Thuốc xyz 2.5', 210400)");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
