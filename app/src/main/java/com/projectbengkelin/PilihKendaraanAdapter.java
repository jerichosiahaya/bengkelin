package com.projectbengkelin;

/**
 * Original Coder: Ricky Ng
 *
 * NOTES:
 * Adapter ini untuk yang pilih kendaraan saat mau booking servis
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PilihKendaraanAdapter extends RecyclerView.Adapter<PilihKendaraanAdapter.ViewHolder> {


    private List<ListKendaraan> listKendaraans;
    private Context context, context2;
    Intent intent;
    private int idServis;


    public PilihKendaraanAdapter(List<ListKendaraan> listKendaraans,int idServis ,Context context) {
        this.listKendaraans = listKendaraans;
        this.context = context;
        this.idServis = idServis;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_kendaraan,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListKendaraan listKendaraan= listKendaraans.get(position);

        holder.txtid.setText(String.valueOf(listKendaraan.getId()));
        holder.txtjenis.setText(listKendaraan.getJenis());
        holder.txttipe.setText(listKendaraan.getTipe());
        holder.txttahun.setText(listKendaraan.getTahun());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            // list kendaraan saat diklik langsung dialihkan ke page booking servis
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, "You clicked " + listKendaraan.getTipe(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (context, Booking.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idKendaraan", listKendaraan.getId());
                intent.putExtra("idServis", idServis);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listKendaraans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtid;
        public TextView txttipe;
        public TextView txtjenis;
        public TextView txttahun;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtid = (TextView) itemView.findViewById(R.id.txtid);
            txttipe = (TextView) itemView.findViewById(R.id.txttipe);
            txtjenis = (TextView) itemView.findViewById(R.id.txtjenis);
            txttahun = (TextView) itemView.findViewById(R.id.txttahun);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.relative);

        }
    }

}