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
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListRoomEmptyAdapter extends RecyclerView.Adapter<ListRoomEmptyAdapter.ViewHolder> implements ListOrderWaitingInterface {
    private Context context;
    private ArrayList<RoomDetail> roomDetails;


    public ListRoomEmptyAdapter(Context context, ArrayList<RoomDetail> roomDetails) {
        this.context = context;
        this.roomDetails = roomDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(roomDetails, new Comparator<RoomDetail>() {
            @Override
            public int compare(RoomDetail o1, RoomDetail o2) {
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });

        RoomDetail roomDetail = roomDetails.get(position);

        if (roomDetails.isEmpty()) {
            return;
        }

        holder.tvNameRoom.setText(roomDetail.getRoomName());
        holder.tvKindOfRoom.setText(Formater.getKindOfRoom(roomDetail.getIdKindOfRoom()));
        holder.tvNumberOfPerson.setText(roomDetail.getMaximumNumberOfPeople() + " people");
        holder.tvPrice.setText(Formater.getFormatMoney(roomDetail.getRoomPrice()));
        holder.imgRoom.setImageResource(R.drawable.sample_image);
    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvNameRoom,tvKindOfRoom,tvNumberOfPerson,tvPrice;
        ImageView imgRoom;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgImageRoom);
            tvNameRoom = itemView.findViewById(R.id.tvNameRoom);
            tvKindOfRoom = itemView.findViewById(R.id.tvRoomType);
            tvPrice = itemView.findViewById(R.id.tvPriceRoom);
            tvNumberOfPerson = itemView.findViewById(R.id.tvNumberOfPerson);

        }
    }
}
