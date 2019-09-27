package com.example.arcitectureexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.RecyclerViewItemDecorator;
import com.example.Utils;
import com.example.arcitectureexample.databinding.ActivityMainBinding;
import com.example.arcitectureexample.db.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.ItemClickListener {


    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    ActivityMainBinding mainBinding;
    private NoteAdapter noteAdapter;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.mainBinding.notesRecycler.setHasFixedSize(true);

        noteAdapter = new NoteAdapter();
        this.mainBinding.notesRecycler.setAdapter(noteAdapter);
        this.mainBinding.notesRecycler.addItemDecoration(new RecyclerViewItemDecorator(Utils.INSTANCE.convertDpToPx(this, 5), Utils.INSTANCE.convertDpToPx(this, 5), Utils.INSTANCE.convertDpToPx(this, 5), Utils.INSTANCE.convertDpToPx(this, 5)));
        noteAdapter.setListener(this);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                noteAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(this.mainBinding.notesRecycler);

    }


    public void onAddNoteClick(View view) {


        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String desc = data.getStringExtra(AddNoteActivity.EXTRA_DESC);
            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIPRITY, 0);

            Note note = new Note(title, desc, priority);

            noteViewModel.insert(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddNoteActivity.EXTRA_ID,-1);
            if (id == -1){

                Toast.makeText(this, "Note Can't Be Update", Toast.LENGTH_SHORT).show();

            }else  {


                String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
                String desc = data.getStringExtra(AddNoteActivity.EXTRA_DESC);
                int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIPRITY, 0);

                Note note = new Note(title, desc, priority);
                note.setId(id);

                noteViewModel.update(note);

                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

            }

        } else {

            Toast.makeText(this, "Note Note Saved", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.deleteAllNotes:

                noteViewModel.deleteAll();

                Toast.makeText(this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onItemClick(Note note) {

        Intent intent = new Intent(this, AddNoteActivity.class);
        intent.putExtra(AddNoteActivity.EXTRA_ID, note.getId());
        intent.putExtra(AddNoteActivity.EXTRA_TITLE, note.getTitle());
        intent.putExtra(AddNoteActivity.EXTRA_DESC, note.getDescription());
        intent.putExtra(AddNoteActivity.EXTRA_PRIPRITY, note.getPriority());
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
    }
}
