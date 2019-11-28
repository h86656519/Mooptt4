package com.example.user.moo_ptt4;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import user.Moo_ptt.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyflavorFragment extends Fragment {
    List<Map<String, Object>> list = new ArrayList<>();
    SimpleAdapter adapter;
    ListView listView;
    FloatingActionButton fab;
    Firebase myFirebaseRef;
    BottomSheetBehavior behavior;
    View MyflavorFragmentview;
    public MyflavorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MyflavorFragmentview = inflater.inflate(R.layout.fragment_myflavor, container, false);
        View bottomSheet = MyflavorFragmentview.findViewById(R.id.bottom_sheet);
        Log.i("suvini","bottomSheet1 : " + bottomSheet);

        listView = (ListView) MyflavorFragmentview.findViewById(R.id.myflavor_listView);
        TextView title_view = (TextView) MyflavorFragmentview.findViewById(R.id.title_view);

        //MenuItem menuItem = (MenuItem) view.findViewById(R.id.addmyflavor);
        Firebase.setAndroidContext(getContext());
        myFirebaseRef = new Firebase("https://mooptt-63043.firebaseio.com/");
        myFirebaseRef.child("forums").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (int i = 1; i <= dataSnapshot.getChildrenCount(); i++) {

                    String addforum = dataSnapshot.child(String.valueOf(i)).child("AddForum").getValue().toString();
                    final String forumname = dataSnapshot.child(String.valueOf(i)).child("ForumName").getValue().toString();
                    String subtitle = dataSnapshot.child(String.valueOf(i)).child("SubTitle").getValue().toString();

                    boolean boolean2 = Boolean.parseBoolean(addforum);
                    //  Log.i("java", "boolean3 : " + i + "" + boolean2);
                    if (boolean2) {
                        //Log.i("java", "為true的欄位 : " + forumname);
                        final Map<String, Object> map = new HashMap<>();
                        map.put("title", forumname);
                        map.put("subtitle", subtitle);
                        map.put("index", i);

                        list.add(map);
                        //  Log.i("java","map" +    map.get("index"));
                        //資料來源是類似表格有列與欄的時候，可使用Map集合儲存列，再使用List將每一列收集後，使用SimpleAdapter。
                        adapter = new SimpleAdapter(
                                getContext(),
                                list,
                                R.layout.row,
                                new String[]{"title", "subtitle"},
                                new int[]{R.id.title_view, R.id.subtitle_view}
                        );

                        // String test = list.get(3).toString();
                        listView.setAdapter(adapter);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //長按
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                // showMenu(view);
                                //取得各個item 名稱
                                final String name = list.get(position).get("title").toString();
                                String index = list.get(position).get("index").toString();
                                showPopup(view, position, getContext(), name, index);
                                //registerForContextMenu(view); 因為不是Activity
                                //  adapter.notifyDataSetChanged();

                                return false;
                            }

                        });
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                               Log.i("java","parent : " + parent);
//                                Log.i("java","view.getId : " + view.getId());
//                                Log.i("java","postion : " + position);
//                                Log.i("java","listgetpostion : " + list.get(position).get("title"));
                                // 變換看板標題
                                String forumsname = list.get(position).get("title").toString();
                                Intent intent = new Intent();  // 連到板內
                                intent.putExtra("name", forumsname);
                                intent.setClass(getContext(), Forums.class);
                                getContext().startActivity(intent);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        fab = (FloatingActionButton) MyflavorFragmentview.findViewById(R.id.buttonsheet3fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bottomsheetclick(view);
                switch (view.getId()) {
                    case R.id.buttonsheet3fab:
                        Log.i("suvini", "view2 : " + view);
                        // View BottomSheetview = view2.findViewById(R.id.bottom_sheet3);
                        // BottomSheetBehavior behavior = BottomSheetBehavior.from(BottomSheetview);
                        // Log.i("suvini","BottomSheetview" + BottomSheetview);
                        View bottomSheet = MyflavorFragmentview.findViewById(R.id.bottom_sheet);
                       // Log.i("suvini","bottomSheet2 : " + bottomSheet);
                        behavior = BottomSheetBehavior.from(bottomSheet);
//                        Log.i("suvini","behavior : " + behavior);
//                behavior.setHideable(false);
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                        break;
                }
            }
        });

        return MyflavorFragmentview;
    }

    public void showPopup(View v, final int position, Context context, final String name, final String index) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.deletemyflavor:
                        // create();
                        //  Log.i("java", "name " + name);
                        //  Log.i("java", "index " + index);
                        //  Firebase myFirebaseRef = new Firebase("https://mooptt-63043.firebaseio.com/");
                        myFirebaseRef.child("forums").child(index).child("AddForum").setValue("false");
                        //移除我的最愛
                        list.get(position).clear();
                        // list.clear();
                        return false;
                    default:
                        return false;
                }
            }
        });
        adapter.notifyDataSetChanged();
        popup.inflate(R.menu.menu_deletemyflavor);
        popup.show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        myFirebaseRef.onDisconnect();
    }
}
