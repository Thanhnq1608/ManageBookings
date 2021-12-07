package com.fpoly.managebookings.views.listRoomEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RecoverySystem;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListRoomEmptyAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetail;
import com.fpoly.managebookings.api.roomDetail.ApiRoomDetailInterface;
import com.fpoly.managebookings.api.roomDetail.GetAllRoomInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.RoomDetail;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.views.createOrder.CreateOrderActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderConfirmedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderOccupiedActivity;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class ListRoomEmptyActivity extends AppCompatActivity implements ApiRoomDetailInterface, GetAllRoomInterface, ListRoomEmptyAdapter.ListRoomEmptyInterface {
    private ListRoomEmptyAdapter mListRoomEmptyAdapter;
    private ApiRoomDetail mApiRoomDetail = new ApiRoomDetail(this,this);
    private LinearLayout layoutListRoomEmpty;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recListRoomEmpty;
    private NavigationView navView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LoadingDialog loadingDialog;
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private LinearLayout layout_button;
    private Button btnCofirm, btnCancel;
    private OrderRoomBooked updateOrderRoomBooked;
    private ApiOrderRoomBooked apiOrderRoomBooked = new ApiOrderRoomBooked();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room_empty);

        layoutListRoomEmpty = (LinearLayout) findViewById(R.id.layoutListRoomEmpty);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_room_empty);
        recListRoomEmpty = (RecyclerView) findViewById(R.id.recListRoomEmpty);
        navView = (NavigationView) findViewById(R.id.nav_view_room_empty);
        btnCofirm = findViewById(R.id.btnConfirm);
        btnCancel = findViewById(R.id.btnCancelRoom);
        layout_button = findViewById(R.id.layout_button);

        intent = getIntent();
        updateOrderRoomBooked = (OrderRoomBooked) intent.getSerializableExtra("ORDERROOMBOOKED");

        if (updateOrderRoomBooked != null) {
            layout_button.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        initializeNavigationView();
        setPullRefresh();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recListRoomEmpty.setLayoutManager(gridLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mApiRoomDetail.getAllRoom();

        //Loading Data
        loadingDialog = new LoadingDialog(ListRoomEmptyActivity.this);
        loadingDialog.startLoadingDialog(2000);
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

    private void initializeNavigationView() {
        toolbar.setTitle(getString(R.string.list_rooms));
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
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public void getAllRoomEmpty(ArrayList<RoomDetail> roomDetails) {
        mListRoomEmptyAdapter = new ListRoomEmptyAdapter(this, roomDetails, updateOrderRoomBooked, this);
        recListRoomEmpty.setAdapter(mListRoomEmptyAdapter);

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
            @Override
            public void onClick(View v) {
                if (updateOrderRoomBooked != null) {
                    int total = 0;
                    for (int i = 0; i < roomDetails.size(); i++) {
                        mApiRoomDetail.removeRoomFromOrder(roomDetails.get(i).getId(), 1, updateOrderRoomBooked.get_id());
                        total = total + roomDetails.get(i).getRoomPrice();
                    }
                    total= updateOrderRoomBooked.getTotalRoomRate() + total;
                    apiOrderRoomBooked.updateTotalRoomRate(updateOrderRoomBooked.get_id(),total );
                    intent = getIntent();
                    String context = intent.getStringExtra("CONTEXT");
                    if (context.equalsIgnoreCase("CreateOrderActivity")){
                        startActivity(new Intent(ListRoomEmptyActivity.this,ListOrderWaitingActivity.class));
                    }else {
                        onBackPressed();
                    }

                }
            }
        });

    }

    @Override
    public void getAllRoom(ArrayList<RoomDetail> roomDetails) {
        mListRoomEmptyAdapter = new ListRoomEmptyAdapter(this, roomDetails, updateOrderRoomBooked, this);
        recListRoomEmpty.setAdapter(mListRoomEmptyAdapter);
    }
}