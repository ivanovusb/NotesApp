package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notesapp.R;
import com.example.notesapp.di.Dependencies;
import com.example.notesapp.domain.Callback;
import com.example.notesapp.domain.FireStoreNotesRepository;
import com.example.notesapp.domain.InMemoryNotesRepository;
import com.example.notesapp.domain.Note;
import com.example.notesapp.domain.SharedPrefNotesRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class NoteCreationFragment extends Fragment {
    private EditText editTextLabel;
    private EditText editTextDetails;
    FloatingActionButton fabCreate;

    public static final String ADD_KEY_RESULT = "NoteCreationFragment_ADD_KEY_RESULT";
    public static final String ARG_NOTE = "ARG_NOTE";

    public static NoteCreationFragment addInstance() {
        return new NoteCreationFragment();
    }


    public NoteCreationFragment() {
        super(R.layout.fragment_note_creation);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextDetails = view.findViewById(R.id.note_creation_text);
        editTextLabel = view.findViewById(R.id.note_creation_label);
        fabCreate = view.findViewById(R.id.fab_create);


        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabCreate.setEnabled(false);
                Dependencies.getNotesRepository().addNote(editTextLabel.getText().toString(), editTextDetails.getText().toString(), new Callback<Note>() {
                    @Override
                    public void onSuccess(Note data) {

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(ARG_NOTE, data);

                        getParentFragmentManager()
                                .setFragmentResult(ADD_KEY_RESULT, bundle);


                        fabCreate.setEnabled(true);

                        getParentFragmentManager()
                                .popBackStack();
                    }

                    @Override
                    public void onError(Throwable exception) {
                        fabCreate.setEnabled(true);
                    }
                });


                Snackbar.make(view, R.string.note_created_snackbar, Snackbar.LENGTH_SHORT).show();
            }
        });
    }


}

