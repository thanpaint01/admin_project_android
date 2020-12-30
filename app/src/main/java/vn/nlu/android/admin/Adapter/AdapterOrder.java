package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
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
import vn.nlu.android.admin.model.OrderData;

public class AdapterOrder extends RecyclerView.Adapter<AdapterOrder.OrderAdapter> {
    ArrayList<Order> data;
    Context context;
    RecyclerView recyclerView;

    public AdapterOrder(ArrayList<Order> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_order, parent, false);
        OrderAdapter adapter = new OrderAdapter(view);
        recyclerView = view.findViewById(R.id.recycleview_order);
        return new OrderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrder.OrderAdapter holder, int position) {
        Order order = data.get(position);
        holder.textshowtext.setText(order.toString());
        ArrayList<OrderData> orderData = order.getData();

        holder.textshowtext2.setText(orderData.toString());

        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_order.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class OrderAdapter extends RecyclerView.ViewHolder {
        TextView textshowtext, textshowtext2;
        RelativeLayout expandable_data_order;
        LinearLayout linear_layout_orderrow;

        public OrderAdapter(@NonNull View itemView) {
            super(itemView);
            textshowtext = itemView.findViewById(R.id.textshowtext);
            textshowtext2 = itemView.findViewById(R.id.textshowtext2);

            linear_layout_orderrow = itemView.findViewById(R.id.linear_layout_orderrow);
            expandable_data_order = itemView.findViewById(R.id.expandable_data_order);


            linear_layout_orderrow.setOnClickListener(new View.OnClickListener() {
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

//    public void changedataAfterEdit(int position) {
//        User user = data.get(position);
//        user.setTen(Application.getPrefranceData("edit-name"));
//        user.setEmail(Application.getPrefranceData("edit-email"));
//        user.setSdt(Application.getPrefranceData("edit-phone"));
//        user.setDiachi(Application.getPrefranceData("edit-address"));
//        user.setTaikhoan(Application.getPrefranceData("edit-username"));
//        user.setMatkhau(Application.getPrefranceData("edit-password"));
//        user.setNgaysinh(Application.getPrefranceData("edit-birthday"));
//        user.setGioitinh(Application.getPrefranceData("edit-gender"));
//        user.setQuyen(Application.getPrefranceDataInt("edit-permisson"));
//        user.setActive(Application.getPrefranceDataInt("edit-active"));
//
//        doRemove(position);
//        data.add(position,user);
//        this.notifyItemChanged(position,user);
//    }

}
