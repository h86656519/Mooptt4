package com.example.user.moo_ptt4;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Park_json extends AppCompatActivity {
    SwipeRefreshLayout mSwipeBikeFriendRefresh;
    TextView parkname_twxtview, hourexpense_twxtview, distance_twxtview;
    List<Map<String, Object>> list = new ArrayList<>();
    // List<Park> list1 = new ArrayList<>();
    SimpleAdapter adapter;
    ListView park_listview;
    Switch swithch_RQ, swithch_distance;
    NavigationView left_NavigationView;
    Context context;
    String parkname, ID, hourexpense, parknbr;
    double parktLongitude, parkLatitude;
    int distance;
    int arraylength;
    Request request;
    final Map<String, Object> map1 = new HashMap<>();
    OkHttpClient client = new OkHttpClient();
    //  String url = "http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000292-002";
    String url = "http://data.ntpc.gov.tw/od/data/api/B1464EF0-9C7C-4A6F-ABF7-6BDF32847E68?$format=json";
    int index = 1;

    LocationManager mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_json);
//        findViewById 是找setContentView裡設的layout 裡的物件,其他xml的物件會找不到，回傳是null
//        ex:  parkname_twxtview = (TextView) findViewById(R.id.parkname);

        mLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        park_listview = (ListView) findViewById(R.id.park_listview);
        swithch_RQ = (Switch) findViewById(R.id.swithch_RQ);
        swithch_distance = (Switch) findViewById(R.id.swithch_distance);
        mSwipeBikeFriendRefresh = (SwipeRefreshLayout) findViewById(R.id.mSwipeBikeFriendRefresh);
        left_NavigationView = (NavigationView) findViewById(R.id.left_NavigationView);
        context = this;

//        swipe_container = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
//        swipe_container.setOnRefreshListener(this);
        aaa();

        // switcher(view);
        Toast parkjson_toast = Toast.makeText(Park_json.this,
                "DEMO : \n 1.okhttp 連線串接 Json  \n 2.listview 根據資料內容 距離 和 剩餘車位 做排序  \n 3.NavigationView 側選攔 \n 4."
                , Toast.LENGTH_LONG);
        View allgroupview = parkjson_toast.getView();
        allgroupview.setBackgroundResource(android.R.color.holo_green_light);
        parkjson_toast.setView(allgroupview);
        parkjson_toast.show();


    }

    public void aaa() {
        //使用OkGo的拦截器
        request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(Park_json.this, "連線失敗請，確認網路", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        //  System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    String test = responseBody.string();
                    // System.out.println(test);

                    JSONArray array = null;     // []
                    try {
                        array = new JSONArray(test);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //    JSONObject object = new JSONObject(jasonString);  // {}

                    arraylength = array.length(); //867
                    Log.i("JSON:", "arraylength : " + arraylength);
                    for (int i = 0; i <= 50; i++) {
                        // ID = array.getJSONObject(i).getString("ID").toString();
                        final Map<String, Object> map = new HashMap<>();

                        try {
                            parkname = array.getJSONObject(i).getString("NAME").toString();
                            parkLatitude = Double.parseDouble(array.getJSONObject(i).getString("TW97X").toString());
                            parktLongitude = Double.parseDouble(array.getJSONObject(i).getString("TW97Y").toString());

                            map.put("parkLatitude", parkLatitude);
                            map.put("parktLongitude", parktLongitude);
                            //  Log.i("JSON", "parkLatitude : " + parkLatitude);
                            //  Log.i("JSON", "parktLongitude : " + parktLongitude);
                            //  Log.i("JSON", "distance : " + distance);

                            // int parknamelength = array.getJSONObject(i).getString("NAME").toString().length();
                            int parknamelength = parkname.length();
                            //開頭不要有"新北市"  字尾不要"停車場"
                            if (parknamelength > 3) {
                                String parkname1 = String.valueOf(parkname).substring(0, 3);
                                if (parkname1.equalsIgnoreCase("新北市")) {
                                    //不要字尾"停車場"
                                    parkname = String.valueOf(parkname).substring(2, parknamelength - 3);
//                                    Log.i("JSON:", "parkname : " + parkname);
                                    map.put("parkname", parkname);
                                    map1.put("parkname", parkname);
                                } else {
                                    parkname = String.valueOf(parkname).substring(0, parknamelength - 3);
//                                    Log.i("JSON:", "parkname : " + parkname);
//                                    Log.i("java", "www" + map1.get("parkname"));
                                    map.put("parkname", parkname);
                                    map1.put("parkname", parkname);
                                }
                            }
                            parknbr = array.getJSONObject(i).getString("TYPE").toString();
                            map.put("parknbr", parknbr);
                            // Log.i("JSON:", "parknbr : " + parknbr);
//                            map.put("parkLatitude", parkLatitude);
//                            map.put("parktLongitude", parktLongitude);

                            int PAYEXlenhth = String.valueOf(array.getJSONObject(i).getString("PAYEX").toString()).length();
                            //   Log.i("json", "PAYEXlenhth : " + PAYEXlenhth);
                            map.put("hourexpensetotal", String.valueOf(array.getJSONObject(i).getString("PAYEX").toString()));
                            if (PAYEXlenhth > 8) {
                                String aaa = String.valueOf(array.getJSONObject(i).getString("PAYEX").toString()).substring(0, 8).toString();
                                String bbb = "小型車計時每小時";
                                if (aaa.equalsIgnoreCase(bbb)) {
                                    hourexpense = String.valueOf(array.getJSONObject(i).getString("PAYEX").toString()).substring(8, 10).toString();
                                    //  Log.i("JSON:", "hourexpense : " + hourexpense);
                                    map.put("hourexpense", hourexpense);
                                } else {
//                                  不能顯示小時費用的皆用詳細表示
                                    map.put("hourexpense", "詳細");
                                    // Log.i("JSON:", "hourexpense : " + hourexpense);
                                }
                            } else {
                                map.put("hourexpense", "詳細");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        list.add(map);
//                        adapter = new SimpleAdapter(
//                                context,
//                                list,
//                                R.layout.row_json,
//                                new String[]{"parkname", "hourexpense", "parknbr", "distance", "hourexpensetotal"},
//                                new int[]{R.id.parkname, R.id.hourexpense, R.id.parknbr, R.id.distance}
//                        );
                        //  park_listview.setAdapter(adapter);
                        //  adapter.notifyDataSetChanged();
//                        requestUserLocation(parkLatitude, parktLongitude, map);
//                        runOnUiThread(new Runnable() {
//                            public void run() {
//                                park_listview.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
                    }

                    adapter = new SimpleAdapter(
                            context,
                            list,
                            R.layout.row_json,
                            new String[]{"parkname", "hourexpense", "parknbr", "distance", "hourexpensetotal"},
                            new int[]{R.id.parkname, R.id.hourexpense, R.id.parknbr, R.id.distance}
                    );
                    runOnUiThread(new Runnable() {
                        public void run() {
                            park_listview.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();

                            requestUserLocation();
                        }
                    });

                }
                mSwipeBikeFriendRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //延迟一秒钟再执行任务
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                //  Log.i("json", "放這下拉ok");
                                //   Log.i("json", "index" + index);
                                mSwipeBikeFriendRefresh.setRefreshing(false);     //取消刷新状态
                                list.clear(); //下拉刷新前先把lst 給清掉，
                                aaa();
                                index++;
                                adapter.notifyDataSetChanged();
                            }
                        }, 100000);
                    }
                });
//        swithch_RQ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //breakbyrdistance();
//                // sorter(list);
//                Collections.sort(list, new MapComparatorAsc());
//                adapter.notifyDataSetChanged();
//            }
//        });


                park_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                PopupMenu popup = new PopupMenu(getApplicationContext(), view);
//              找其他xml的物件要用下面這行
                        View v = getLayoutInflater().inflate(R.layout.popupwindow_layout, null);
                        PopupWindow popWnd = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                popup.setOnDismissListener();
                        final String listname = list.get(position).get("parkname").toString();
                        String mapname = list.get(position).get("parkname").toString();
                        if (listname == mapname) {
                            String hourexpensetotal = list.get(position).get("hourexpensetotal").toString();
                            if (hourexpensetotal.equals(";")) {
                                hourexpensetotal = "無費用資訊";
                                Log.i("json", "hourexpensetotal" + hourexpensetotal);
                            }
//                    popup.getMenu().add(hourexpensetotal);
//                    popup.show();
//                   沒放popup的setOnDismissListener監聽器背景色不會變回，所以暫時做此功能

                            TextView popupwindows_textview = (TextView) v.findViewById(R.id.popupwindows_textview);
                            popupwindows_textview.setText(hourexpensetotal);

                            darkenBackground(0.2f); //調暗背景(非PopupWindow 的背景)
                            popWnd.showAtLocation(view, Gravity.CENTER, 0, -400);

                        }
                        //設監聽器將點選其他位置時回復正常背景色
                        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                darkenBackground(1f);
                            }
                        });
                        adapter.notifyDataSetChanged();
                        return false;
                    }
                });
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void requestUserLocation() {

        //判斷當前是否已經獲得了定位權限
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            //  Toast.makeText(Park_json.this, "權限沒開", Toast.LENGTH_SHORT).show();
//            如果是6.0以上的去需求權限
            requestCameraPermission();
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            final Map map = list.get(i);

            mLocation.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(final Location location) {
                    float[] result = new float[1];

//                Log.i("java", "緯度 : " + location.getLatitude());
//                Log.i("java", "經度 : " + location.getLongitude());

                    Double parkLatitude = Double.parseDouble(map.get("parkLatitude").toString());
                    Double parktLongitude = Double.parseDouble(map.get("parktLongitude").toString());
                    Log.i("json", parkLatitude + ","+parktLongitude);
                    Location.distanceBetween(location.getLatitude(),
                            location.getLongitude(),
                            parkLatitude, parktLongitude,
                            result);

                    distance = (int) result[0];
                    // Log.i("JSON", "distance : " + distance);

                    //              adapter.notifyDataSetChanged();
                    //從這co
                    getlistview(map);
                }

                @Override
                public void onStatusChanged(final String s, final int i, final Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(final String s) {
                }

                public void onProviderDisabled(final String s) {
                }
            }, Park_json.this.getMainLooper());
        }


    }

//    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
//    public void requestUserLocation(final double parkLatitude, final double parktLongitude, final Map map) {
//
//        final LocationManager mLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        //判斷當前是否已經獲得了定位權限
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
//            //  Toast.makeText(Park_json.this, "權限沒開", Toast.LENGTH_SHORT).show();
////            如果是6.0以上的去需求權限
//            requestCameraPermission();
//            return;
//        }
//
//        mLocation.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
//            @Override
//            public void onLocationChanged(final Location location) {
//                float[] result = new float[1];
//
////                Log.i("java", "緯度 : " + location.getLatitude());
////                Log.i("java", "經度 : " + location.getLongitude());
//                Location.distanceBetween(location.getLatitude(),
//                        location.getLongitude(),
//                        parkLatitude, parktLongitude,
//                        result);
//
//                distance = (int) result[0];
//                // Log.i("JSON", "distance : " + distance);
//
//                //              adapter.notifyDataSetChanged();
//                //從這co
//                getlistview(map);
//            }
//
//            @Override
//            public void onStatusChanged(final String s, final int i, final Bundle bundle) {
//            }
//
//            @Override
//            public void onProviderEnabled(final String s) {
//            }
//
//            public void onProviderDisabled(final String s) {
//            }
//        }, Park_json.this.getMainLooper());
//    }

    //list只有一個，map數量根據資料內容; 比對list 和 map 直接put 就會只有最會一筆的list
    public void getlistview(Map map) {
        for (int i = 0; i <= 50; i++) {
            //  Log.i("json", "list : " + i + list.get(i).get("parkname"));
            //  Log.i("json", "map : " + i + map.get("parkname"));

            //相同停車場名稱的才put
            if (list.get(i).get("parkname").equals(parkname)) {
                // Log.i("距離", "距離(公尺) : " + distance);
                // Log.i("json", "listsize" + list.size());
                //顯示單位換算 Log.i("json", "mapsize" + map1.size());
                if (distance > 1000000) {
                    String distanc = distance / 1000000 + ""; //km
                    //    Log.i("json", "distanc : " + distanc);

                    map.put("distance", distanc);
                } else {
                    String distanc = distance / 100000 + ""; //m
                    //Log.i("json", "distanc : " + distanc);
                    map.put("distance", distanc);
                }
                // list.add(map);
                //   fixlist(map);
            }
        }
        adapter.notifyDataSetChanged();
    }

    //修改list裡面的值
    public void updatelist(Map map) {
        // Log.i("java", "fixlist" + list.get(2).put("hourexpense", "100"));
        adapter.notifyDataSetChanged();
    }

    private void requestCameraPermission() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M)
            return;

        final List<String> permissionsList = new ArrayList<>();
        if (this.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.CAMERA);
        if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            permissionsList.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionsList.size() < 1)
            return;
        if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
            this.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0x00);
        else
            goToAppSetting();
    }

    private void goToAppSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", this.getPackageName(), null));
        startActivityForResult(intent, 0x00);
    }

    public void onSwitchClicked(View view) {
        switch (view.getId()) {
            case R.id.swithch_distance:
                if (swithch_distance.isChecked()) {
                    // Log.i("json", "swithch_distance : open");
                    Collections.sort(list, new breakbydistanceAsc());
                    adapter.notifyDataSetChanged();
                } else {
                    Collections.sort(list, new breakbydistanceDec());
                    // Log.i("json", "swithch_distance : close");
                    adapter.notifyDataSetChanged();
                }
                break;

            case R.id.swithch_RQ:
                if (swithch_RQ.isChecked()) {
                    //Log.i("json", "swithch_RQ : open");
                    Collections.sort(list, new breakbyparknbreAsc());
                    adapter.notifyDataSetChanged();
                } else {
                    // Log.i("json", "swithch_RQ : close");
                    Collections.sort(list, new breakbyparknbreDec());
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }


    static class breakbydistanceDec implements Comparator<Map<String, Object>> {
        @Override
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = Integer.valueOf(m1.get("distance").toString());
//            Object a =  m1.get("distance");
//            String aa =  a.toString();
//            Integer aaa = Integer.valueOf(aa);
//            Object v1 = m1.get("distance");
            Integer v2 = Integer.valueOf(m2.get("distance").toString());
            if (v1 != null) {
//                Log.i("json", "return : " + v1.compareTo(v2));
//                Log.i("JSON:", "parkname : " + m1.get("parkname").toString());
//                Log.i("JSON:", "parkname : " + m2.get("parkname").toString());
                return v1.compareTo(v2); // 升
            }
            return 0;
        }

    }

    static class breakbydistanceAsc implements Comparator<Map<String, Object>> {
        @Override
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = Integer.valueOf(m1.get("distance").toString());
            Integer v2 = Integer.valueOf(m2.get("distance").toString());
            if (v1 != null) {
//                Log.i("json", "return : " + v1.compareTo(v2));
//                Log.i("JSON:", "parkname : " + m1.get("parkname").toString());
//                Log.i("JSON:", "parkname : " + m2.get("parkname").toString());

                return v2.compareTo(v1);   //降
            }

            return 0;
        }

    }

    static class breakbyparknbreAsc implements Comparator<Map<String, Object>> {
        @Override
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = Integer.valueOf(m1.get("parknbr").toString());
            Integer v2 = Integer.valueOf(m2.get("parknbr").toString());
            if (v1 != null) {
//                Log.i("json", "return : " + v1.compareTo(v2));
//                Log.i("JSON:", "parkname : " + m1.get("parkname").toString());
//                Log.i("JSON:", "parkname : " + m2.get("parkname").toString());
                return v1.compareTo(v2); // 升
            }

            return 0;
        }

    }

    static class breakbyparknbreDec implements Comparator<Map<String, Object>> {
        @Override
        public int compare(Map<String, Object> m1, Map<String, Object> m2) {
            Integer v1 = Integer.valueOf(m1.get("parknbr").toString());
            Integer v2 = Integer.valueOf(m2.get("parknbr").toString());
            if (v1 != null) {
//                Log.i("json", "return : " + v1.compareTo(v2));
//                Log.i("JSON:", "parkname : " + m1.get("parkname").toString());
//                Log.i("JSON:", "parkname : " + m2.get("parkname").toString());
                return v2.compareTo(v1); // 降
            }

            return 0;
        }

    }

    //設定背景色
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("IOException", "連線關閉");
        Log.i("client", "client.cache : " + client.cache());
        if (client.cache() != null) {
            try {
                // client.connectionPool().evictAll();
                Log.i("client", "client : " + client);
                client.cache().close();
                Log.i("IOException", "清除緩存成功");
            } catch (IOException e) {
                Log.i("IOException", "清除緩存失敗");
            }
        } else {
            Log.i("client", "client.cache 為 null ");
        }
        final Call call = client.newCall(request);
        call.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        final Call call = client.newCall(request);
        call.cancel();
    }


    //thread 是背景執行
    // 新ui背景執行不能更新ui
    //    public void Runwork() {
//        try {
//            String jasonString = net(url);
//            JSONArray array = new JSONArray(jasonString);     // []
//            //    JSONObject object = new JSONObject(jasonString);  // {}
//
//            int arraylength = array.length();
//            Log.i("JSON:", "length : " + arraylength);
//            for (int i = 0; i < 2; i++) {
//                // ID = array.getJSONObject(i).getString("ID").toString();
//                parkname = array.getJSONObject(i).getString("NAME").toString();
//
//                final Map<String, Object> map = new HashMap<>();
//                map.put("parkname", parkname);
////                    Log.i("JSON:", "mewID : " + ID);
//                Log.i("JSON:", "parkname : " + parkname);
//                // 不能顯示小時費用的皆用詳細表示
////                    String aaa = String.valueOf(array.getJSONObject(i).getString("PAYEX").toString()).substring(0, 8).toString();
//                String bbb = "小型車計時每小時";
////                    Log.i("JSON:", "aaa : " + aaa);
////                    if (aaa.equalsIgnoreCase(bbb)) {
////                        hourexpense = String.valueOf(array.getJSONObject(i).getString("PAYEX").toString()).substring(8, 10).toString();
////                        Log.i("JSON:", "hourexpense124 : " + hourexpense);
////                        map.put("hourexpense", hourexpense);
////                    } else {
////                        map.put("hourexpense", "詳細");
////                        Log.i("JSON:", "hourexpense : " + hourexpense_twxtview);
////                    }
//
//                //   map.put("distanse", i);
//                //Log.i("JSON","MAP:" + map.get("parkname"));
//                list.add(map);
//
//                adapter = new SimpleAdapter(
//                        context,
//                        list,
//                        R.layout.row_json,
//                        new String[]{"parkname", "hourexpense", "distanse"},
//                        new int[]{R.id.parkname, R.id.hourexpense, R.id.distanse}
//                );
//
//                park_listview.setAdapter(adapter);
////                    park_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //長按
////                        @Override
////                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
////
////                           // park_scrollview.get(position).get("parkname");
////                          //  showparkdetail(view, position, context, ID);
////                            return false;
////                        }
////                    });
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    String net(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        Response response = client.newCall(request).onResponse();
//        return response.body().string();
//    }
}
