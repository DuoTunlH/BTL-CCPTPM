package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_doc_truyen.common.common_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class XacThucNguoiDung extends AppCompatActivity {
    Button btnXacthuc, btnGuiLai;
    EditText edtnumber_verifi;
    TextView txtBoDem ;
    ProgressBar Pbar;
    FirebaseAuth mAuth;
    String VerificationID;
    int Xacthucthucong = 0 ;
    CountDownTimer timer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_thuc_nguoi_dung);
        btnXacthuc = findViewById(R.id.btnXacThuc);
        edtnumber_verifi = findViewById(R.id.edtnumner_verification);
        Pbar = findViewById(R.id.Pbar);
        btnGuiLai = findViewById(R.id.btnGuiLai);
        txtBoDem = findViewById(R.id.txtBodem);
        mAuth = FirebaseAuth.getInstance();
        Pbar.setVisibility(View.INVISIBLE);

        String phonenumber = getIntent().getStringExtra("phonenumber");
        sendVerificationToUser(phonenumber);

        btnXacthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edtnumber_verifi.getText().toString())) {
                    Toast.makeText(XacThucNguoiDung.this, "Chưa nhập OTP", Toast.LENGTH_SHORT).show();
                } else {
                    Pbar.setVisibility(View.VISIBLE);
                    verificode(edtnumber_verifi.getText().toString());
                    Xacthucthucong = 1 ;
                }
            }
        });
        btnGuiLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationToUser(phonenumber);
                txtBoDem.setText("59");
                btnGuiLai.setEnabled(false);
                btnGuiLai.setBackgroundColor(Color.GRAY);
                edtnumber_verifi.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        timer = new CountDownTimer(Integer.parseInt(txtBoDem.getText().toString())*1000 , 1000) {
            @Override
            public void onTick(long l) {
                txtBoDem.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                btnGuiLai.setEnabled(true);
                btnGuiLai.setBackgroundResource(R.drawable.background_btn);

            }
        }.start();
    }

    private void sendVerificationToUser(String phonenumber) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+84" + phonenumber)       // Phone number to verify
                        .setTimeout(5L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            final String code = credential.getSmsCode();
            if (code != null) {
                edtnumber_verifi.setText(code);
                verificode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(XacThucNguoiDung.this, "Gửi mã xác thực thất bại", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(XacThucNguoiDung.this, DangKy.class));
            finish();
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            VerificationID = s;
        }
    };

    private void verificode(String code) {
        PhoneAuthCredential Credential = PhoneAuthProvider.getCredential(VerificationID, code);
        signinbyCredential(Credential);
    }

    private void signinbyCredential(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    addData();
                    Toast.makeText(XacThucNguoiDung.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(XacThucNguoiDung.this, DangNhap.class));
                    finish();
                } else {
                    if(Xacthucthucong == 1 ){
                        Pbar.setVisibility(View.INVISIBLE);
                        Toast.makeText(XacThucNguoiDung.this, "Mã xác thực chưa đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void addData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        user_helper user_helper = new user_helper(common_user.user, common_user.email, common_user.phone, common_user.password);
        databaseReference.child(common_user.user).setValue(user_helper);
    }
}