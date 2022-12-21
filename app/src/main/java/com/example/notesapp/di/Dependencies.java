package com.example.notesapp.di;


import com.example.notesapp.domain.FireStoreNotesRepository;
import com.example.notesapp.domain.NotesRepository;

public class Dependencies {

    private static final NotesRepository NOTES_REPOSITORY = new FireStoreNotesRepository();

    public static NotesRepository getNotesRepository() {
        return NOTES_REPOSITORY;
    }
}
