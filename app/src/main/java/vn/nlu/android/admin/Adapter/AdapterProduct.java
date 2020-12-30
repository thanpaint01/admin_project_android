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

import vn.nlu.android.admin.Activity.product.Decription;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.adminUI_Fragment.user.AdminFragment_User;
import vn.nlu.android.admin.model.Product;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductAdapter> {
    ArrayList<Product> data;
    Context context;

    public AdapterProduct(ArrayList<Product> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_product,parent,false);
        ProductAdapter adapter = new ProductAdapter(view);
        return new ProductAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct.ProductAdapter holder, int position) {
        Product p = data.get(position);

        holder.textView_idproduct.setText(""+p.getIdsp());
        holder.textView_nameproduct.setText(""+p.getTensp());

        holder.textView_branchproduct.setText(""+p.getTenhang());
        holder.textView_priceproduct.setText(""+p.getGia());
        holder.textView_storeproduct.setText(""+p.getSoluong());
        holder.textView_saleproduct.setText(""+p.getSale());
        holder.textView_ramproduct.setText(""+p.getRam());
        holder.textView_romproduct.setText(""+p.getRom());
        holder.textView_batteryproduct.setText(""+p.getPin());
        holder.textView_guaranteeproduct.setText(""+p.getBaohanh());
        holder.textView_screenproduct.setText(""+p.getKichthuoc());
        holder.textView_frontcamproduct.setText(""+p.getCameraTruoc());
        holder.textView_rearcamproduct.setText(""+p.getCameraSau());
        holder.textView_statusproduct.setText(""+p.getTinhtrang());
        holder.textView_activeproduct.setText(""+p.getActive());

        Picasso.get().load(p.getImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_product);
        Picasso.get().load(p.getImg01())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_product1);
        Picasso.get().load(p.getImg02())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_product2);
        Picasso.get().load(p.getImg03())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_product3);
        Picasso.get().load(p.getImg04())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_product4);

        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_product.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ProductAdapter extends RecyclerView.ViewHolder {
        TextView textView_idproduct,textView_nameproduct,textView_branchproduct,textView_priceproduct,textView_storeproduct,
        textView_saleproduct,textView_ramproduct,textView_romproduct,textView_batteryproduct,textView_guaranteeproduct,
        textView_screenproduct,textView_frontcamproduct,textView_rearcamproduct,textView_statusproduct,textView_activeproduct;
        LinearLayout linear_layout_productrow;
        RelativeLayout expandable_data_product;
        ImageView imgView_product,imgView_product1,imgView_product2,imgView_product3,imgView_product4;
        Button button_descriptionproduct,button_editproduct;

        public ProductAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idproduct = itemView.findViewById(R.id.textView_idproduct);
            textView_nameproduct = itemView.findViewById(R.id.textView_nameproduct);
            textView_branchproduct = itemView.findViewById(R.id.textView_branchproduct);
            textView_priceproduct = itemView.findViewById(R.id.textView_priceproduct);
            textView_storeproduct = itemView.findViewById(R.id.textView_storeproduct);
            textView_saleproduct = itemView.findViewById(R.id.textView_saleproduct);
            textView_ramproduct = itemView.findViewById(R.id.textView_ramproduct);
            textView_romproduct = itemView.findViewById(R.id.textView_romproduct);
            textView_batteryproduct = itemView.findViewById(R.id.textView_batteryproduct);
            textView_guaranteeproduct = itemView.findViewById(R.id.textView_guaranteeproduct);
            textView_screenproduct = itemView.findViewById(R.id.textView_screenproduct);
            textView_frontcamproduct = itemView.findViewById(R.id.textView_frontcamproduct);
            textView_rearcamproduct = itemView.findViewById(R.id.textView_rearcamproduct);
            textView_statusproduct = itemView.findViewById(R.id.textView_statusproduct);
            textView_activeproduct = itemView.findViewById(R.id.textView_activeproduct);

            imgView_product = itemView.findViewById(R.id.imgView_product);
            imgView_product1 = itemView.findViewById(R.id.imgView_product1);
            imgView_product2 = itemView.findViewById(R.id.imgView_product2);
            imgView_product3 = itemView.findViewById(R.id.imgView_product3);
            imgView_product4 = itemView.findViewById(R.id.imgView_product4);

            linear_layout_productrow = itemView.findViewById(R.id.linear_layout_productrow);
            expandable_data_product = itemView.findViewById(R.id.expandable_data_product);
            button_descriptionproduct = itemView.findViewById(R.id.button_descriptionproduct);
            button_editproduct = itemView.findViewById(R.id.button_editproduct);

            linear_layout_productrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = data.get(getAdapterPosition());
                    p.setExpandable(!p.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_descriptionproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putString("tieude1",p.getTieude1());
                    b.putString("tieude2",p.getTieude2());
                    b.putString("tieude3",p.getTieude3());
                    b.putString("chitiet1",p.getChitiet1());
                    b.putString("chitiet2",p.getChitiet2());
                    b.putString("chitiet3",p.getChitiet3());
                    b.putString("anh1",p.getAnh1());
                    b.putString("anh2",p.getAnh2());
                    b.putString("anh3",p.getAnh3());
                    Intent i = new Intent(context, Decription.class);
                    i.putExtra("data",b);
                    context.startActivity(i);
                }
            });
            button_editproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Product p = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id",p.getIdsp());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
        }
    }
}
