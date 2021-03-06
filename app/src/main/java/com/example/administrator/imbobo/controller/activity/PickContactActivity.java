package com.example.administrator.imbobo.controller.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.example.administrator.imbobo.controller.adapter.PickContactAdapter;

import com.example.administrator.imbobo.R;
import com.example.administrator.imbobo.model.Model;
import com.example.administrator.imbobo.model.bean.PickContactInfo;
import com.example.administrator.imbobo.model.bean.UserInfo;
import com.example.administrator.imbobo.utils.Constant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leon on 2018/11/17
 * Functions: （创建群组时）选择人页面
 */
public class PickContactActivity extends Activity {

    private TextView tv_pick_save;
    private ListView lv_pick;
    private List<PickContactInfo> mPicks;
    private PickContactAdapter pickContactAdapter;

    //群中已经存在的群成员
    private List<String> mExistMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);

        //获取传递过来的数据
        getData();

        initView();

        initData();

        initListener();
    }

    private void getData(){
        String groupId = getIntent().getStringExtra(Constant.GROUP_ID);

        if (groupId != null){
            EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
            //获取群中已经存在的所有群成员
            mExistMembers = group.getMembers();
        }

        //为了避免空指针异常mExistMembers == null 给mExistMembers一个空的集合
        if (mExistMembers == null){
            mExistMembers = new ArrayList<>();
        }
    }

    private void initListener(){

        //list view 条目的点击事件的监听
        lv_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CheckBox 选中状态的切换
                CheckBox cb_pick = view.findViewById(R.id.cb_pick);
                cb_pick.setChecked(!cb_pick.isChecked());

                //修改数据
                PickContactInfo pickContactInfo = mPicks.get(position);
                pickContactInfo.setChecked(cb_pick.isChecked());

                //刷新页面
                pickContactAdapter.notifyDataSetChanged();
            }
        });

        //右上角保存按钮的点击事件
        tv_pick_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取到已经选择的联系人
                List<String> names = pickContactAdapter.getPickContacts();

                //给启动页面返回数据
                Intent intent = new Intent();
                intent.putExtra("members",names.toArray(new String[0]));

                //设置结果码因为要返回参数
                setResult(RESULT_OK,intent);

                //结束当前页面
                finish();
            }
        });
    }

    private void initData(){

        //从本地数据库中获取所有的联系人信息
        List<UserInfo> contacts = Model.getInstance().getDbManager().getContactTableDao().getCountacts();

        mPicks = new ArrayList<>();

        if (contacts != null && contacts.size() >= 0){
            //集合 转换
            for (UserInfo contact : contacts){
                PickContactInfo pickContactInfo = new PickContactInfo(contact,false);
                mPicks.add(pickContactInfo);
            }

            //初始化list view 创建适配器 放进了if语句里避免空指针异常
            //pickContactAdapter = new PickContactAdapter(this,mPicks);
            //lv_pick.setAdapter(pickContactAdapter);
        }

        //初始化list view 创建适配器 放进了if语句里避免空指针异常
        pickContactAdapter = new PickContactAdapter(this,mPicks,mExistMembers);
        lv_pick.setAdapter(pickContactAdapter);
    }

    private void initView(){
        tv_pick_save = (TextView)findViewById(R.id.tv_pick_save);
        lv_pick = (ListView)findViewById(R.id.lv_pick);
    }
}
