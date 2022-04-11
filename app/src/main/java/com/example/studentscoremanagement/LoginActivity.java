package com.example.studentscoremanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentscoremanagement.Model.TaiKhoan;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText txtUserName, txtPass;
    DBHelper database;
    String phoneNumber, codeVerify;

    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "StudentScoreMn";

    public static final String USER_INFOR="USER_INFOR";
    public static final String VERIFY_CODE="VERIFY_CODE";


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
                    phoneNumber=tk.getSdt();
                    codeVerify="Mã xác thực: "+randomVerifyCode();

                    //Gửi tin nhắn
//                    askPermissionAndSendSMS();

                    Intent intent = new Intent(LoginActivity.this, VerifyActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString(DBHelper.COL_TAIKHOAN_TEN,tk.getTenTaiKhoan());
                    bundle.putString(DBHelper.COL_TAIKHOAN_MATKHAU,tk.getMatKhau());
                    bundle.putString(DBHelper.COL_TAIKHOAN_SDT,tk.getSdt());
                    bundle.putString(DBHelper.COL_TAIKHOAN_ANH,tk.getAnh());
                    intent.putExtra(USER_INFOR,bundle);

                    intent.putExtra(VERIFY_CODE,codeVerify);
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

    private void askPermissionAndSendSMS() {

        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager();
    }

    private void sendSMS_by_smsManager()  {
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send Message
            smsManager.sendTextMessage(phoneNumber,
                    null,
                    codeVerify,
                    null,
                    null);

            Log.i( LOG_TAG,"Mã xác thực đã được gửi đến SĐT của bạn!");
            Toast.makeText(getApplicationContext(),"Mã xác thực đã được gửi đến SĐT của bạn!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Gửi mã xác thực thất bại...", ex);
            Toast.makeText(getApplicationContext(),"Gửi mã xác thực thất bại... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public String randomVerifyCode() {
        String code = "";
        Random rand = new Random();
        for (int i = 1; i <= 6; i++) {
            int tempIntCode = rand.nextInt(10);
            code += tempIntCode;
        }

        return code;
    }
    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE_SEND_SMS: {

                // Note: If request is cancelled, the result arrays are empty.
                // Permissions granted (SEND_SMS).
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.i( LOG_TAG,"Permission granted!");
//                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                    this.sendSMS_by_smsManager();
                }
                // Cancelled or denied.
                else {
                    Log.i( LOG_TAG,"Permission denied!");
//                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
//                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

}