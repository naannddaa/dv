package com.amelia.dv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.amelia.dv.R;
import com.amelia.dv.SubPengajuan;

import java.util.ArrayList;

public class AdapterSubPengajuanSurat extends RecyclerView.Adapter<AdapterSubPengajuanSurat.ViewHolder> {

    private ArrayList<SubPengajuan> subPengajuanArrayList;
    private Context context;
    private OnClickListener onClickListener;

    public AdapterSubPengajuanSurat(Context context, ArrayList<SubPengajuan> subPengajuanArrayList, OnClickListener onClickListener) {
        this.context = context;
        this.subPengajuanArrayList = subPengajuanArrayList;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subsurat_merge, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SubPengajuan subPengajuan = subPengajuanArrayList.get(position);
        holder.textView.setText(subPengajuan.getNama());

        // Set the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClickListener(position); // Pass the clicked position
            }
        });
    }

    @Override
    public int getItemCount() {
        return subPengajuanArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            imageView = itemView.findViewById(R.id.g_icon);
        }
    }

    public interface OnClickListener {
        void onClickListener(int position);
    }
}
