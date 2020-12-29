package com.positive.themeselector.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.positive.themeselector.R;
import com.positive.themeselector.adapter.ViewPagerFragmentAdapter;
import com.positive.themeselector.api.RetrofitClient;
import com.positive.themeselector.fragment.ThemeFragment;
import com.positive.themeselector.model.ThemeCategory;
import com.positive.themeselector.model.ThemeResponse;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ThemeResponse themeResponse;
    Context context;

    public ArrayList<ThemeCategory> themeCategories;

    ViewPager2 viewPager2;
    ViewPagerFragmentAdapter myAdapter;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = this;
        binding();
        callApi();
    }

    public void binding() {
        viewPager2 = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.home_tab_layout);
    }

    public void callApi() {
        Call<ThemeResponse> call = RetrofitClient.getInstance().getApi().getThemes();
        call.enqueue(new Callback<ThemeResponse>() {
            @Override
            public void onResponse(Call<ThemeResponse> call, Response<ThemeResponse> response) {

                themeResponse = response.body();
                themeCategories = themeResponse.data;
                setUpData();
            }

            @Override
            public void onFailure(Call<ThemeResponse> call, Throwable t) {
                Log.e("err", t.getMessage());
            }
        });

    }

    public void setUpData() {
        myAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        for (int i = 0; i < themeCategories.size(); i++) {
            if (themeCategories.get(i).themes.size() > 0) {
                myAdapter.addFragment(ThemeFragment.newInstance(themeCategories.get(i).Cat_Id,themeCategories.get(i).themes), themeCategories.get(i).Category_Name, themeCategories.get(i).Icon);
            }
        }
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(myAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setCustomView(getTabView(position));
            }
        }).attach();


    }

    public View getTabView(final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_tab, null);
        TextView tabItemName = (TextView) view.findViewById(R.id.tab_name);
        ImageView tabItemAvatar =
                (ImageView) view.findViewById(R.id.tab_image);
        ImageView tabBorder = view.findViewById(R.id.tab_border_image);

        tabItemName.setText(myAdapter.mFragmentTitleList.get(position));
        tabItemName.setTextColor(context.getResources().getColor(android.R.color.background_light));
        Glide.with(context).load(myAdapter.mFragmentIconList.get(position)).into(tabItemAvatar);
        return view;
    }

}