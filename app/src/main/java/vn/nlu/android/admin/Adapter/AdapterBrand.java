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
import com.android.volley.DefaultRetryPolicy;
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

import vn.nlu.android.admin.Activity.brand.Edit;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.config.Server;
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
                    b.putString("name", brand.getNameOfBrand());
                    b.putString("img", brand.getRemovehost());
                    b.putInt("active", brand.getActive());
                    Intent i = new Intent(context, Edit.class);
                    i.putExtra("data",b);
                    context.startActivity(i);
                }
            });
            button_deletebrand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleterow+"hang", new Response.Listener<String>() {
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
                                    Brand brand = data.get(getAdapterPosition());
                                    params.put("id",""+brand.getId());
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest);
                            stringRequest.setRetryPolicy(new DefaultRetryPolicy( 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

