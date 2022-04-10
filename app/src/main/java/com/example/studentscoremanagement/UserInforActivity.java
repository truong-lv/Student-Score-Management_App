package com.example.studentscoremanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UserInforActivity extends AppCompatActivity {
    Button btnUpdateInfor;
    LinearLayout llLogout;
    ImageView imUserAvatar;

    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);

        setControl();
        setEvent();
    }

    private void setEvent() {
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
                openGallery();
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
            String test = data.getData().toString();
            imageUri = Uri.parse(test);
            Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
            imUserAvatar.setImageURI(imageUri);
        }
    }

    private void setControl() {
        btnUpdateInfor = findViewById(R.id.btnUdateInfor);
        llLogout = findViewById(R.id.llLogout);
        imUserAvatar = findViewById(R.id.imUserAvatarInUserInfor);
    }
}