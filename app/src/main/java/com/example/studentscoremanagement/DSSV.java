package com.example.studentscoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.studentscoremanagement.Model.AdapterHocSinh;
import com.example.studentscoremanagement.Model.HocSinh;

import java.util.ArrayList;

public class DSSV extends AppCompatActivity {
//    final String DATABASE_NAME = "DBHelper.java"
    SQLiteDatabase database;

    ListView listView;
    ArrayList<HocSinh> list;
    AdapterHocSinh adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssv);

        addControls();
        readData();
    }

    private void addControls() {
        listView = findViewById(R.id.listViewMSHS);
        list = new ArrayList<>();
        adapter = new AdapterHocSinh(this, list);
        listView.setAdapter(adapter);
    }
    private void readData(){
        Cursor cursor = database.rawQuery("SELECT * FROM TB_HOCSINH", null);
        list.clear();
        for (int i=0; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);
            String mahs = cursor.getString(0);
            String ho = cursor.getString(1);
            String ten = cursor.getString(2);
            String phai = cursor.getString(3);
            String ngaysinh = cursor.getString(4);

            list.add(new HocSinh(mahs, ho, ten, phai, ngaysinh));
        }
        adapter.notifyDataSetChanged();
    }
}