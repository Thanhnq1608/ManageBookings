package com.fpoly.managebookings.views.listRoomEmpty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListRoomEmptyAdapter;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.models.RoomDetail;

import java.util.ArrayList;

public class ListRoomEmptyActivity extends AppCompatActivity implements ApiRoomDetailInterface {
    private RecyclerView recView;
    private ListRoomEmptyAdapter mListRoomEmptyAdapter;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_empty);

        mApiRoomDetail.getAllRoom(1);
        recView= findViewById(R.id.recListRoomEmpty);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {
        mListRoomEmptyAdapter = new ListRoomEmptyAdapter(this,roomDetails);
        recView.setAdapter(mListRoomEmptyAdapter);
    }

    @Override
    public void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails) {

    }
}