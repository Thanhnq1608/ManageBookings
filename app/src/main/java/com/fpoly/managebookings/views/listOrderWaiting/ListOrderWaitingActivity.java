package com.fpoly.managebookings.views.listOrderWaiting;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrdersAdapter;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.tool.DialogExit;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.views.createOrder.CreateOrderActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.fpoly.managebookings.views.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.Observable;

public class ListOrderWaitingActivity extends AppCompatActivity implements ApiOrderBookedInterface {
    private RecyclerView recView;
    private ListOrdersAdapter adapter;
    private ApiOrderRoomBooked getOrderWaiting = new ApiOrderRoomBooked(this);
    private ArrayList<OrderRoomBooked> orderRoomBookeds = new ArrayList<>();
    private Toolbar toolbar;
    private TextView toolbar_text;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private LinearLayout layout;
    private DrawerLayout drawerLayout;
    private Observable<ArrayList<OrderRoomBooked>> observable;
    private LoadingDialog loadingDialog;
    private NavigationView navigationView;
    private EditText edt_search;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order_waiting);

        recView = findViewById(R.id.recyclerHero);
        layout = findViewById(R.id.layoutListWaiting);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbar_text = findViewById(R.id.toolbar_text);
        navigationView = findViewById(R.id.nav_view);
        edt_search = findViewById(R.id.edt_search);


        initializeNavigationView();

        //Get all room empty with bookingStatus = 0
        recView.setLayoutManager(new GridLayoutManager(this, 1));

        setPullRefresh();

        getInfoUser();

    }

    private void getInfoUser() {
        TextView tv_email_drawer_header, tv_fullname_drawer_header;
        RoundedImageView ava_drawer_header;

        View mView = navigationView.getHeaderView(0);
        tv_email_drawer_header = mView.findViewById(R.id.tv_email_drawer_header);
        tv_fullname_drawer_header = mView.findViewById(R.id.tv_fullname_drawer_header);
        ava_drawer_header = mView.findViewById(R.id.ava_drawer_header);

        tv_email_drawer_header.setText(SharedPref_InfoUser.getInstance(this).LoggedInEmail());
        tv_fullname_drawer_header.setText(SharedPref_InfoUser.getInstance(this).LoggedInFullName());
        Picasso.get().load(SharedPref_InfoUser.getInstance(this).LoggedInUserAvatar()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(ava_drawer_header);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishApp();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn  " + '"' + "TRỞ VỀ" + '"' + "  lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void finishApp(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrderWaiting.getOrderByBookingStatus(0);

        //Loading Data
        loadingDialog = new LoadingDialog(ListOrderWaitingActivity.this);
        loadingDialog.startLoadingDialog(1000);

    }

    void searchOrderByPhone(){
        edt_search.setText("");
        if (edt_search != null){
            edt_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    adapter.getFilter().filter(s);
                }
            });
        }
    }

    private void initializeNavigationView() {
        toolbar_text.setText(getString(R.string.orders_waiting));
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
                    case R.id.confirm_order:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListOrderConfirmedActivity.class));
                        break;
                    case R.id.occupied_order:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListOrderOccupiedActivity.class));
                        break;
                    case R.id.orders_completed:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListOrdersCompletedActivity.class));
                        break;
                    case R.id.list_rooms:
                        startActivity(new Intent(ListOrderWaitingActivity.this, ListRoomEmptyActivity.class));
                        break;
                    case R.id.create_order:
                        startActivity(new Intent(ListOrderWaitingActivity.this, CreateOrderActivity.class));
                        break;
                    case R.id.menu_exit:
                        DialogExit dialogExit = new DialogExit();
                        dialogExit.exit(ListOrderWaitingActivity.this);
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
                onStart();
                pullRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {
        if (!list.isEmpty()) {
            edt_search.setVisibility(View.VISIBLE);
            recView.setVisibility(View.VISIBLE);
            adapter = new ListOrdersAdapter(ListOrderWaitingActivity.this, list);
            recView.setAdapter(adapter);
            searchOrderByPhone();
        } else {
            edt_search.setVisibility(View.INVISIBLE);
            recView.setVisibility(View.GONE);
            layout.setBackgroundResource(R.drawable.background_empty);
        }
    }

    @Override
    public void responseCreateOrder(OrderRoomBooked orderRoomBooked) {

    }
}