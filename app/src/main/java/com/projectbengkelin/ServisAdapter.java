package com.projectbengkelin;

/**
 * Original Coder: Ricky Ng
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServisAdapter extends RecyclerView.Adapter<ServisAdapter.ViewHolder> {


    private List<ListServisDetail> listServiss;
    private Context context;

    public ServisAdapter(List<ListServisDetail> listServiss, Context context) {
        this.listServiss = listServiss;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_servis_detail,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListServisDetail listServisDetail= listServiss.get(position);

        holder.txtdetail.setText(listServisDetail.getDetail());
        holder.txtharga.setText(String.valueOf(listServisDetail.getHarga()));


    }

    @Override
    public int getItemCount() {
        return listServiss.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtdetail;
        public TextView txtharga;


        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdetail = (TextView) itemView.findViewById(R.id.txtdetail);
            txtharga = (TextView) itemView.findViewById(R.id.txtharga);


        }
    }

}