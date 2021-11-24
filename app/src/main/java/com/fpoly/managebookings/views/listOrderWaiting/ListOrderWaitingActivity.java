package com.fpoly.managebookings.views.listOrderWaiting;

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
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrderWaitingAdapter;
import com.fpoly.managebookings.adapter.ListRoomEmptyAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class ListOrderWaitingActivity extends AppCompatActivity implements ApiOrderBookedInterface {
    private RecyclerView recView;
    private ListOrderWaitingAdapter adapter;
    private ApiOrderRoomBooked getOrderWaiting = new ApiOrderRoomBooked(this);
    private ArrayList<OrderRoomBooked> orderRoomBookeds = new ArrayList<>();
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LinearLayout layout;
    private DrawerLayout drawerLayout;
    private Observable<ArrayList<OrderRoomBooked>> observable;
    private LoadingDialog loadingDialog;
    private NavigationView navigationView;
    private int bookingStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_waiting);
        bookingStatus = 0;
        getOrderWaiting.getOrderByBookingStatus(bookingStatus);

        recView = findViewById(R.id.recyclerHero);
        layout = findViewById(R.id.layoutListWaiting);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

        //Loading Data
        loadingDialog = new LoadingDialog(ListOrderWaitingActivity.this);
        loadingDialog.startLoadingDialog();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 2000);

        initializeNavigationView();
        setPullRefresh();

        //Get all room empty with bookingStatus = 0
        recView.setLayoutManager(new GridLayoutManager(this, 1));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderWaiting.getOrderByBookingStatus(bookingStatus);
    }

    private void initializeNavigationView() {
        toolbar.setTitle(getString(R.string.orders_waiting));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.orders_waiting:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListOrderWaitingActivity.class));
                        break;
                    case R.id.orders_completed:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListOrdersCompletedActivity.class));
                        break;
                    case R.id.list_rooms:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListRoomEmptyActivity.class));
                        break;
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    void setPullRefresh() {
        final SwipeRefreshLayout pullRefresh = findViewById(R.id.refresh);
        pullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                finish();
                startActivity(getIntent());
                pullRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_status_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.waiting:
                this.bookingStatus = 0;
                onResume();
                break;
            case R.id.check_in:
                this.bookingStatus = 1;
                onResume();
                break;
            case R.id.occupied:
                this.bookingStatus = 2;
                onResume();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {
        if (!list.isEmpty()) {
            recView.setVisibility(View.VISIBLE);
            adapter = new ListOrderWaitingAdapter(ListOrderWaitingActivity.this, list);
            recView.setAdapter(adapter);
        } else {
            recView.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.background_empty);
        }
    }

    @Override
    public void getOrderCompleted(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderConfirmed(ArrayList<OrderRoomBooked> list) {

    }

    @Override
    public void getOrderOccupied(ArrayList<OrderRoomBooked> list) {

    }
}