package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.domain.InMemoryNotesRepository;
import com.example.notesapp.domain.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class NoteCreationFragment extends Fragment {
    private EditText editTextLabel;
    private EditText editTextDetails;
    FloatingActionButton fabCreate;



    public NoteCreationFragment() {
        super(R.layout.fragment_note_creation);
    }


    public EditText getEditTextLabel() {
        return editTextLabel;
    }

    public EditText getEditTextDetails() {
        return editTextDetails;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextDetails = view.findViewById(R.id.note_creation_text);
        editTextLabel = view.findViewById(R.id.note_creation_label);
        fabCreate = view.findViewById(R.id.fab_create);


        }







    }

