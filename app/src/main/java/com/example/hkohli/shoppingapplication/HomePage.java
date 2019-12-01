package com.example.hkohli.shoppingapplication;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import org.json.JSONException;

public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup The Logo and The name of the application
        toolbar.setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Kunal's App");
        toolbar.setSubtitle("Welcome to the app");

        managetabLayoutAndViewPager();



        // Floating button and drawer crap

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(HomePage.this, ""+"lll", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this).setTitle("Are You Sure you want to exit")
                    .setNegativeButton("Yes Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setPositiveButton("No, I will Stay",null)
                    .setNeutralButton("Logout",null)
                    .create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest. searchable.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if(id == R.id.action_search)
        {
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_sell) {

            startActivity(new Intent(this,ReadyToSell.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_shop) {

            Intent intent = new Intent(this,MyShop.class);
            intent.putExtra("SHOP_TYPE","MY");
            startActivity(intent);

        } else if (id == R.id.nav_enquiries) {
           startActivity(new Intent(this,MyOrders.class));

        } else if (id == R.id.nav_shop_setup) {
            startActivity(new Intent(this,EditProfile.class));

        } else if (id == R.id.nav_profile) {
            item.setTitle("Temp diff shop");
            Intent intent = new Intent(this,MyShop.class);
            intent.putExtra("SHOP_TYPE","NOTMY");
            startActivity(intent);

        } else if (id == R.id.nav_favourite) {
            Toast.makeText(HomePage.this, ""+id, Toast.LENGTH_SHORT).show();
            item.setTitle("Temp Checker");
            startActivity(new Intent(this,SplashScreen.class));

        } else if (id == R.id.nav_buy) {
            startActivity(new Intent(this,SearchResults.class));
        }
        else if(id == R.id.nav_sell) {
            startActivity(new Intent(this,LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void managetabLayoutAndViewPager()
    {
        tabLayout = (TabLayout)findViewById(R.id.home_page_tablayout);
        viewPager = (ViewPager)findViewById(R.id.content_home_page_frame_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Feed"));
        tabLayout.addTab(tabLayout.newTab().setText("Categories"));

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager()
                ,tabLayout.getTabCount());

        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }




}
