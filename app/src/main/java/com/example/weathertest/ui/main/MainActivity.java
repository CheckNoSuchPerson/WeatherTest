package com.example.weathertest.ui.main;

import static com.example.weathertest.constant.GlobalConstant.IS_INIT;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.example.executor.ExecutorManager;
import com.example.mydialog.MyDialog;
import com.example.view.SlideUpLayout;
import com.example.weathertest.App;
import com.example.weathertest.database.week.WeekWeather;
import com.example.weathertest.datainterface.DataInterface;
import com.example.weathertest.R;
import com.example.weathertest.base.BaseActivity;
import com.example.weathertest.constant.GlobalConstant;
import com.example.weathertest.constant.Response;
import com.example.weathertest.database.city.City;
import com.example.weathertest.database.city.CityManager;
import com.example.weathertest.database.day.WeatherDay;
import com.example.weathertest.databinding.ActivityMainBinding;
import com.example.weathertest.datasave.DataSaveHelper;
import com.example.weathertest.ui.citysearch.CitySearchActivity;
import com.example.weathertest.utils.WeatherMapUtil;
import com.example.weathertest.view.WeatherMainView;
import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.lang.ref.WeakReference;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private City default_city;
    private WeekWeatherAdapter adapter;

    private boolean dayCallback, weekCallback;
    private int count = 0;

    private Gson gson;
    private MyDialog dialog;
    private ViewStub viewStub;
    private SlideUpLayout mSlideUpLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //首次启动需要给一定时间等待数据初始化 和写入
        if (!MMKV.defaultMMKV().getBoolean(IS_INIT, false)) {
            try {
                App.getCount().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                finish();
            }
        }

        gson = new Gson();
        default_city = gson.fromJson(MMKV.defaultMMKV().getString(GlobalConstant.DEFAULT_CITY, null), City.class);
        binding.tvToolBar.setText(default_city.cityZh);
        //这里做一个数据库的查询测试
        /**ExecutorManager.mIoExecutor.execute(
         new Runnable() {
        @Override public void run() {
        try {
        //保证查询线程的执行时序
        App.getCount().await();
        List<City> cities = CityManager.getInstance().queryCitiesByProvince("北京");
        Log.e("debugt", "cities = " + cities.size());
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        }
        }
         );*/

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewStub = findViewById(R.id.vtslideup);

        initRecyclerView();
        initRefresh();
        initEvent();
        getWeatherInfo(default_city.id);
    }

    private void initRefresh() {
        binding.srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherInfo(default_city.id);
            }
        });
    }

    private void initRecyclerView() {
        adapter = new WeekWeatherAdapter(null);
        binding.rvWeatherList.setAdapter(adapter);
        binding.rvWeatherList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void getWeatherInfo(String id) {
        viewModel.getDayWeatherInfo(id);
        viewModel.getWeekWeatherInfo(id);
    }

    private void initEvent() {
        //菜单点击事件
        binding.toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_connect_110:
                        startPhoneCall("tel:110");
                        break;
                    case R.id.item_connect_120:
                        startPhoneCall("tel:120");
                        break;
                }
                return true;
            }
        });

        //左侧图标点击事件
        binding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CitySearchActivity.class);
                intent.putExtra(CitySearchActivity.VIEW_NAME, binding.rootMain.getId());

                @SuppressWarnings("unchecked")
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainActivity.this,
                        binding.actionBar,
                        CitySearchActivity.VIEW_NAME);

                // Now we can start the Activity, providing the activity options as a bundle
                ActivityCompat.startActivityForResult(MainActivity.this, intent, 1, activityOptions.toBundle());
            }
        });

        //浮标点击事件
        binding.actionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new MyDialog.Builder(MainActivity.this)
                        .setView(R.layout.dialog_tips)
                        .setViewText(R.id.tv_dialog_tips, getString(R.string.no_forecast_api))
                        .setViewClickListener(R.id.tv_dialog_sure, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        }).setDefaultWindowAnimator().show();
                //AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setView(R.layout.dialog_tips).show();
            }
        });

        //反馈点击事件
        binding.viewWeather.setListener(new WeatherMainView.FeedbackListener() {
            @Override
            public void onClick(WeakReference<View> v) {
                if (mSlideUpLayout == null) {
                    mSlideUpLayout = (SlideUpLayout) viewStub.inflate();
                }
                mSlideUpLayout.setVisibility(View.VISIBLE);
            }
        });

        //当日天气数据绑定
        viewModel.dayLivedata.observe(this, new Observer<Response<WeatherDay>>() {
            @Override
            public void onChanged(Response<WeatherDay> dayWeatherResponse) {
                handleResult(dayWeatherResponse, data -> {
                    binding.viewWeather.setData(Integer.parseInt(data.getTem()), data.getWea());
                    binding.viewWeather.setAnimation(WeatherMapUtil.getFileMap(data.getWea_img()));

                    //1.这里将数据做一次保存，方便下一个activity获取
                    //2.也可以将WeatherDay序列化 然后进行传递
                    DataSaveHelper.setData(data);

                    dayCallback = true;
                }, s -> {
                    dayCallback = false;
                    binding.viewWeather.setData(Integer.parseInt("0"), "null");
                });

                triggerCallback();
            }
        });

        //一周天气数据绑定
        viewModel.weekLivedata.observe(this, new Observer<Response<WeekWeather>>() {
            @Override
            public void onChanged(Response<WeekWeather> weekWeatherResponse) {
                handleResult(weekWeatherResponse, data -> {
                    adapter.setList(data.data);
                    weekCallback = true;
                }, s -> {
                    adapter.setList(null);
                    weekCallback = false;
                });
                triggerCallback();
            }
        });
    }

    private void triggerCallback() {
        count++;
        //代表两次请求都结束了
        if (count == 2) {
            count = 0;
            //更新刷新状态
            binding.srlRefresh.setRefreshing(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == requestCode) {
            String stringExtra = data != null ? data.getStringExtra(GlobalConstant.CITY_ID) : null;
            if (stringExtra != null) {
                binding.tvToolBar.setText(data.getStringExtra(GlobalConstant.CITY_ZH));
                getWeatherInfo(stringExtra);
                default_city = gson.fromJson(MMKV.defaultMMKV().getString(GlobalConstant.DEFAULT_CITY, null), City.class);
            }
        }

    }

    /**
     * 拨打电话
     *
     * @param number
     */
    private void startPhoneCall(String number) {
        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_DIAL);

        intent.setData(Uri.parse(number));

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (mSlideUpLayout != null && mSlideUpLayout.getVisibility() ==View.VISIBLE){
            mSlideUpLayout.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }
    }
}