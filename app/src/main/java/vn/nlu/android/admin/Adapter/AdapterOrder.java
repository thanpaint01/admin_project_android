package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Order;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderAdapter> {
    ArrayList<Order> data;
    Context context;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public AdapterOrder(ArrayList<Order> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_order, parent, false);
        return new OrderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrder.OrderAdapter holder, int position) {
        Order order = data.get(position);

        holder.textView_idorder.setText(""+ order.getIddonhang());
        holder.textView_numdetaildata.setText(""+ order.getData().size());
        holder.textView_datecreatedata.setText(""+ order.getNgaytao());
        holder.textView_paymethoddata.setText(""+ order.getHinhthuctt());
        holder.textView_orderusernamedata.setText(""+ order.getTen());
        holder.textView_orderuserphonedata.setText(""+ order.getSdt());
        holder.textView_orderlocationdata.setText(""+ order.getDiachi());

        Picasso.get().load(Server.HOST+ order.getImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgViewAItemCartOrder);
        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_order.setVisibility(isExpandable ? View.VISIBLE : View.GONE);


        // Create sub item view adapter
        AdapterOrderData adapterOrderData = new AdapterOrderData(order.getData());
        holder.recycleview_orderdata.setAdapter(adapterOrderData);
        holder.recycleview_orderdata.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OrderAdapter extends RecyclerView.ViewHolder {
        TextView textView_idorder, textView_numdetaildata,textView_datecreatedata,textView_paymethoddata,textView_orderusernamedata
                ,textView_orderuserphonedata,textView_orderlocationdata;
        RelativeLayout expandable_data_order;
        RecyclerView recycleview_orderdata;
        ImageView imgViewAItemCartOrder;
        LinearLayout lineCart;

        public OrderAdapter(@NonNull View itemView) {
            super(itemView);
            recycleview_orderdata = itemView.findViewById(R.id.recycleview_orderdata);
            System.out.println("dask" + recycleview_orderdata);
            textView_idorder = itemView.findViewById(R.id.textView_idorder);
            textView_numdetaildata = itemView.findViewById(R.id.textView_numdetaildata);
            textView_datecreatedata = itemView.findViewById(R.id.textView_datecreatedata);
            textView_paymethoddata = itemView.findViewById(R.id.textView_paymethoddata);
            textView_orderusernamedata = itemView.findViewById(R.id.textView_orderusernamedata);
            textView_orderuserphonedata = itemView.findViewById(R.id.textView_orderuserphonedata);
            textView_orderlocationdata = itemView.findViewById(R.id.textView_orderlocationdata);
            imgViewAItemCartOrder = itemView.findViewById(R.id.imgViewAItemCartOrder);

            lineCart = itemView.findViewById(R.id.lineCart);
            expandable_data_order = itemView.findViewById(R.id.expandable_data_order);
            lineCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = data.get(getAdapterPosition());
                    order.setExpandable(!order.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });


        }
    }

    public void doRemove(int position) {
        data.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, data.size());
    }

}
