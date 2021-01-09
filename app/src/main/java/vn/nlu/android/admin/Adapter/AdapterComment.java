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
import vn.nlu.android.admin.model.Comment;
import vn.nlu.android.admin.model.Tag;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.CommentAdapter> {
    ArrayList<Comment> data;
    Context context;

    public AdapterComment(ArrayList<Comment> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_comment,parent,false);
        CommentAdapter adapter = new CommentAdapter(view);
        return new CommentAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComment.CommentAdapter holder, int position) {
        Comment comment = data.get(position);

        holder.textView_idcomment.setText(""+ comment.getId());
        holder.textView_idusercomment.setText(""+ comment.getIduser());
        holder.textView_productnamecomment.setText(""+ comment.getProductname());
        holder.textView_commentcomment.setText(""+ comment.getComment());
        holder.textView_activecomment.setText(""+ comment.getActive());
        String status = comment.getActive() ==1?"Disable":"Active";
        holder.button_editstatus.setText(""+ status);

        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_comment.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CommentAdapter extends RecyclerView.ViewHolder {
        TextView textView_idcomment,textView_idusercomment,textView_productnamecomment,textView_activecomment,textView_commentcomment;
        LinearLayout linear_layout_commentrow;
        RelativeLayout expandable_data_comment;
        Button button_editstatus;

        public CommentAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idcomment = itemView.findViewById(R.id.textView_idcomment);
            textView_idusercomment = itemView.findViewById(R.id.textView_idusercomment);
            textView_productnamecomment = itemView.findViewById(R.id.textView_productnamecomment);
            textView_commentcomment = itemView.findViewById(R.id.textView_commentcomment);
            textView_activecomment = itemView.findViewById(R.id.textView_activecomment);

            linear_layout_commentrow = itemView.findViewById(R.id.linear_layout_commentrow);
            expandable_data_comment = itemView.findViewById(R.id.expandable_data_comment);
            button_editstatus = itemView.findViewById(R.id.button_editstatus);


            linear_layout_commentrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = data.get(getAdapterPosition());
                    comment.setExpandable(!comment.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editstatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = data.get(getAdapterPosition());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.updaterow+"binhluan", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if(response.equals("success"))
                                        doUpdate(getAdapterPosition());
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    Comment comment = data.get(getAdapterPosition());
                                    int status = comment.getActive() ==1?0:1;
                                    params.put("active",""+status);
                                    params.put("id",""+comment.getId());
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
    public void doUpdate(int position){
        Comment c = data.get(position);
        int status = c.getActive() ==1?0:1;
        data.get(position).setActive(status);
        this.notifyItemChanged(position);
    }
}

