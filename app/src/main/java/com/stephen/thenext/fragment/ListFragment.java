package com.stephen.thenext.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stephen.thenext.R;
import com.stephen.thenext.adpater.MyAdapter;
import com.stephen.thenext.polly.Bean;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Stephen on 2015-08-22.
 */
public class ListFragment extends Fragment {

    private static String TAG = "Stephen";
    private ListView listView;
    public List<String> playlists;
    public static int currentPos = 0;
    public static final String CURRENTPOS = "CURRENTPOS";
    public static List<Bean> beanLists;

    //same
    public static int[] res = new int[]{R.raw.haojiubujian, R.raw.taixiangai, R.raw.youqingsuiyue};
    private int[] names = new int[]{R.string.haojiubujian, R.string.taixiangai, R.string.youqingsuiyue};

    private static MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.playlist_layout, container, false);

        playlists = new ArrayList<>();
        beanLists = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            playlists.add(getString(names[i]));
            Bean bean = new Bean();
            bean.setBeanId(i);
            bean.setName(playlists.get(i));
            if (currentPos == i) {
                bean.setIsSelected(true);
            } else {
                bean.setIsSelected(false);
            }
            beanLists.add(bean);
        }

        listView = (ListView) view.findViewById(R.id.list);
        listView.setDividerHeight(5);
        myAdapter = new MyAdapter(getActivity(), playlists);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == currentPos) {
                    return;
                }
                refreshCheckedItem(position);
                EventBus.getDefault().post(new Bean(0, true, null));
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!this.isHidden()) {
            startAnimations();
        }
    }

    private void startAnimations() {
        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(), R.anim.translate_in));
        listView.setLayoutAnimation(lac);
    }

    public void refreshCheckedItem(int checked) {
        if (checked > res.length) {
            checked = 0;
        } else if (checked < 0) {
            checked = res.length - 1;
        }
//        Log.d(TAG, "refreshCheckedItem----------" + checked);
        beanLists.get(checked).setIsSelected(true);
        beanLists.get(currentPos).setIsSelected(false);
        currentPos = checked;
        myAdapter.notifyDataSetChanged();
//        Log.d(TAG, "refreshCheckedItem----------" + currentPos);
    }
}
