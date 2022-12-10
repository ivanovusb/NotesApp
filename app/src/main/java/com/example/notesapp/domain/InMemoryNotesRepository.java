package com.example.notesapp.domain;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.notesapp.ui.MainActivity;
import com.example.notesapp.ui.NoteCreationFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository{
    public static NotesRepository INSTANCE;
    NoteCreationFragment noteCreationFragment;


    private Context context;

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryNotesRepository(context);
        }
        return INSTANCE;
    }


    private InMemoryNotesRepository(Context context) {
        this.context = context;
    }


    @Override
    public List<Note> getAll() {
        ArrayList<Note> result = new ArrayList<>();

        result.add(new Note("Заметка 1", "детали..."));
        result.add(new Note("Заметка 2", "детали..."));


        return result;
    }



    @Override
    public void add(Note note) {

    }

}
