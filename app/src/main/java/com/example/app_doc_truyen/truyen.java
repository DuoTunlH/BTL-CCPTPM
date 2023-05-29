package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Trace;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.app_doc_truyen.Adapter.MyTruyenAdapter;
import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.model.PhanLoai;
import com.example.app_doc_truyen.model.Truyen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class truyen extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imgCart, imageViewSearch;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_truyen);
        recyclerView = findViewById(R.id.recyView_Truyen);
        imgCart = findViewById(R.id.imgCart);
        imageViewSearch = findViewById(R.id.iconsearch);
        search = findViewById(R.id.edtsearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fettruyen(common.phanloai_item);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(truyen.this, Loai.class);
                startActivity(intent);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.getText().toString().isEmpty()) {
                    fettruyen(common.phanloai_item);
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
                List<Truyen> lsTruyen = new ArrayList<>();
                for (int i = 0; i < common.truyenlist.size(); i++) {
                    String s = common.truyenlist.get(i).Name;
                    if (s.toUpperCase().contains(content_search.toUpperCase())) {
                        lsTruyen.add(common.truyenlist.get(i));
                    }
                }
                recyclerView.setAdapter(new MyTruyenAdapter(truyen.this, lsTruyen));
            }

        });
    }


    private void fettruyen(PhanLoai phanloai_item) {
        common.truyenlist = phanloai_item.truyens;
        recyclerView.setAdapter(new MyTruyenAdapter(this, common.truyenlist));
    }
}