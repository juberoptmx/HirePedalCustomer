package com.hirepedal.customer.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hirepedal.customer.R;
import com.hirepedal.customer.aboutme.TermsConditionsFragment;
import com.hirepedal.customer.aboutus.AboutUsFragment;
import com.hirepedal.customer.application.HirePedalApplication;
import com.hirepedal.customer.base.BaseActivity;
import com.hirepedal.customer.dashboard.DashboardFragment;
import com.hirepedal.customer.models.User;
import com.hirepedal.customer.myaccount.MyAccountFragment;
import com.hirepedal.customer.signup.SignUpListener;
import com.hirepedal.customer.splash.SplashFragment;
import com.hirepedal.customer.utils.Logger;
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager;
import java.io.File;


public class RootActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, SignUpListener {

    private static final String TAG = RootActivity.class.getSimpleName();
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText todoListTitle;

    RelativeLayout fragment_container;

    public static RootActivity rootActivity = null;

    public static boolean isAppWentToBg = false;
    public static boolean isWindowFocused = false;

    public RootActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HirePedalApplication.getInstance().getComponent().inject(this);
        setContentView(R.layout.activity_root);
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.setNavigationView(toolbar);
        fragment_container = (RelativeLayout) findViewById(R.id.fragment_container);
        rootActivity = RootActivity.this;

        todoListTitle = findViewById(R.id.et_todo_title);


        if(SharedPreferenceManager.getFeaturePreference(RootActivity.this)!=null){
            int feature = Integer.parseInt(SharedPreferenceManager.getFeaturePreference(RootActivity.this));
            Log.e("Feature",""+feature);
            showFragment(new DashboardFragment());
            // showFragment(new SearchFragment());
        } else{
            showFragment(new SplashFragment());
        }

    }


    public void onBackStackChanged() {
        super.onBackStackChanged();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    Fragment f = fragmentManager.findFragmentById(R.id.mainContainer);
                    if (f != null && f instanceof DashboardFragment) {
//                        setActionTitleBar(false, false, R.string.dashboard);
//                        showHamburger(true);
//
//                        if (toggle != null) {
//                            toggle.setDrawerIndicatorEnabled(true);
//                            toggle.syncState();
//                        }
//
//                        getSupportActionBar().setHomeButtonEnabled(true);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, 500);

    }


    @Override
    public void setActionTitleBar(boolean noActionBar, boolean backButtonVisible, int title) {
        this.noTitle(noActionBar);
        if (!noActionBar) {
            this.setTitle(title);
        } else {
            showHamburger(false);
            return;
        }
        if (backButtonVisible) {
            this.showHamburger(false);
        } else {
            this.showHamburger(true);
        }
    }

    @Override
    public void setActionTitleBar(boolean noActionBar, boolean backButtonVisible, CharSequence title) {

        this.noTitle(noActionBar);
        if (!noActionBar) {
            this.setTitle(title);
        } else {
            showHamburger(false);
            return;
        }
        if (backButtonVisible) {
            this.showHamburger(false);
        } else {
            this.showHamburger(true);
        }
    }

    @Override
    public void updateHamburger() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        /*Setting user info*/
        setUserInHamburgerMenu();
    }

    public void setUserInHamburgerMenu(){

        User user = new Gson().fromJson(SharedPreferenceManager.getUser(this),new TypeToken<User>(){}.getType());

        ImageView profileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_image);
        profileImage.setImageResource(R.drawable.ic_profile);

        TextView firstName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_first_name);
        firstName.setText(user.getFullName());

        TextView mailid = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_mailid);
        mailid.setText(user.getWorkEmail());

        if (user.getProfilePicUrl() != null) {
            Uri uri =  Uri.parse(user.getProfilePicUrl());
            File imgFile = new File(uri.toString());

            if(imgFile.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                profileImage.setImageBitmap(bitmap);
            }
            else {
                profileImage.setImageResource(R.drawable.bg_dashboard);
            }

        }

        else {
            profileImage.setImageResource(R.drawable.bg_dashboard);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        switch (id) {
            case R.id.nav_dashboard: {

                DashboardFragment dashboardFragment = new DashboardFragment();
                clearBackStackTraceAndAddNew(dashboardFragment);
                break;
            }

            case R.id.nav_my_account: {
                MyAccountFragment myAccountFragment = new MyAccountFragment();
                showFragment(myAccountFragment);
                break;
            }

            case R.id.nav_about_us: {
                AboutUsFragment travelFragment = new AboutUsFragment();
                showFragment(travelFragment);
                break;
            }
            case R.id.nav_terms_conditions: {
                TermsConditionsFragment travelFragment = new TermsConditionsFragment();
                showFragment(travelFragment);
                break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showHamburger(boolean hamburgerShow) {
        //  The order should not be change, to show hamburger icon first hide back button and set drawerindicator to true...
        if (hamburgerShow) {
            backTitleButtonEnable(false);
            toggle.setDrawerIndicatorEnabled(true);
            ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        } else {
            toggle.setDrawerIndicatorEnabled(false);
            ((DrawerLayout) findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            backTitleButtonEnable(true);
            toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RootActivity.this.onBackPressed();
                }
            });
        }
    }

    @Override
    public void showHamburger(boolean hamburgerShow, final String fragmentag) {
        //  The order should not be change, to show hamburger icon first hide back button and set drawerindicator to true...
        if (hamburgerShow) {
            this.backTitleButtonEnable(false);
            this.toggle.setDrawerIndicatorEnabled(true);
            ((DrawerLayout) this.findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        } else {
            this.toggle.setDrawerIndicatorEnabled(false);
            ((DrawerLayout) this.findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            this.backTitleButtonEnable(true);
            this.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onBackPressed();

                    /*if (fragmentag.equals(NotificationFragment.class.getSimpleName()) == true) {
                        goToNotificationFragment();
                    } else {
                        onBackPressed();
                    }*/
                }
            });
        }
    }

    @Override
    public void showHamburger(boolean hamburgerShow, final boolean homeButton) {
        //  The order should not be change, to show hamburger icon first hide back button and set drawerindicator to true...
        if (hamburgerShow) {
            this.backTitleButtonEnable(false, homeButton);
            this.toggle.setDrawerIndicatorEnabled(true);
            ((DrawerLayout) this.findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawer(GravityCompat.START);
        } else {
            this.toggle.setDrawerIndicatorEnabled(false);
            ((DrawerLayout) this.findViewById(R.id.drawer_layout)).setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            this.backTitleButtonEnable(true, homeButton);
            this.toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeButton == true) {
                        onBackPressed();
                    } else {
                        onBackPressed();
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();




    }

    private void setNavigationView(Toolbar toolbar) {
        drawerLayout = (DrawerLayout) RootActivity.this.findViewById(R.id.drawer_layout);
        try {
            this.toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerStateChanged(int newState) {
                    if (newState == DrawerLayout.STATE_SETTLING) {
                        if (!isDrawerOpen()) {
                            // starts opening
                            Logger.d(TAG, "Drawer Is opened");
                            //updateProfile();
                        } else {
                            // closing drawer
                            Logger.d(TAG, "Drawer Is Closed");
                        }

                        supportInvalidateOptionsMenu();
                    }
                }
            };
            drawerLayout.addDrawerListener(this.toggle);

            this.toggle.syncState();

            this.navigationView = (NavigationView) this.findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            setUserInHamburgerMenu();




        } catch (NullPointerException nex) {
            nex.printStackTrace();
        }
    }

    boolean isDrawerOpen() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //drawer is open
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public static RootActivity getInstance() {
        return rootActivity;
    }

    @Override
    protected void onStart() {
        applicationWillEnterForeground();
        super.onStart();
    }

    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            isAppWentToBg = false;
        }
    }

    @Override
    protected void onPause() {
        this.completeProgress();
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.d(TAG, "onStop ");
        applicationdidenterbackground();
    }

    public void applicationdidenterbackground() {
        if (!isWindowFocused) {
            isAppWentToBg = true;
            /*Toast.makeText(getApplicationContext(),"App is Going to Background", Toast.LENGTH_SHORT).show();*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private Fragment getCurrentFragment() {
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        int stackCount = fragmentManager.getBackStackEntryCount();
        if( fragmentManager.getFragments() != null ) return fragmentManager.getFragments().get( stackCount > 0 ? stackCount-1 : stackCount );
        else return null;
    }



    @Override
    public void completeSignUp(String message) {

    }


    @Override
    public void showEditText(boolean isShown){
        if(isShown){
            todoListTitle.setVisibility(View.VISIBLE);
        }else{
            todoListTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public String getTodoTitle() {
        return todoListTitle.getText().toString();
    }

    @Override
    public void clearTodoTitle() {
        todoListTitle.setText("");
    }

    @Override
    public void setTodoTitle(String title) {
        todoListTitle.setText(""+title);
    }

    @Override
    public String getPasswordTitle() {
        return todoListTitle.getText().toString();
    }


    @Override
    public void clearPasswordTitle() {
        todoListTitle.setText("");
    }

    @Override
    public void setPasswordTitle(String title) {
        todoListTitle.setText(""+title);
    }





}
