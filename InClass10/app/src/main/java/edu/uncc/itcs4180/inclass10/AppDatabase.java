package edu.uncc.itcs4180.inclass10;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Course.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CourseDao courseDao();
}
