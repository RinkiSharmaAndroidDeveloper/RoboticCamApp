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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FragmentMaintanceConfigureMenu extends Fragment {

    public FragmentMaintanceConfigureMenu() {
        // Required empty public constructor
    }


    private Typeface Graphik_Regular,Graphik_Medium;
    Fragment fragmentSubShowCamera,fragmentSubDeleteUser,fragmentSubFragmentShowSettings;
    FragmentCallBack dataActivity;
    TextView tvCamera,tvManageUser,tvSettins,tvloginmessage,tvMaintainceconfig;
    LinearLayout llCamera,llManageUser,llSettins;;
    DBHelper objDB;
    DBHelper.User user;
    DBHelper.UserLoginHistory userLoginHistory;
    ArrayList<DBHelper.UserLoginHistory> arruserLoginHistory;

    private void startSubCameraCreateMenu() {
        getFragmentManager().beginTransaction().
                replace(R.id.camera_create_menu_frame, fragmentSubShowCamera).
                addToBackStack(null).
                commit();
    }


    private void resetBtnsColor (){
        llCamera.setBackgroundResource(R.drawable.admin_sub_menu_unpressed);
        llManageUser.setBackgroundResource(R.drawable.admin_sub_menu_unpressed);
        llSettins.setBackgroundResource(R.drawable.admin_sub_menu_unpressed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_maintance_config_menu, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();
        fragmentSubShowCamera = new SubFragmentShowCameraDataAndManage();
        fragmentSubDeleteUser = new SubFragmentDeleteUser();
        fragmentSubFragmentShowSettings =  new SubFragmentShowSettings();
        tvCamera = (TextView) v.findViewById(R.id.tv_managecamera);
        tvManageUser= (TextView) v.findViewById(R.id.tv_manage_user);
        tvSettins= (TextView) v.findViewById(R.id.tv_settins);
        tvloginmessage = (TextView) v.findViewById(R.id.login_message);
        tvMaintainceconfig=(TextView) v.findViewById(R.id.maintanance_conf_menu_new);
        tvManageUser.setTypeface(Graphik_Regular);
        tvSettins.setTypeface(Graphik_Regular);
        tvloginmessage.setTypeface(Graphik_Regular);
        tvMaintainceconfig.setTypeface(Graphik_Regular);

        llCamera = (LinearLayout)  v.findViewById(R.id.ll_managecamera);
        llManageUser = (LinearLayout)  v.findViewById(R.id.ll_manage_user);
        llSettins = (LinearLayout)  v.findViewById(R.id.ll_settins);
        tvCamera.setOnClickListener(myListener);
        tvManageUser.setOnClickListener(myListener);
        tvSettins.setOnClickListener(myListener);

        objDB = new DBHelper(getActivity().getApplicationContext());
        user = dataActivity.getCurrentUserData();
        String lastlogindatetime = dataActivity.getLastLoginOfuser(user.id);
        tvloginmessage.setText("Last login as("+user.name+"):"+lastlogindatetime );
        return v;
    }



    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            resetBtnsColor();

            switch (v.getId()){
                case (R.id.tv_managecamera) :
                        startSubCameraCreateMenu();
                        llCamera.setBackgroundResource(R.drawable.admin_sub_menu_pressed);
                    break;
                case (R.id.tv_manage_user) :
                    getFragmentManager().beginTransaction().
                            replace(R.id.camera_create_menu_frame, fragmentSubDeleteUser).
                            addToBackStack(null).
                            commit();
                    llManageUser.setBackgroundResource(R.drawable.admin_sub_menu_pressed);
                    break;
                case (R.id.tv_settins) :

                    getFragmentManager().beginTransaction().
                            replace(R.id.camera_create_menu_frame, fragmentSubFragmentShowSettings).
                            addToBackStack(null).
                            commit();
                    llSettins.setBackgroundResource(R.drawable.admin_sub_menu_pressed);
                    break;


            }
        }
    };


}
