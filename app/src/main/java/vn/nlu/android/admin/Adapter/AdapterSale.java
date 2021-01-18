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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vn.nlu.android.admin.Activity.sale.Edit;
import vn.nlu.android.admin.R;
import vn.nlu.android.admin.adminUI_Fragment.sale.AdminFragment_Sale;
import vn.nlu.android.admin.config.Server;
import vn.nlu.android.admin.model.Comment;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_sale, parent, false);
        SaleAdapter adapter = new SaleAdapter(view);
        return new SaleAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSale.SaleAdapter holder, int position) {
        Sale sale = data.get(position);

        holder.textView_idsale.setText("" + sale.getId());
        holder.textView_salesale.setText("" + sale.getSale() + "%");
        holder.textView_daystartsale.setText("" + sale.getNgaybdkm());
        holder.textView_dayendsale.setText("" + sale.getNgayktkm());

        holder.textView_activesale.setText("" + sale.getActive());


        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_sale.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SaleAdapter extends RecyclerView.ViewHolder {
        TextView textView_idsale, textView_salesale, textView_daystartsale, textView_dayendsale, textView_activesale;
        LinearLayout linear_layout_salerow;
        RelativeLayout expandable_data_sale;
        Button button_editsale, button_deletesale;

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
                    b.putString("sale", sale.getSale());
                    b.putString("ngaybdkm", sale.getNgaybdkm());
                    b.putString("ngayktkm", sale.getNgayktkm());
                    b.putInt("active", sale.getActive());
                    b.putInt("id", sale.getId());
                    Intent i = new Intent(context, Edit.class);
                    i.putExtra("data",b);
                    context.startActivity(i);
                }
            });
            button_deletesale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleterow + "khuyenmai", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success"))
                                    doRemove(getAdapterPosition());

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    onClick( dialog,  which);

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    Sale sale = data.get(getAdapterPosition());
                                    params.put("id", "" + sale.getId());
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

    public void doRemove(int position) {
        data.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, data.size());
    }
}


