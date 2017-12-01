package com.emtcontrols.cam.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentAdminSuperUserScreen extends Fragment {
    private Typeface Graphik_Regular,Graphik_Bold_Italick,Graphik_Bold;
    Button BtnTouchToContinue;
    TextView logout,userlogin;
    DBHelper.User user;
    DBHelper.UserLoginHistory userLoginHistory;
    DBHelper objDB;
    private FragmentCallBack dataActivity;
    public FragmentAdminSuperUserScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_superuser_screen, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Bold_Italick = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-BoldItalic.otf");
        Graphik_Bold= Typeface.createFromAsset(getActivity().getAssets(),"fonts/graphik_bold.otf");
        dataActivity = (FragmentCallBack) getActivity();
        BtnTouchToContinue=(Button) v.findViewById(R.id.BtnTouchToContinue);
        userlogin=(TextView) v.findViewById(R.id.txtUserLogin);
        logout = (TextView) v.findViewById(R.id.btnlogout);
        BtnTouchToContinue.setTypeface(Graphik_Bold);
        logout.setTypeface(Graphik_Regular);
        userlogin.setTypeface(Graphik_Bold_Italick);
        BtnTouchToContinue.setOnClickListener(myListener);
        logout.setOnClickListener(myListener);
        user = dataActivity.getCurrentUserData();
        objDB = new DBHelper(getActivity().getApplicationContext());
        return v;
    }
    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()){
                case (R.id.BtnTouchToContinue) :
                    dataActivity.setAdminScreen();
                    break;
                case (R.id.btnlogout) :
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    String currentDateandTime = sdf.format(new Date());
                    objDB.updateUserLoginHistory(user.id,currentDateandTime);
                    dataActivity.startAutorizationDialog();
                    dataActivity.setBackgroundImage();
                    break;
            }
        }
    };



}
