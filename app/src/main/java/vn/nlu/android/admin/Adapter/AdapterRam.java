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

public class AdapterRam extends RecyclerView.Adapter<AdapterRam.RamAdapter> {
    ArrayList<Tag> data;
    Context context;

    public AdapterRam(ArrayList<Tag> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RamAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_ram,parent,false);
        RamAdapter adapter = new RamAdapter(view);
        return new RamAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRam.RamAdapter holder, int position) {
        Tag ram = data.get(position);

        holder.textView_idram.setText(""+ ram.getId());
        holder.textView_storageram.setText(""+ ram.getData());
        holder.textView_activeram.setText(""+ ram.getActive());


        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_ram.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RamAdapter extends RecyclerView.ViewHolder {
        TextView textView_idram,textView_storageram,textView_activeram;
        LinearLayout linear_layout_ramrow;
        RelativeLayout expandable_data_ram;
        Button button_editram,button_deleteram;

        public RamAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idram = itemView.findViewById(R.id.textView_idram);
            textView_storageram = itemView.findViewById(R.id.textView_storageram);
            textView_activeram = itemView.findViewById(R.id.textView_activeram);



            linear_layout_ramrow = itemView.findViewById(R.id.linear_layout_ramrow);
            expandable_data_ram = itemView.findViewById(R.id.expandable_data_ram);
            button_editram = itemView.findViewById(R.id.button_editram);
            button_deleteram = itemView.findViewById(R.id.button_deleteram);

            linear_layout_ramrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag ramRomBattery = data.get(getAdapterPosition());
                    ramRomBattery.setExpandable(!ramRomBattery.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag ramRomBattery = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", ramRomBattery.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deleteram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleterow+"ram", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    doRemove(getAdapterPosition());
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    Tag ram = data.get(getAdapterPosition());
                                    params.put("id",""+ram.getId());
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

