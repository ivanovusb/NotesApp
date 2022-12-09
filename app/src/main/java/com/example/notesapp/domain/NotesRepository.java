package com.example.notesapp.domain;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public interface NotesRepository {

    List<Note> getAll();

    void add(Note note);

}
