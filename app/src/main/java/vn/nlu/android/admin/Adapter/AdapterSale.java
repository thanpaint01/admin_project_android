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
import vn.nlu.android.admin.model.Sale;

public class AdapterSale extends RecyclerView.Adapter<AdapterSale.SaleAdapter> {
    ArrayList<Sale> data;
    Context context;

    public AdapterSale(ArrayList<Sale> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SaleAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_sale,parent,false);
        SaleAdapter adapter = new SaleAdapter(view);
        return new SaleAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSale.SaleAdapter holder, int position) {
        Sale sale = data.get(position);

        holder.textView_idsale.setText(""+ sale.getId());
        holder.textView_salesale.setText(""+ sale.getSale()+"%");
        holder.textView_daystartsale.setText(""+ sale.getNgaybdkm());
        holder.textView_dayendsale.setText(""+ sale.getNgayktkm());

        holder.textView_activesale.setText(""+ sale.getActive());


        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_sale.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SaleAdapter extends RecyclerView.ViewHolder {
        TextView textView_idsale,textView_salesale,textView_daystartsale,textView_dayendsale,textView_activesale;
        LinearLayout linear_layout_salerow;
        RelativeLayout expandable_data_sale;
        Button button_editsale,button_deletesale;

        public SaleAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idsale = itemView.findViewById(R.id.textView_idsale);
            textView_salesale = itemView.findViewById(R.id.textView_salesale);
            textView_daystartsale = itemView.findViewById(R.id.textView_daystartsale);
            textView_dayendsale = itemView.findViewById(R.id.textView_dayendsale);
            textView_activesale = itemView.findViewById(R.id.textView_activesale);

            linear_layout_salerow = itemView.findViewById(R.id.linear_layout_salerow);
            expandable_data_sale = itemView.findViewById(R.id.expandable_data_sale);
            button_editsale = itemView.findViewById(R.id.button_editsale);
            button_deletesale = itemView.findViewById(R.id.button_deletesale);


            linear_layout_salerow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sale sale = data.get(getAdapterPosition());
                    sale.setExpandable(!sale.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editsale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sale sale = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", sale.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deletesale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sale sale = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", sale.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });

        }
    }
}
