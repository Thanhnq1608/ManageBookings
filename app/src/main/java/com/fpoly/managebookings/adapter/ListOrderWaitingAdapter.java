package com.fpoly.managebookings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.models.OrderRoomBooked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOrderWaitingAdapter extends RecyclerView.Adapter<ListOrderWaitingAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderRoomBooked> orderRoomBookeds;

    public ListOrderWaitingAdapter(Context context, ArrayList<OrderRoomBooked> orderRoomBookeds) {
        this.context = context;
        this.orderRoomBookeds = orderRoomBookeds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phong_cho_xac_nhan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(orderRoomBookeds, new Comparator<OrderRoomBooked>() {
            @Override
            public int compare(OrderRoomBooked o1, OrderRoomBooked o2) {
                return o1.getTimeBooking().compareTo(o2.getTimeBooking());
            }
        });

        OrderRoomBooked orderRoomBooked = orderRoomBookeds.get(position);

        if (orderRoomBookeds.isEmpty()) {
            return;
        }

        holder.tvDate.setText(orderRoomBooked.get);



    }

    @Override
    public int getItemCount() {
        return orderRoomBookeds.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvDate,tvTime,tvFullName,tvPhone,tvMail,tvNumberOfPerson,tvStatusOrder;
        ImageView imgRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgImageRoom);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvMail = itemView.findViewById(R.id.tvMail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvNumberOfPerson = itemView.findViewById(R.id.tvNumberOfPerson);
//            textView14 = (TextView) findViewById(R.id.textView14);
            tvStatusOrder = itemView.findViewById(R.id.tvStatusOrder);

        }
    }
}
