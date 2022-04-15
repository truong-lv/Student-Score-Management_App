package com.example.studentscoremanagement.fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentscoremanagement.Adapter.AdapterHocSinh;
import com.example.studentscoremanagement.DBHelper;
import com.example.studentscoremanagement.Model.HocSinh;
import com.example.studentscoremanagement.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DSSVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DSSVFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DBHelper database;

    Button btnThem,buttonBC;
    ListView lvHocSinh;
    ArrayList<HocSinh> arrayHocSinh;
    AdapterHocSinh adapter;
    TextView textGV;

    String idClass;

    public static final String CLASS_ID="CLASS_ID";
    public static final String TEACHER_NAME="TEACHER_NAME";

    public DSSVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_dssv.
     */
    // TODO: Rename and change types and number of parameters
    public static DSSVFragment newInstance(String param1, String param2) {
        DSSVFragment fragment = new DSSVFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_dssv, container, false);
        getClassId();
        //lvHocSinh.setAdapter(adapter);
        setControl(view);
        setEvent();
        GetDataHocSinh();
        return view;
    }
    private void getClassId() {
        idClass= getArguments().getString(HomeFragment.ID_CLASS);
    }

    private void setControl(View view) {
        database=new DBHelper(getContext());
        lvHocSinh = view.findViewById(R.id.listViewMSHS);
        btnThem=view.findViewById(R.id.buttonThem);
        buttonBC=view.findViewById(R.id.buttonBC);
        arrayHocSinh = new ArrayList<>();
        textGV=view.findViewById(R.id.textGV);
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSinhVien();
            }
        });
        buttonBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(DSSV.this, ReportActivity.class);
//                intent.putExtra(CLASS_ID,idClass);
//                intent.putExtra(TEACHER_NAME,textGV.getText());
//                startActivity(intent);
            }
        });
    }

    // để data ra mh
    private void GetDataHocSinh(){
        Cursor dataHS = database.GetData("SELECT * FROM "+DBHelper.TB_HOCSINH+" WHERE "+DBHelper.COL_HOCSINH_MALOP+"='"+idClass+"'");//nghe ko bn ko nghe==>ông out meet r
//ng
        dataHS.moveToFirst();
        do
        {
            String id = dataHS.getString(0);
            //Toast.makeText(this, id , Toast.LENGTH_SHORT).show();
            String ho = dataHS.getString(1);
            //Toast.makeText(this, ho , Toast.LENGTH_SHORT).show();
            String ten = dataHS.getString(2);
            //Toast.makeText(this, ten , Toast.LENGTH_SHORT).show();
            String phai = dataHS.getString(3);
            //Toast.makeText(this, phai , Toast.LENGTH_SHORT).show();
            String ngaySinh = dataHS.getString(4);
            //Toast.makeText(this, ngaySinh , Toast.LENGTH_SHORT).show();
            arrayHocSinh.add(new HocSinh(id, ho, ten, phai, ngaySinh));
        }while (dataHS.moveToNext());
        //Toast.makeText(this, String.valueOf(arrayHocSinh.get(0).getTen()) , Toast.LENGTH_SHORT).show();
        //adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Context: "+getActivity(), Toast.LENGTH_SHORT).show();
        Log.d("print activity", getActivity().toString());
        Log.d("print context", getContext().toString());
        adapter = new AdapterHocSinh(getContext(), R.layout.activity_dssv_ds, arrayHocSinh, getActivity());
        lvHocSinh.setAdapter(adapter);


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.buttonThem){
            ThemSinhVien();
        }
        return super.onOptionsItemSelected(item);
    }


    private  void ThemSinhVien(){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.themhocsinh);

        EditText editMHS = (EditText) dialog.findViewById(R.id.editTextNhapMHS);
        EditText editHo = (EditText) dialog.findViewById(R.id.editTextNhapHo);
        EditText editTen = (EditText) dialog.findViewById(R.id.editTextNhapTen);
        EditText editPhai = (EditText) dialog.findViewById(R.id.editTextNhapPhai);
        EditText editNSinh = (EditText) dialog.findViewById(R.id.editTextNhapNgaySinh);
        Button btnHuy = (Button) dialog.findViewById(R.id.buttonHUY);
        Button btnThem = (Button) dialog.findViewById(R.id.buttonLUU);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mhs = editMHS.getText().toString();
                String ho = editHo.getText().toString();
                String ten = editTen.getText().toString();
                String phai = editPhai.getText().toString();
                String ngaysinh= editNSinh.getText().toString();
                if(mhs.equals("") || ho.equals("") || ten.equals("") || phai.equals("") ||ngaysinh.equals("")){
                    Toast.makeText(getContext(), "Vui lòng nhập không để trống!", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO "+DBHelper.TB_HOCSINH+" VALUES ('"+mhs +"', '"+ho+"', '"+ten+"', '"+phai+"', '"+ngaysinh+"')");
                    Toast.makeText(getContext(), "Đã Thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataHocSinh();
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}