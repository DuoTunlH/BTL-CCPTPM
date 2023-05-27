package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_doc_truyen.Adapter.MyAdapterBL;
import com.example.app_doc_truyen.ListBL.ListView_BL;
import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.common.common_user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class chitiet_tap extends AppCompatActivity {
    ImageView imgChiTiet;
    TextView txtName;
    TextView txtSoTrang, txtTacgia;
    EditText Cmt;
    ListView lsViewCmt;
    ImageView imgCart2;
    Button btnDoc, btnGui;
    int position;
    CheckBox ckeYeuThich;
    List<ListView_BL> cmt_arr;
    String nameSach;
    List<String> lsyeuthich;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PhanLoai");
    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_chitiet_tap);
        imgCart2 = findViewById(R.id.imgCart2);
        imgChiTiet = findViewById(R.id.img_tap);
        txtName = findViewById(R.id.txt_name);
        txtSoTrang = findViewById(R.id.txtSoTrang);
        Cmt = findViewById(R.id.edtCmt);
        txtTacgia = findViewById(R.id.txttacgia);
        lsViewCmt = findViewById(R.id.ls_cmt);
        btnDoc = findViewById(R.id.btnDoc);
        btnGui = findViewById(R.id.btngui);
        ckeYeuThich = findViewById(R.id.tym);
        Bundle bundle = getIntent().getExtras();
        position = bundle.getInt("position");
        String img = common.truyen_item.taps.get(position).links.get(0);
        String Name = common.truyen_item.taps.get(position).Name;
        Picasso.get().load(img).into(imgChiTiet);
        laydulieubinhluan();
        txtName.setText(Name);
        txtTacgia.setText("Tác giả: " + common.truyen_item.author);
        txtSoTrang.setText(String.valueOf(common.truyen_item.taps.get(position).links.size()));
        imgCart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitiet_tap.this, tap.class);
                startActivity(intent);
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameSach = snapshot.child(common.phanloai_item.Name).child("truyens").child(String.valueOf(common.truyen_position)).child("taps").child(String.valueOf(position)).child("Name").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lsyeuthich = new ArrayList<>();
                for (DataSnapshot item : snapshot.child(common_user.user).child("yeuthich").getChildren()) {
                    String s = item.getValue(String.class);
                    lsyeuthich.add(s);
                    if (s.equals(nameSach)) {
                        ckeYeuThich.setChecked(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ckeYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ckeYeuThich.isChecked()) {
                    lsyeuthich.add(common.taplist.get(position).Name);
                    Toast.makeText(chitiet_tap.this, "Bạn vừa thêm truyện vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    lsyeuthich.remove(common.taplist.get(position).Name);
                    Toast.makeText(chitiet_tap.this, "Bạn vừa loại bỏ truyện ra danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }

                databaseReference1.child(common_user.user).child("yeuthich").setValue(lsyeuthich);
            }
        });
        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitiet_tap.this, giaodiendoctruyen.class);
                bundle.putInt("position", position);
                ;
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmt_arr.add(new ListView_BL(common_user.user, Cmt.getText().toString()));
                databaseReference.child(common.phanloai_item.Name).child("truyens").child(String.valueOf(common.truyen_position)).child("taps").child(String.valueOf(position)).child("binhluan").setValue(cmt_arr);
                loader_listview(cmt_arr);
                Cmt.setText("");
                Toast.makeText(chitiet_tap.this, "Bình luận xong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loader_listview(List<ListView_BL> arr) {
        MyAdapterBL adapter = new MyAdapterBL(this, R.layout.listbl_item, arr);
        lsViewCmt.setAdapter(adapter);
    }

    private void laydulieubinhluan() {
        cmt_arr = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot binhluan : snapshot.child(common.phanloai_item.Name).child("truyens").child(String.valueOf(common.truyen_position)).child("taps").child(String.valueOf(position)).child("binhluan").getChildren()) {
                    ListView_BL bl = binhluan.getValue(ListView_BL.class);
                    cmt_arr.add(bl);
                }
                loader_listview(cmt_arr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}