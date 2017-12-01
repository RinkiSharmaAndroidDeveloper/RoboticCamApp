package com.emtcontrols.cam.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.emtcontrols.cam.R;


public class FragmentAdminFirstSetup extends Fragment {


    public FragmentAdminFirstSetup() {
        // Required empty public constructor
    }

    private FragmentCallBack dataActivity;
    Button btnFirstSetup;
    ListView lvTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_first_setup, container, false);

        dataActivity = (FragmentCallBack) getActivity();

        btnFirstSetup = (Button)v.findViewById(R.id.btn_first_setup);
        lvTable = (ListView)v.findViewById(R.id.lv_twble);

        lvTable.setVisibility(View.INVISIBLE);

        btnFirstSetup.setOnClickListener(myListener);

        return v;
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case (R.id.btn_first_setup) :
                    lvTable.setVisibility(View.VISIBLE); break;

            }
        }
    };

}
