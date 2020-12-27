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
import vn.nlu.android.admin.model.Tag;

public class AdapterRom extends RecyclerView.Adapter<AdapterRom.RomAdapter> {
    ArrayList<Tag> data;
    Context context;

    public AdapterRom(ArrayList<Tag> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row_rom,parent,false);
        RomAdapter adapter = new RomAdapter(view);
        return new RomAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRom.RomAdapter holder, int position) {
        Tag rom = data.get(position);

        holder.textView_idrom.setText(""+ rom.getId());
        holder.textView_storagerom.setText(""+ rom.getData());
        holder.textView_activerom.setText(""+ rom.getActive());


        boolean isExpandable = data.get(position).isExpandable();
        holder.expandable_data_rom.setVisibility(isExpandable? View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RomAdapter extends RecyclerView.ViewHolder {
        TextView textView_idrom,textView_storagerom,textView_activerom;
        LinearLayout linear_layout_romrow;
        RelativeLayout expandable_data_rom;
        Button button_editrom,button_deleterom;

        public RomAdapter(@NonNull View itemView) {
            super(itemView);
            textView_idrom = itemView.findViewById(R.id.textView_idrom);
            textView_storagerom = itemView.findViewById(R.id.textView_storagerom);
            textView_activerom = itemView.findViewById(R.id.textView_activerom);



            linear_layout_romrow = itemView.findViewById(R.id.linear_layout_romrow);
            expandable_data_rom = itemView.findViewById(R.id.expandable_data_rom);
            button_editrom = itemView.findViewById(R.id.button_editrom);
            button_deleterom = itemView.findViewById(R.id.button_deleterom);

            linear_layout_romrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag rom = data.get(getAdapterPosition());
                    rom.setExpandable(!rom.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_editrom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag rom = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", rom.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });
            button_deleterom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Tag rom = data.get(getAdapterPosition());
                    Bundle b = new Bundle();
                    b.putInt("id", rom.getId());
                    Intent i = new Intent();
                    i.putExtra("data",b);
                }
            });

        }
    }
}
