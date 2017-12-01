package com.emtcontrols.cam.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FragmentUserLogout extends Fragment {
    private Typeface Graphik_Regular,Graphik_Bold;
    private FragmentCallBack dataActivity;

    TextView tvUserName, tvLoginTime, tvLoginDate, tvLastLogin,userlogoutheader;
    Button btnComLogOut;
    DBHelper objDB;
    DBHelper.User user;
    DBHelper.UserLoginHistory userLoginHistory;

    ArrayList<DBHelper.UserLoginHistory> arruserLoginHistory;

    public FragmentUserLogout() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_logout, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Bold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/graphik_bold.otf");
        dataActivity = (FragmentCallBack) getActivity();

        btnComLogOut = (Button) v.findViewById(R.id.logout_common);
        tvUserName = (TextView) v.findViewById(R.id.user_name_tv);
        tvLoginTime = (TextView) v.findViewById(R.id.login_time);
        tvLoginDate = (TextView) v.findViewById(R.id.login_date);
        userlogoutheader= (TextView) v.findViewById(R.id.userlogoutheader);
        tvLastLogin = (TextView) v.findViewById(R.id.last_login);
        btnComLogOut.setTypeface(Graphik_Bold);
        tvUserName.setTypeface(Graphik_Regular);
        tvLoginTime.setTypeface(Graphik_Regular);
        tvLoginDate.setTypeface(Graphik_Regular);
        tvLastLogin.setTypeface(Graphik_Regular);
        userlogoutheader.setTypeface(Graphik_Regular);
        objDB = new DBHelper(getActivity().getApplicationContext());
        user = dataActivity.getCurrentUserData();

        btnComLogOut.setOnClickListener(myListener);

        tvUserName.setText(tvUserName.getText()+user.name);
        userLoginHistory = objDB.getUserloginhistory(user.id);

        arruserLoginHistory = objDB.getUserloginhistories(user.id, 2);

        if (arruserLoginHistory.size() == 2) {
            String ss= getdiferenceInHours(arruserLoginHistory.get(0).logintgime,arruserLoginHistory.get(1).logintgime);
            tvLastLogin.setText(tvLastLogin.getText()+ss);


        }

        tvLoginDate.setText(tvLoginDate.getText()+ getDate(userLoginHistory.logintgime));
        tvLoginTime.setText(tvLoginTime.getText()+getTime(userLoginHistory.logintgime));


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

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            String currentDateandTime = sdf.format(new Date());
            objDB.updateUserLoginHistory(user.id,currentDateandTime);
            dataActivity.startAutorizationDialog();
            dataActivity.setBackgroundImage();
        }
    };

}
