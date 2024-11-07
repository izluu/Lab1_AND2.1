package fpoly.account.ph56774_thi19.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import fpoly.account.ph56774_thi19.Model.SanPham;
import fpoly.account.ph56774_thi19.lab1_and2.R;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    private Activity activity;
    private ArrayList<SanPham> lst;
    private OnItemClickListener onItemClickListener;

    public Adapter(Activity activity, ArrayList<SanPham> lst) {
        this.activity = activity;
        this.lst = lst;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.item_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        SanPham sanPham = lst.get(position);
        holder.txtContent.setText(sanPham.getContent());
        holder.txtTitle.setText(sanPham.getTitle());
        holder.txtDate.setText(sanPham.getDate());
        holder.txtStyle.setText(sanPham.getStyle());

        Picasso.get().load(sanPham.getSrc()).into(holder.imgAvatar);

        // Set up the click listener for the item
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(sanPham);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    public static class AdapterViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAvatar;
        private TextView txtContent, txtTitle, txtDate, txtStyle;

        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtContent = itemView.findViewById(R.id.tvContent);
            txtTitle = itemView.findViewById(R.id.tvTittle);
            txtDate = itemView.findViewById(R.id.tvDate);
            txtStyle = itemView.findViewById(R.id.tvStyle);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void updateData(ArrayList<SanPham> newList) {
        this.lst.clear();
        this.lst.addAll(newList);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(SanPham sanPham);
    }
}
