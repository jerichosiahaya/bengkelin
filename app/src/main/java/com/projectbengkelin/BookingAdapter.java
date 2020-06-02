package com.projectbengkelin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {


    private List<ListBooking> listBookings;
    private Context context;

    public BookingAdapter(List<ListBooking> listBookings, Context context) {
        this.listBookings = listBookings;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_booking,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListBooking listBooking= listBookings.get(position);

        holder.txtidbooking.setText(String.valueOf(listBooking.getIdBooking()));
        holder.txttipemobil.setText(listBooking.getJenisKendaraan());
        holder.txtjenisservis.setText(listBooking.getJenisServis());
        holder.txtpickup.setText(listBooking.getPickup());
        holder.txttanggal.setText(listBooking.getTanggal());
        holder.txtestimasi.setText(listBooking.getEstimasi());
        holder.txttotalbiaya.setText(String.valueOf(listBooking.getTotalBiaya()));

        holder.btnDetail.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, DetailServis.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idBooking", listBooking.getIdBooking());
                intent.putExtra("total_biaya", listBooking.getTotalBiaya());
                context.startActivity(intent);
            }
        } );




        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return listBookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtidbooking;
        public TextView txttipemobil;
        public TextView txtjenisservis;
        public TextView txttanggal;
        public TextView txtpickup;
        public TextView txtestimasi;
        public TextView txttotalbiaya;

        public Button btnDetail;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtidbooking = (TextView) itemView.findViewById(R.id.txtidbooking);
            txttipemobil = (TextView) itemView.findViewById(R.id.txttipemobil);
            txtjenisservis = (TextView) itemView.findViewById(R.id.txtjenisservis);
            txttanggal = (TextView) itemView.findViewById(R.id.txttanggal);
            txtpickup = (TextView) itemView.findViewById(R.id.txtpickup);
            btnDetail = (Button) itemView.findViewById(R.id.btnDetail);
            txtestimasi = (TextView) itemView.findViewById(R.id.txtestimasi);
           txttotalbiaya = (TextView) itemView.findViewById(R.id.txttotalbiaya);
            relativeLayout= (RelativeLayout) itemView.findViewById(R.id.relative);

        }
    }

}