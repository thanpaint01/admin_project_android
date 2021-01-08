package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        holder.textView_idorder.setText("" + order.getIddonhang());
        holder.textView_numdetaildata.setText("" + order.getData().size());
        holder.textView_datecreatedata.setText("" + order.getNgaytao());
        holder.textView_paymethoddata.setText("" + order.getHinhthuctt());
        holder.textView_orderusernamedata.setText("" + order.getTen());
        holder.textView_orderuserphonedata.setText("" + order.getSdt());
        holder.textView_orderlocationdata.setText("" + order.getDiachi());

        Picasso.get().load(Server.HOST + order.getImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgViewAItemCartOrder);
        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_order.setVisibility(isExpandable ? View.VISIBLE : View.GONE);


        // Create sub item view adapter
        AdapterOrderData adapterOrderData = new AdapterOrderData(order.getData());
        holder.recycleview_orderdata.setAdapter(adapterOrderData);
        holder.recycleview_orderdata.setRecycledViewPool(viewPool);

        switch (order.getIdtinhtrang()) {
            case "1":
                holder.button_order1.setText("Delivering");
                holder.button_order1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processorder("Delivering",order);
                        doRemove(position);
                    }
                });
                holder.button_order2.setText("Cancel");
                holder.button_order2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processorder("Cancel",order);
                        doRemove(position);
                    }
                });
                break;
            case "2":
                holder.button_order1.setText("Delivered");
                holder.button_order1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        processorder("Delivered",order);
                        doRemove(position);
                    }
                });
                holder.button_order2.setVisibility(View.GONE);
                break;
            case "3":
                holder.button_order1.setVisibility(View.GONE);
                holder.button_order2.setVisibility(View.GONE);
                break;
            case "4":
                holder.button_order1.setVisibility(View.GONE);
                holder.button_order2.setVisibility(View.GONE);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OrderAdapter extends RecyclerView.ViewHolder {
        TextView textView_idorder, textView_numdetaildata, textView_datecreatedata, textView_paymethoddata, textView_orderusernamedata, textView_orderuserphonedata, textView_orderlocationdata;
        RelativeLayout expandable_data_order;
        RecyclerView recycleview_orderdata;
        ImageView imgViewAItemCartOrder;
        LinearLayout lineCart;
        Button button_order1, button_order2;

        public OrderAdapter(@NonNull View itemView) {
            super(itemView);
            recycleview_orderdata = itemView.findViewById(R.id.recycleview_orderdata);
            textView_idorder = itemView.findViewById(R.id.textView_idorder);
            textView_numdetaildata = itemView.findViewById(R.id.textView_numdetaildata);
            textView_datecreatedata = itemView.findViewById(R.id.textView_datecreatedata);
            textView_paymethoddata = itemView.findViewById(R.id.textView_paymethoddata);
            textView_orderusernamedata = itemView.findViewById(R.id.textView_orderusernamedata);
            textView_orderuserphonedata = itemView.findViewById(R.id.textView_orderuserphonedata);
            textView_orderlocationdata = itemView.findViewById(R.id.textView_orderlocationdata);
            imgViewAItemCartOrder = itemView.findViewById(R.id.imgViewAItemCartOrder);

            button_order1 = itemView.findViewById(R.id.button_order1);
            button_order2 = itemView.findViewById(R.id.button_order2);

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

    private void processorder(String command, Order order) {
        String idstatus = "1";
        switch (command){
            case "Delivering": idstatus = "2";break;
            case "Delivered": idstatus = "3";break;
            case "Cancel": idstatus = "4";break;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String finalIdstatus = idstatus;
        StringRequest stringRequestCheckExist = new StringRequest(Request.Method.POST, Server.updaterow + "donhang", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("response" + response);
                if (response.trim().equals("success")) {
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idtinhtrang", finalIdstatus);
                params.put("iddonhang",""+order.getIddonhang());
                return params;
            }
        };
        requestQueue.add(stringRequestCheckExist);
    }

    public void doRemove(int position){
        data.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, data.size());
    }
}
