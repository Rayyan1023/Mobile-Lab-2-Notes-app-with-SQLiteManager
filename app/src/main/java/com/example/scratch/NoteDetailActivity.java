package com.example.scratch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText titleEditText, descEditText;
    private Button saveNote;
    private Button deleteButton;
    private RadioGroup colorRadioGroup;
    private Note selectedNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        saveNote = findViewById(R.id.saveNote);
        colorRadioGroup = findViewById(R.id.colorRadioGroup);
        initWidgets();

        checkForEditNote();

        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        // Add an OnClickListener for the radio group
        colorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                // Handle the color selection here
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

        if (color != null) {
            // Do something with the selected color if needed
        }
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

        if(selectedNote != null) {
            titleEditText.setText(selectedNote.getTitle());
            descEditText.setText(selectedNote.getDescription());
        }
        else{
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveNote() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        String title = titleEditText.getText().toString().trim();
        String desc = descEditText.getText().toString();

        if (title.isEmpty()) {
            titleEditText.setError("Title cannot be empty");
            return;
        }

        if (selectedNote == null) {
            // Creating a new note
            int id = Note.noteArrayList.size();
            Note newNote = new Note(id, title, desc);

            RadioGroup colorRadioGroup = findViewById(R.id.colorRadioGroup);
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
        } else {
            // Editing an existing note
            selectedNote.setTitle(title);
            selectedNote.setDescription(desc);
            sqLiteManager.updateNoteInDB(selectedNote);
        }

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