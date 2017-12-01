package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FragmentAutorization extends Fragment {

    private FragmentCallBack dataActivity;
    private Fragment fragmentAutorization,fragmentUserBar,fragmentUserControlMenu;
    private EditText inLogin, inPassword;
    private TextView errormessage ,txtUserLogin,Forgot_password;
    private Button btnLogIn;
    private TextView btnExit;
    DBHelper objDB;
    DBHelper.User user;
    private CheckBox cbRememberMe;
    DBHelper.UserSetting userSetting;
    public JSONObject jsonobject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataActivity = (FragmentCallBack) getActivity();

        View view = inflater.inflate(R.layout.fragment_autorization, container, false);

        inLogin = (EditText) view.findViewById(R.id.InLogin);
        inPassword = (EditText) view.findViewById(R.id.InPassword);
        cbRememberMe = (CheckBox) view.findViewById(R.id.cbremember) ;
        txtUserLogin = (TextView)  view.findViewById(R.id.txtUserLogin) ;
        Forgot_password = (TextView)  view.findViewById(R.id.Forgot_password) ;

        String namePassword = dataActivity.getRememberme();
        try {
            if(namePassword!=null && !namePassword.isEmpty()) {
                jsonobject = new JSONObject(namePassword);
                inLogin.setText(jsonobject.get("username").toString());
                inPassword.setText(jsonobject.get("password").toString());
                cbRememberMe.setChecked(true);
            }
            else
            {
                inLogin.setText("");
                inPassword.setText("");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnLogIn = (Button) view.findViewById(R.id.BtnLogIn);
        btnExit = (TextView) view.findViewById(R.id.BtnExit);
        errormessage =  (TextView) view.findViewById(R.id.usernameerror);
        Typeface Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Typeface Graphik_Medium= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        Typeface Graphik_Bold=Typeface.createFromAsset(getActivity().getAssets(),"fonts/graphik_bold.otf");
        Typeface Graphik_Italic = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-RegularItalic.otf");
        btnLogIn.setTypeface(Graphik_Bold);
        txtUserLogin.setTypeface(Graphik_Regular);
        errormessage.setTypeface(Graphik_Regular);
        errormessage.setEnabled(false);
        btnExit.setTypeface(Graphik_Regular);
        cbRememberMe.setTypeface(Graphik_Regular);
        Forgot_password.setTypeface(Graphik_Italic);
        inLogin.setTypeface(Graphik_Regular);
        inPassword.setTypeface(Graphik_Regular);
        btnLogIn.setOnClickListener(myListener);
        btnExit.setOnClickListener(myListener);
        fragmentAutorization = new FragmentAutorization();

        fragmentUserBar = new FragmentUserBar();
        fragmentUserControlMenu = new FragmentUserControlMenu();

        //Clear camera selection
        dataActivity.setPathOfCamera("");
        dataActivity.setSelectedCameraName("");

        return view;
    }



    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case (R.id.BtnLogIn) :
                    errormessage.setEnabled(false);
                   String vLogin = inLogin.getText().toString();
                    String vPassword = inPassword.getText().toString();
                    Boolean IsValid = true;
                    if(IsValid&& TextUtils.isEmpty(vLogin))
                    {
                        errormessage.setText("Please enter username");
                        errormessage.setEnabled(true);
                        //inLogin.setError("Please enter username");
                        IsValid =false;
                    }
                    if(IsValid&& TextUtils.isEmpty(vPassword))
                    {
                        errormessage.setText("Please enter password");
                        errormessage.setEnabled(true);
                       // inPassword.setError("Please enter password");
                        IsValid =false;
                    }

                   if(IsValid) {
                       String login = String.valueOf(inLogin.getText());
                       String password = String.valueOf(inPassword.getText());
                       objDB = new DBHelper(getActivity().getApplicationContext());
                       user = objDB.validateUser(login,password);
                       if (user!=null && user.isadmin==1 ) {
                           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                           String currentDateandTime = sdf.format(new Date());
                           objDB.insertUserloginhistory(user.id, currentDateandTime, currentDateandTime);

                           if(cbRememberMe.isChecked())
                               dataActivity.setRememberme(login,password);
                           else
                               dataActivity.setRememberme("","");
                           dataActivity.startAdminFirstSetupMenu(user);
                           dataActivity.setBackgroundAdminImage();
                       }
                        else if (user != null && user.isadmin == 0) {
                           if(cbRememberMe.isChecked())
                               dataActivity.setRememberme(login,password);
                           else
                               dataActivity.setRememberme("","");
                            dataActivity.checkAutorizationInformation(user); //To do add user login history
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            String currentDateandTime = sdf.format(new Date());
                            objDB.insertUserloginhistory(user.id, currentDateandTime, currentDateandTime);
                            userSetting = objDB.getUserSettingByUserId(user.id);
                            dataActivity.setBrightness(userSetting.brightness);
                            if(userSetting.backgroundcolor.isEmpty())
                                dataActivity.setBackgroundImage();
                           if(!userSetting.backgroundcolor.isEmpty()) {
                                       dataActivity.setBackgroundColor((userSetting.backgroundcolor));
                               }
                        }
                         else {
                            errormessage.setText("Invalid Username/Password");
                         }
                  }
                    break;

                case (R.id.BtnExit) : dataActivity.closeApplication();
                    break;
            }
        }
    };


}
