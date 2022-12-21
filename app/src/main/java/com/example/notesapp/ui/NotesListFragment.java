package com.example.notesapp.ui;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.di.Dependencies;
import com.example.notesapp.domain.Callback;
import com.example.notesapp.domain.FireStoreNotesRepository;
import com.example.notesapp.domain.InMemoryNotesRepository;
import com.example.notesapp.domain.Note;
import com.example.notesapp.domain.SharedPrefNotesRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesListFragment extends Fragment {

    public static final String NOTES_CLICKED_KEY = "NOTES_CLICKED_KEY";
    public static final String SELECTED_NOTE = "SELECTED_NOTE";
    public static final String DISPLAY_MODE = "DISPLAY_MODE";
    private int index;
    Bundle bundle;
    private Note selectedNote;
    private int selectedPosition;
    private NotesAdapter adapter;
    private int displayMode = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notesList = view.findViewById(R.id.notes_list);
        adapter = new NotesAdapter(this);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(3000L);
        defaultItemAnimator.setRemoveDuration(3000L);
        notesList.setItemAnimator(defaultItemAnimator);

        adapter.setNoteClicked(new NotesAdapter.OnNoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, NoteDetailsFragment.editInstance(note))
                            .addToBackStack("details")
                            .commit();
            }

            @Override
            public void onNoteLongClicked(Note note, int position) {
                selectedNote = note;
                selectedPosition = position;
            }
        });


        if (bundle != null) {
            displayMode = bundle.getInt(DISPLAY_MODE);
            switch (displayMode) {
                case 1:
                    notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                    break;
                case 0:
                    notesList.setLayoutManager(new LinearLayoutManager(requireContext()));
                    break;
            }
        } else {
            notesList.setLayoutManager(new LinearLayoutManager(requireContext()));
        }


        Toolbar toolbar = view.findViewById(R.id.toolbar_notes_list);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_display_mode:
                        switch (displayMode) {
                            case 0:
                                notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                                item.setIcon(R.drawable.ic_notes_display_view_list_24);
                                displayMode = 1;
                                return true;

                            case 1:
                                notesList.setLayoutManager(new LinearLayoutManager(requireContext()));
                                item.setIcon(R.drawable.ic_notes_display_view_block_24);
                                displayMode = 0;
                                return true;
                        }

                        return true;
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


        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putInt(DISPLAY_MODE, displayMode);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new NoteCreationFragment())
                            .addToBackStack("list")
                            .commit();
            }
        });


        notesList.setAdapter(adapter);

        getParentFragmentManager()
                .setFragmentResultListener(NoteCreationFragment.ADD_KEY_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        Note note = result.getParcelable(NoteCreationFragment.ARG_NOTE);

//                index = adapter.addNote(note);
//                adapter.notifyItemInserted(index);

                    }
                });

        ProgressBar progressBar = view.findViewById(R.id.progress_load_notes);
        progressBar.setVisibility(View.VISIBLE);

        Dependencies.getNotesRepository().getAll(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable exception) {

            }
        });


        MainActivity.onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_note_item_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_delete_note_context:

                Dependencies.getNotesRepository().deleteNote(selectedNote, new Callback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        new AlertDialog.Builder(requireContext())
                                .setTitle(getResources().getString(R.string.delete_dialog_title))
                                .setMessage(getResources().getString(R.string.delete_dialog_message))
                                .setIcon(R.drawable.ic_error_24)
                                .setCancelable(false)
                                .setPositiveButton(R.string.positive_answer, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        adapter.removeNote(selectedNote);
                                        adapter.notifyItemRemoved(selectedPosition);
                                    }
                                })
                                .setNegativeButton(R.string.negative_answer, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .show();
                        Snackbar.make(requireView(), R.string.note_delete_snackbar, Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable exception) {

                    }
                });

                return true;
        }

        return super.onContextItemSelected(item);


    }
}
