package com.example.androidcarmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidcarmanager.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Custom_Adapter extends BaseAdapter {
    List<Model_Adapter> list;
    ArrayList<Model_Adapter> arrayList;
    Context context ;

    public Custom_Adapter(Context context, List<Model_Adapter> list ) {
        this.context = context;
        this.list = list;
        this.arrayList = arrayList;
        this.arrayList = new ArrayList<Model_Adapter>();
        this.arrayList.addAll(list);
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class MyHolder {
        TextView titleTv, dateTv;
        MyHolder(View v) {
            titleTv = (TextView) v.findViewById(R.id.tvlisttitle);
            dateTv = (TextView) v.findViewById(R.id.tvlistdate);
        }
    }

    @Override
    public View getView(final int position, View converView, ViewGroup parent){
        View row = converView;
        MyHolder holder=null;
        if(row==null){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.listview,null);
            holder = new MyHolder(row);
            row.setTag(holder);
        }else{
            holder = (MyHolder) row.getTag();
        }
        holder.titleTv.setText(list.get(position).getTitle());
        Calendar c=Calendar.getInstance();
        c.setTimeInMillis(arrayList.get(position).getDate());
        c.add(Calendar.MONTH,1);
        holder.dateTv.setText(c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));
        return row;
    }
    //filter
    public void dateTitleFilter(Long startDate, Long endDate, String query){
        query = query.toLowerCase(Locale.getDefault());

        list.clear();
        if (startDate == null && endDate == null && query.length()==0){
            list.addAll(arrayList);
        }
        else {
            for (Model_Adapter model : arrayList){
                if ((model.getTitle().toLowerCase(Locale.getDefault()).contains(query))
                        && ( model.getDate() >= startDate)
                        && ( model.getDate() <= endDate))
                {
                    list.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
