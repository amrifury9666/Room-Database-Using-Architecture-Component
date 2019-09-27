package com.example.arcitectureexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.arcitectureexample.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {

    public static String EXTRA_ID = "EXTRA_ID";
    public static String EXTRA_TITLE = "EXTRA_TITLE";
    public static String EXTRA_DESC = "EXTRA_DESC";
    public static String EXTRA_PRIPRITY = "EXTRA_PRIORITY";

    ActivityAddNoteBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_note);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Note");

            this.mBinding.titleEt.setText(intent.getStringExtra(EXTRA_TITLE));
            this.mBinding.descEt.setText(intent.getStringExtra(EXTRA_DESC));
            this.mBinding.numberPicker.setValue(intent.getIntExtra(EXTRA_PRIPRITY,1));

        }else  {

            setTitle("Add Note");
        }

        mBinding.numberPicker.setMinValue(1);
        mBinding.numberPicker.setMaxValue(10);


    }


    private void saveNote() {
        String titile = mBinding.titleEt.getText().toString();
        String desc = mBinding.descEt.getText().toString();
        int priority = mBinding.numberPicker.getValue();

        if (titile.trim().isEmpty() || desc.trim().isEmpty()){

            Toast.makeText(this, "Please Enter A Proper Title And Description", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE,titile);
        intent.putExtra(EXTRA_DESC,desc);
        intent.putExtra(EXTRA_PRIPRITY,priority);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1){

            intent.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,intent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_save_note, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.saveNote:

                saveNote();
                return true;
            default:


                return super.onOptionsItemSelected(item);


        }

    }


}
