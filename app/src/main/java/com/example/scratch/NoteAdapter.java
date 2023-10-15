package com.example.scratch;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.scratch.Note;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    // Constructor for the NoteAdapter class
    public NoteAdapter(Context context, List<Note> notes) {
        // Call the parent class constructor
        super(context, 0, notes);
    }

    // Get a view that displays data at a specific position in the list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the Note object at the specified position
        Note note = getItem(position);

        // Check if the view is being recycled; if not, inflate the layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_cell, parent, false);
        }

        // Find the TextViews within the layout
        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView desc = convertView.findViewById(R.id.cellDesc);

        // Set the text for title and description from the Note object
        title.setText(note.getTitle());
        desc.setText(note.getDescription());

        // Set the background color of the view based on the note's color
        String color = note.getColor();
        if (color != null) {
            convertView.setBackgroundColor(Color.parseColor(color));
        }

        // Return the populated view for the specific list item
        return convertView;
    }
}
