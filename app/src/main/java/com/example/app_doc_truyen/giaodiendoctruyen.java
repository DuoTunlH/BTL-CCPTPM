package com.example.app_doc_truyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app_doc_truyen.Adapter.MyViewPagerAdapter;
import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.model.Tap;

public class giaodiendoctruyen extends AppCompatActivity {
    ViewPager viewPager;
    ImageView imgbackk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_giaodiendoctruyen);
        imgbackk = findViewById(R.id.imgback);
        viewPager = findViewById(R.id.viewPagerGD);
        Bundle bundle = getIntent().getExtras();
        int position = bundle.getInt("position");
        Toast.makeText(this, common.truyen_item.taps.get(position).Name + "", Toast.LENGTH_SHORT).show();
        fetchlink(common.truyen_item.taps.get(position));
        imgbackk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(giaodiendoctruyen.this, tap.class);
                startActivity(intent);
            }
        });
    }

    private void fetchlink(Tap tap) {
        if (tap != null) {
            if (tap.links.size() > 0) {
                MyViewPagerAdapter adapter = new MyViewPagerAdapter(this, tap.links);
                viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                    @Override
                    public void transformPage(@NonNull View page, float position) {
                        page.setTranslationX(-position * page.getWidth());
                        page.setPivotX(0);
                        page.setPivotY(page.getHeight() / 2);
                        page.setCameraDistance(20000);

                        if (position < -1) {     // [-Infinity,-1)
                            // This page is way off-screen to the left.
                            page.setAlpha(0);

                        } else if (position <= 0) {    // [-1,0]
                            page.setAlpha(1);
                            page.setRotationY(-120 * Math.abs(position));
                        } else if (position <= 1) {    // (0,1]
                            page.setAlpha(1);
                            page.setRotationY(120 * Math.abs(position));

                        } else {    // (1,+Infinity]
                            // This page is way off-screen to the right.
                            page.setAlpha(0);

                        }

                    }
                });
                viewPager.setAdapter(adapter);

            } else
                Toast.makeText(this, "không có ảnh", Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(this, "tập đang được tải...", Toast.LENGTH_SHORT).show();
    }

}
