package com.example.notesapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notesapp.R;
import com.example.notesapp.domain.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NoteDetailsFragment extends Fragment {

    private TextView title;
    private EditText details;

    public static final String ARG_NOTE = "ARG_NOTE";

    public static NoteDetailsFragment newInstance(Note note) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NoteDetailsFragment() {
        super(R.layout.fragment_note_details);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = view.findViewById(R.id.title);
        details = view.findViewById(R.id.details);


        getParentFragmentManager()
                .setFragmentResultListener(NotesListFragment.NOTES_CLICKED_KEY, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(NotesListFragment.SELECTED_NOTE);

                        showNote(note);
                    }
                });

        if (getArguments() != null && getArguments().containsKey(ARG_NOTE)) {
            Note note = getArguments().getParcelable(ARG_NOTE);

            showNote(note);
        }
    }

    private void showNote(Note note) {
        title.setText(note.getTitle());
        details.setHint(note.getDetails());
    }

}
