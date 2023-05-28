package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class DangNhap extends AppCompatActivity {
    TextView txtDisplayDangNhap;
    EditText edtTenDN, edtPass;
    Button btnDangNhap;
    CheckBox ckeHienMK ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        txtDisplayDangNhap = findViewById(R.id.txtDangKy);
        edtTenDN = findViewById(R.id.edtTenDangNhap);
        edtPass = findViewById(R.id.edtPassWord);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        ckeHienMK = findViewById(R.id.ckeHienMK);
        ckeHienMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ckeHienMK.isChecked()){
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT);
                }else
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        txtDisplayDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TenDN = edtTenDN.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                Query check = databaseReference.orderByChild("tenDK").equalTo(TenDN);

                check.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String passfire = snapshot.child(TenDN).child("matkhau").getValue(String.class);
                            if (passfire.equals(pass)) {
                                String email = snapshot.child(TenDN).child("email").getValue(String.class);
                                String phone = snapshot.child(TenDN).child("phone").getValue(String.class);
                                String mk = snapshot.child(TenDN).child("matkhau").getValue(String.class);
                                Intent intent = new Intent(DangNhap.this, thongtincanhan.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("tenDN", TenDN);
                                bundle.putString("email", email);
                                bundle.putString("phone", phone);
                                bundle.putString("mk", mk);
                                intent.putExtras(bundle);
                                Toast.makeText(getApplicationContext(), "Đăng Nhập thành công", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "sai password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "sai tên đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }
}