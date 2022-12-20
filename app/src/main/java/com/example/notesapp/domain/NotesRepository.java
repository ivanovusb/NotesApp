package com.example.notesapp.domain;

import com.example.notesapp.ui.NoteCreationFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public interface NotesRepository {

    void getAll(Callback<List<Note>> callback);

    void addNote(String title, String details, Callback<Note> callback);

    void deleteNote(Note note, Callback<Void> callback);

    void updateNote(Note note, String title, String details, Callback<Note> callback);

}
