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
import vn.nlu.android.admin.model.Slide;
import vn.nlu.android.admin.model.User;

public class AdapterSlide extends RecyclerView.Adapter<AdapterSlide.SlideAdapter> {
    ArrayList<Slide> data;
    Context context;

    public AdapterSlide(ArrayList<Slide> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SlideAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_slide, parent, false);
        SlideAdapter adapter = new SlideAdapter(view);
        return new SlideAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSlide.SlideAdapter holder, int position) {
        Slide slide = data.get(position);

        holder.textView_idslide.setText("" + slide.getId());
        holder.textView_activeslide.setText("" + slide.getActive());
        Picasso.get().load(slide.getResouceImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_slide);

        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_slide.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class SlideAdapter extends RecyclerView.ViewHolder {
        TextView textView_idslide, textView_activeslide;
        LinearLayout linear_layout_sliderow;
        RelativeLayout expandable_data_slide;
        ImageView imgView_slide;
        Button button_editslide, button_deleteslide;

        public SlideAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idslide = itemView.findViewById(R.id.textView_idslide);
            textView_activeslide = itemView.findViewById(R.id.textView_activeslide);

            linear_layout_sliderow = itemView.findViewById(R.id.linear_layout_sliderow);
            expandable_data_slide = itemView.findViewById(R.id.expandable_data_slide);
            button_editslide = itemView.findViewById(R.id.button_editslide);
            button_deleteslide = itemView.findViewById(R.id.button_deleteslide);

            imgView_slide = itemView.findViewById(R.id.imgView_slide);

            linear_layout_sliderow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Slide slide = data.get(getAdapterPosition());
                    slide.setExpandable(!slide.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editslide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Slide slide = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", slide.getId());
                    Intent i = new Intent();
                    i.putExtra("data", b);
                }
            });
            button_deleteslide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleterow + "slide", new Response.Listener<String>() {
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
                                    Slide slide = data.get(getAdapterPosition());
                                    params.put("id", "" + slide.getId());
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
                            // Do nothing
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

