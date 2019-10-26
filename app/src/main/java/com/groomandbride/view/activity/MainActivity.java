package com.groomandbride.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.groomandbride.R;
import com.groomandbride.data.body.ReqCategoryHallBody;
import com.groomandbride.data.models.Hall;
import com.groomandbride.data.models.MainCategory;
import com.groomandbride.data.network.ApiInterface;
import com.groomandbride.data.network.RetrofitApiClient;
import com.groomandbride.utils.SharedPrefsUtils;
import com.groomandbride.view.adapters.HallsAdapter;
import com.groomandbride.view.adapters.MainCateAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    RelativeLayout splashImg;
    TextView userName_nav, usermail_nav;
    LinearLayout navLoginRegLin;

    Button btnLoginNav;
    Button btnRegisterNav;
    EditText searchEt;

    Toolbar toolbar;
    NavigationView navigationView;

    ProgressBar progressBar;
    private RecyclerView mMainCatRecycler, mHallsRecycler;
    LinearLayoutManager mHallsRecLayoutManager;

    private MainCateAdapter mMainCatAdapter;
    private HallsAdapter mHallsAdapter;
    private ArrayList<MainCategory> mMainCatList = new ArrayList<>();
    private ArrayList<Hall.DataBean> mListFromSplash = new ArrayList<>();
    //private ArrayList<Hall.DataBean> mListOnPause = new ArrayList<>();
    HashMap<String,Integer> hashMapHalls;

    int pageNumAll = 0;
    int pageNumSub = 0;
    int mMainCatClicked = 0; //0 for all category and 1 for subcategory
    String mCurrentMainCatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initVars();

        //FirebaseMessaging.getInstance().subscribeToTopic("test");
        /*if (SharedPrefsUtils.getInstance().isFirstTimeHome()){
            splashImg.setVisibility(View.VISIBLE);
        }*/
        mListFromSplash = getIntent().getParcelableArrayListExtra("list");


        handleNavItems();
        loadMainCate();
        initMainCatRecycler(mMainCatList);
        initHallsRec();
        performClickOnPosZeroMainCate();

        btnLoginNav.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            //finish();
        });
        btnRegisterNav.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            //finish();
        });
        searchEt.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));

    }

/*

    private void hideSplashImg() {
        splashImg.setVisibility(View.GONE);
        SharedPrefsUtils.getInstance().setFirstTimeHome(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPrefsUtils.getInstance().setFirstTimeHome(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPrefsUtils.getInstance().setFirstTimeHome(true);
    }
*/

    private void handleNavItems() {
        if (!SharedPrefsUtils.getInstance().isUserLogged()){
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_fav).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navLoginRegLin.setVisibility(View.VISIBLE);
            userName_nav.setVisibility(View.GONE);
            usermail_nav.setVisibility(View.GONE);
        }else {
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_fav).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navLoginRegLin.setVisibility(View.GONE);
            userName_nav.setVisibility(View.VISIBLE);
            usermail_nav.setVisibility(View.VISIBLE);
            userName_nav.setText(SharedPrefsUtils.getInstance().getUsername());
            usermail_nav.setText(SharedPrefsUtils.getInstance().getUserObject().getUser().getUserEmail());

        }
    }

    private void performClickOnPosZeroMainCate() {
        try{
            new Handler().postDelayed(() -> {
                if (mMainCatRecycler.findViewHolderForAdapterPosition(0) != null){
                    mMainCatRecycler.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            },100);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void loadMainCate() {
        mMainCatList.add(new MainCategory("All", R.drawable.all,10, 0, R.drawable.all2));
        mMainCatList.add(new MainCategory("5d0cbfc9a758321414bf9871", "Hotel", R.drawable.hotel, R.drawable.hotel2));
        mMainCatList.add(new MainCategory("5d0cbfc9a758321414bf9872", "Club", R.drawable.club, R.drawable.club2));
        mMainCatList.add(new MainCategory("5d0cbfc9a758321414bf9874", "Villa", R.drawable.villa, R.drawable.villa2));
        mMainCatList.add(new MainCategory("5d0cbfc9a758321414bf9875", "Open area", R.drawable.openair, R.drawable.openair2));
        mMainCatList.add(new MainCategory("5d0cbfc9a758321414bf9873", "Yacht", R.drawable.yacht, R.drawable.yacht2));
        mMainCatList.add(new MainCategory("5d1214f4de675f000488d442", "Individual", R.drawable.building, R.drawable.building2));
    }

    private void initMainCatRecycler(ArrayList<MainCategory> mMainCatList) {
        mMainCatAdapter = new MainCateAdapter(mMainCatList, MainActivity.this, new MainCateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MainCategory itemMainCate) {
                showProgress();
                if (itemMainCate.getName().equals("All")){
                    mMainCatClicked = 0;
                    if (mListFromSplash != null && mListFromSplash.size() > 0){
                        pageNumAll = 1;
                        loadAllFromSplash(mListFromSplash);
                    }else {
                        pageNumAll = 0;
                        loadAllHalls(pageNumAll);
                    }

                }
                else {
                    if (mListFromSplash != null){
                        mListFromSplash.clear();
                    }
                    pageNumSub = 0;
                    mMainCatClicked = 1;
                    mCurrentMainCatId = itemMainCate.getId();
                    loadCatHalls(mCurrentMainCatId, pageNumSub);
                }
            }
        });
        LinearLayoutManager mLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mMainCatRecycler.setLayoutManager(mLayoutManagaer);
        mMainCatRecycler.setNestedScrollingEnabled(false);
        mMainCatRecycler.setHasFixedSize(true);
        mMainCatRecycler.setAdapter(mMainCatAdapter);

    }

    private void loadAllFromSplash(ArrayList<Hall.DataBean> mListFromSplash) {
        hideProgress();
        int startIndex = mHallsAdapter.mHallsList.size();
        mHallsAdapter.mHallsList.addAll(mListFromSplash);
        mHallsAdapter.notifyItemRangeInserted(startIndex, 10);
    }


    private void initHallsRec(){
        mHallsAdapter = new HallsAdapter(new ArrayList<>(), this, itemHall -> {
            //TODO go to hall details
            Intent intent = new Intent(getApplicationContext(), HallDetailesActivity.class);
            intent.putExtra("hall", itemHall);
            startActivity(intent);
        });
        mHallsRecLayoutManager = new LinearLayoutManager(MainActivity.this);
        mHallsRecycler.setLayoutManager(mHallsRecLayoutManager);
        mHallsRecycler.setNestedScrollingEnabled(true);
        mHallsRecycler.setHasFixedSize(true);
        mHallsRecycler.setAdapter(mHallsAdapter);

        //Pagination
        mHallsRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHallsRecLayoutManager != null && mHallsRecLayoutManager.findLastCompletelyVisibleItemPosition() == mHallsAdapter.mHallsList.size() - 1) {
                    //showProgress(progressBar);
                    if (mMainCatClicked == 0){
                        loadAllHalls(++pageNumAll);
                    }else {

                        loadCatHalls(mCurrentMainCatId, ++pageNumSub);
                    }

                }
            }
        });
    }


    private void loadCatHalls(String id, int pageNumSub) {
        ReqCategoryHallBody reqCategoryHallBody = new ReqCategoryHallBody(10, pageNumSub, id);

        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<Hall> resCateHallCall = apiService.getCategoryHalls(reqCategoryHallBody);
        resCateHallCall.enqueue(new Callback<Hall>() {
            @Override
            public void onResponse(Call<Hall> call, Response<Hall> response) {
                hideProgress();
                if (response.isSuccessful()){
                    //initHallsRec(response.body().getData());
                    if (response.body().isResult()){
                        if (pageNumSub == 0){
                            mHallsAdapter.mHallsList.clear();
                            mHallsAdapter.notifyDataSetChanged();
                        }
                        int startIndex = mHallsAdapter.mHallsList.size();
                        mHallsAdapter.mHallsList.addAll(response.body().getData());
                        mHallsAdapter.notifyItemRangeInserted(startIndex, 10);
                    }else {
                        //Toast.makeText(MainActivity.this, "No more halls found!", Toast.LENGTH_SHORT).show();
                        Log.i("GB", "onResponse: No more halls found!");
                    }
                }
            }

            @Override
            public void onFailure(Call<Hall> call, Throwable t) {
                hideProgress();
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }

    private void loadAllHalls(int pageNumAll) {

        hashMapHalls = new HashMap<>();
        hashMapHalls.put("limit", 10);
        hashMapHalls.put("offset", pageNumAll);

        ApiInterface apiService =  RetrofitApiClient.getClient().create(ApiInterface.class);
        Call<Hall> resHallCall = apiService.getAllHalls(hashMapHalls);
        resHallCall.enqueue(new Callback<Hall>() {
            @Override
            public void onResponse(Call<Hall> call, Response<Hall> response) {
                hideProgress();
                //hideSplashImg();
                if (response.code() == 200){
                    //initHallsRec(response.body().getData());
                    if (response.body().getData().size() != 0){
                        if (pageNumAll == 0){
                            mHallsAdapter.mHallsList.clear();
                            mHallsAdapter.notifyDataSetChanged();
                        }
                        int startIndex = mHallsAdapter.mHallsList.size();
                        mHallsAdapter.mHallsList.addAll(response.body().getData());
                        mHallsAdapter.notifyItemRangeInserted(startIndex, 10);
                    }else {
                        //Toast.makeText(MainActivity.this, "No more halls found!", Toast.LENGTH_SHORT).show();
                        Log.i("GB", "onResponse: No more halls found!");
                    }

                }
            }

            @Override
            public void onFailure(Call<Hall> call, Throwable t) {
                hideProgress();
                //hideSplashImg();
                Log.e("Error", "onFailure: " + t.getMessage() );
                showNoteDialog("Error", String.valueOf(getText(R.string.network_error)));
            }
        });
    }


    private void initVars() {
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        splashImg = findViewById(R.id.splashImg);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mMainCatRecycler = findViewById(R.id.recMainCategories);
        mHallsRecycler = findViewById(R.id.recMainHalls);
        progressBar = findViewById(R.id.progress_bar);
        searchEt = findViewById(R.id.search_edit_text);

        ImageView drawer_button = findViewById(R.id.menu_icon);
        drawer_button.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));

        //adding the nxt 2 lines to set selected NavigationDrawer to Home fragment on start of app.
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);


        //add action when click on item in header view from Drawer.
        View headerview = navigationView.getHeaderView(0);
        userName_nav = headerview.findViewById(R.id.userName_nav);
        usermail_nav = headerview.findViewById(R.id.usermail_nav);
        navLoginRegLin = headerview.findViewById(R.id.navLoginRegLin);
        btnLoginNav = headerview.findViewById(R.id.btnLoginNav);
        btnRegisterNav = headerview.findViewById(R.id.btnRegisterNav);
    }


    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
        handleNavItems();
        //loadAllFromSplash(mListOnPause);
        //mListFromSplash = getIntent().getParcelableArrayListExtra("list");
        /*NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //adding the nxt 2 lines to set selected NavigationDrawer to Home fragment on start of app.
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);
        performClickOnPosZeroMainCate();*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //act as pressed on home
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            performClickOnPosZeroMainCate();
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_fav) {
            startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
        } else if (id == R.id.nav_privacy) {
            startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
        } else if (id == R.id.nav_terms) {
            startActivity(new Intent(MainActivity.this, TermsActivity.class));
        }else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
        }else if (id == R.id.nav_logout) {
            showLogoutDialog();
            navigationView.setCheckedItem(R.id.nav_home);
            navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_fav).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navLoginRegLin.setVisibility(View.VISIBLE);
            userName_nav.setVisibility(View.GONE);
            usermail_nav.setVisibility(View.GONE);
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
