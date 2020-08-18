package com.jasonoh.xmlpullparsermoviesex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    ArrayList<MemberMovies> memberMovies;
    LayoutInflater inflater;

    public MyAdapter( ArrayList<MemberMovies> memberMovies, LayoutInflater inflater ) {
        this.memberMovies = memberMovies;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return memberMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return memberMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) convertView = inflater.inflate( R.layout.listview_item, null );

        //리스트뷰 아이템에 있는 것들 참조
        TextView tv_rank = convertView.findViewById(R.id.tv_rank);
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_openDate = convertView.findViewById(R.id.tv_openDate);
        TextView tv_audiCnt = convertView.findViewById(R.id.tv_audiCnt);
        TextView tv_audiAcc = convertView.findViewById(R.id.tv_audiAcc);

        tv_rank.setText( memberMovies.get(position).rank );
        tv_name.setText( memberMovies.get(position).name );
        tv_openDate.setText( memberMovies.get(position).openDate );
        tv_audiCnt.setText( memberMovies.get(position).audiCnt );
        tv_audiAcc.setText( memberMovies.get(position).audiAcc );

        return convertView;
    }
}//MyAdapter class
