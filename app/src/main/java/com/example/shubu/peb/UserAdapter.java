package com.example.shubu.peb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 7/5/2017.
 */

public class UserAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public UserAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(User object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        userHolder userHolder;
        row=convertView;
        if (row == null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.all_users_layout,parent,false);
            userHolder=new userHolder();
            userHolder.name=(TextView) row.findViewById(R.id.name);
            row.setTag(userHolder);
        }
        else {
            userHolder=(UserAdapter.userHolder)row.getTag();
        }
        User user=(User) this.getItem(position);
        userHolder.name.setText(user.name);
        return row;
    }
    static class userHolder{
        TextView name;
    }
}
