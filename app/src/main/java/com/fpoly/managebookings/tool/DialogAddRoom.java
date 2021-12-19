package com.fpoly.managebookings.tool;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ResponseCreateRoomInterface;
import com.fpoly.managebookings.models.RoomDetail;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.JsonObject;

public class DialogAddRoom implements ResponseCreateRoomInterface {
    private Activity context;
    private int idRoomType = 4;
    private int roomPrice;
    private BottomSheetDialog bottomSheetDialog;
    private FixSizeForToast fixSizeForToast;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);
    private boolean isDialog;
    private GetPriceInterface mGetPriceInterface;

    public interface GetPriceInterface{
        void getPrice(int price);
    }

    public DialogAddRoom(Activity context) {
        this.context = context;
        fixSizeForToast = new FixSizeForToast(context);
    }

    public DialogAddRoom(Activity context, GetPriceInterface mGetPriceInterface) {
        this.context = context;
        this.mGetPriceInterface = mGetPriceInterface;
        fixSizeForToast = new FixSizeForToast(context);
    }

    public boolean dialogAddRoom(RoomDetail roomDetail) {
        bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.layout_add_new_room);
        bottomSheetDialog.setCancelable(false);

        ImageView btnCancelCreateRoom;
        TextView btnConfirmCreateRoom;
        Spinner spnTypeRoom;
        EditText edtIdRoom;
        EditText edtNameRoom;
        EditText edtMaxPersonRoom;
        EditText edtPriceRoom;

        btnCancelCreateRoom = (ImageView) bottomSheetDialog.findViewById(R.id.btn_cancel_createRoom);
        btnConfirmCreateRoom = (TextView) bottomSheetDialog.findViewById(R.id.btn_confirm_createRoom);
        spnTypeRoom = (Spinner) bottomSheetDialog.findViewById(R.id.spn_type_room);
        edtIdRoom = (EditText) bottomSheetDialog.findViewById(R.id.edt_id_room);
        edtNameRoom = (EditText) bottomSheetDialog.findViewById(R.id.edt_name_room);
        edtMaxPersonRoom = (EditText) bottomSheetDialog.findViewById(R.id.edt_max_person_room);
        edtPriceRoom = (EditText) bottomSheetDialog.findViewById(R.id.edt_price_room);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(context, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTypeRoom.setAdapter(adapter);

        if (roomDetail != null) {
            edtIdRoom.setText(roomDetail.getIdRoom());
            edtNameRoom.setText(roomDetail.getRoomName());
            edtMaxPersonRoom.setText(String.valueOf(roomDetail.getMaximumNumberOfPeople()));
            edtPriceRoom.setText(String.valueOf(roomDetail.getRoomPrice()));
        }

        if (roomDetail != null) {
            switch (roomDetail.getIdKindOfRoom()) {
                case 0:
                    spnTypeRoom.setSelection(1);
                    break;
                case 1:
                    spnTypeRoom.setSelection(2);
                    break;
                case 2:
                    spnTypeRoom.setSelection(3);
                    break;
                case 3:
                    spnTypeRoom.setSelection(4);
                    break;
            }
        }

        spnTypeRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextSize(35);
                switch (position) {
                    case 0:
                        idRoomType = 4;
                        break;
                    case 1:
                        idRoomType = 0;
                        break;
                    case 2:
                        idRoomType = 1;
                        break;
                    case 3:
                        idRoomType = 2;
                        break;
                    case 4:
                        idRoomType = 3;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                idRoomType = 4;
            }
        });

        btnConfirmCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtIdRoom.getText() == null || edtNameRoom.getText() == null || edtPriceRoom.getText() == null || edtMaxPersonRoom.getText() == null || idRoomType == 4) {
                    fixSizeForToast.fixSizeToast("Data cannot be empty");
                } else {
//                        roomPrice = (int) decimalFormat.parse(edtPriceRoom.getText().toString());
                    roomPrice = Integer.parseInt(edtPriceRoom.getText().toString());
                    mGetPriceInterface.getPrice(roomPrice);
                    UploadImage.getInstance(context).changeAvatar();

//                    JsonObject jsonObject = new JsonObject();
//                    jsonObject.addProperty("idRoom", edtIdRoom.getText().toString());
//                    jsonObject.addProperty("roomName", edtNameRoom.getText().toString());
//                    jsonObject.addProperty("maximumNumberOfPeople", Integer.parseInt(edtMaxPersonRoom.getText().toString()));
//                    jsonObject.addProperty("roomStatus", 0);
//                    jsonObject.addProperty("idKindOfRoom", idRoomType);
//                    jsonObject.addProperty("roomPrice", roomPrice);
//                    jsonObject.addProperty("idBooking", 0);
//
//                    mApiRoomDetail.createNewRoom(jsonObject);
                }
            }
        });

        btnCancelCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismis(bottomSheetDialog);
                isDialog = false;
            }
        });

        bottomSheetDialog.show();
        return isDialog;
    }

    private void dialogDismis(BottomSheetDialog bottomSheetDialog) {
        bottomSheetDialog.dismiss();
    }

    @Override
    public void createSucces(RoomDetail roomDetail) {
        fixSizeForToast.fixSizeToast("Create Room Success");

        dialogDismis(bottomSheetDialog);
        isDialog = true;
    }

    @Override
    public void createFail(String status) {
        fixSizeForToast.fixSizeToast(status);
        isDialog = false;
    }
}
