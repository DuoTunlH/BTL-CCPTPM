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
import com.example.app_doc_truyen.truyen;
import com.example.app_doc_truyen.common.common;
import com.example.app_doc_truyen.model.PhanLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyPhanLoaiAdapter extends RecyclerView.Adapter<MyPhanLoaiAdapter.MyViewHolder> {
    Context context;
    List<PhanLoai> phanLoais;
    LayoutInflater inflater;

    public MyPhanLoaiAdapter(Context context, List<PhanLoai> phanLoais) {
        this.context = context;
        this.phanLoais = phanLoais;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override

    public MyPhanLoaiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.phanloai_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyPhanLoaiAdapter.MyViewHolder holder, int position) {
        Picasso.get().load(phanLoais.get(position).image).into(holder.imgPhanLoai);
        holder.txtName.setText(phanLoais.get(position).Name);
        holder.setRecyviewItemLister(new RecyviewItemLister() {
            @Override
            public void onclick(View view, int position) {
                common.phanloai_item = phanLoais.get(position);
                Intent intent = new Intent(context, truyen.class);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return phanLoais.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyviewItemLister recyviewItemLister;
        ImageView imgPhanLoai;
        TextView txtName;

        public void setRecyviewItemLister(RecyviewItemLister recyviewItemLister) {
            this.recyviewItemLister = recyviewItemLister;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhanLoai = itemView.findViewById(R.id.img_phanloai);
            txtName = itemView.findViewById(R.id.txtname_phanloai);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyviewItemLister.onclick(view, getAdapterPosition());
                }
            });
        }

    }
}
