package com.fpoly.managebookings.tool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.orderDetail.ApiOrderDetail;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookingDetailInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class DialogPayment extends BottomSheetDialogFragment implements ApiOrderBookingDetailInterface, ApiRoomDetailInterface {
    private View view;
    private ImageView btnCancelFilter;
    private TextView tvTitleDialog;
    private TextView btnConfirmFilter;
    private LinearLayout layoutExtraService;
    private TextView minus1;
    private TextView quantily1;
    private TextView add1;
    private TextView minus2;
    private TextView quantily2;
    private TextView add2;
    private TextView minus3;
    private TextView quantily13;
    private TextView add3;
    private TextView minus4;
    private TextView quantily4;
    private TextView add4;
    private LinearLayout layoutPayment;
    private TextView tvRoomCharge;
    private TextView tvAdvanceDeposit;
    private TextView tvExtraService;
    private TextView tvVAT;
    private TextView tvTotal, tvPaymentAmount;
    private EditText edtPayingCustomers;
    private TextView tvErrorPaying;
    private LinearLayout layoutPaymentSuccess;
    private TextView tvExcessCash;
    private int price;
    private int advanceDeposit;
    private double totalPayment;
    private int extra = 0;
    private boolean value = false;
    private OrderRoomBooked orderRoomBooked;
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);
    private ApiOrderDetail mApiOrderDetail = new ApiOrderDetail();
    private ArrayList<RoomDetail> roomDetails;

    public DialogPayment(OrderRoomBooked orderRoomBooked, ArrayList<RoomDetail> roomDetails) {
        this.orderRoomBooked = orderRoomBooked;
        this.roomDetails = roomDetails;
        this.price = orderRoomBooked.getTotalRoomRate();
        this.advanceDeposit = orderRoomBooked.getAdvanceDeposit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_payment, container, false);

        findViewById(view);
        changeQuantily(add1, minus1, quantily1);
        changeQuantily(add2, minus2, quantily2);
        changeQuantily(add3, minus3, quantily13);
        changeQuantily(add4, minus4, quantily4);

        btnCancelFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutExtraService.getVisibility() == View.VISIBLE) {
                    dismiss();
                } else if (layoutPayment.getVisibility() == View.VISIBLE) {
                    layoutPayment.setVisibility(View.GONE);
                    layoutExtraService.setVisibility(View.VISIBLE);
                } else {
                    value = true;
                    onClickDone();
                }
            }
        });

        edtPayingCustomers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    if (Integer.parseInt(s.toString()) < totalPayment) {
                        tvErrorPaying.setText("*Payment amount is not enough!");
                    } else {
                        tvErrorPaying.setText("");
                    }
                } else {
                    tvErrorPaying.setText("*Cannot be empty");
                }
            }
        });

        btnConfirmFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutExtraService.getVisibility() == View.VISIBLE) {
                    layoutPayment.setVisibility(View.VISIBLE);
                    layoutExtraService.setVisibility(View.GONE);

                    setTextPayment();

                } else if (layoutPayment.getVisibility() == View.VISIBLE) {

                    if (edtPayingCustomers.getText().length() != 0) {
                        if (tvErrorPaying.getText() == null || tvErrorPaying.getText().equals("")) {
                            layoutPayment.setVisibility(View.GONE);
                            layoutPaymentSuccess.setVisibility(View.VISIBLE);

                            double cash = Integer.parseInt(edtPayingCustomers.getText().toString()) - totalPayment;
                            tvExcessCash.setText(Formater.getFormatMoney((int) cash));
                        }
                    } else {
                        tvErrorPaying.setText("*Cannot be empty");
                    }

                } else {
                    value = true;
                    onClickDone();
                }
            }
        });
        return view;
    }

    public boolean valueReceiver() {
        return this.value;
    }

    private void findViewById(View view) {
        btnCancelFilter = (ImageView) view.findViewById(R.id.btn_cancel_filter);
        tvTitleDialog = (TextView) view.findViewById(R.id.tvTitleDialog);
        btnConfirmFilter = (TextView) view.findViewById(R.id.btn_confirm_filter);
        layoutExtraService = (LinearLayout) view.findViewById(R.id.layout_extra_service);
        minus1 = (TextView) view.findViewById(R.id.minus1);
        quantily1 = (TextView) view.findViewById(R.id.quantily1);
        add1 = (TextView) view.findViewById(R.id.add1);
        minus2 = (TextView) view.findViewById(R.id.minus2);
        quantily2 = (TextView) view.findViewById(R.id.quantily2);
        add2 = (TextView) view.findViewById(R.id.add2);
        minus3 = (TextView) view.findViewById(R.id.minus3);
        quantily13 = (TextView) view.findViewById(R.id.quantily13);
        add3 = (TextView) view.findViewById(R.id.add3);
        minus4 = (TextView) view.findViewById(R.id.minus4);
        quantily4 = (TextView) view.findViewById(R.id.quantily4);
        add4 = (TextView) view.findViewById(R.id.add4);
        layoutPayment = (LinearLayout) view.findViewById(R.id.layout_payment);
        tvRoomCharge = (TextView) view.findViewById(R.id.tvRoomCharge);
        tvAdvanceDeposit = (TextView) view.findViewById(R.id.tvAdvanceDeposit);
        tvExtraService = (TextView) view.findViewById(R.id.tv_extra_service);
        tvVAT = (TextView) view.findViewById(R.id.tvVAT);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        edtPayingCustomers = (EditText) view.findViewById(R.id.edt_paying_customers);
        tvErrorPaying = (TextView) view.findViewById(R.id.tv_error_paying);
        layoutPaymentSuccess = (LinearLayout) view.findViewById(R.id.layout_payment_success);
        tvExcessCash = (TextView) view.findViewById(R.id.tv_excess_cash);
        tvPaymentAmount = view.findViewById(R.id.tv_payment_amount);
    }

    private void onClickDone(){
        JsonObject jsonObject = new JsonObject();
        String note = "Nước ngọt ( 20k ) x " + quantily1.getText().toString() + "\n" +
                "Nước lọc ( 10k ) x " + quantily2.getText().toString() + "\n" +
                "Dọn phòng ( 50k ) x " + quantily13.getText().toString() + "\n" +
                "Bữa ăn thêm ( 100k ) x " + quantily4.getText().toString() + "\n" +
                "Tổng tiền dịch vụ:" + extra;
        jsonObject.addProperty("note", note);
        int total = orderRoomBooked.getTotalRoomRate() + extra;
        jsonObject.addProperty("totalRoomRate", total);
        mApiOrderRoomBooked.update(orderRoomBooked.get_id(), jsonObject);
        mApiOrderRoomBooked.changeBookingStatus(orderRoomBooked.get_id(), 3);
        mApiOrderDetail.createOrderDetail(roomDetails, orderRoomBooked.get_id());
        mApiRoomDetail.updateWhileRemoveOrCheck(orderRoomBooked.get_id(),0,"");
        startActivity(new Intent(getContext(), ListOrdersCompletedActivity.class));
    }

    private void changeQuantily(TextView add, TextView minus, TextView quantily) {
        final int[] temp = {0};
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp[0] = temp[0] + 1;
                quantily.setText("" + temp[0]);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(quantily.getText().toString()) > 0) {
                    temp[0] = temp[0] - 1;
                    quantily.setText("" + temp[0]);
                }
            }
        });
    }

    private void setTextPayment() {
        extra = Integer.parseInt(quantily1.getText().toString()) * 20000 +
                Integer.parseInt(quantily2.getText().toString()) * 10000 +
                Integer.parseInt(quantily13.getText().toString()) * 50000 +
                Integer.parseInt(quantily4.getText().toString()) * 100000;

        tvExtraService.setText(Formater.getFormatMoney(extra));
        tvRoomCharge.setText(Formater.getFormatMoney(price));
        tvAdvanceDeposit.setText(Formater.getFormatMoney(advanceDeposit));
        tvVAT.setText(Formater.getFormatMoney((int) (price * 0.05)));
        totalPayment = price + price * 0.05 + extra - advanceDeposit;
        double paymentAmount = price + price * 0.05 + extra ;
        tvTotal.setText(Formater.getFormatMoney((int) paymentAmount));
        tvPaymentAmount.setText(Formater.getFormatMoney((int) totalPayment));
    }

    @Override
    public void deleteOrder(String statusDelete) {

    }

    @Override
    public void changeBookingStatus(String updateStatus) {

    }

    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getRoomById(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void updateWhileRemoveOrder(String message) {

    }
}
