package com.example.app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DangKy extends AppCompatActivity {
    TextView txtDisplayDangKy;
    EditText edtTenDK, edtEmail, edtSoDienThoai, edtMatKhau, edtXacNhanMatKhau;
    Button btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        txtDisplayDangKy = findViewById(R.id.txtDangNhap);
        edtTenDK = findViewById(R.id.edtTenDangKy);
        edtEmail = findViewById(R.id.edtEmail);
        edtSoDienThoai = findViewById(R.id.edtPhone);
        edtMatKhau = findViewById(R.id.edtPassWord);
        edtXacNhanMatKhau = findViewById(R.id.edtPassWordConfirm);
        btnDangKy = findViewById(R.id.btnDangKy);


        txtDisplayDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);
            }
        });
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validTenDK() && validEmail() && validPhone() && validPass() && validXNPass()) ;
                {
                    String pass = edtMatKhau.getText().toString();
                    String xnpass = edtXacNhanMatKhau.getText().toString();
                    if (pass.equals(xnpass)) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                        String TenDK = edtTenDK.getText().toString();
                        String email = edtEmail.getText().toString();
                        String phone = edtSoDienThoai.getText().toString();
                        String mk = edtMatKhau.getText().toString();

                        user_helper user_helper = new user_helper(TenDK, email, phone, mk);
                        databaseReference.child(TenDK).setValue(user_helper);
                        Toast.makeText(getApplicationContext(), "đã đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangKy.this, DangNhap.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "xác nhận mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    private boolean validTenDK() {
        String TenDK = edtTenDK.getText().toString();
        if (TenDK.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Tên đăng ký không được để trống", Toast.LENGTH_SHORT).show();
            edtXacNhanMatKhau.requestFocus();
            return false;
        } else
            return true;
    }

    private Boolean validXNPass() {
        String XNPass = edtXacNhanMatKhau.getText().toString();
        if (XNPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "trường Xác nhận mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            edtXacNhanMatKhau.requestFocus();
            return false;
        } else
            return true;
    }

    private Boolean validPass() {
        String valPass = "^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
        String Pass = edtMatKhau.getText().toString();
        if (Pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "trường Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Pass.matches(valPass)) {
            Toast.makeText(getApplicationContext(), "Mật khẩu chưa đủ mạnh", Toast.LENGTH_SHORT).show();
            edtMatKhau.requestFocus();
            return false;
        } else return true;
    }

    private Boolean validPhone() {
        String Phone = edtSoDienThoai.getText().toString();
        if (Phone.isEmpty()) {
            Toast.makeText(getApplicationContext(), "trường số điện thoại không được để trống", Toast.LENGTH_SHORT).show();
            edtSoDienThoai.requestFocus();
            return false;
        } else return true;
    }

    private Boolean validEmail() {
        String email = edtEmail.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "trường email không được để trống", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            Toast.makeText(getApplicationContext(), "địa chỉ email chưa hợp lệ", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return false;
        } else return true;
    }
}