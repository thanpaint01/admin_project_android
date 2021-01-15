package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.OrderData;

public class AdapterOrderData extends RecyclerView.Adapter<AdapterOrderData.OrderDataAdapter> {
    ArrayList<OrderData> data;

    public AdapterOrderData(ArrayList<OrderData> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public OrderDataAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_orderdata, parent, false);
        return new OrderDataAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderData.OrderDataAdapter holder, int position) {
        OrderData orderdata = data.get(position);
        holder.textView_idorderdata.setText(""+ orderdata.getIdchitiet());
        holder.textView_nameproductdata.setText(""+ orderdata.getTensp());
        holder.textView_amountdata.setText(""+ orderdata.getSoluong());
        holder.textView_pricedata.setText(""+ orderdata.getGia());
        holder.textView_saledata.setText(""+ orderdata.getGiamgia());
        holder.textView_totaldata.setText(""+ orderdata.getTongcong());

        Picasso.get().load(Server.HOST+ orderdata.getImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgViewOrderData);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OrderDataAdapter extends RecyclerView.ViewHolder {
        TextView textView_idorderdata, textView_nameproductdata,textView_amountdata,textView_pricedata,textView_saledata,textView_totaldata;
        ImageView imgViewOrderData;
        public OrderDataAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idorderdata = itemView.findViewById(R.id.textView_idorderdata);
            textView_nameproductdata = itemView.findViewById(R.id.textView_nameproductdata);
            textView_amountdata = itemView.findViewById(R.id.textView_amountdata);
            textView_pricedata = itemView.findViewById(R.id.textView_pricedata);
            textView_saledata = itemView.findViewById(R.id.textView_saledata);
            textView_totaldata = itemView.findViewById(R.id.textView_totaldata);
            imgViewOrderData = itemView.findViewById(R.id.imgViewOrder);
        }
    }
}
