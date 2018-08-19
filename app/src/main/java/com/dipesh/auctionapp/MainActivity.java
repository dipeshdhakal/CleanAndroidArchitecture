package com.dipesh.auctionapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sushant.auctionapp.R;
import com.dipesh.auctionapp.addauctionitem.AddAuctionItemActivity;
import com.dipesh.auctionapp.auctionitems.AuctionItemsFragment;
import com.dipesh.auctionapp.data.RepositoryImpl;
import com.dipesh.auctionapp.login.LoginActivity;
import com.dipesh.auctionapp.wonitems.WonItemsFragment;

/**
 * Created by Dipeshdhakal on 5/19/16.
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tlMainActivity;
    private ViewPager vpMainActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        tlMainActivity = (TabLayout) findViewById(R.id.tlMainActivity);
        vpMainActivity = (ViewPager) findViewById(R.id.vpMainActivity);
        vpMainActivity.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));

        tlMainActivity.setupWithViewPager(vpMainActivity);

    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }


    private static class MainViewPagerAdapter extends FragmentPagerAdapter{

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 1){
                return AuctionItemsFragment.newInstance();
            } else {
                return WonItemsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 1){
                return "Auction Items";
            } else {
                return "Won items";
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menuAddAuctionItem){
            AddAuctionItemActivity.start(this);
        } else if(item.getItemId() == R.id.menuLogout){
            RepositoryImpl.getInstance().clearSession();
            finish();
            LoginActivity.start(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
