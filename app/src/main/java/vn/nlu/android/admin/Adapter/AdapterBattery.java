package vn.nlu.android.admin.Adapter;

import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Tag;

public class AdapterBattery extends RecyclerView.Adapter<AdapterBattery.BatteryAdapter> {
    ArrayList<Tag> data;
    Context context;

    public AdapterBattery(ArrayList<Tag> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public BatteryAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_pin,parent,false);
        BatteryAdapter adapter = new BatteryAdapter(view);
        return new BatteryAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBattery.BatteryAdapter holder, int position) {
        Tag battery = data.get(position);

        holder.textView_idpin.setText(""+ battery.getId());
        holder.textView_storagepin.setText(""+ battery.getData());
        holder.textView_activepin.setText(""+ battery.getActive());


        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_pin.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BatteryAdapter extends RecyclerView.ViewHolder {
        TextView textView_idpin,textView_storagepin,textView_activepin;
        LinearLayout linear_layout_pinrow;
        RelativeLayout expandable_data_pin;
        Button button_editpin,button_deletepin;

        public BatteryAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idpin = itemView.findViewById(R.id.textView_idpin);
            textView_storagepin = itemView.findViewById(R.id.textView_storagepin);
            textView_activepin = itemView.findViewById(R.id.textView_activepin);



            linear_layout_pinrow = itemView.findViewById(R.id.linear_layout_pinrow);
            expandable_data_pin = itemView.findViewById(R.id.expandable_data_pin);
            button_editpin = itemView.findViewById(R.id.button_editorder);
            button_deletepin = itemView.findViewById(R.id.button_deleteorder);

            linear_layout_pinrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag pin = data.get(getAdapterPosition());
                    pin.setExpandable(!pin.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editpin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag rom = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", rom.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deletepin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleterow+"pin", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success"))
                                        doRemove(getAdapterPosition());
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    Tag pin = data.get(getAdapterPosition());
                                    params.put("id",""+pin.getId());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);

                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
        }
    }
    public void doRemove(int position){
        data.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, data.size());
    }
}

