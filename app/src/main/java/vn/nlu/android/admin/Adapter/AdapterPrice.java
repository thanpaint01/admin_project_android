package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.model.Tag;

public class AdapterPrice extends RecyclerView.Adapter<AdapterPrice.PriceAdapter> {
    ArrayList<Tag> data;
    Context context;

    public AdapterPrice(ArrayList<Tag> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public PriceAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_price,parent,false);
        PriceAdapter adapter = new PriceAdapter(view);
        return new PriceAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPrice.PriceAdapter holder, int position) {
        Tag price = data.get(position);

        holder.textView_idprice.setText(""+ price.getId());
        holder.textView_storageprice.setText(""+ price.getData());
        holder.textView_activeprice.setText(""+ price.getActive());


        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_price.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PriceAdapter extends RecyclerView.ViewHolder {
        TextView textView_idprice,textView_storageprice,textView_activeprice;
        LinearLayout linear_layout_pricerow;
        RelativeLayout expandable_data_price;
        Button button_editprice,button_deleteprice;

        public PriceAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idprice = itemView.findViewById(R.id.textView_idprice);
            textView_storageprice = itemView.findViewById(R.id.textView_storageprice);
            textView_activeprice = itemView.findViewById(R.id.textView_activeprice);



            linear_layout_pricerow = itemView.findViewById(R.id.linear_layout_pricerow);
            expandable_data_price = itemView.findViewById(R.id.expandable_data_price);
            button_editprice = itemView.findViewById(R.id.button_editprice);
            button_deleteprice = itemView.findViewById(R.id.button_deleteprice);

            linear_layout_pricerow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag price = data.get(getAdapterPosition());
                    price.setExpandable(!price.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editprice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag price = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", price.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deleteprice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag price = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", price.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });

        }
    }
}
