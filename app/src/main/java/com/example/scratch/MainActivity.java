package com.example.scratch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ListView noteListView;
    private SearchView searchView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> filteredNotes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        loadFromDbToMemory();
        setNoteAdapter();
        setOnClickListener();
        setupSearchView();
    }

    private void initWidgets() {
        noteListView = findViewById(R.id.noteListView);
        searchView = findViewById(R.id.searchView);
    }

    private void loadFromDbToMemory() {
        SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(this);
        sqLiteManager.populateNoteListArray();
    }

    private void setNoteAdapter() {
        noteAdapter = new NoteAdapter(getApplicationContext(), Note.nonDeletedNotes());
        noteListView.setAdapter(noteAdapter);
    }

    private void setOnClickListener() {
        noteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = (Note) noteListView.getItemAtPosition(position);
                Intent editNoteIntent = new Intent(getApplicationContext(), NoteDetailActivity.class);
                editNoteIntent.putExtra(Note.NOTE_EDIT_EXTRA, selectedNote.getId());
                startActivity(editNoteIntent);
            }
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the notes based on the search query
                filterNotes(newText);
                return true;
            }
        });
    }

    private void filterNotes(String query) {
        filteredNotes.clear();
        for (Note note : Note.nonDeletedNotes()) {
            if (note.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(note);
            }
        }
        noteAdapter = new NoteAdapter(getApplicationContext(), filteredNotes);
        noteListView.setAdapter(noteAdapter);
    }

    public void newNote(View view) {
        Intent newNoteIntent = new Intent(this, NoteDetailActivity.class);
        startActivity(newNoteIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setNoteAdapter();
    }
}
