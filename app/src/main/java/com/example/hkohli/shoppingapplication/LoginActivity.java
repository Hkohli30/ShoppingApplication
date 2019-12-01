package com.example.hkohli.shoppingapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Welcome to Dealing India");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        manageFloatingButton();

        manageTabLayoutAndViewPager();



    }

    public void manageTabLayoutAndViewPager()
    {
        viewPager = (ViewPager)findViewById(R.id.login_page_viewPager);
        tabLayout = (TabLayout)findViewById(R.id.login_page_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("SignUp"));

        MyAdapter myAdapter = new MyAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(myAdapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    public void manageFloatingButton()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // Adapter Class
    class MyAdapter extends FragmentStatePagerAdapter
    {
        int tab_size;
        public MyAdapter(FragmentManager fm,int tab_size)
        {
            super(fm);
            this.tab_size = tab_size;

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            switch(position)
            {
                case 0 : fragment = new LoginFragment();    break;
                case 1 : fragment = new SignupFragment();    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return tab_size;
        }
    }

}
