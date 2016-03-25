package com.example.user.projectcoordinatesdb2;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.sql.SQLDataException;

/**
 * Created by user on 2016-03-25.
 */
class dbHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="CoordinatesDB.db";
    private static final int DATABASE_VERSION=1;

    public dbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public  void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE contact ( id TEXT PRIMARY KEY, " + "latitude TEXT, longitude TEXT);");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS contact");
        onCreate(db);
    }
}
public class CoordinateDB extends Activity{
    dbHelper helper;
    SQLiteDatabase db;
    EditText edit_id, edit_latitude, edit_longitude;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate);

        helper=new dbHelper(this);
        try{
            db=helper.getWritableDatabase();
        }catch (SQLiteException ex){
            db=helper.getReadableDatabase();
        }
        edit_id=(EditText)findViewById(R.id.id);
        edit_latitude=(EditText)findViewById(R.id.latitude);
        edit_longitude=(EditText)findViewById(R.id.longitude);
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String id=edit_id.getText().toString();
                String latitude=edit_latitude.getText().toString();
                String longitude=edit_longitude.getText().toString();
                db.execSQL("INSERT INTO contact VALUES (null, '"+id+"','"+latitude+"','"
                +longitude+"');");
            }
        });
        findViewById(R.id.query).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String id=edit_id.getText().toString();
                Cursor cursor;
                cursor=db.rawQuery("SELECT latitude, longitude FROM contact WHERE id='"+id+"';", null);

                while(cursor.moveToNext()){
                    String latitude=cursor.getString(1);
                    edit_latitude.setText(latitude);
                    String longitude=cursor.getString(2);
                    edit_longitude.setText(longitude);
                }
            }
        });


    }
}
