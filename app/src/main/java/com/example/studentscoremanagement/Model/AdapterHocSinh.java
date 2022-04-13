package com.example.studentscoremanagement.Model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentscoremanagement.R;

import java.util.ArrayList;

public class AdapterHocSinh extends ArrayAdapter<HocSinh> {

     Context context;
      int layout;
     ArrayList<HocSinh> hocSinhList;

    public AdapterHocSinh(@NonNull Context context, int resource, @NonNull ArrayList<HocSinh> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.hocSinhList = objects;
    }


    @Override
    public int getCount() {
        return hocSinhList.size();
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(layout,null);

        TextView txtid=  convertView.findViewById(R.id.textViewMSHS);
        TextView txtHo=   convertView.findViewById(R.id.textViewHO);
        TextView txtTen= convertView.findViewById(R.id.textViewTEN);
        TextView txtPhai= convertView.findViewById(R.id.textViewPHAI);
        TextView txtNgaySinh= convertView.findViewById(R.id.textViewNS);

        HocSinh hocSinh = hocSinhList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,hocSinh.getMaHocSinh(),Toast.LENGTH_SHORT).show();

            }
        });
        Log.d("print",String.valueOf(getCount()));
        txtid.setText(hocSinh.getMaHocSinh());
        txtHo.setText(hocSinh.getHo());
        txtTen.setText(hocSinh.getTen());
        txtPhai.setText(hocSinh.getPhai());
        txtNgaySinh.setText(hocSinh.getNgaySinh());


        return convertView;
    }
}

