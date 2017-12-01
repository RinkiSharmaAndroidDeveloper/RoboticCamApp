package com.emtcontrols.cam.fragments;


import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class FragmentAdminLogout extends Fragment {

    public FragmentAdminLogout() {
        // Required empty public constructor
    }

    private FragmentCallBack dataActivity;
    private Typeface Graphik_Regular,Graphik_Medium;
    Button  btnLogout;
    ImageView cancel_logout;
    TextView btnLogs,tvUserLogs,tvadminInfo,tvAdminLastLogin,tvLastLogin;
    LinearLayout llLogs,visibleLayout;
    TextView tvadminloginname,tvadminlastlogin,tvadminlogintime;
    DBHelper.User user;
    DBHelper.UserLoginHistory userLoginHistory;
    DBHelper objDB;
    ArrayList<DBHelper.UserLoginHistory> arruserLoginHistory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_logout, container, false);

        dataActivity = (FragmentCallBack) getActivity();

        btnLogout = (Button)v.findViewById(R.id.logout);
        btnLogs = (TextView)v.findViewById(R.id.logs);
        tvUserLogs = (TextView)v.findViewById(R.id.user_logs);
        tvadminInfo = (TextView)v.findViewById(R.id.tvadminloginname);
        tvAdminLastLogin= (TextView)v.findViewById(R.id.tvadminlastlogin);
        tvLastLogin= (TextView)v.findViewById(R.id.tvadminlogintime);

        cancel_logout = (ImageView) v.findViewById(R.id.cancel_log);
        visibleLayout = (LinearLayout) v.findViewById(R.id.invisible_layout);
        tvadminloginname = (TextView) v.findViewById(R.id.tvadminloginname);
        tvadminlastlogin = (TextView) v.findViewById(R.id.tvadminlastlogin);
        tvadminlogintime = (TextView) v.findViewById(R.id.tvadminlogintime);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-LightItalic.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        tvUserLogs.setTypeface(Graphik_Regular);
        tvadminInfo .setTypeface(Graphik_Regular);
        tvAdminLastLogin.setTypeface(Graphik_Regular);
        tvLastLogin.setTypeface(Graphik_Regular);
        user = dataActivity.getCurrentUserData();

        objDB = new DBHelper(getActivity().getApplicationContext());

       tvadminloginname.setText(tvadminloginname.getText()+user.name);

         userLoginHistory = objDB.getUserloginhistory(user.id);

        arruserLoginHistory = objDB.getUserloginhistories(user.id, 2);
        tvadminlogintime.setText(tvadminlogintime.getText()+getTime(userLoginHistory.logintgime));

        if (arruserLoginHistory.size() == 2) {
            String ss= getdiferenceInHours(arruserLoginHistory.get(0).logintgime,arruserLoginHistory.get(1).logintgime);

           String lastlogindate= getDate(arruserLoginHistory.get(0).logintgime);

            tvadminlastlogin.setText(tvadminlastlogin.getText()+ss +" " + lastlogindate);


        }




        //llLogs = (LinearLayout)v.findViewById(R.id.ll_admin_logs);

       // llLogs.setVisibility(View.INVISIBLE);

        btnLogout.setOnClickListener(myListener);
        btnLogs.setOnClickListener(myListener);
        cancel_logout.setOnClickListener(myListener);
       // btnLogs.setOnClickListener(toggleListener);

        return v;
    }

    private String getdiferenceInHours(String date1, String date2) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            Date Date1 = format.parse(date2);
            Date Date2 = format.parse(date1);
            long restDatesinMillis = Date2.getTime()-Date1.getTime();
            return String.format("%d h, %d min",
                    (TimeUnit.MILLISECONDS.toSeconds(restDatesinMillis)/60)/60,
                    TimeUnit.MILLISECONDS.toSeconds(restDatesinMillis) / 60 );

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }



    private String getDate(String currentDateandTime)  {

        String retdate = "";
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(currentDateandTime);
            retdate= new SimpleDateFormat("dd-MMM-yyyy").format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return retdate;

    }

    private String getTime(String currentDateandTime)  {

        String retTime = "";
        SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(currentDateandTime);
            retTime= new SimpleDateFormat("HH:mm:ss").format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return retTime;

    }
    View.OnClickListener toggleListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            /*if (btnLogs.isChecked()) {
                llLogs.setVisibility(View.VISIBLE);
                btnLogs.setBackgroundResource(R.drawable.button_menu_pressed);
            } else {
                llLogs.setVisibility(View.INVISIBLE);
                btnLogs.setBackgroundResource(R.drawable.button_menu_unpressed);
            }*/
        }
    };

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case (R.id.logout) :
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    String currentDateandTime = sdf.format(new Date());
                    objDB.updateUserLoginHistory(user.id,currentDateandTime);
                    dataActivity.startAutorizationDialog();
                    dataActivity.setBackgroundImage();
                    break;
                case(R.id.logs) :
                        visibleLayout.setVisibility(View.VISIBLE);
                    break;
                case(R.id.cancel_log) :
                    visibleLayout.setVisibility(View.INVISIBLE);
                    break;

            }
        }
    };

}
