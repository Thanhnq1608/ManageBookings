package com.fpoly.managebookings.views.listRoomEmpty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListRoomEmptyAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.pictureOfRoom.ApiPictureRoom;
import com.fpoly.managebookings.api.pictureOfRoom.ResponGetPictureRoom;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.api.roomDetail.GetAllRoomInterface;
import com.fpoly.managebookings.api.roomDetail.ResponseCreateRoomInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.models.picture.PictureOfRoom;
import com.fpoly.managebookings.tool.DialogAddRoom;
import com.fpoly.managebookings.tool.DialogMessage;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.Formater;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.tool.UploadImage;
import com.fpoly.managebookings.views.createOrder.CreateOrderActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.fpoly.managebookings.views.login.LoginActivity;
import com.fpoly.managebookings.views.orderBookingDetail.OrderBookingDetailActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListRoomEmptyActivity extends AppCompatActivity implements ResponGetPictureRoom, DialogAddRoom.GetPriceInterface, ApiRoomDetailInterface, GetAllRoomInterface, ListRoomEmptyAdapter.ListRoomEmptyInterface {
    private ListRoomEmptyAdapter mListRoomEmptyAdapter;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this, this);
    private LinearLayout layoutListRoomEmpty;
    private Button btnSortMoney;
    private Button btnSortType;
    private Button btnSortFloor;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recListRoomEmpty;
    private NavigationView navView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LoadingDialog loadingDialog = new LoadingDialog(ListRoomEmptyActivity.this);
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private LinearLayout layout_button;
    private Button btnCofirm, btnCancel;
    private OrderRoomBooked updateOrderRoomBooked;
    private boolean isDataEntry;
    private ApiOrderRoomBooked apiOrderRoomBooked = new ApiOrderRoomBooked();
    private Intent intent;
    private boolean doubleBackToExitPressedOnce = false;
    private ArrayList<RoomDetail> detailArrayList = new ArrayList<>();
    private ArrayList<PictureOfRoom> imageRooms = new ArrayList<>();
    private DialogAddRoom dialogAddRoom;
    private ApiPictureRoom apiPictureRoom;
    private int getPrice = 0;
    private int total = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_empty);

        findViewsById();
        dialogAddRoom = new DialogAddRoom(ListRoomEmptyActivity.this,this);

        intent = getIntent();
        updateOrderRoomBooked = (OrderRoomBooked) intent.getSerializableExtra("ORDERROOMBOOKED");
        isDataEntry = intent.getBooleanExtra("DATAENTRY", false);
        apiPictureRoom = new ApiPictureRoom(this);


        if (updateOrderRoomBooked != null) {
            layout_button.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        if (isDataEntry == true) {
            initializeDataEntry();
        } else {
            initializeNavigationView();
        }

        setPullRefresh();
        getInfoUser();

        btnSortMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BitmapDrawable drawable = new BitmapDrawable();
//                drawable.set
//                Bitmap bitmap = Bitmap.createBitmap(R.drawable.custom_button);
//                btnSortMoney.setBackgroundResource(R.drawable.custom_button);
//                btnSortType.setBackgroundResource(R.drawable.custom_button3);
//                btnSortFloor.setBackgroundResource(R.drawable.custom_button3);

                mListRoomEmptyAdapter = new ListRoomEmptyAdapter(ListRoomEmptyActivity.this, detailArrayList, updateOrderRoomBooked, ListRoomEmptyActivity.this, imageRooms, 0, isDataEntry);
                recListRoomEmpty.setAdapter(mListRoomEmptyAdapter);
                loadingDialog.startLoadingDialog(2000);

            }
        });

        btnSortType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnSortMoney.setBackgroundResource(R.drawable.custom_button3);
//                btnSortType.setBackgroundResource(R.drawable.custom_button);
//                btnSortFloor.setBackgroundResource(R.drawable.custom_button3);

                mListRoomEmptyAdapter = new ListRoomEmptyAdapter(ListRoomEmptyActivity.this, detailArrayList, updateOrderRoomBooked, ListRoomEmptyActivity.this, imageRooms, 1, isDataEntry);
                recListRoomEmpty.setAdapter(mListRoomEmptyAdapter);
                loadingDialog.startLoadingDialog(2000);
            }
        });

        btnSortFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btnSortMoney.setBackgroundResource(R.drawable.custom_button3);
//                btnSortType.setBackgroundResource(R.drawable.custom_button3);
//                btnSortFloor.setBackgroundResource(R.drawable.custom_button);

                mListRoomEmptyAdapter = new ListRoomEmptyAdapter(ListRoomEmptyActivity.this, detailArrayList, updateOrderRoomBooked, ListRoomEmptyActivity.this, imageRooms, 2, isDataEntry);
                recListRoomEmpty.setAdapter(mListRoomEmptyAdapter);
                loadingDialog.startLoadingDialog(2000);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recListRoomEmpty.setLayoutManager(gridLayoutManager);


    }

    private void findViewsById() {
        layoutListRoomEmpty = (LinearLayout) findViewById(R.id.layoutListRoomEmpty);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_room_empty);
        recListRoomEmpty = (RecyclerView) findViewById(R.id.recListRoomEmpty);
        navView = (NavigationView) findViewById(R.id.nav_view_room_empty);
        btnCofirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancelRoom);
        layout_button = findViewById(R.id.layout_button);
        btnSortMoney = (Button) findViewById(R.id.btn_sort_money);
        btnSortType = (Button) findViewById(R.id.btn_sort_type);
        btnSortFloor = (Button) findViewById(R.id.btn_sort_floor);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mApiRoomDetail.getAllRoom();
        mApiRoomDetail.getRooMByStatus(0);
        //Loading Data
        loadingDialog.startLoadingDialog(4000);
    }

    @Override
    public void onBackPressed() {
        if (isDataEntry) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finishAffinity();
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            fixSizeForToast.fixSizeToast("Nhấn  " + '"' + "TRỞ VỀ" + '"' + "  lần nữa để thoát");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isDataEntry == true) {
            getMenuInflater().inflate(R.menu.menu_add, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.menu_filter_room, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (isDataEntry == true) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    break;
                case R.id.menu_add_room:
                    boolean temp = dialogAddRoom.dialogAddRoom(null);
                    if (temp) {
                        onStart();
                    }
                    break;
            }
        } else {
            switch (item.getItemId()) {
                case R.id.room_all:
                    mApiRoomDetail.getAllRoom();
                    loadingDialog.startLoadingDialog(2000);
                    break;
                case R.id.room_empty:
                    mApiRoomDetail.getRooMByStatus(0);
                    loadingDialog.startLoadingDialog(2000);
                    break;
                case R.id.room_reserved:
                    mApiRoomDetail.getRooMByStatus(1);
                    loadingDialog.startLoadingDialog(2000);
                    break;
                case R.id.room_occupied:
                    mApiRoomDetail.getRooMByStatus(2);
                    loadingDialog.startLoadingDialog(2000);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    void setPullRefresh() {
        final SwipeRefreshLayout pullRefresh = findViewById(R.id.refresh);
        pullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onStart();
                pullRefresh.setRefreshing(false);
            }
        });
    }

    private void getInfoUser() {
        TextView tv_email_drawer_header, tv_fullname_drawer_header;
        RoundedImageView ava_drawer_header;

        View mView = navView.getHeaderView(0);
        tv_email_drawer_header = mView.findViewById(R.id.tv_email_drawer_header);
        tv_fullname_drawer_header = mView.findViewById(R.id.tv_fullname_drawer_header);
        ava_drawer_header = mView.findViewById(R.id.ava_drawer_header);

        ava_drawer_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage.getInstance(ListRoomEmptyActivity.this).changeAvatar();
            }
        });

        tv_email_drawer_header.setText(SharedPref_InfoUser.getInstance(this).LoggedInEmail());
        tv_fullname_drawer_header.setText(SharedPref_InfoUser.getInstance(this).LoggedInFullName());
        Picasso.get().load(SharedPref_InfoUser.getInstance(this).LoggedInUserAvatar()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(ava_drawer_header);
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                if (isDataEntry) {
                    ClipData array = data.getClipData();
                    Uri[] arrayImage = new Uri[array.getItemCount()];
                    for (int i = 0; i<array.getItemCount(); i++){
                        arrayImage[i] = array.getItemAt(i).getUri();
                        Log.e("ima", " "+array.getItemAt(i).getUri());
                    }
                    dialogAddRoom.getArrayImages(arrayImage, getPrice);
                } else {
                    UploadImage.getInstance(this).uploadImage(data.getData());
                }
            }
//        }
    }

    private void initializeDataEntry() {
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText(getString(R.string.list_rooms));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void initializeNavigationView() {
        TextView toolbar_text = findViewById(R.id.toolbar_text);
        toolbar_text.setText(getString(R.string.list_rooms));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_waiting:
                        startActivity(new Intent(ListRoomEmptyActivity.this, ListOrderWaitingActivity.class));
                        break;
                    case R.id.confirm_order:
                        startActivity(new Intent(ListRoomEmptyActivity.this, ListOrderConfirmedActivity.class));
                        break;
                    case R.id.occupied_order:
                        startActivity(new Intent(ListRoomEmptyActivity.this, ListOrderOccupiedActivity.class));
                        break;
                    case R.id.orders_completed:
                        startActivity(new Intent(ListRoomEmptyActivity.this, ListOrdersCompletedActivity.class));
                        break;
                    case R.id.create_order:
                        startActivity(new Intent(ListRoomEmptyActivity.this, CreateOrderActivity.class));
                        break;
                    case R.id.list_rooms:
                        startActivity(new Intent(ListRoomEmptyActivity.this, ListRoomEmptyActivity.class));
                        break;
                    case R.id.menu_logout:
                        SharedPref_InfoUser.getInstance(ListRoomEmptyActivity.this).clearSharedPreferences();
                        startActivity(new Intent(ListRoomEmptyActivity.this, LoginActivity.class));
                        finishAffinity();
                        break;
                    case R.id.menu_exit:
                        DialogMessage dialogMessage = new DialogMessage();
                        dialogMessage.exit(ListRoomEmptyActivity.this);
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {
        this.detailArrayList.clear();
        this.detailArrayList.addAll(roomDetails);
        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(roomDetails.get(0).getRoomPrice());
        for (int i = 1; i < roomDetails.size(); i++) {
            for (int j = 0; j < ints.size(); j++) {
                if (roomDetails.get(i).getRoomPrice() == ints.get(j)) {
                    continue;
                } else {
                    ints.add(roomDetails.get(i).getRoomPrice());
                    Log.e("image", ""+ints.get(i));
                    break;
                }
            }
        }

        apiPictureRoom.getListPictureRooms(ints);
    }

    @Override
    public void getRoomById(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void getAllRoomByIdBooking(ArrayList<RoomDetail> roomDetails) {

    }

    @Override
    public void updateWhileRemoveOrder(String message) {
        fixSizeForToast.fixSizeToast(message);
    }

    @Override
    public void roomsAreSelected(List<RoomDetail> roomDetails) {
        btnCofirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (updateOrderRoomBooked != null) {
                    int total = 0;
                    for (int i = 0; i < roomDetails.size(); i++) {
                        mApiRoomDetail.removeRoomFromOrder(roomDetails.get(i).getId(), 1, updateOrderRoomBooked.get_id());
                        total = total + roomDetails.get(i).getRoomPrice();
                    }
                    setTotalWhileAddRoom(total, updateOrderRoomBooked);
                    intent = getIntent();
                    String context = intent.getStringExtra("CONTEXT");
                    if (context.equalsIgnoreCase("CreateOrderActivity")) {
                        dialogDeposit();
                    } else {
                        Intent intent = new Intent(ListRoomEmptyActivity.this, OrderBookingDetailActivity.class);
                        intent.putExtra("ORDERROOMBOOKED", updateOrderRoomBooked);
                        startActivity(intent);
                    }

                }
            }
        });

    }

    void dialogDeposit(){

        BottomSheetDialog dialog = new BottomSheetDialog(ListRoomEmptyActivity.this);
        dialog.setContentView(R.layout.dialog_advance_deposit);
        dialog.setCancelable(false);

        TextView tvYes, tvNo, tvError;
        EditText edtDeposit;

        tvNo = dialog.findViewById(R.id.tv_no_dialog);
        tvYes = dialog.findViewById(R.id.tv_yes_dialog);
        edtDeposit = dialog.findViewById(R.id.edt_advan_deposit);
        tvError = dialog.findViewById(R.id.tv_error_deposit);

        edtDeposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null){
                    if (Integer.parseInt(s.toString()) < (int) total*0.3 ){
                        tvError.setText("*Deposit should not be less than 30% of total bill");
                    }else if (Integer.parseInt(s.toString()) > total) {
                        tvError.setText("*Deposit cannot be more than total bill");
                    }else {
                        tvError.setText("");
                    }
                }else {
                    tvError.setText("You cannot leave data blank");
                }
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(ListRoomEmptyActivity.this, ListOrderWaitingActivity.class));
            }
        });

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvError.getText().toString().equals("")){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("advanceDeposit", Integer.parseInt(edtDeposit.getText().toString().trim())+updateOrderRoomBooked.getAdvanceDeposit());
                    apiOrderRoomBooked.update(updateOrderRoomBooked.get_id(), jsonObject);
                    dialog.dismiss();
                    startActivity(new Intent(ListRoomEmptyActivity.this, ListOrderWaitingActivity.class));
                }
            }
        });

        dialog.show();
    }

    @Override
    public void removeRoomSuccess(String status) {
        fixSizeForToast.fixSizeToast(status);
        onStart();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void setTotalWhileAddRoom(int total, OrderRoomBooked orderRoomRate) {
        try {
            String[] dateTime = Formater.getUsedTDate(updateOrderRoomBooked.getTimeBookingStart(), updateOrderRoomBooked.getTimeBookingEnd()).split(",");
            if (Integer.parseInt(dateTime[0]) > 0) {
                if (Integer.parseInt(dateTime[1]) > 0 && Integer.parseInt(dateTime[1]) <= 12) {
                    total = total * (Integer.parseInt(dateTime[0])) + (total / 2);
                } else if (Integer.parseInt(dateTime[1]) > 12 && Integer.parseInt(dateTime[1]) < 24){
                    total = total * (Integer.parseInt(dateTime[0])) + total;
                }else {
                    total = total * (Integer.parseInt(dateTime[0]));
                }
            } else {
                if (Integer.parseInt(dateTime[1]) > 0 && Integer.parseInt(dateTime[1]) <= 12) {
                    total = (total / 2);
                }
            }
            this.total = total;
            total = total + orderRoomRate.getTotalRoomRate();
            apiOrderRoomBooked.updateTotalRoomRate(updateOrderRoomBooked.get_id(), total);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllRoom(ArrayList<RoomDetail> roomDetails) {
        this.detailArrayList.clear();
        this.detailArrayList.addAll(roomDetails);

        ArrayList<Integer> ints = new ArrayList<>();

        ints.add(roomDetails.get(0).getRoomPrice());

        for (int i = 1; i < roomDetails.size(); i++) {
            for (int j = 0; j < ints.size(); j++) {
                if (roomDetails.get(i).getRoomPrice() == ints.get(j)) {
                    continue;
                } else {
                    ints.add(roomDetails.get(i).getRoomPrice());
                    break;
                }
            }
        }

        apiPictureRoom.getListPictureRooms(ints);
    }


    @Override
    public void responsePicture(PictureOfRoom pictureOfRoom) {
    }

    @Override
    public void responseListPicture(List<PictureOfRoom> imageRooms) {
        this.imageRooms.clear();
        this.imageRooms.addAll(imageRooms);
        mListRoomEmptyAdapter = new ListRoomEmptyAdapter(this, detailArrayList, updateOrderRoomBooked, this, imageRooms, 5, isDataEntry);
        recListRoomEmpty.setAdapter(mListRoomEmptyAdapter);
    }

    @Override
    public void getPrice(int price) {
        this.getPrice = price;
    }
}