package com.example.studentscoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentscoremanagement.Model.Lop;

import java.util.ArrayList;

public class ChooseClassActivity extends AppCompatActivity {
    ArrayList<String> dataString = new ArrayList<>();

    Button btnConfirm;
    Spinner spnClass;
    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class);
        
        addControl();
        setEvent();
    }

    private void setEvent() {
        Initialize();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataString);
        spnClass.setAdapter(adapter);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseClassActivity.this, "Lớp: "+ spnClass.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Initialize() {
        database=new DBHelper(this);
        ArrayList<String> classes = Lop.getAllClassId(database);
        for (int i = 0; i < classes.size(); i++) {
            dataString.add(classes.get(i));
        }
    }

    private void addControl() {
        btnConfirm = findViewById(R.id.btnConfirm);
        spnClass = findViewById(R.id.spnClass);
    }
}