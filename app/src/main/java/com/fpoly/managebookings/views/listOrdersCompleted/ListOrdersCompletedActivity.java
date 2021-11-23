package com.fpoly.managebookings.views.listOrdersCompleted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderWaitingAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ListOrdersCompletedActivity extends AppCompatActivity implements ApiOrderBookedInterface {
    private ApiOrderRoomBooked mApiOrderRoomBooked = new ApiOrderRoomBooked(this);
    private ListOrderWaitingAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout layout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders_completed);

        //get All Order Completed with boookingStatus = 3
        mApiOrderRoomBooked.getOrderByBookingStatus(3);

        recyclerView= findViewById(R.id.recListOrderCompleted);
        layout = findViewById(R.id.layoutListCompleted);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout_complete);
        navigationView = findViewById(R.id.nav_view_complete);

        //Loading Data
        loadingDialog = new LoadingDialog(ListOrdersCompletedActivity.this);
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        },1000);

        initializeNavigationView();
        setPullRefresh();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    void setPullRefresh(){
        final SwipeRefreshLayout pullRefresh = findViewById(R.id.refresh);
        pullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recreate();
                pullRefresh.setRefreshing(false);
            }
        });
    }

    private void initializeNavigationView() {
        toolbar.setTitle(getString(R.string.orders_comleted));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_waiting:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListOrderWaitingActivity.class));
                        break;
                    case R.id.orders_completed:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListOrdersCompletedActivity.class));
                        break;
                    case R.id.list_rooms:
                        startActivity(new Intent(ListOrdersCompletedActivity.this, ListRoomEmptyActivity.class));
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderCompleted(ArrayList<OrderRoomBooked> list) {
        if (!list.isEmpty()){
            adapter = new ListOrderWaitingAdapter(this,list);
            recyclerView.setAdapter(adapter);
        }else {
            recyclerView.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.background_empty);
        }
    }

    @Override
    public void getOrderConfirmed(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderOccupied(ArrayList<OrderRoomBooked> list) {

    }
}