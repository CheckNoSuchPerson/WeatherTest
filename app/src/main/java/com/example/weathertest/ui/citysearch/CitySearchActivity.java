package com.example.weathertest.ui.citysearch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.SharedElementCallback;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.weathertest.R;
import com.example.weathertest.constant.GlobalConstant;
import com.example.weathertest.database.city.City;
import com.example.weathertest.database.city.CityManager;
import com.example.weathertest.database.day.WeatherDay;
import com.example.weathertest.databinding.ActivityCitySearchBinding;
import com.example.weathertest.datasave.DataSaveHelper;
import com.example.weathertest.view.CitySearchView;
import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.util.List;

public class CitySearchActivity extends AppCompatActivity {
    public static final String VIEW_NAME = "detail:header:root";
    ActivityCitySearchBinding binding;
    private CitySearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setTransitionName(binding.flWea, VIEW_NAME);

        initAnimatorListener();
        initRecycler();
        initSearchEvent();
    }

    //共享元素动画结束后显示后面的布局
    private void initAnimatorListener() {
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                binding.tvCityName.setVisibility(View.VISIBLE);
                binding.tvCityTemp.setVisibility(View.VISIBLE);
                binding.tvTempRange.setVisibility(View.VISIBLE);

                WeatherDay data = DataSaveHelper.getData();
                if (data != null) {
                    binding.tvTempRange.setText(getString(R.string.temp_range, data.getTem_day(), data.getTem_night()));
                    binding.tvCityTemp.setText(getString(R.string.temperature, Integer.parseInt(data.getTem())));
                    binding.tvCityName.setText(data.getCity());
                }
            }
        });
    }

    //初始化list
    private void initRecycler() {
        adapter = new CitySearchAdapter(null);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            City item = (City) adapter.getItem(position);
            MMKV.defaultMMKV().putString(GlobalConstant.DEFAULT_CITY, new Gson().toJson(item));
            Intent intent = new Intent();
            intent.putExtra(GlobalConstant.CITY_ID, item.id);
            intent.putExtra(GlobalConstant.CITY_ZH, item.cityZh);
            setResult(1, intent);
            finishAfterTransition();
        });

        binding.rvCitySearch.setAdapter(adapter);
        binding.rvCitySearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    //初始化搜索状态
    private void initSearchEvent() {
        binding.customCsv.setEventListener(new CitySearchView.EventListener() {
            @Override
            public void onCancelClick(View v) {
                adapter.setList(null);
            }

            @Override
            public void onSearchClick(String value) {
                adapter.setList(CityManager.getInstance().queryFormSearch(value));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}