package edu.uncc.itcs4180.myapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * from notes")
    List<Note> getAll();

    @Query("SELECT * from notes WHERE id = :id limit 1")
    Note findById(long id);

    @Update
    void update(Note note);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);
}
