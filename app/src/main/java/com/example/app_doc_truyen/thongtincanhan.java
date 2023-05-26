package com.example.app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.common.common_user;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class thongtincanhan extends AppCompatActivity {
    EditText edtTen, edtemail, edtphone, edtmk;
    Button btnLuu;
    TextView txtusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtincanhan);
        btnLuu = findViewById(R.id.btnLuu);
        edtTen = findViewById(R.id.edtTen);
        edtemail = findViewById(R.id.edtEmailCN);
        edtphone = findViewById(R.id.edtPhoneCN);
        edtmk = findViewById(R.id.edtMatKhauCN);
        txtusername = findViewById(R.id.txtusername);

        Bundle bundle = getIntent().getExtras();
        common_user.user = bundle.getString("tenDN");
        common_user.email = bundle.getString("email");
        common_user.phone = bundle.getString("phone") ;
        common_user.password = bundle.getString("mk") ;
        txtusername.setText(common_user.user);
        edtTen.setText(common_user.user);
        edtemail.setText( common_user.email);
        edtphone.setText(common_user.phone);
        edtmk.setText(common_user.password);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtemail.getText().toString();
                String phone = edtphone.getText().toString();
                String pass = edtmk.getText().toString();
                String username = edtTen.getText().toString();
                databaseReference.child(username).child("email").setValue(email);
                databaseReference.child(username).child("phone").setValue(phone);
                databaseReference.child(username).child("matkhau").setValue(pass);
                Toast.makeText(getApplicationContext(), "dữ liệu đã được cập nhật", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(thongtincanhan.this, Loai.class);
                startActivity(intent);
            }
        });
    }
}