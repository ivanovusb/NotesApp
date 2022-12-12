package com.example.notesapp.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.notesapp.R;
import com.example.notesapp.domain.InMemoryNotesRepository;
import com.example.notesapp.domain.Note;
import com.example.notesapp.domain.NoteCreateRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NotesListFragment extends Fragment {

    public static final String NOTES_CLICKED_KEY = "NOTES_CLICKED_KEY";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";
    private FloatingActionButton fabAdd;
    List<Note> notes;
    NoteCreationFragment noteCreationFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar_notes_list);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        Toast.makeText(requireContext(), "SEARCH", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.action_sort:
                        Toast.makeText(requireContext(), "SORT", Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });

        fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, noteCreationFragment = new NoteCreationFragment())
                        .addToBackStack("")
                        .commit();
            }
        });



            getParentFragmentManager()
                    .setFragmentResultListener("newNote", getViewLifecycleOwner(), new FragmentResultListener() {
                        @Override
                        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                            Note note = new Note(result.getString("title"), result.getString("details"));
                            notes.add(note);
                        }
                    });


            notes = InMemoryNotesRepository.getInstance(requireContext()).getAll();


        LinearLayout container = view.findViewById(R.id.container);


        for (Note note : notes) {
            View itemView = getLayoutInflater().inflate(R.layout.item_note, container, false);

            itemView.findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(SELECTED_NOTE, note);
                        getParentFragmentManager()
                                .setFragmentResult(NOTES_CLICKED_KEY, bundle);
                    } else {
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, NoteDetailsFragment.newInstance(note))
                                .addToBackStack("details")
                                .commit();
                    }
                }
            });

            TextView title = itemView.findViewById(R.id.title);
            title.setText(note.getTitle());
            TextView details = itemView.findViewById(R.id.details);
            details.setText(note.getDetails());

            container.addView(itemView);


        }

    }
}
