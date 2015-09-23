package com.stephen.thenext.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stephen.thenext.R;
import com.stephen.thenext.fragment.ListFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Stephen on 2015/9/21.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> playLists;

    public MyAdapter(Context mContext, List<String> playLists) {
        this.mContext = mContext;
        this.playLists = playLists;
    }

    @Override
    public int getCount() {
        return playLists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
            convertView.setTag(holder);
            holder.textView = (TextView) convertView.findViewById(R.id.name_tv);
            holder.imageView = (ImageView) convertView.findViewById(R.id.checked_iv);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(playLists.get(position));
        if (ListFragment.beanLists.get(position).isSelected()) {
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
