package com.fpoly.managebookings.views.roomDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.Formater;

public class RoomDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageRoom;
    private TextView tvRoomNameDetail;
    private TextView tvRoomPositionDetail;
    private TextView tvRoomPriceDetail;
    private ImageView imgWifi;
    private ImageView imgPool;
    private ImageView imgGym;
    private ImageView imgRestaurant;
    private ImageView imgPaking;
    private TextView textView14;
    private TextView textView15;
    private TextView tvContentRoom;
    private Button btnSelectRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        anhXa();

        Intent intent = getIntent();
        RoomDetail roomDetail =(RoomDetail) intent.getSerializableExtra("ROOMDETAIL");
        boolean isHideButton = intent.getBooleanExtra("HIDEBUTTON",false);

        if (isHideButton){
            btnSelectRoom.setVisibility(View.INVISIBLE);
        }

        if (roomDetail != null){
            tvRoomNameDetail.setText(roomDetail.getRoomName());
            tvRoomPositionDetail.setText(roomDetail.getIdRoom());
            tvRoomPriceDetail.setText(Formater.getFormatMoney(roomDetail.getRoomPrice()));
        }

        if (roomDetail != null){
            setToolbar(roomDetail.getRoomName());
        }else {
            setToolbar(getString(R.string.room_detail_lower));
        }

    }

    void setToolbar(String name){
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    void anhXa(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageRoom = (ImageView) findViewById(R.id.image_room);
        tvRoomNameDetail = (TextView) findViewById(R.id.tv_room_name_detail);
        tvRoomPositionDetail = (TextView) findViewById(R.id.tv_room_position_detail);
        tvRoomPriceDetail = (TextView) findViewById(R.id.tv_room_price_detail);
        imgWifi = (ImageView) findViewById(R.id.img_wifi);
        imgPool = (ImageView) findViewById(R.id.img_pool);
        imgGym = (ImageView) findViewById(R.id.img_gym);
        imgRestaurant = (ImageView) findViewById(R.id.img_restaurant);
        imgPaking = (ImageView) findViewById(R.id.img_paking);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView15 = (TextView) findViewById(R.id.textView15);
        tvContentRoom = (TextView) findViewById(R.id.tv_content_room);
        btnSelectRoom = (Button) findViewById(R.id.btnSelectRoom);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}