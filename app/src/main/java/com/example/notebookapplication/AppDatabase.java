package com.example.notebookapplication;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.notebookapplication.database.Note;
import com.example.notebookapplication.database.NoteDao;

//TODO:5 ser schema
@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mAppDatabase;

    public abstract NoteDao getNoteDao();

    public static AppDatabase getInstance(Context context){
        if (mAppDatabase==null){
            mAppDatabase = Room.databaseBuilder(context,AppDatabase.class,"db_notebook")
                                //TODO:2 remove allow main thread , do it on background thread
                                .allowMainThreadQueries()
                                .build();
        }

        return mAppDatabase;
    }
}
