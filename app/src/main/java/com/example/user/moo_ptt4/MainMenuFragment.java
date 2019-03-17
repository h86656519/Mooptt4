package com.example.user.moo_ptt4;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment {
    String returnthemeName;
    GridView gridView;

    private int[] image = {R.drawable.mainmenu_grid_item_1, R.drawable.mainmenu_grid_item_2, R.drawable.mainmenu_grid_item_3, R.drawable.mainmenu_grid_item_4, R.drawable.mainmenu_grid_item_5, R.drawable.mainmenu_grid_item_6, R.drawable.mainmenu_grid_item_7,
            R.drawable.mainmenu_grid_item_10, R.drawable.mainmenu_grid_item_11, R.drawable.mainmenu_grid_item_12, R.drawable.mainmenu_grid_item_13
    };
    private int[] imgText = {
          //  R.string.klass
    };
    //"熱門文章", "個人信箱", "個人訊息", "聊天室", "關於我", "敲敲看", "關於Moo_ptt", "登出", "最近預覽", "設定"
    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainmenu, container, false);

        // new SettingTheme().SettingTheme(context);
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
//            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                items,
                R.layout.grid_item,
                new String[]{"image", "text"},
                new int[]{R.id.gridview_image, R.id.text});

        gridView = (GridView) view.findViewById(R.id.mainmenu_gridview);
        gridView.setNumColumns(3);
        gridView.setHorizontalSpacing(15);
//        gridView.setVerticalSpacing(5);

//        returnthemeName = new SettingTheme().themeName(getContext());
//        if (themeName.equals("黑色")) {
//            gridView.setBackgroundColor(getResources().getColor(R.color.black));
//        } else if (themeName.equals("白色")) {
//            gridView.setBackgroundColor(getResources().getColor(R.color.white));
//        }

        //  gridView.setColumnWidth(1);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(context, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show();
//                Log.i("json", " view.getId : " + view.getId());
//                position 從0開始算
//                Log.i("json", "position : " + position);
                switch (position) {
                    case 9:
//                        Intent intent = new Intent();
//                        intent.setClass(getContext(), PreferenceActivityWithPreferenceFragment.class);
//                        getContext().startActivity(intent);
                        getContext().startActivity(new Intent(getContext(), PreferenceActivityWithPreferenceFragment.class));
                        break;
                    case 10:
                        getContext().startActivity(new Intent(getContext(), SettingsActivity.class));
                        break;
                }
            }
        });

        return view;
    }

}
