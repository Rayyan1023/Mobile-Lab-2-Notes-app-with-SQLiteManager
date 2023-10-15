package com.example.scratch;

import java.util.ArrayList;
import java.util.Date;

public class Note {
    // Key for passing the note ID as an extra in an Intent
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

    // Constructor for non-deleted notes (without the deleted date)
    public Note(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        deleted = null;
    }

    // Get a note by its ID
    public static Note getNoteForID(int passedNoteID) {
        for (Note note : noteArrayList) {
            if (note.getId() == passedNoteID) {
                return note;
            }
        }
        return null; // If the note with the given ID is not found
    }

    // Get a list of non-deleted notes
    public static ArrayList<Note> nonDeletedNotes() {
        ArrayList<Note> nonDeleted = new ArrayList<>();
        for (Note note : noteArrayList) {
            if (note.getDeleted() == null) {
                nonDeleted.add(note);
            }
        }
        return nonDeleted;
    }

    // Getter and setter for the note's color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Getters and setters for other fields
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
