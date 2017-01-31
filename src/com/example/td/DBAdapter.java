// Author:		Andrew Stebel
// Date:		Dec 8th, 2013
// Description:	Used to connect to a local database and pull data from it


package com.example.td;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBAdapter {
	static final String TAG = "DBAdapter";    
    static final String DATABASE_NAME = "GMI_DB";
    static final String DATABASE_TABLE_RATINGS = "GMI_ESG_RATINGS";
    static final int DATABASE_VERSION = 1;
    
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
//            try {
//                db.execSQL(DATABASE_CREATE);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
        }
     
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE_RATINGS);
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException  {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()  {
        DBHelper.close();
    }
    
    
    //---retrieves the global impact ratings of the company
    public Cursor getCompanyImpactRatings(String name) throws SQLException  {	
    	 Cursor mCursor = db.query(true, DATABASE_TABLE_RATINGS, new String[] {"governance_impact",
    			 "board_impact","pay_impact","ownership_impact","accounting_impact",
    			 "environmental_impact", "social_impact"}, 
    			 "company_legal_name" + "=\"" +name+"\"", //where clause
    			 null,null, null, null, null);
         if (mCursor != null) {
             mCursor.moveToFirst();
         }
         return mCursor;
    }
    
    //---retrieves the all the companies and their ratings
    public Cursor getAllCompanies() throws SQLException  {	
    	 Cursor mCursor = db.query(true, DATABASE_TABLE_RATINGS, new String[] {"company_legal_name","governance_impact",
    			 "board_impact","pay_impact","ownership_impact","accounting_impact",
    			 "environmental_impact", "social_impact"}, 
    			 null, null,null, null, null, null);
         if (mCursor != null) {
             mCursor.moveToFirst();
         }
         return mCursor;
    }
}