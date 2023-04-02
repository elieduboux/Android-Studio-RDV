package com.example.projet;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    // Table Name
    public static final String TABLE_NAME = "RDV_LIST";

    // Table columns
    public static final String _ID   = "_id";
    public static final String TITLE = "title";
    public static final String MDATE = "date";
    public static final String STATE = "state";
    // Database Information
    static final String DB_NAME = "RdvList.DB";
    // database version
    static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE +
            " TEXT NOT NULL, " + MDATE + " TEXT, "+ STATE + " NUMBER(1));";

    // TODO: probably add "CONSTRAINT ck_testbool_ischk CHECK (is_checked IN (1,0))"

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void open() throws SQLException {
        database = this.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

//    public void add(...){
// ...
//    }
//    public int update(...) {
//…
//    }
//    public Cursor getAllMoments(){
//…
//    }
//    public void delete(...){
//...
//    }

}
