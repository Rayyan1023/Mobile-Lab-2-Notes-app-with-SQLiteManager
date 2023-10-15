package com.example.scratch;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    public static String NOTE_EDIT_EXTRA = "noteEdit";
    private int id;
    public static ArrayList<Note> noteArrayList = new ArrayList<>();
    private String title;
    private String description;
    private Date deleted;
    private String color; // Add a field to store the color

    public Note(int id, String title, String description, Date deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }

    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
    }

    public static Note getNoteForID(int passedNoteID) {
        for(Note note: noteArrayList){
            if(note.getId() == passedNoteID)
                return note;
        }
        return null;
    }

    public static ArrayList<Note> nonDeletedNotes (){
        ArrayList<Note> nonDeleted = new ArrayList<>();
        for(Note note: noteArrayList){
            if(note.getDeleted() == null)
                nonDeleted.add(note);
        }
        return nonDeleted;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }



}

