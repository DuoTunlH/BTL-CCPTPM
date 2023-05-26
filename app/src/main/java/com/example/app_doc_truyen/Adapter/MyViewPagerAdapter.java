package com.example.app_doc_truyen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.app_doc_truyen.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {
    Context context;
    List<String> imagelink;
    LayoutInflater inflater;

    public MyViewPagerAdapter(Context context, List<String> imagelink) {
        this.context = context;
        this.imagelink = imagelink;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imagelink.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View imagelayout = inflater.inflate(R.layout.viewpageritem, container , false);
        PhotoView pager_img = (PhotoView) imagelayout.findViewById(R.id.pager_image);
        Picasso.get().load(imagelink.get(position)).into(pager_img);
        container.addView(imagelayout);
        return imagelayout ;
    }
}
