package com.example.studentscoremanagement.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentscoremanagement.ChooseClassActivity;
import com.example.studentscoremanagement.DBHelper;
import com.example.studentscoremanagement.DSSV;
import com.example.studentscoremanagement.LoginActivity;
import com.example.studentscoremanagement.Model.Lop;
import com.example.studentscoremanagement.Model.TaiKhoan;
import com.example.studentscoremanagement.R;
import com.example.studentscoremanagement.UserInforActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> dataString = new ArrayList<>();

    Button btnConfirm;
    ImageButton ibtInfor;
    Spinner spnClass;
    DBHelper database;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String ID_CLASS="ID_CLASS";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addControl(view);
        setEvent();

        return view;
    }
    private void setEvent() {
        Initialize();

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, dataString);
        spnClass.setAdapter(adapter);

        HomeFragment homeFragment=this;
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(getActivity(), "Lớp: "+ spnClass.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                DSSVFragment dssvFragment=new DSSVFragment();
                Bundle args = new Bundle();
                args.putString(ID_CLASS,spnClass.getSelectedItem().toString());
                dssvFragment.setArguments(args);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, dssvFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    private void Initialize() {
        database=new DBHelper(getContext());
        ArrayList<String> classes = Lop.getAllClassId(database);
        for (int i = 0; i < classes.size(); i++) {
            dataString.add(classes.get(i));
        }
    }

    private void addControl(View view) {
        btnConfirm = view.findViewById(R.id.btnConfirm);
        spnClass = view.findViewById(R.id.spnClass);
    }
}