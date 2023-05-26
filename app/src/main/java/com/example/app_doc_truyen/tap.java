package com.example.app_doc_truyen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.model.Tap;
import com.example.app_doc_truyen.model.Truyen;

import java.util.ArrayList;
import java.util.List;

public class tap extends AppCompatActivity {
    ListView lstap;
    ImageView imgCart1, imageViewSearch;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tap);
        lstap = findViewById(R.id.list_tap);
        imgCart1 = findViewById(R.id.imgCart1);
        imageViewSearch = findViewById(R.id.iconsearch);
        search = findViewById(R.id.edtsearch);
        choose(common.truyen_item);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.getText().toString().isEmpty()) {
                    choose(common.truyen_item);
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
                List<Tap> lsTap = new ArrayList<>();
                for (int i = 0; i < common.taplist.size(); i++) {
                    String s = common.taplist.get(i).Name;
                    if (s.toUpperCase().contains(content_search.toUpperCase())) {
                        lsTap.add(common.taplist.get(i));
                    }
                }
                ReAddData(lsTap);
            }
        });
        lstap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(tap.this, chitiet_tap.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", i);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imgCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(tap.this, truyen.class);
                startActivity(intent);
            }
        });
    }

    private void ReAddData(List<Tap> lsTap) {
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < lsTap.size(); i++) {
            ls.add(lsTap.get(i).Name);
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ls);
        lstap.setAdapter(adapter);
    }

    private void choose(Truyen truyen_item) {
        common.taplist = truyen_item.taps;
        List<String> ls = new ArrayList<>();
        for (int i = 0; i < common.taplist.size(); i++) {
            ls.add(common.taplist.get(i).Name);
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ls);
        lstap.setAdapter(adapter);
    }

}