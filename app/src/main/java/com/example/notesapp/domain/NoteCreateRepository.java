package com.example.notesapp.domain;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.example.notesapp.ui.NoteCreationFragment;

import java.util.ArrayList;
import java.util.List;

public class NoteCreateRepository implements NotesRepository{

    public static NotesRepository INSTANCE;
    NoteCreationFragment noteCreationFragment;


    private Context context;

    public static NotesRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NoteCreateRepository(context);
        }
        return INSTANCE;
    }

    private NoteCreateRepository(Context context) {
        this.context = context;
    }


    @Override
    public void add(Note note) {
    }

    @Override
    public List<Note> getAll() {
        return null;
    }




}
