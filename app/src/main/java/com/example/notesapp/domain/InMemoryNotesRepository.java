package com.example.notesapp.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InMemoryNotesRepository implements NotesRepository{

    public static NotesRepository INSTANCE;

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
        result.add(new Note("Заметка 3", "детали..."));
        result.add(new Note("Заметка 4", "детали..."));
        result.add(new Note("Заметка 5", "детали..."));

        return result;
    }

    @Override
    public void add(Note note) {

    }
}
