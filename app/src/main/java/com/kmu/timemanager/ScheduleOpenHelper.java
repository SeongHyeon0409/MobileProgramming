package com.kmu.timemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ScheduleOpenHelper extends SQLiteOpenHelper {

    Context context;

    public ScheduleOpenHelper(Context context) {
        super(context, "schedule.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table course (" +
                    "courseID int not null AUTO_INCREMENT primary key," +
                    "courseUniversity varchar(50)," +
                    "courseYear int," +
                    "courseArea varchar(50)," +
                    "courseMajor varchar(50)," +
                    "courseTitle varchar(100)," +
                    ");";

            db.execSQL(sql);
            Toast.makeText(context, "[schedule] 테이블 생성", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

