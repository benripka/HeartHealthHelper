package com.example.notessaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNoteActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "com.example.notessaver.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.notessaver.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.example.notessaver.EXTRA_PRIORITY";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker priorityPicker;
    private FloatingActionButton saveButton;
    private FloatingActionButton cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextTitle = findViewById(R.id.new_title);
        editTextTitle = findViewById(R.id.new_description);
        priorityPicker = findViewById(R.id.new_priority_picker);
        saveButton = findViewById(R.id.save_note_button);
        cancelButton = findViewById(R.id.cancel_button);

        priorityPicker.setMinValue(1);
        priorityPicker.setMaxValue(10);

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String description = editTextTitle.getText().toString();
        int priority = priorityPicker.getValue();
        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Field missing!", Toast.LENGTH_LONG).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();
    }
}
