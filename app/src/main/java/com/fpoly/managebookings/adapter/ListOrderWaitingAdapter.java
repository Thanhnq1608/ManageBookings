package com.fpoly.managebookings.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingInterface;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingPresenter;
import com.fpoly.managebookings.views.orderBookingDetail.OrderBookingDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOrderWaitingAdapter extends RecyclerView.Adapter<ListOrderWaitingAdapter.ViewHolder> implements ListOrderWaitingInterface {
    private Context context;
    private ArrayList<OrderRoomBooked> orderRoomBookeds;
    private ListOrderWaitingPresenter mListOrderWaitingPresenter = new ListOrderWaitingPresenter(this);

    public ListOrderWaitingAdapter(Context context, ArrayList<OrderRoomBooked> orderRoomBookeds) {
        this.context = context;
        this.orderRoomBookeds = orderRoomBookeds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(orderRoomBookeds, new Comparator<OrderRoomBooked>() {
            @Override
            public int compare(OrderRoomBooked o1, OrderRoomBooked o2) {
                return o1.getTimeBookingStart().compareTo(o2.getTimeBookingStart());
            }
        });

        OrderRoomBooked orderRoomBooked = orderRoomBookeds.get(position);

        if (orderRoomBookeds.isEmpty()) {
            return;
        }

        holder.tvDate.setText(Formater.formatToDate(orderRoomBooked.getTimeBookingStart()));
        holder.tvTime.setText(Formater.formatToHour(orderRoomBooked.getTimeBookingStart()));
        holder.tvFullName.setText(orderRoomBooked.getFullName());
        holder.tvPhone.setText(String.valueOf(orderRoomBooked.getPhone()));
        holder.tvStatusOrder.setText(Formater.getBookingStatus(orderRoomBooked.getBookingStatus()));
        holder.imgRoom.setImageResource(R.drawable.sample_image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderBookingDetailActivity.class);
                intent.putExtra("ORDERROOMBOOKED",orderRoomBooked);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderRoomBookeds.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvDate,tvTime,tvFullName,tvPhone,tvStatusOrder;
        ImageView imgRoom;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.itemOrderWaiting);
            imgRoom = itemView.findViewById(R.id.imgImageRoom);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvFullName = itemView.findViewById(R.id.tvFullName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
//            textView14 = (TextView) findViewById(R.id.textView14);
            tvStatusOrder = itemView.findViewById(R.id.tvStatus);

        }
    }
}
