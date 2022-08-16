package com.example.weathertest;

import static com.example.weathertest.constant.GlobalConstant.IS_INIT;

import androidx.room.Room;

import com.example.BaseApp;
import com.example.executor.ExecutorManager;
import com.example.net.HttpManager;
import com.example.weathertest.constant.GlobalConstant;
import com.example.weathertest.database.city.AppDatabase;
import com.example.weathertest.database.city.City;
import com.example.weathertest.database.city.CityManager;
import com.example.weathertest.database.day.DayWeatherDatabase;
import com.example.weathertest.database.day.DayWeatherManager;
import com.example.weathertest.database.week.WeekDbManager;
import com.example.weathertest.utils.DefaultCityUtil;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import dagger.hilt.android.HiltAndroidApp;

/**
 * Create by Hastur
 * on 2022/8/5
 */
@HiltAndroidApp
public class App extends BaseApp {
    private static App mApp;
    private static CountDownLatch count;

    @Override
    public void onCreate() {
        super.onCreate();
        HttpManager.init(GlobalConstant.BASE_URL);
        HttpManager.getInstance();
        mApp = this;
        count = new CountDownLatch(1);
        ExecutorManager.mIoExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //DatabaseCopyUtil.copyDataBase(App.this, "city_db");
                //初始化3个数据库
                CityManager.getInstance();
                DayWeatherManager.getInstance();
                WeekDbManager.getInstance();
                boolean isInit = MMKV.defaultMMKV().getBoolean(IS_INIT, false);
                if (!isInit) {
                    initDatabase();
                }
                count.countDown();
            }
        });
    }

    //模拟数据，仅加载一次。如果有条件可以去执行assets目录下的sql脚本生成db，然后将数据copy到相应目录下
    private void initDatabase() {
        List<City> list = new ArrayList<>();
        City city = new City("101010100", "beijing", "北京", "beijing", "北京", "China", "中国", "beijing", "北京", "39.904989", "116.405285");
        list.add(city);
        list.add(new City("101010200", "haidian", "海淀", "beijing", "北京", "China", "中国", "beijing", "北京", "39.956074", "116.310316"));
        list.add(new City("101010300", "chaoyang", "朝阳", "beijing", "北京", "China", "中国", "beijing", "北京", "39.921489", "116.486409"));
        list.add(new City("101010400", "shunyi", "顺义", "beijing", "北京", "China", "中国", "beijing", "北京", "40.128936", "116.653525"));
        list.add(new City("101010500", "huairou", "怀柔", "beijing", "北京", "China", "中国", "beijing", "北京", "40.324272", "116.637122"));
        list.add(new City("101010600", "tongzhou", "通州", "beijing", "北京", "China", "中国", "beijing", "北京", "39.902486", "116.658603"));
        list.add(new City("101010700", "changping", "昌平", "beijing", "北京", "China", "中国", "beijing", "北京", "40.218085", "116.235906"));
        list.add(new City("101010800", "yanqing", "延庆", "beijing", "北京", "China", "中国", "beijing", "北京", "40.465325", "115.985006"));
        list.add(new City("101010900", "fengtai", "丰台", "beijing", "北京", "China", "中国", "beijing", "北京", "39.863642", "116.286968"));
        list.add(new City("101011000", "shijingshan", "石景山", "beijing", "北京", "China", "中国", "beijing", "北京", "39.914601", "116.195445"));
        list.add(new City("101011100", "daxing", "大兴", "beijing", "北京", "China", "中国", "beijing", "北京", "39.728908", "116.338033"));
        list.add(new City("101011200", "fangshan", "房山", "beijing", "北京", "China", "中国", "beijing", "北京", "39.735535", "116.139157"));
        list.add(new City("101011300", "miyun", "密云", "beijing", "北京", "China", "中国", "beijing", "北京", "40.377362", "116.843352"));
        list.add(new City("101011400", "mentougou", "门头沟", "beijing", "北京", "China", "中国", "beijing", "北京", "39.937183", "116.105381"));
        list.add(new City("101011500", "pinggu", "平谷", "beijing", "北京", "China", "中国", "beijing", "北京", "40.144783", "117.112335"));
        list.add(new City("101011600", "dongcheng", "东城", "beijing", "北京", "China", "中国", "dongcheng", "东城", "39.917544", "116.418757"));
        list.add(new City("101011700", "xicheng", "西城", "beijing", "北京", "China", "中国", "xicheng", "西城", "39.915309", "116.366794"));

        list.add(new City("101220101","hefei","合肥","anhui","安徽","China","中国","hefei","合肥","31.86119","117.283042"));
        list.add(new City("101220102","changfeng","长丰","anhui","安徽","China","中国","hefei","合肥","32.478548","117.164699"));
        list.add(new City("101220103","feidong","肥东","anhui","安徽","China","中国","hefei","合肥","31.883992","117.463222"));
        list.add(new City("101220104","feixi","肥西","anhui","安徽","China","中国","hefei","合肥","31.719646","117.166118"));
        list.add(new City("101220105","chaohu","巢湖","anhui","安徽","China","中国","hefei","合肥","31.600518","117.874155"));
        list.add(new City("101220106","lujiang","庐江","anhui","安徽","China","中国","hefei","合肥","31.251488","117.289844"));
        list.add(new City("101220107","yaohai","瑶海","anhui","安徽","China","中国","hefei","合肥","31.86961","117.315358"));
        list.add(new City("101220108","luyang","庐阳","anhui","安徽","China","中国","hefei","合肥","31.869011","117.283776"));
        list.add(new City("101220109","shushan","蜀山","anhui","安徽","China","中国","hefei","合肥","31.855868","117.262072"));
        list.add(new City("101220110","baohe","包河","anhui","安徽","China","中国","hefei","合肥","31.82956","117.285751"));
        list.add(new City("101220201","bengbu","蚌埠","anhui","安徽","China","中国","bengbu","蚌埠","32.939667","117.363228"));
        list.add(new City("101220202","huaiyuan","怀远","anhui","安徽","China","中国","bengbu","蚌埠","32.956934","117.200171"));
        list.add(new City("101220203","guzhen","固镇","anhui","安徽","China","中国","bengbu","蚌埠","33.318679","117.315962"));
        list.add(new City("101220204","wuhe","五河","anhui","安徽","China","中国","bengbu","蚌埠","33.146202","117.888809"));
        list.add(new City("101220205","longzihu","龙子湖","anhui","安徽","China","中国","bangbu","蚌埠","32.950452","117.382312"));
        list.add(new City("101220206","bangshan","蚌山","anhui","安徽","China","中国","bangbu","蚌埠","32.938066","117.355789"));
        list.add(new City("101220207","yuhui","禹会","anhui","安徽","China","中国","bangbu","蚌埠","32.931933","117.35259"));
        list.add(new City("101220208","huaishang","淮上","anhui","安徽","China","中国","bangbu","蚌埠","32.963147","117.34709"));
        list.add(new City("101220301","wuhu","芜湖","anhui","安徽","China","中国","wuhu","芜湖","31.326319","118.376451"));
        list.add(new City("101220302","fanyang","繁昌","anhui","安徽","China","中国","wuhu","芜湖","31.080896","118.201349"));
        list.add(new City("101220303","wuhuxian","芜湖县","anhui","安徽","China","中国","wuhu","芜湖","31.145262","118.572301"));
        list.add(new City("101220304","nanling","南陵","anhui","安徽","China","中国","wuhu","芜湖","30.919638","118.337104"));
        list.add(new City("101220305","wuwei","无为","anhui","安徽","China","中国","wuhu","芜湖","31.303075","117.911432"));
        list.add(new City("101220306","jinghu","镜湖","anhui","安徽","China","中国","wuhu","芜湖","31.32559","118.376343"));
        list.add(new City("101220307","yijiang","弋江","anhui","安徽","China","中国","wuhu","芜湖","31.313394","118.377476"));
        list.add(new City("101220308","jiujiang","鸠江","anhui","安徽","China","中国","wuhu","芜湖","31.362716","118.400174"));
        list.add(new City("101220309","sanshan","三山","anhui","安徽","China","中国","wuhu","芜湖","31.225423","118.233987"));
        list.add(new City("101220401","huainan","淮南","anhui","安徽","China","中国","huainan","淮南","32.647574","117.018329"));
        list.add(new City("101220402","fengtai","凤台","anhui","安徽","China","中国","huainan","淮南","32.705382","116.722769"));
        list.add(new City("101220403","panji","潘集","anhui","安徽","China","中国","huainan","淮南","32.782117","116.816879"));
        list.add(new City("101220404","datong","大通","anhui","安徽","China","中国","huainan","淮南","32.632066","117.052927"));
        list.add(new City("101220405","tianjiaan","田家庵","anhui","安徽","China","中国","huainan","淮南","32.644342","117.018318"));
        list.add(new City("101220406","xiejiaji","谢家集","anhui","安徽","China","中国","huainan","淮南","32.598289","116.865354"));

        CityManager.getInstance().insertCityList(list);
        MMKV.defaultMMKV().putBoolean(IS_INIT, true);

        //首次加载默认的city
        DefaultCityUtil.setDefaultCity(city);
    }

    public static App getApp() {
        return mApp;
    }


    public static CountDownLatch getCount() {
        return count;
    }

}
