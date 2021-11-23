package com.fpoly.managebookings.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingInterface;
import com.fpoly.managebookings.views.roomDetail.RoomDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListOrderDetailAdapter extends RecyclerView.Adapter<ListOrderDetailAdapter.ViewHolder>  {
    private Context context;
    private ArrayList<RoomDetail> roomDetails = new ArrayList<>();

    public ListOrderDetailAdapter(Context context, ArrayList<RoomDetail> roomDetails) {
        this.context = context;
        this.roomDetails = roomDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_of_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Collections.sort(roomDetails, new Comparator<RoomDetail>() {
            @Override
            public int compare(RoomDetail o1, RoomDetail o2) {
                return Formater.getKindOfRoom(o1.getIdKindOfRoom()).compareTo(Formater.getKindOfRoom(o2.getIdKindOfRoom()));
            }
        });

        RoomDetail roomDetail = roomDetails.get(position);
        if (roomDetails.isEmpty()) {
            return;
        }

        if (position > 0) {
            int i = position - 1;
            if (i < roomDetails.size() && roomDetails.get(position).getIdKindOfRoom() == roomDetails.get(i).getIdKindOfRoom()) {
                holder.title.setVisibility(View.GONE);
            }
        }

        holder.tvKindOfRoom.setText(Formater.getKindOfRoom(roomDetail.getIdKindOfRoom()));
        holder.tvPrice.setText(Formater.getFormatMoney(roomDetail.getRoomPrice()));
        holder.tvRoomName.setText(roomDetail.getRoomName());
        holder.tvRoomPosition.setText(roomDetail.getIdRoom());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomDetailActivity.class);
                intent.putExtra("ROOMDETAIL",roomDetail);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout title;
        TextView tvKindOfRoom, tvRoomName, tvPrice, tvRoomPosition;
        ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleRoom);
            tvKindOfRoom = itemView.findViewById(R.id.tvKindOfRoom);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvGiaTienPhong);
            tvRoomPosition = itemView.findViewById(R.id.tvRoomPosition);
            layout = itemView.findViewById(R.id.layout_room_detail_order);
        }
    }
}
