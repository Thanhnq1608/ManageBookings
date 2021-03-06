package com.fpoly.managebookings.views.listOrderWaiting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fpoly.managebookings.R;
import com.fpoly.managebookings.adapter.ListOrdersAdapter;
import com.fpoly.managebookings.api.ApiService;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderRoomBooked;
import com.fpoly.managebookings.api.orderRoomBooked.ApiOrderBookedInterface;
import com.fpoly.managebookings.api.user.ApiLoginUserInterface;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.models.OrderRoomBooked;
import com.fpoly.managebookings.models.User;
import com.fpoly.managebookings.tool.DialogMessage;
import com.fpoly.managebookings.tool.FixSizeForToast;
import com.fpoly.managebookings.tool.LoadingDialog;
import com.fpoly.managebookings.tool.SharedPref_InfoUser;
import com.fpoly.managebookings.tool.UploadImage;
import com.fpoly.managebookings.views.createOrder.CreateOrderActivity;
import com.fpoly.managebookings.views.listOrdersCompleted.ListOrdersCompletedActivity;
import com.fpoly.managebookings.views.listRoomEmpty.ListRoomEmptyActivity;
import com.fpoly.managebookings.views.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class ListOrderWaitingActivity extends AppCompatActivity implements ApiOrderBookedInterface, ApiLoginUserInterface {
    private RecyclerView recView;
    private ListOrdersAdapter adapter;
    private ApiOrderRoomBooked getOrderWaiting = new ApiOrderRoomBooked(this);
    private ApiUser apiUser = new ApiUser(this);
    private Toolbar toolbar;
    private TextView toolbar_text;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RoundedImageView ava_drawer_header;
    private LinearLayout layout;
    private DrawerLayout drawerLayout;
    private FixSizeForToast fixSizeForToast = new FixSizeForToast(this);
    private LoadingDialog loadingDialog;
    private NavigationView navigationView;
    private EditText edt_search;
    private boolean doubleBackToExitPressedOnce = false;
    private static final String TAG = ListOrderWaitingActivity.class.getName();

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

        messageFirebase();

    }

    private void messageFirebase() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "FCM Fail" + task.getException());
                    return;
                }

                String token = task.getResult();

                if (!token.equals(SharedPref_InfoUser.getInstance(ListOrderWaitingActivity.this).loggedInUserTokenIdApp())) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("tokenId", token);
                    apiUser.updateTokenId(SharedPref_InfoUser.getInstance(ListOrderWaitingActivity.this).LoggedInUserToken(),jsonObject);
//                    SharedPref_InfoUser.getInstance(ListOrderWaitingActivity.this).storeUserToKenIdApp(token);
                }

                Log.e(TAG, token);
            }
        });
    }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && data != null){
                UploadImage.getInstance(this).uploadImage(data.getData());
            }
    }

    private void getInfoUser() {
        TextView tv_email_drawer_header, tv_fullname_drawer_header;
        RoundedImageView ava_drawer_header;


        View mView = navigationView.getHeaderView(0);
        tv_email_drawer_header = mView.findViewById(R.id.tv_email_drawer_header);
        tv_fullname_drawer_header = mView.findViewById(R.id.tv_fullname_drawer_header);
        ava_drawer_header = mView.findViewById(R.id.ava_drawer_header);

        ava_drawer_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage.getInstance(ListOrderWaitingActivity.this).changeAvatar();
            }
        });

        tv_email_drawer_header.setText(SharedPref_InfoUser.getInstance(this).LoggedInEmail());
        tv_fullname_drawer_header.setText(SharedPref_InfoUser.getInstance(this).LoggedInFullName());
        Picasso.get().load(SharedPref_InfoUser.getInstance(this).LoggedInUserAvatar()).placeholder(R.drawable.ic_user).error(R.drawable.ic_user).into(ava_drawer_header);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        fixSizeForToast.fixSizeToast("Nh???n  " + '"' + "TR??? V???" + '"' + "  l???n n???a ????? tho??t");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrderWaiting.getOrderByBookingStatus(0);

        //Loading Data
        loadingDialog = new LoadingDialog(ListOrderWaitingActivity.this);
        loadingDialog.startLoadingDialog(1000);

    }

    void searchOrderByPhone() {
        edt_search.setText("");
        if (edt_search != null) {
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
                    case R.id.menu_logout:
                        SharedPref_InfoUser.getInstance(ListOrderWaitingActivity.this).clearSharedPreferences();
                        startActivity(new Intent(ListOrderWaitingActivity.this, LoginActivity.class));
                        finishAffinity();
                        break;
                    case R.id.menu_exit:
                        DialogMessage dialogMessage = new DialogMessage();
                        dialogMessage.exit(ListOrderWaitingActivity.this);
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
                onStart();
                pullRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void getOrderWaiting(ArrayList<OrderRoomBooked> list) {
        if (list.size() != 0) {
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

    @Override
    public void loginSuccess(String token, User user, String status) {
        Log.e("User token", token);
    }

    @Override
    public void loginFail(String status) {

    }
}