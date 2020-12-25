package vn.nlu.android.admin.Adapter;

import android.content.Context;
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
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.nlu.android.admin.R;
import vn.nlu.android.admin.model.User;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.UserAdapter> {
    ArrayList<User> data;
    Context context;

    public AdapterUser(ArrayList<User> data,Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_user,parent,false);
        UserAdapter adapter = new UserAdapter(view);
        return new UserAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUser.UserAdapter holder, int position) {
        User u = data.get(position);

        holder.textView_iduser.setText(""+u.getIduser());
        holder.textView_nameuser.setText(u.getTen());
        holder.textView_emailuser.setText(u.getEmail());
        holder.textView_phoneuser.setText(u.getSdt());
        holder.textView_addressuser.setText(u.getDiachi());
        holder.textView_usernameuser.setText(u.getTaikhoan());
        holder.textView_passworduser.setText(u.getMatkhau());
        holder.textView_birthdayruser.setText(u.getNgaysinh());
        holder.textView_genderuser.setText(u.getGioitinh());
        holder.textView_quyenuser.setText(""+u.getQuyen());
        holder.textView_activeuser.setText(""+u.getActive());

        Picasso.get().load(u.getImg())
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(holder.imgView_user);

        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_user.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserAdapter extends RecyclerView.ViewHolder {
        TextView textView_iduser,textView_nameuser,textView_emailuser,textView_phoneuser,textView_addressuser,
                textView_usernameuser,textView_passworduser,textView_birthdayruser,textView_genderuser,textView_quyenuser,textView_activeuser;
        LinearLayout linear_layout_userrow;
        RelativeLayout expandable_data_user;
        ImageView imgView_user;
        Button button_edituser;

        public UserAdapter(@NonNull View itemView) {
            super(itemView);
            textView_iduser = itemView.findViewById(R.id.textView_iduser);
            textView_nameuser = itemView.findViewById(R.id.textView_nameuser);
            textView_emailuser = itemView.findViewById(R.id.textView_emailuser);
            textView_phoneuser = itemView.findViewById(R.id.textView_phoneuser);
            textView_addressuser = itemView.findViewById(R.id.textView_addressuser);
            textView_usernameuser = itemView.findViewById(R.id.textView_usernameuser);
            textView_passworduser = itemView.findViewById(R.id.textView_passworduser);
            textView_birthdayruser = itemView.findViewById(R.id.textView_birthdayruser);
            textView_genderuser = itemView.findViewById(R.id.textView_genderuser);
            textView_quyenuser = itemView.findViewById(R.id.textView_quyenuser);
            textView_activeuser = itemView.findViewById(R.id.textView_activeuser);
            imgView_user = itemView.findViewById(R.id.imgView_user);

            linear_layout_userrow = itemView.findViewById(R.id.linear_layout_userrow);
            expandable_data_user = itemView.findViewById(R.id.expandable_data_user);
            button_edituser = itemView.findViewById(R.id.button_edituser);

            linear_layout_userrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User u = data.get(getAdapterPosition());
                    u.setExpandable(!u.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_edituser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User u = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putString("name",u.getTen());
                    b.putString("mail",u.getEmail());
                    b.putString("phone",u.getSdt());
                    b.putString("address",u.getDiachi());
                    b.putString("dob",u.getNgaysinh());
                    b.putString("gender",u.getGioitinh());
                    b.putInt("permisson",u.getQuyen());
                    b.putInt("active",u.getActive());
                    Intent i = new Intent();
                    i.putExtra("data",b);

                }
            });

        }
    }
}
