package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.common.common_user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UaThich extends AppCompatActivity {
    ListView lsYeuthich;
    List<String> lsYT;
    ImageView imgUndo, imageViewSearch;
    EditText search;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ua_thich);
        lsYeuthich = findViewById(R.id.lsYeuthich);
        imgUndo = findViewById(R.id.imgUndo);
        imageViewSearch = findViewById(R.id.iconsearch);
        search = findViewById(R.id.edtsearch);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.getText().toString().isEmpty()) {
                    loader_data();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content_search = search.getText().toString();
                List<String> lsYThich = new ArrayList<>();
                for (int i = 0; i < lsYT.size(); i++) {
                    String s = lsYT.get(i);
                    if (s.toUpperCase().contains(content_search.toUpperCase())) {
                        lsYThich.add(s);
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(UaThich.this, android.R.layout.simple_dropdown_item_1line, lsYThich);
                lsYeuthich.setAdapter(adapter);
            }
        });
        loader_data();
        imgUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UaThich.this, Loai.class);
                startActivity(intent);
            }
        });
    }

    private void loader_data() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lsYT = new ArrayList<>();
                for (DataSnapshot item : snapshot.child(common_user.user).child("yeuthich").getChildren()) {
                    String s = item.getValue(String.class);
                    lsYT.add(s);
                }
                ArrayAdapter adapter = new ArrayAdapter(UaThich.this, android.R.layout.simple_dropdown_item_1line, lsYT);
                lsYeuthich.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}