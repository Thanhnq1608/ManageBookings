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
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingInterface;
import com.fpoly.managebookings.views.roomDetail.RoomDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListRoomEmptyAdapter extends RecyclerView.Adapter<ListRoomEmptyAdapter.ViewHolder> implements ListOrderWaitingInterface {
    private Context context;
    private ArrayList<RoomDetail> roomDetails;
    private OrderRoomBooked orderRoomBooked;
    private List<RoomDetail> roomDetailList = new ArrayList<>();
    private ListRoomEmptyInterface mListRoomEmptyInterface;
    private boolean isCheck = false;

    public interface ListRoomEmptyInterface {
        void roomsAreSelected(List<RoomDetail> roomDetails);
    }


    public ListRoomEmptyAdapter(Context context, ArrayList<RoomDetail> roomDetails, OrderRoomBooked orderRoomBooked, ListRoomEmptyInterface mListRoomEmptyInterface) {
        this.context = context;
        this.roomDetails = roomDetails;
        this.orderRoomBooked = orderRoomBooked;
        this.mListRoomEmptyInterface = mListRoomEmptyInterface;
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

        if (orderRoomBooked != null) {
            holder.layout_item_room.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (holder.img_check.getVisibility() == View.INVISIBLE && isCheck == false) {
                        holder.layout_item_room.setBackgroundResource(android.R.color.system_accent3_100);
                        isCheck = true;
                        holder.img_check.setVisibility(View.VISIBLE);
                        roomDetailList.add(roomDetail);
                        mListRoomEmptyInterface.roomsAreSelected(roomDetailList);
                    }
                    return true;
                }
            });
            holder.layout_item_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCheck) {
                        if (holder.img_check.getVisibility() == View.VISIBLE) {
                            holder.img_check.setVisibility(View.INVISIBLE);
                            holder.layout_item_room.setBackgroundResource(R.drawable.background_shadow_layout_item);
                            roomDetailList.remove(roomDetail);
                            mListRoomEmptyInterface.roomsAreSelected(roomDetailList);
                            if (roomDetailList.isEmpty()) {
                                isCheck = false;
                            }
                        } else {
                            holder.layout_item_room.setBackgroundResource(android.R.color.system_accent3_100);
                            holder.img_check.setVisibility(View.VISIBLE);
                            roomDetailList.add(roomDetail);
                            mListRoomEmptyInterface.roomsAreSelected(roomDetailList);
                        }
                    } else {
                        Intent intent = new Intent(context, RoomDetailActivity.class);
                        intent.putExtra("ROOMDETAIL", roomDetail);
                        context.startActivity(intent);
                    }
                }
            });
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameRoom, tvKindOfRoom, tvNumberOfPerson, tvPrice;
        ImageView imgRoom, img_check;
        ConstraintLayout layout_item_room;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgImageRoom);
            tvNameRoom = itemView.findViewById(R.id.tvNameRoom);
            tvKindOfRoom = itemView.findViewById(R.id.tvRoomType);
            tvPrice = itemView.findViewById(R.id.tvPriceRoom);
            tvNumberOfPerson = itemView.findViewById(R.id.tvNumberOfPerson);
            layout_item_room = itemView.findViewById(R.id.layout_item_room);
            img_check = itemView.findViewById(R.id.img_check);
        }
    }
}
