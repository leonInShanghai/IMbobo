package com.example.administrator.imbobo.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by Leon on 2018/11/4.
 * Functions: InviteActivity(邀请信息) listview 适配器
 */
public class InviteAdapter extends BaseAdapter {

    private Context mContext;

    public InviteAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
