package com.fpoly.managebookings.views.roomDetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.pictureOfRoom.ApiPictureRoom;
import com.fpoly.managebookings.api.pictureOfRoom.ResponGetPictureRoom;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.picture.PictureOfRoom;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.squareup.picasso.Picasso;

public class RoomDetailActivity extends AppCompatActivity implements ResponGetPictureRoom {
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
    private LinearLayout layout;
    private Button btnSelectRoom;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail();
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked();
    private OrderRoomBooked orderRoomBooked;
    private RoomDetail roomDetail;
    private boolean isHideButton;
    private ApiPictureRoom apiPictureRoom = new ApiPictureRoom(this);
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        anhXa();
        loadingDialog = new LoadingDialog(this);

        Intent intent = getIntent();
        roomDetail = (RoomDetail) intent.getSerializableExtra("ROOMDETAIL");
        isHideButton = intent.getBooleanExtra("HIDEBUTTON", false);
        orderRoomBooked = (OrderRoomBooked) intent.getSerializableExtra("ORDERROOMBOOKED");

        if (isHideButton) {
            btnSelectRoom.setVisibility(View.INVISIBLE);
        }

        if (roomDetail != null) {
            tvRoomNameDetail.setText(roomDetail.getRoomName());
            tvRoomPositionDetail.setText(roomDetail.getIdRoom());
            tvRoomPriceDetail.setText(Formater.getFormatMoney(roomDetail.getRoomPrice()));
        }

        if (roomDetail != null) {
            setToolbar(roomDetail.getRoomName());
        } else {
            setToolbar(getString(R.string.room_detail_lower));
        }

        btnSelectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = 0;
                mApiRoomDetail.removeRoomFromOrder(roomDetail.getId(), 1, orderRoomBooked.get_id());
                total = total + roomDetail.getRoomPrice();
                total = orderRoomBooked.getTotalRoomRate() + total;
                mApiOrderRoomBooked.updateTotalRoomRate(orderRoomBooked.get_id(), total);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        apiPictureRoom.getPictureRoom(roomDetail.getRoomPrice());
        loadingDialog.startLoadingDialog(2000);
    }

    void setToolbar(String name) {
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText(name);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    void anhXa() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        imageRoom = (ImageView) findViewById(R.id.img_room_detail);
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
        layout = (LinearLayout) findViewById(R.id.image_container);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void responsePicture(PictureOfRoom pictureOfRoom) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < pictureOfRoom.getPicture().length; i++) {
            layoutParams.setMargins(20, 20, 20, 20);
            layoutParams.gravity = Gravity.CENTER;
            ImageView imageView = new ImageView(this);
            Picasso.get().load(pictureOfRoom.getPicture()[i]).placeholder(R.drawable.ic_image).error(R.drawable.ic_image).into(imageView);
            imageView.setLayoutParams(layoutParams);

            layout.addView(imageView);

        }
    }
}