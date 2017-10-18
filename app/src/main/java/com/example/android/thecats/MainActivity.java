package com.example.android.thecats;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.android.thecats.response.Category;
import com.example.android.thecats.response.Image;
import com.example.android.thecats.response.Response;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends ActionBarActivity {
    private static final String CUSTOM_CATEGORY = "favourites";
    private static final String ALL_CATEGORIES = "ALL";

    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    private String[] categories;
    private int currentCategoryIndex = -1;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    private ConnectionReceiver connectionReceiver = new ConnectionReceiver();
    private boolean connected = false;

    private CallbackManager callbackManager;
    private String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.registerReceiver(connectionReceiver, new IntentFilter(
                "android.net.conn.CONNECTIVITY_CHANGE"));


        tryConnect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(connectionReceiver);
    }


    public void tryConnect() {
        if (Utils.isOnline(this) && !connected) {
            prepareMainActivity();
            prepareNavigationDrawer();
            configToolBar();
            initFacebookLogin();
            checkAlreadyLogging();
            connected = true;
        }
    }

    private boolean checkAlreadyLogging() {
        AccessToken token;
        token = AccessToken.getCurrentAccessToken();
        if (token != null && userId == null) {
            userId = token.getUserId();
            Glide
                    .with(MainActivity.this)
                    .load(Utils.getProfilePhotoById(userId))
                    .asBitmap()
                    .centerCrop()
                    .into(getBitmapTargetForUserPhoto());
            String title = "id:" + userId;
            ((TextView) findViewById(R.id.user_id)).setText(title);
            return true;
        }
        return token != null;
    }

    // round corners
    private BitmapImageViewTarget getBitmapTargetForUserPhoto() {
        final ImageView mImg = ((ImageView) findViewById(R.id.user_pic));
        return new BitmapImageViewTarget(mImg) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mImg.setImageDrawable(circularBitmapDrawable);
            }
        };
    }

    private void initFacebookLogin() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {

                userId = loginResult.getAccessToken().getUserId();
//                String authToken = loginResult.getAccessToken().getToken();

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "login canceled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });


        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    //write your code here what to do when user logout
                    ImageView mImg = ((ImageView) findViewById(R.id.user_pic));
                    mImg.setImageResource(android.R.color.transparent);
                    userId = null;
                    ((TextView) findViewById(R.id.user_id)).setText("");
                } else {
                    Glide
                            .with(MainActivity.this)
                            .load(Utils.getProfilePhotoById(userId))
                            .asBitmap()
                            .centerCrop()
                            .into(getBitmapTargetForUserPhoto());
                    String title = "id:" + userId;
                    ((TextView) findViewById(R.id.user_id)).setText(title);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(MainActivity.this, "onActivityResult " + requestCode + " " + resultCode, Toast.LENGTH_LONG).show();

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean loadMore(RecyclerViewAdapter adapter, int count) {
        if (!Utils.isOnline(this) || adapter == null) return false;
        ArrayList<Image> createLists = null;
        if (categories[currentCategoryIndex].equals(ALL_CATEGORIES)) {
            createLists = loadFirst(count, null);
            adapter.addItems(createLists);
        } else if (!categories[currentCategoryIndex].equals(CUSTOM_CATEGORY)) {
            createLists = loadFirst(count, categories[currentCategoryIndex]);
            adapter.addItems(createLists);
        } else return false;

        return true;
    }

    private void prepareMainActivity() {
        recyclerView = (RecyclerView) findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Image> createLists = loadFirst(50, null);

        adapter = new RecyclerViewAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);

        adapter.setClickListener(new RecyclerClickListener() {
            @Override
            public void onClick(View v) {
                Image img = adapter.getItem(getPos());

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", img.getId());
                intent.putExtra("pos", String.valueOf(getPos()));
                intent.putExtra("url", img.getUrl());
                intent.putExtra("userId", userId);
                //Start details activity
                startActivity(intent);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = ((GridLayoutManager) recyclerView.getLayoutManager());

                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    //End of list
                    loadMore(adapter, 50);
                }

            }
        });
    }


    private void prepareNavigationDrawer() {
        categories = loadCategories();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, categories));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (categories[i].equals(CUSTOM_CATEGORY) && !checkAlreadyLogging()) {
                        Toast.makeText(MainActivity.this, "Login first!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ArrayList<Image> images = null;
                    if (categories[i].equals(ALL_CATEGORIES)) {
                        images = loadFirst(50, null);
                    } else images = loadFirst(50, categories[i]);
                    currentCategoryIndex = i;
                    if (toolbar != null) toolbar.setTitle(categories[i]);
                    Toast.makeText(MainActivity.this, "Category " + categories[i], Toast.LENGTH_SHORT).show();
                    adapter.setGalleryList(images);
                    recyclerView.scrollToPosition(0);
                    mDrawerLayout.closeDrawers();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private class LoadCategoriesTask extends AsyncTask<Void, Void, retrofit2.Response<Response>> {

        @Override
        protected retrofit2.Response<Response> doInBackground(Void... voids) {
            retrofit2.Response<Response> response = null;
            try {
                response = App.getApi().getCategories().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private String[] loadCategories() {
        Response response = null;
        LoadCategoriesTask t = new LoadCategoriesTask();
        t.execute();
        try {
            retrofit2.Response<Response> rez = t.get();
            if (rez == null)
                Toast.makeText(this, "Categories Load Failed", Toast.LENGTH_LONG).show();
            else {
                response = rez.body();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<String> strings = new ArrayList<>();

        strings.add(CUSTOM_CATEGORY);
        strings.add(ALL_CATEGORIES);
        currentCategoryIndex = 1;

        for (Category c : response.getData().getCategories().getCategory()) {
            strings.add(c.getName());
        }

        String[] stockArr = new String[strings.size()];
        stockArr = strings.toArray(stockArr);

        return stockArr;
    }

    private void configToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.categ);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);


        new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
    }


    private class LoadImagesTask extends AsyncTask<String, Void, retrofit2.Response<com.example.android.thecats.response.Response>> {

        @Override
        protected retrofit2.Response<com.example.android.thecats.response.Response> doInBackground(String... strings) {
            retrofit2.Response<com.example.android.thecats.response.Response> response = null;
            try {
                if (strings.length < 2) throw new IOException();
                if (strings[0] == null)
                    response = App.getApi().getData(Integer.parseInt(strings[1])).execute();
                else if (strings[0].equals(CUSTOM_CATEGORY))
                    response = App.getApi().getFavourites(userId).execute();
                else
                    response = App.getApi().getData(Integer.parseInt(strings[1]), strings[0]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    private ArrayList<Image> loadFirst(int count, String category) {
        com.example.android.thecats.response.Response response = null;
        LoadImagesTask t = new LoadImagesTask();
        t.execute(category, String.valueOf(count));
        try {
            retrofit2.Response<com.example.android.thecats.response.Response> rez = t.get();
            if (rez == null) Toast.makeText(this, "Error loading", Toast.LENGTH_LONG).show();
            else {
                response = rez.body();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<Image> images = new ArrayList<>();
        if (response != null)
            images.addAll(response.getData().getImages().getImages());

//        System.out.println("sizesizesizesizesize " + images.size());
//        Toast.makeText(MainActivity.this, images.size(), Toast.LENGTH_LONG).show();
        return images;
    }

}
