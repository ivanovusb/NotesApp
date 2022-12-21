package com.example.notesapp.domain;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InMemoryNotesRepository implements NotesRepository {
    public static NotesRepository INSTANCE;

    private List<Note> data = new ArrayList<>();

    private Executor executor = Executors.newSingleThreadExecutor();

    private Handler handler = new Handler(Looper.getMainLooper());

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
    public void getAll(Callback<List<Note>> callback) {

//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
        callback.onSuccess(data);
//                    }
//                });
//            }
//        });
    }

    @Override
    public void addNote(String title, String details, Callback<Note> callback) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        Note note = new Note(UUID.randomUUID().toString(), title, details, new Date());

        data.add(note);

//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
        callback.onSuccess(note);

//                    }
//                });
//            }
//        });
    }

    @Override
    public void deleteNote(Note note, Callback<Void> callback) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        data.remove(note);

//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
        callback.onSuccess(null);
//                    }
//                });
//            }
//        });
    }

    @Override
    public void updateNote(Note note, String title, String details, Callback<Note> callback) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

        Note newNote = new Note(UUID.randomUUID().toString(), title, details, note.getCreatedDate());

        int index = data.indexOf(note);

        data.set(index, newNote);

//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
        callback.onSuccess(newNote);
//                    }
//                });
//            }
//        });
    }


}
