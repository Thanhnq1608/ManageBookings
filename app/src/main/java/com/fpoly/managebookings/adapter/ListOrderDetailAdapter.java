package com.fpoly.managebookings.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.fpoly.managebookings.views.orderBookingDetail.OrderBookingDetailActivity;
import com.fpoly.managebookings.views.roomDetail.RoomDetailActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListOrderDetailAdapter extends RecyclerView.Adapter<ListOrderDetailAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RoomDetail> roomDetails = new ArrayList<>();
    private List<RoomDetail> listIdRoom = new ArrayList<>();
    private RoomSelectedInterface mRoomSelectedInterface;
    private boolean isCheck = false;
    private OrderRoomBooked orderRoomBooked;

    public interface RoomSelectedInterface {
        void listIdRoomSelected(List<RoomDetail> roomIds);
    }

    public ListOrderDetailAdapter(Context context, ArrayList<RoomDetail> roomDetails, RoomSelectedInterface mRoomSelectedInterface, OrderRoomBooked orderRoomBooked) {
        this.context = context;
        this.roomDetails = roomDetails;
        this.mRoomSelectedInterface = mRoomSelectedInterface;
        this.orderRoomBooked = orderRoomBooked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_of_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
        holder.layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (holder.btn_checked.getVisibility() == View.INVISIBLE && isCheck == false && orderRoomBooked.getBookingStatus() != 3) {
                    holder.layout.setBackgroundResource(android.R.color.system_accent3_100);
                    isCheck = true;
                    holder.btn_checked.setVisibility(View.VISIBLE);
                    listIdRoom.add(roomDetail);
                    mRoomSelectedInterface.listIdRoomSelected(listIdRoom);
                }

                return true;
            }
        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    if (holder.btn_checked.getVisibility() == View.VISIBLE) {
                        holder.btn_checked.setVisibility(View.INVISIBLE);
                        holder.layout.setBackgroundResource(R.drawable.background_shadow_layout_item);
                        listIdRoom.remove(roomDetail);
                        mRoomSelectedInterface.listIdRoomSelected(listIdRoom);
                        if (listIdRoom.isEmpty()) {
                            isCheck = false;
                        }
                    } else {
                        holder.btn_checked.setVisibility(View.VISIBLE);
                        holder.layout.setBackgroundResource(android.R.color.system_accent3_100);
                        listIdRoom.add(roomDetail);
                        mRoomSelectedInterface.listIdRoomSelected(listIdRoom);
                    }

                } else {
                    Intent intent = new Intent(context, RoomDetailActivity.class);
                    intent.putExtra("ROOMDETAIL", roomDetail);
                    intent.putExtra("HIDEBUTTON", true);
                    context.startActivity(intent);
                }
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
        LinearLayout layout;
        ImageView btn_checked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleRoom);
            tvKindOfRoom = itemView.findViewById(R.id.tvKindOfRoom);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvGiaTienPhong);
            tvRoomPosition = itemView.findViewById(R.id.tvRoomPosition);
            layout = itemView.findViewById(R.id.layout_room_detail_order);
            btn_checked = itemView.findViewById(R.id.btn_checked);
        }
    }
}
