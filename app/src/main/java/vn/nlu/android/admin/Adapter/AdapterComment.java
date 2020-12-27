package vn.nlu.android.admin.Adapter;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.model.Comment;

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
        Button button_editcomment,button_deletecomment;

        public CommentAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idcomment = itemView.findViewById(R.id.textView_idcomment);
            textView_idusercomment = itemView.findViewById(R.id.textView_idusercomment);
            textView_productnamecomment = itemView.findViewById(R.id.textView_productnamecomment);
            textView_commentcomment = itemView.findViewById(R.id.textView_commentcomment);
            textView_activecomment = itemView.findViewById(R.id.textView_activecomment);

            linear_layout_commentrow = itemView.findViewById(R.id.linear_layout_commentrow);
            expandable_data_comment = itemView.findViewById(R.id.expandable_data_comment);
            button_editcomment = itemView.findViewById(R.id.button_editcomment);
            button_deletecomment = itemView.findViewById(R.id.button_deletecomment);


            linear_layout_commentrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = data.get(getAdapterPosition());
                    comment.setExpandable(!comment.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editcomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", comment.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deletecomment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Comment comment = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", comment.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });

        }
    }
}
