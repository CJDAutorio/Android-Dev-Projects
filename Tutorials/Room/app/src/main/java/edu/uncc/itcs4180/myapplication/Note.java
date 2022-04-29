package edu.uncc.itcs4180.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String subject;

    @ColumnInfo
    public String note;

    public Note(long id, String subject, String note) {
        this.id = id;
        this.subject = subject;
        this.note = note;
    }

    public Note(String subject, String note) {
        this.subject = subject;
        this.note = note;
    }

    public Note() {
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
