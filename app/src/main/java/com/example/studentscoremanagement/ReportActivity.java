package com.example.studentscoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentscoremanagement.Adapter.ReportItemAdapter;
import com.example.studentscoremanagement.Model.DiemHocSinhDTO;
import com.example.studentscoremanagement.Model.HocSinh;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    TextView tvClassId, tvTeacherName;
    Button btnExportPDF;
    ListView lvReport;
    ArrayList<DiemHocSinhDTO> diemHocSinhDTOS;
    ReportItemAdapter reportItemAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        setControl();
        setEvent();
    }

    private void setEvent() {
        dbHelper=new DBHelper(this);
        ArrayList<HocSinh> hocSinhs=new ArrayList<>();
        Cursor cursor= dbHelper.GetData("SELECT * FROM "+DBHelper.TB_HOCSINH+" WHERE "+DBHelper.COL_HOCSINH_MALOP+"='12A1'");
        cursor.moveToFirst();
        do{
            HocSinh hocSinh=new HocSinh();
            hocSinh.setMaHS(cursor.getString(0));
            hocSinh.setHo(cursor.getString(1));
            hocSinh.setTen(cursor.getString(2));
            hocSinh.setPhai(cursor.getString(3));
            hocSinh.setNgaySinh(cursor.getString(4));
            hocSinh.setMaLop(cursor.getString(5));
            hocSinhs.add(hocSinh);
        }while (cursor.moveToNext());

        diemHocSinhDTOS=new ArrayList<>();
        for(HocSinh hs:hocSinhs){
            diemHocSinhDTOS.add(hs.getStudentScore(dbHelper));
        }

        tvClassId.setText("12A1");
        tvTeacherName.setText("Huỳnh Phước Sang");
        reportItemAdapter=new ReportItemAdapter(ReportActivity.this,R.layout.report_item,diemHocSinhDTOS);
        lvReport.setAdapter(reportItemAdapter);
    }

    private void setControl() {
        lvReport=findViewById(R.id.lvReport);
        tvClassId=findViewById(R.id.tvClassId);
        tvTeacherName=findViewById(R.id.tvTeacherName);
        btnExportPDF=findViewById(R.id.btnExportPDF);
    }
}