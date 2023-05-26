package com.example.app_doc_truyen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_doc_truyen.ListBL.ListView_BL;
import com.example.app_doc_truyen.R;

import java.util.ArrayList;
import java.util.List;

public class MyAdapterBL extends ArrayAdapter<ListView_BL> {
    Context context ;
    int mResource ;
    public MyAdapterBL(@NonNull Context context, int resource, @NonNull List<ListView_BL> objects) {
        super(context, resource, objects);
        this.context = context ;
        this.mResource = resource ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(mResource , parent , false );
        TextView txtusername = convertView.findViewById(R.id.txtuser);
        TextView txtbl = convertView.findViewById(R.id.txtbl);
        txtusername.setText(getItem(position).getUser());
        txtbl.setText(getItem(position).getBl());
        return convertView ;
    }

}
