package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by i3 on 06 22 2016.
 */
public class SubFragmentBackgroundColor extends Fragment {
    private Spinner Backgroundcolor;
    private Typeface Graphik_Regular,Graphik_Medium;
    private TextView tv_Background;

    private FragmentCallBack dataActivity;
    private DBHelper.User user;
    private DBHelper.UserSetting userSetting;
    DBHelper objDB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_change_background_color, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        Backgroundcolor = (Spinner) v.findViewById(R.id.spColor);
        tv_Background= (TextView) v.findViewById(R.id.tv_Background);
        tv_Background.setTypeface(Graphik_Regular);
        dataActivity = (FragmentCallBack) getActivity();
        user = dataActivity.getCurrentUserData();
        objDB = new DBHelper(getActivity().getApplicationContext());
        userSetting = objDB.getUserSettingByUserId(user.id);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.colors_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // Apply the adapter to the spinner
        Backgroundcolor.setAdapter(adapter);
        if (!userSetting.backgroundcolor.isEmpty() && !userSetting.backgroundcolor.equals(null)) {
            int spinnerPosition = adapter.getPosition(userSetting.backgroundcolor);
            Backgroundcolor.setSelection(spinnerPosition);
        }
        Backgroundcolor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos,
                                       long id) {

                String backgroundcolor = Backgroundcolor.getSelectedItem().toString();
                objDB = new DBHelper(getActivity().getApplicationContext());
                if(backgroundcolor.equals("NoColor"))
                    objDB.updateUserSettingByUserId(user.id,"backgroundcolor","");
                else
                    objDB.updateUserSettingByUserId(user.id,"backgroundcolor",backgroundcolor);
                //  objDB.se(user.id,vNewPassword );
               // showSucessmessage.setVisibility(v.getVisibility());
                if(backgroundcolor.equals("NoColor"))
                    dataActivity.setBackgroundImage();
                else
                    dataActivity.setBackgroundColor(backgroundcolor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        return v;
    }












}

