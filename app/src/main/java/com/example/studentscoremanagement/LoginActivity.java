package com.example.studentscoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentscoremanagement.Model.TaiKhoan;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText txtUserName, txtPass;
    DBHelper database;

    public static final String USER_INFOR="USER_INFOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControl();
        setEvent();
    }

    private void setEvent() {
        database=new DBHelper(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=txtUserName.getText().toString();
                String pass=txtPass.getText().toString();
                TaiKhoan tk =new TaiKhoan(username,pass);
                if(tk.checkLogin(database)){
                    Intent intent = new Intent(LoginActivity.this, ChooseClassActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(DBHelper.COL_TAIKHOAN_TEN,tk.getTenTaiKhoan());
                    bundle.putString(DBHelper.COL_TAIKHOAN_MATKHAU,tk.getMatKhau());
                    bundle.putString(DBHelper.COL_TAIKHOAN_SDT,tk.getSdt());
                    bundle.putString(DBHelper.COL_TAIKHOAN_ANH,tk.getAnh());
                    intent.putExtra(USER_INFOR,bundle);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
                }
//

            }
        });
    }

    private void addControl() {
        txtUserName=findViewById(R.id.txtTK);
        txtPass=findViewById(R.id.txtMK);
        btnLogin=findViewById(R.id.btnLogin);
    }

}