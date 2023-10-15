package com.example.scratch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.scratch.MainActivity;
import com.example.scratch.Note;
import com.example.scratch.R;
import com.example.scratch.SQLiteManager;

import java.util.Date;

public class NoteDetailActivity extends AppCompatActivity {
    private EditText titleEditText, descEditText;
    private Button saveNote, deleteButton;
    private RadioGroup colorRadioGroup;
    private Note selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        // Initialize UI elements
        saveNote = findViewById(R.id.saveNote);
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        initWidgets();

        // Check if it's an edit or a new note
        checkForEditNote();

        // Add a click listener for the "Save" button
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        // Add a change listener for the color selection RadioGroup
        colorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Handle the color selection
                handleColorSelection(checkedId);
            }
        });
    }

    private void handleColorSelection(int selectedColorId) {
        String color = null;

        if (selectedColorId == R.id.yellow) {
            color = "#e5eb34"; // Yellow color
        } else if (selectedColorId == R.id.blue) {
            color = "#3489eb"; // Blue color
        } else if (selectedColorId == R.id.green) {
            color = "#34eb3d"; // Green color
        }

        // You can perform actions based on the selected color if needed
    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        descEditText = findViewById(R.id.descriptionEditText);
        deleteButton = findViewById(R.id.deleteNoteButton);
    }

    private void checkForEditNote() {
        Intent previousIntent = getIntent();
        int passedNoteID = previousIntent.getIntExtra(Note.NOTE_EDIT_EXTRA, -1);
        selectedNote = Note.getNoteForID(passedNoteID);

        if (selectedNote != null) {
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDescription());
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isSaveNoteRunning = false; // Flag to track if the function is running

    public void saveNote() {
        if (isSaveNoteRunning) {
            return; // If the function is already running, exit
        }

        isSaveNoteRunning = true; // Set the flag to indicate the function is running
        saveNote.setEnabled(false); // Disable the "Save" button

        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = titleEditText.getText().toString().trim();
        String desc = descEditText.getText().toString();

        if (title.isEmpty()) {
            titleEditText.setError("Title cannot be empty");
            isSaveNoteRunning = false; // Reset the flag
            saveNote.setEnabled(true); // Re-enable the "Save" button
            return;
        }

        boolean isNewNote = selectedNote == null;

        if (isNewNote) {
            // Check if a note with the same title and description already exists
            boolean isDuplicate = false;
            for (Note note : Note.noteArrayList) {
                if (note.getTitle().equals(title) && note.getDescription().equals(desc)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (!isDuplicate) {
                // Creating a new note
                int id = Note.noteArrayList.size();
                Note newNote = new Note(id, title, desc);

                // Retrieve the selected color
                int selectedColorId = colorRadioGroup.getCheckedRadioButtonId();

                if (selectedColorId == R.id.yellow) {
                    newNote.setColor("#e5eb34"); // Yellow color
                } else if (selectedColorId == R.id.blue) {
                    newNote.setColor("#3489eb"); // Blue color
                } else if (selectedColorId == R.id.green) {
                    newNote.setColor("#34eb3d"); // Green color
                }

                Note.noteArrayList.add(newNote);
                sqLiteManager.addNoteToDatabase(newNote);
            }
        } else {
            // Editing an existing note
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            sqLiteManager.updateNoteInDB(selectedNote);
        }

        isSaveNoteRunning = false; // Reset the flag
        saveNote.setEnabled(true); // Re-enable the "Save" button

        Intent newNoteIntent = new Intent(this, MainActivity.class);
        startActivity(newNoteIntent);
    }




    public void deleteNote(View view) {
        selectedNote.setDeleted(new Date());
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.updateNoteInDB(selectedNote);
        finish();
    }
}
