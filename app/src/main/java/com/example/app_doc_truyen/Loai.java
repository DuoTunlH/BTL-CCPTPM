package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_doc_truyen.Adapter.MyPhanLoaiAdapter;
import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.common.common_user;
import com.example.app_doc_truyen.model.PhanLoai;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Loai extends AppCompatActivity {
    RecyclerView recyclerView_PhanLoai;
    ImageView imageViewSearch, imgmenu;
    EditText search;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView txtName, txtemail;
    List<PhanLoai> loais;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PhanLoai");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loai);
        anhxaXML();
        navigationView.setItemIconTintList(null);
        navigationView.bringToFront();
        navigationView.setCheckedItem(R.id.menuTrangChu);
        txtName.setText(common_user.user);
        txtemail.setText(common_user.email);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.menuTrangChu:

                    break;
                case R.id.menuUaThich:
                    Intent intent = new Intent(Loai.this, UaThich.class);
                    startActivity(intent);
                    break;
                case R.id.thongtincanhan:
                    Intent intent1 = new Intent(Loai.this, thongtincanhan.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("tenDN", common_user.user);
                    bundle.putString("email", common_user.email);
                    bundle.putString("phone", common_user.phone);
                    bundle.putString("mk", common_user.password);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    break;
                case R.id.pass:
                    Intent intent2 = new Intent(Loai.this, DoiPassword.class);
                    startActivity(intent2);
                    break;
                case R.id.logout:
                    Intent intent3 = new Intent(Loai.this, DangNhap.class);
                    startActivity(intent3);
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
        imgmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loais = new ArrayList<>();
                for (DataSnapshot loaiDataSnapshot : snapshot.getChildren()) {
                    PhanLoai loai = loaiDataSnapshot.getValue(PhanLoai.class);
                    loais.add(loai);
                }

                recyclerView_PhanLoai.setHasFixedSize(true);
                recyclerView_PhanLoai.setLayoutManager(new GridLayoutManager(Loai.this, 2));
                loader_loai(loais);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (search.getText().toString().isEmpty()) {
                    loader_loai(loais);
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
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<PhanLoai> lsPL = new ArrayList<>();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            PhanLoai s = item.getValue(PhanLoai.class);
                            PhanLoai k = s;
                            if (k.Name.toUpperCase().contains(content_search.toUpperCase())) {
                                lsPL.add(s);
                            }
                        }
                        loader_loai(lsPL);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void anhxaXML() {
        imageViewSearch = findViewById(R.id.iconsearch);
        search = findViewById(R.id.edtsearch);
        recyclerView_PhanLoai = findViewById(R.id.recyView_PhanLoai);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        imgmenu = findViewById(R.id.imgCart0);
        View headerView = navigationView.getHeaderView(0);
        txtName = headerView.findViewById(R.id.txtName);
        txtemail = headerView.findViewById(R.id.txtemail);
    }

    private void loader_loai(List<PhanLoai> loais) {
        common.common_item = loais;
        recyclerView_PhanLoai.setAdapter(new MyPhanLoaiAdapter(this, loais));
    }


}