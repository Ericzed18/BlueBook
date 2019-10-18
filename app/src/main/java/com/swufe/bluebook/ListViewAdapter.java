package com.swufe.bluebook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.swufe.bluebook.Backstage.SystemTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    private List<SystemTask> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private static String TAG = "ListViewAdapter";


    public ListViewAdapter(Context context, List<SystemTask> data){
        this.context = context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    public final class Zujian{
        public ImageView imageView;
        public TextView tv1;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if(convertView==null){
            zujian = new Zujian();
            convertView=layoutInflater.inflate(R.layout.listview_item,null);
            zujian.imageView=convertView.findViewById(R.id.image);
            zujian.tv1=convertView.findViewById(R.id.abstract1);
            convertView.setTag(zujian);
        }else {
            zujian=(Zujian)convertView.getTag();
        }
        Picasso.with(context).load(data.get(position).getCoverUrl()).into((zujian).imageView);
        zujian.tv1.setText(data.get(position).getTitle());
        Log.i(TAG, "getView: "+data.get(position).getTitle());

        return convertView;
    }
}
