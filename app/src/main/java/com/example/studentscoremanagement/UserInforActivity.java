package com.example.studentscoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscoremanagement.Model.TaiKhoan;

import java.util.logging.Logger;

public class UserInforActivity extends AppCompatActivity implements EventListener{
    TaiKhoan user;

    Button btnUpdateInfor;
    LinearLayout llLogout;
    ImageView imUserAvatar;
    TextView txtAccount;
    EditText txtPassword, txtPhoneNumber;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        setControl();
        setEvent();
        setDataByBundle();
    }

    private void setDataByBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(LoginActivity.USER_INFOR);
        user = new TaiKhoan(bundle.getString(DBHelper.COL_TAIKHOAN_TEN), bundle.getString(DBHelper.COL_TAIKHOAN_MATKHAU),
                bundle.getString(DBHelper.COL_TAIKHOAN_SDT), bundle.getString(DBHelper.COL_TAIKHOAN_ANH));
        if (bundle == null)
        {
            Toast.makeText(this, "bundle is null", Toast.LENGTH_SHORT).show();
            return;
        }

        txtAccount.setText("", TextView.BufferType.EDITABLE);
        txtPassword.setText("", TextView.BufferType.EDITABLE);
        txtPhoneNumber.setText("", TextView.BufferType.EDITABLE);

        txtAccount.setText(user.getTenTaiKhoan(), TextView.BufferType.EDITABLE);
        txtPassword.setText(user.getMatKhau(), TextView.BufferType.EDITABLE);
        txtPhoneNumber.setText(user.getSdt(), TextView.BufferType.EDITABLE);

        String avatar = user.getAnh();
        if (avatar == null || avatar.isEmpty())
        {
            //set default avatar
        }
        else
        {
            imageUri = Uri.parse(avatar);
            imUserAvatar.setImageURI(imageUri);
            Log.d("print", "created " + user.getAnh());
        }
    }

    private void setEvent() {
        imUserAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnUpdateInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog cdd=new CustomDialog(UserInforActivity.this, UserInforActivity.this);
                cdd.show();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            user.setAnh(data.getData().toString());
            Log.d("print", user.getAnh());
            imageUri = Uri.parse(user.getAnh());
            imUserAvatar.setImageURI(imageUri);
        }
    }

    private void setControl() {
        btnUpdateInfor = findViewById(R.id.btnUdateInfor);
        llLogout = findViewById(R.id.llLogout);
        imUserAvatar = findViewById(R.id.imUserAvatarInUserInfor);
        txtAccount = findViewById(R.id.txtAccount);
        txtPassword = findViewById(R.id.txtPassword);
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
    }

    @Override
    public void doSomething() {
        user.setMatKhau(txtPassword.getText().toString());
        user.setSdt(txtPhoneNumber.getText().toString());

        DBHelper database = new DBHelper(UserInforActivity.this);
        //save data
        user.saveToDatabase(database);
        Intent intent = new Intent(this, getClass());
        Bundle bundle=new Bundle();
        bundle.putString(DBHelper.COL_TAIKHOAN_TEN,user.getTenTaiKhoan());
        bundle.putString(DBHelper.COL_TAIKHOAN_MATKHAU,user.getMatKhau());
        bundle.putString(DBHelper.COL_TAIKHOAN_SDT,user.getSdt());
        bundle.putString(DBHelper.COL_TAIKHOAN_ANH,user.getAnh());
        intent.putExtra(LoginActivity.USER_INFOR,bundle);
        startActivity(intent);
//        finish();
    }
}