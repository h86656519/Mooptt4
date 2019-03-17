package com.example.user.moo_ptt4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Forums extends AppCompatActivity {
    ListView listView;
    TextView title_view;
    SimpleAdapter adapter;
    List<Map<String, Object>> list = new ArrayList<>();
    final Intent intent = new Intent();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums);
        context = this;
        listView = (ListView) findViewById(R.id.forums_listView);
        title_view = (TextView) findViewById(R.id.title_view);
        //Log.i("java","Forums");
        Intent intent = this.getIntent();
        String forumsname = intent.getStringExtra("name");
        getSupportActionBar().setTitle(forumsname);

        int 看板數量 = getResources().getStringArray(R.array.看板).length;
        // Log.i("java","看板數量 : " + 看板數量);
        for (int i = 0; i < 看板數量; i++) {
            Map<String, Object> map = new HashMap<>();
//            Object s = getResources().getStringArray(R.array.看板)[i];
//            Log.i("forumsn","forumsn" + s);
            map.put("title", getResources().getStringArray(R.array.看板)[i]);
            map.put("subtitle", getResources().getStringArray(R.array.看板副標題)[i]);
            list.add(map);

            adapter = new SimpleAdapter(
                    context,
                    list,
                    R.layout.row,
                    new String[]{"title", "subtitle"},
                    new int[]{R.id.title_view, R.id.subtitle_view}
            );
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int postion, long id) {
                    final Intent intent = new Intent();
                    switch (postion) {

                        case 0:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://tw.yahoo.com"));
                            startActivity(intent);
                            break;
                        case 1:
                            intent.setClass(context, Park_json.class);
                          //  Log.i("java", "postion" + postion);
                            startActivity(intent);
                            break;
                        case 2:

                            break;

                    }
//                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    PopupMenu popup = new PopupMenu(context, view);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                //暫時不作menu功能

                            }
                            return false;
                        }
                    });
                    return false;
                }
            });
        }

    }


}

