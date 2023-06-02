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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.common.common_user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoiPassword extends AppCompatActivity {
    Button btnLuu, btnHuy;
    String pass;
    CheckBox ckeHienMK;
    EditText passOld, passNew, confirmPassNew;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
    ImageView imgbackk;
    TextView txtusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_password);
        btnLuu = findViewById(R.id.btnLuu);
        btnHuy = findViewById(R.id.btnHuy);
        ckeHienMK = findViewById(R.id.ckeHienMKK);
        passOld = findViewById(R.id.edtmkHienTai);
        passNew = findViewById(R.id.edtmkMoi);
        confirmPassNew = findViewById(R.id.edtXacNhanmk);
        imgbackk = findViewById(R.id.imgback);
        txtusername = findViewById(R.id.txtusername);
        txtusername.setText(common_user.user);


        imgbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoiPassword.this, Loai.class);
                startActivity(intent);
                finish();
            }
        });
        ckeHienMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckeHienMK.isChecked()) {
                    passOld.setInputType(InputType.TYPE_CLASS_TEXT);
                } else
                    passOld.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pass = snapshot.child(common_user.user).child("matkhau").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passRegex = "^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
                if (passOld.getText().toString().equals(pass)) {
                    if (passNew.getText().toString().matches(passRegex)) {
                        if (passNew.getText().toString().equals(confirmPassNew.getText().toString())) {

                            databaseReference.child(common_user.user).child("matkhau").setValue(passNew.getText().toString());
                            Toast.makeText(DoiPassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(DoiPassword.this, "Xác nhận mật khẩu sai", Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(DoiPassword.this, "Mật khẩu chưa đủ mạnh", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(DoiPassword.this, "Bạn nhập mật khẩu sai", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passOld.setText("");
                passNew.setText("");
                confirmPassNew.setText("");
            }
        });
    }
}