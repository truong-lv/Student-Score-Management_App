package com.example.studentscoremanagement.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentscoremanagement.R;

import java.util.ArrayList;

public class AdapterHocSinh extends BaseAdapter {
    Context context;
    ArrayList<HocSinh>list;

    public AdapterHocSinh(Context context, ArrayList<HocSinh> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_dssv, null);
        TextView mshs = (TextView) row.findViewById(R.id.textViewMSHS);
        TextView ho = (TextView) row.findViewById(R.id.textViewHO);
        TextView ten = (TextView) row.findViewById(R.id.textViewTEN);
        TextView phai = (TextView) row.findViewById(R.id.textViewPHAI);
        TextView ngaysinh = (TextView) row.findViewById(R.id.textViewNS);
        Button them = (Button) row.findViewById(R.id.buttonThem);
        Button xoa = (Button) row.findViewById(R.id.buttonXoa);
        Button sua = (Button) row.findViewById(R.id.buttonSua);

        HocSinh hocSinh = list.get(i);

       mshs.setText(hocSinh.getMaHocSinh() + "");
       ho.setText(hocSinh.getHo());
       ten.setText(hocSinh.getTen());
       phai.setText(hocSinh.getPhai());
       ngaysinh.setText(hocSinh.getNgaySinh());

        return row;
    }
}
