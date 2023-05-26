package com.example.app_doc_truyen.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_doc_truyen.Interface.RecyviewItemLister;
import com.example.app_doc_truyen.R;
import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.model.PhanLoai;
import com.example.app_doc_truyen.model.Truyen;
import com.example.app_doc_truyen.tap;
import com.example.app_doc_truyen.truyen;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyTruyenAdapter extends RecyclerView.Adapter<MyTruyenAdapter.MyViewHolder> {
    Context context;
    List<Truyen> truyens;
    LayoutInflater inflater;

    public MyTruyenAdapter(Context context, List<Truyen> truyens) {
        this.context = context;
        this.truyens = truyens;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyTruyenAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.truyen_item, parent, false);
        return new MyTruyenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTruyenAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(truyens.get(position).image_T).into(holder.imgTruyen);
        holder.txtName.setText(truyens.get(position).Name);
        holder.setIRecyviewItemLister(new RecyviewItemLister() {
            @Override
            public void onclick(View view, int position) {
                common.truyen_item = truyens.get(position);
                common.truyen_position = position ;
                Intent intent = new Intent(context, tap.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return truyens.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgTruyen;
        TextView txtName;
        RecyviewItemLister IrecyviewItemLister;

        public void setIRecyviewItemLister(RecyviewItemLister recyviewItemLister) {
            this.IrecyviewItemLister = recyviewItemLister;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgTruyen = itemView.findViewById(R.id.img_truyen);
            txtName = itemView.findViewById(R.id.txtname_truyen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IrecyviewItemLister.onclick(view, getAdapterPosition());
                }
            });
        }

    }
}