package com.example.shubu.peb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by dell on 7/9/2017.
 */

public class AddUserAdapter extends ArrayAdapter {
    public AddUserAdapter(Context context, int resource) {
        super(context, resource);
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
        UserAdapter.userHolder userHolder;
        row=convertView;
        if (row == null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.activity_register,parent,false);
            userHolder=new UserAdapter.userHolder();
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
