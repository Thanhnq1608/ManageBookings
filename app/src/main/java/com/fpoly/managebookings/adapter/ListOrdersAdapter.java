package com.fpoly.managebookings.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingInterface;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingPresenter;
import com.fpoly.managebookings.views.orderBookingDetail.OrderBookingDetailActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListOrdersAdapter extends RecyclerView.Adapter<ListOrdersAdapter.ViewHolder> implements ListOrderWaitingInterface, Filterable {
    private Context context;
    private ArrayList<OrderRoomBooked> orderRoomBookedFilter;
    private ArrayList<OrderRoomBooked> orderRoomBookeds;
    private ListOrderWaitingPresenter mListOrderWaitingPresenter = new ListOrderWaitingPresenter(this);
    private ListOrderFilterInterface mListOrderFilterInterface;

    public interface ListOrderFilterInterface {
        void listOrderFilter(ArrayList<OrderRoomBooked> orderRoomBookeds);
    }

    public ListOrdersAdapter(Context context, ArrayList<OrderRoomBooked> orderRoomBookeds) {
        this.context = context;
        this.orderRoomBookeds = orderRoomBookeds;
        orderRoomBookedFilter = new ArrayList<>(orderRoomBookeds);
        notifyDataSetChanged();
    }


    public ListOrdersAdapter(Context context, ArrayList<OrderRoomBooked> orderRoomBookeds, ListOrderFilterInterface mListOrderFilterInterface) {
        this.context = context;
        this.orderRoomBookeds = orderRoomBookeds;
        orderRoomBookedFilter = new ArrayList<>(orderRoomBookeds);
        this.mListOrderFilterInterface = mListOrderFilterInterface;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(orderRoomBookeds, new Comparator<OrderRoomBooked>() {
            @Override
            public int compare(OrderRoomBooked o1, OrderRoomBooked o2) {
                return o2.getCreatedAt().compareTo(o1.getCreatedAt());
            }
        });
        Log.e("=====", "" + orderRoomBookeds.get(position).getTimeBookingStart());

        OrderRoomBooked orderRoomBooked = orderRoomBookeds.get(position);

        if (orderRoomBookeds.isEmpty()) {
            return;
        }
        try {
            if (orderRoomBooked.getBookingStatus() != 3) {
                holder.tvDate.setText(Formater.formatDateToStringForCreateAt(orderRoomBooked.getCreatedAt()));
            } else {
                holder.tvDate.setText(Formater.formatDateTimeToString(orderRoomBooked.getTimeBookingEnd()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvFullName.setText(orderRoomBooked.getFullName());
        holder.tvPhone.setText(String.valueOf(orderRoomBooked.getPhone()));
        Formater.setStatusForOrder(orderRoomBooked.getBookingStatus(), holder.tvStatusOrder);
        holder.imgRoom.setImageResource(R.drawable.sample_image);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderBookingDetailActivity.class);
                intent.putExtra("ORDERROOMBOOKED", orderRoomBooked);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderRoomBookeds.size();
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            return null;
        }
        return mFilter;
    }

    private Filter mFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<OrderRoomBooked> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(orderRoomBookedFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (OrderRoomBooked item : orderRoomBookedFilter) {
                    if (item.getPhone().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            orderRoomBookeds.clear();
            orderRoomBookeds.addAll((ArrayList) results.values);
            if (orderRoomBookeds.get(0).getBookingStatus() == 3) {
                mListOrderFilterInterface.listOrderFilter(orderRoomBookeds);
            }
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTime, tvFullName, tvPhone, tvStatusOrder;
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
            tvStatusOrder = itemView.findViewById(R.id.tvStatus);

        }
    }
}
