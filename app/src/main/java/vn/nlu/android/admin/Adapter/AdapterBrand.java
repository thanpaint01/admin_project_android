package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.model.Brand;

public class AdapterBrand extends RecyclerView.Adapter<AdapterBrand.BrandAdapter> {
    ArrayList<Brand> data;
    Context context;

    public AdapterBrand(ArrayList<Brand> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public BrandAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_brand,parent,false);
        BrandAdapter adapter = new BrandAdapter(view);
        return new BrandAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBrand.BrandAdapter holder, int position) {
        Brand brand = data.get(position);

        holder.textView_idbrand.setText(""+ brand.getId());
        holder.textView_namebrand.setText(""+ brand.getNameOfBrand());
        holder.textView_activebrand.setText(""+ brand.getActive());
        Picasso.get().load(brand.getImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_brand);

        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_brand.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BrandAdapter extends RecyclerView.ViewHolder {
        TextView textView_idbrand,textView_namebrand,textView_activebrand;
        LinearLayout linear_layout_brandrow;
        RelativeLayout expandable_data_brand;
        ImageView imgView_brand;
        Button button_editbrand,button_deletebrand;

        public BrandAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idbrand = itemView.findViewById(R.id.textView_idbrand);
            textView_namebrand = itemView.findViewById(R.id.textView_namebrand);
            textView_activebrand = itemView.findViewById(R.id.textView_activebrand);

            linear_layout_brandrow = itemView.findViewById(R.id.linear_layout_brandrow);
            expandable_data_brand = itemView.findViewById(R.id.expandable_data_brand);
            button_editbrand = itemView.findViewById(R.id.button_editbrand);
            button_deletebrand = itemView.findViewById(R.id.button_deletebrand);

            imgView_brand = itemView.findViewById(R.id.imgView_brand);

            linear_layout_brandrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Brand brand = data.get(getAdapterPosition());
                    brand.setExpandable(!brand.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editbrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Brand brand = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", brand.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deletebrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Brand brand = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", brand.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });

        }
    }
}
