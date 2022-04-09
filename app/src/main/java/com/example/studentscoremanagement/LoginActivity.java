package com.example.studentscoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText txtUserName, txtPass;
    DBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControl();
        setEvent();
    }

    private void setEvent() {
        database=new DBHelper(this);
        Cursor testData=database.GetData("SELECT * FROM tbTaiKhoan");
        Toast.makeText(LoginActivity.this, "Độ dài: "+testData.getCount(), Toast.LENGTH_SHORT).show();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent intent = new Intent(LoginActivity.this, ChooseClassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        txtUserName=findViewById(R.id.txtTK);
        txtPass=findViewById(R.id.txtMK);
        btnLogin=findViewById(R.id.btnLogin);
    }
}