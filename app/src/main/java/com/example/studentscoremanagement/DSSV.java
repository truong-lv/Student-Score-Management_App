package com.example.studentscoremanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentscoremanagement.Adapter.AdapterHocSinh;
import com.example.studentscoremanagement.Model.HocSinh;

import java.util.ArrayList;

public class DSSV extends AppCompatActivity {

    DBHelper database;

    Button btnThem,buttonBC;
    ListView lvHocSinh;
    ArrayList<HocSinh> arrayHocSinh;
    AdapterHocSinh adapter;

    String idClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dssv);



        getClassId();
        //lvHocSinh.setAdapter(adapter);
        setControl();
        setEvent();
        GetDataHocSinh();
    }

    private void getClassId() {
        Intent intent=getIntent();
        idClass=intent.getStringExtra(ChooseClassActivity.ID_CLASS);
    }

    private void setControl() {
        database=new DBHelper(DSSV.this);
        lvHocSinh = (ListView) findViewById(R.id.listViewMSHS);
        btnThem=findViewById(R.id.buttonThem);
        arrayHocSinh = new ArrayList<>();
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSinhVien();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSinhVien();
            }
        });
    }

    // để data ra mh
    private void GetDataHocSinh(){
        Cursor dataHS = database.GetData("SELECT * FROM "+DBHelper.TB_HOCSINH+" WHERE "+DBHelper.COL_HOCSINH_MALOP+"='"+idClass+"'");//nghe ko bn ko nghe==>ông out meet r
//ng
        dataHS.moveToFirst();
        do
        {
            String id = dataHS.getString(0);
            //Toast.makeText(this, id , Toast.LENGTH_SHORT).show();
            String ho = dataHS.getString(1);
            //Toast.makeText(this, ho , Toast.LENGTH_SHORT).show();
            String ten = dataHS.getString(2);
            //Toast.makeText(this, ten , Toast.LENGTH_SHORT).show();
            String phai = dataHS.getString(3);
            //Toast.makeText(this, phai , Toast.LENGTH_SHORT).show();
            String ngaySinh = dataHS.getString(4);
            //Toast.makeText(this, ngaySinh , Toast.LENGTH_SHORT).show();
            arrayHocSinh.add(new HocSinh(id, ho, ten, phai, ngaySinh));
        }while (dataHS.moveToNext());
        Toast.makeText(this, String.valueOf(arrayHocSinh.get(0).getTen()) , Toast.LENGTH_SHORT).show();
        //adapter.notifyDataSetChanged();
        adapter = new AdapterHocSinh(DSSV.this, R.layout.activity_dssv_ds, arrayHocSinh);
        lvHocSinh.setAdapter(adapter);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.buttonThem){
            ThemSinhVien();
        }
        return super.onOptionsItemSelected(item);
    }


    private  void ThemSinhVien(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.themhocsinh);

        EditText editMHS = (EditText) dialog.findViewById(R.id.editTextNhapMHS);
        EditText editHo = (EditText) dialog.findViewById(R.id.editTextNhapHo);
        EditText editTen = (EditText) dialog.findViewById(R.id.editTextNhapTen);
        EditText editPhai = (EditText) dialog.findViewById(R.id.editTextNhapPhai);
        EditText editNSinh = (EditText) dialog.findViewById(R.id.editTextNhapNgaySinh);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHUY);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonLUU);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mhs = editMHS.getText().toString();
                String ho = editHo.getText().toString();
                String ten = editTen.getText().toString();
                String phai = editPhai.getText().toString();
                String ngaysinh= editNSinh.getText().toString();
                if(mhs.equals("") || ho.equals("") || ten.equals("") || phai.equals("") ||ngaysinh.equals("")){
                    Toast.makeText(DSSV.this, "Vui lòng nhập không để trống!", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO "+DBHelper.TB_HOCSINH+" VALUES ('"+mhs +"', '"+ho+"', '"+ten+"', '"+phai+"', '"+ngaysinh+"')");
                    Toast.makeText(DSSV.this, "Đã Thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataHocSinh();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }




//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.listViewMSHS){
//            Xoa_SuaSinhVien();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private  void Xoa_SuaSinhVien(){
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.xoa_sua_hs);
//
//
//        Button btnXoa = (Button) dialog.findViewById(R.id.buttonXOA);
//        Button btnSua = (Button) dialog.findViewById(R.id.buttonSUA);
//
//        btnSua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogSua();
//
//            }
//        });
//
//        btnXoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogXoa();
//            }
//        });
//        dialog.show();
//    }
//
//
//    private  void DialogSua(){
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.themhocsinh);
//
//        EditText editMHS = (EditText) dialog.findViewById(R.id.editTextNhapMHS);
//        EditText editHo = (EditText) dialog.findViewById(R.id.editTextNhapHo);
//        EditText editTen = (EditText) dialog.findViewById(R.id.editTextNhapTen);
//        EditText editPhai = (EditText) dialog.findViewById(R.id.editTextNhapPhai);
//        EditText editNSinh = (EditText) dialog.findViewById(R.id.editTextNhapNgaySinh);
//        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHUY);
//        Button btnThem = (Button) dialog.findViewById(R.id.buttonLUU);
//
//        btnThem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String mahsMoi = editMHS.getText().toString().trim();
//                String hoMoi = editHo.getText().toString().trim();
//                String tenMoi = editTen.getText().toString().trim();
//                String phaiMoi = editPhai.getText().toString().trim();
//                String ngayMoi= editNSinh.getText().toString().trim();
//                if(mahsMoi.equals("") || hoMoi.equals("") || tenMoi.equals("") || phaiMoi.equals("") ||ngayMoi.equals("")){
//                    Toast.makeText(DSSV.this, "Vui lòng nhập không để trống!", Toast.LENGTH_SHORT).show();
//                }else {
//                    database.QueryData("UPDATE TB_HOCSINH SET ('"+ mahsMoi +"', '"+hoMoi+"', '"+tenMoi+"', '"+phaiMoi+"', '"+ngayMoi+"'");
//                    Toast.makeText(DSSV.this, "ĐÃ CẬP NHẬT", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                    GetDataHocSinh();
//                }
//            }
//        });
//
//        btnHuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//
//    public void DialogXoa (String ten, String maHocSinh){
//        database.QueryData("DELETE FORM TB_HOCSINH WHERE Id = '"+ maHocSinh +"'");
//        Toast.makeText(DSSV.this, "ĐÃ XÓA " + ten, Toast.LENGTH_SHORT).show();
//        GetDataHocSinh();
////        for (HocSinh item : arrayHocSinh){
////            if(item.getMaHocSinh().equals(hocSinh.getMaHocSinh())){
////                arrayHocSinh.remove(item);
////                break;
////            }
//        }
//        //cập nhật adapter
////        adapter.notifyDataSetChanged();
}