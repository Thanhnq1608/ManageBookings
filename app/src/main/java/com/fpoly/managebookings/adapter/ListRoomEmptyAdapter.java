package com.fpoly.managebookings.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import com.fpoly.managebookings.api.pictureOfRoom.ApiPictureRoom;
import com.fpoly.managebookings.api.pictureOfRoom.ResponGetPictureRoom;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ResponseRemoveRoomInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.picture.PictureOfRoom;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingInterface;
import com.fpoly.managebookings.views.roomDetail.RoomDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListRoomEmptyAdapter extends RecyclerView.Adapter<ListRoomEmptyAdapter.ViewHolder> implements ResponGetPictureRoom, ListOrderWaitingInterface, ResponseRemoveRoomInterface {
    private Context context;
    private ArrayList<RoomDetail> roomDetails;
    private OrderRoomBooked orderRoomBooked;
    private List<RoomDetail> roomDetailList = new ArrayList<>();
    private ListRoomEmptyInterface mListRoomEmptyInterface;
    private boolean isCheck = false;
    private int isSort;
    private boolean isDataEntry = false;
    private ApiRoomDetail apiRoomDetail;
    private ApiPictureRoom apiPictureRoom;
    private List<String> imageRooms = new ArrayList<>();

    public interface ListRoomEmptyInterface {
        void roomsAreSelected(List<RoomDetail> roomDetails);
        void removeRoomSuccess(String status);
    }

    public ListRoomEmptyAdapter(Context context, ArrayList<RoomDetail> roomDetails, OrderRoomBooked orderRoomBooked, ListRoomEmptyInterface mListRoomEmptyInterface, int isSort, boolean isDataEntry) {
        this.context = context;
        this.roomDetails = roomDetails;
        this.orderRoomBooked = orderRoomBooked;
        this.mListRoomEmptyInterface = mListRoomEmptyInterface;
        this.isSort = isSort;
        this.isDataEntry = isDataEntry;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_detail, parent, false);
        apiRoomDetail = new ApiRoomDetail(this);
        apiPictureRoom = new ApiPictureRoom(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (isSort) {
            case 0:
                Collections.sort(roomDetails, new Comparator<RoomDetail>() {
                    @Override
                    public int compare(RoomDetail o1, RoomDetail o2) {
                        return Integer.valueOf(o1.getRoomPrice()).compareTo(Integer.valueOf(o2.getRoomPrice()));
                    }
                });
                break;
            case 1:
                holder.layotTitle.setVisibility(View.VISIBLE);
                Collections.sort(roomDetails, new Comparator<RoomDetail>() {
                    @Override
                    public int compare(RoomDetail o1, RoomDetail o2) {
                        return String.valueOf(o1.getIdKindOfRoom()).compareTo(String.valueOf(o2.getIdKindOfRoom()));
                    }
                });
                holder.tvTitle.setText(Formater.getKindOfRoom(roomDetails.get(position).getIdKindOfRoom()));
                if (position > 0) {
                    int i = position - 1;
                    if (i < roomDetails.size() && roomDetails.get(position).getIdKindOfRoom() == roomDetails.get(i).getIdKindOfRoom()) {
                        holder.layotTitle.setVisibility(View.GONE);
                    }
                }
                break;
            case 2:
                holder.layotTitle.setVisibility(View.VISIBLE);
                if (Integer.parseInt(roomDetails.get(position).getIdRoom().substring(0, 1)) == 1) {
                    holder.tvTitle.setText(roomDetails.get(position).getIdRoom().substring(0, 1) + "st floor");
                } else if (Integer.parseInt(roomDetails.get(position).getIdRoom().substring(0, 1)) == 2) {
                    holder.tvTitle.setText(roomDetails.get(position).getIdRoom().substring(0, 1) + "nd floor");
                } else if (Integer.parseInt(roomDetails.get(position).getIdRoom().substring(0, 1)) == 3) {
                    holder.tvTitle.setText(roomDetails.get(position).getIdRoom().substring(0, 1) + "rd floor");
                } else {
                    holder.tvTitle.setText(roomDetails.get(position).getIdRoom().substring(0, 1) + "th floor");
                }
                Collections.sort(roomDetails, new Comparator<RoomDetail>() {
                    @Override
                    public int compare(RoomDetail o1, RoomDetail o2) {
                        return Integer.valueOf(o1.getIdRoom().substring(0, 1)).compareTo(Integer.valueOf(o2.getIdRoom().substring(0, 1)));
                    }
                });
                if (position > 0) {
                    int i = position - 1;
                    if (i < roomDetails.size() && Integer.parseInt(roomDetails.get(position).getIdRoom().substring(0, 1)) == Integer.parseInt(roomDetails.get(i).getIdRoom().substring(0, 1))) {
                        holder.layotTitle.setVisibility(View.GONE);
                    }
                }
                break;
            default:
                Collections.sort(roomDetails, new Comparator<RoomDetail>() {
                    @Override
                    public int compare(RoomDetail o1, RoomDetail o2) {
                        return o1.getRoomName().compareTo(o2.getRoomName());
                    }
                });
        }

        RoomDetail roomDetail = roomDetails.get(position);

        if (roomDetails.isEmpty()) {
            return;
        }
        apiPictureRoom.getPictureRoom(roomDetail.getRoomPrice());

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
                        if (orderRoomBooked != null) {
                            Intent intent = new Intent(context, RoomDetailActivity.class);
                            intent.putExtra("ROOMDETAIL", roomDetail);
                            intent.putExtra("ORDERROOMBOOKED", orderRoomBooked);
                            context.startActivity(intent);
                        } else {
                            Intent intent = new Intent(context, RoomDetailActivity.class);
                            intent.putExtra("ROOMDETAIL", roomDetail);
                            context.startActivity(intent);
                        }
                    }
                }
            });

        }

        if (isDataEntry == false && orderRoomBooked == null) {
            holder.layout_item_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RoomDetailActivity.class);
                    intent.putExtra("ROOMDETAIL", roomDetail);
                    context.startActivity(intent);
                }
            });
        }

        if (isDataEntry) {
            holder.layout_item_room.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (holder.img_close.getVisibility() == View.GONE && isCheck == false) {
                        holder.layout_item_room.setBackgroundResource(android.R.color.system_accent3_100);
                        isCheck = true;
                        holder.img_close.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });
            holder.layout_item_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCheck) {
                        isCheck = false;
                        holder.layout_item_room.setBackgroundResource(R.drawable.background_shadow_layout_item);
                        holder.img_close.setVisibility(View.GONE);
                    }else {
                        Intent intent = new Intent(context, RoomDetailActivity.class);
                        intent.putExtra("ROOMDETAIL", roomDetail);
                        intent.putExtra("DATAENTRY", true);
                        context.startActivity(intent);
                    }

                }
            });
        }

        holder.tvNameRoom.setText(roomDetail.getRoomName());
        holder.tvKindOfRoom.setText(Formater.getKindOfRoom(roomDetail.getIdKindOfRoom()));
        holder.tvNumberOfPerson.setText(roomDetail.getMaximumNumberOfPeople() + " people");
        holder.tvPrice.setText(Formater.getFormatMoney(roomDetail.getRoomPrice()));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(imageRooms.get(position)).placeholder(R.drawable.ic_image).error(R.drawable.sample_image).into(holder.imgRoom);
            }
        }, 4000);
        holder.tvRoomStatus.setText(Formater.getRoomStatus(roomDetail.getRoomStatus()));
        holder.img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiRoomDetail.removeRoom(roomDetail.getId());
            }
        });

        switch (roomDetail.getRoomStatus()) {
            case 0:
                holder.tvRoomStatus.setTextColor(0xFF28B237);
                break;
            case 1:
                holder.tvRoomStatus.setTextColor(0xFFFFC107);
                break;
            case 2:
                holder.tvRoomStatus.setTextColor(0xFFF10A0A);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return roomDetails.size();
    }

    @Override
    public void response(String status) {
        mListRoomEmptyInterface.removeRoomSuccess(status);
    }

    @Override
    public void responsePicture(PictureOfRoom pictureOfRoom) {
        this.imageRooms.add(pictureOfRoom.getPicture()[0]);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameRoom, tvKindOfRoom, tvNumberOfPerson, tvPrice, tvRoomStatus, tvTitle;
        ImageView imgRoom, img_check, img_close;
        ConstraintLayout layout_item_room;
        LinearLayout layotTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRoom = itemView.findViewById(R.id.imgImageRoom);
            tvNameRoom = itemView.findViewById(R.id.tvNameRoom);
            tvKindOfRoom = itemView.findViewById(R.id.tvRoomType);
            tvPrice = itemView.findViewById(R.id.tvPriceRoom);
            tvNumberOfPerson = itemView.findViewById(R.id.tvNumberOfPerson);
            layout_item_room = itemView.findViewById(R.id.layout_item_room);
            img_check = itemView.findViewById(R.id.img_check);
            tvRoomStatus = itemView.findViewById(R.id.tv_room_status);
            tvTitle = itemView.findViewById(R.id.tv_title_item_room);
            layotTitle = itemView.findViewById(R.id.layout_title_item_room);
            img_close = itemView.findViewById(R.id.img_close);
        }
    }
}
