package com.emtcontrols.cam.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.communication.HTTPCommunication;
import com.emtcontrols.cam.configuration.Camera;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;
import com.emtcontrols.cam.fragments.FragmentAdminBar;
import com.emtcontrols.cam.fragments.FragmentAdminFirstSetup;
import com.emtcontrols.cam.fragments.FragmentAdminLogout;
import com.emtcontrols.cam.fragments.FragmentAutorization;
import com.emtcontrols.cam.fragments.FragmentCallBack;
import com.emtcontrols.cam.fragments.FragmentMaintanceConfigureMenu;
import com.emtcontrols.cam.fragments.FragmentUserBar;
import com.emtcontrols.cam.fragments.FragmentUserControlMenu;
import com.emtcontrols.cam.fragments.FragmentUserLogout;
import com.emtcontrols.cam.fragments.FragmentAdminSuperUserScreen;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity implements FragmentCallBack{

    public HTTPCommunication mHTTPCommunication;
    public FrameLayout main_frame;
    FragmentAutorization fragmentAutorization;
    Fragment fragmentUserBar, fragmentUserControlMenu, fragmentUserLogout ;
    Fragment fragmentAdminBar, fragmentAdminFirsSetupMenu, fragmentAdminLogout , fragmentMaintanceConfigureMenu,fragmentadminsuperuserscreen;

    ArrayList<User> userList;
    public DBHelper.User objUser;
    User currentUser, admin;
    private final int CAM_QUANTITY = 12;
    //boolean[] camListAccess = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true};
    ArrayList<Camera> camListParam;
    public String pathOfCamera ;
    public Boolean IsPanelLock = false;
    public String selectedCameraName;
    public String selectedCameraPreset;
    String jsonStr;
    SharedPreferences sPref;
    String SAVED_TEXT = "saved_text";
    String REMEMBER_ME = "remember_me";

      DBHelper objdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        objdb = new DBHelper(this);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_frame = (FrameLayout) findViewById(R.id.main_frame) ;
        // set screen on of app
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sPref = getPreferences(MODE_PRIVATE);
        jsonStr = sPref.getString(SAVED_TEXT, "");

        mHTTPCommunication = new HTTPCommunication();

        Type itemsListType = new TypeToken<ArrayList<User>>() {}.getType();
        userList = new Gson().fromJson(jsonStr, itemsListType);

        if(userList == null) {
            userList = new ArrayList<User>();

//            for (int i = 0; i < CAM_QUANTITY; i++ ) {
//                Camera camParam = new Camera();
//                camListParam.add(i, camParam );
//
//            }

            userList.add(new User("u", "u", camListParam));
        }

        fragmentAutorization = new FragmentAutorization();

        fragmentUserBar = new FragmentUserBar();
        fragmentUserControlMenu = new FragmentUserControlMenu();
        fragmentUserLogout = new FragmentUserLogout();

        fragmentMaintanceConfigureMenu = new FragmentMaintanceConfigureMenu();

        fragmentadminsuperuserscreen=new FragmentAdminSuperUserScreen();

        fragmentAdminBar = new FragmentAdminBar();
        fragmentAdminFirsSetupMenu = new FragmentAdminFirstSetup();
        fragmentAdminLogout = new FragmentAdminLogout();

        startAutorizationDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();

        jsonStr = new Gson().toJson(userList);

        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_TEXT, jsonStr);
        ed.commit();
    }

    public void setRememberme(String username,String password)
    {
        if(!username.isEmpty()) {
            sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(REMEMBER_ME, "{'username':'" + username + "','password':'" + password + "'}");
            ed.commit();
        }
        else
        {
            sPref = getPreferences(MODE_PRIVATE);
            if(sPref!=null)
               sPref.edit().remove(REMEMBER_ME).commit();

        }

    }
    public String getLastLoginOfuser(Integer userID)
    {
        DBHelper objDB;
        objDB = new DBHelper(this);
        ArrayList<DBHelper.UserLoginHistory> arruserLoginHistory;

        arruserLoginHistory = objDB.getUserloginhistories(userID, 2);
        if (arruserLoginHistory.size() == 2) {
            String ss= getdiferenceInHours(arruserLoginHistory.get(0).logintgime,arruserLoginHistory.get(1).logintgime);

            String lastlogindate= getDate(arruserLoginHistory.get(0).logintgime);

          return  ss +" " + lastlogindate;


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
    public void setBrightness(String brightnes)
    {
        float brightness = Integer.parseInt(brightnes) / (float)255;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = brightness;
        getWindow().setAttributes(lp);
    }
    public void setBackgroundColor(String backgroundColor)
    {
        if(backgroundColor.equals("Gray"))
            main_frame.setBackgroundColor(Color.GRAY);
        else
            main_frame.setBackgroundColor(Color.BLACK);
    }
    public void setBackgroundImage()
    {
        main_frame.setBackgroundResource(R.drawable.main_background);
    }
    public void setBackgroundAdminImage()
    {
        main_frame.setBackgroundResource(R.drawable.admin_background);
    }
    public String getRememberme()
    {
        sPref = getPreferences(MODE_PRIVATE);
        return sPref.getString(REMEMBER_ME, "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }

    @Override
    public void checkAutorizationInformation(DBHelper.User user) {

        objUser=user;
        getFragmentManager().beginTransaction().
                remove(fragmentAutorization).
                add(R.id.bottom_frame, fragmentUserBar).
                add(R.id.top_frame, fragmentUserControlMenu).
                addToBackStack(null).
                commit();

        /*currentUser = null;
        admin = null;

        for (User user : userList) {
            if(user.isMyLogin(login, password) == true) {
                currentUser = user;

                //writing user lodin date and time
                Date loginData = new Date();
                SimpleDateFormat loginDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                SimpleDateFormat loginTimeFormat = new SimpleDateFormat("HH:mm:ss");
                currentUser.addTimeToHistory(loginTimeFormat.format(loginData), loginDateFormat.format(loginData));
            }

        }

        if(currentUser != null){
            getFragmentManager().beginTransaction().
                    remove(fragmentAutorization).
                    add(R.id.bottom_frame, fragmentUserBar).
                    add(R.id.top_frame, fragmentUserControlMenu).
                    addToBackStack(null).
                    commit();

        }*/

    }

    public void setAdminScreen()
    {
        getFragmentManager().beginTransaction().
                remove(fragmentadminsuperuserscreen).
                add(R.id.bottom_frame, fragmentAdminBar).
                add(R.id.top_frame, fragmentMaintanceConfigureMenu).
                addToBackStack(null).
                commit();
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public String getPathOfCamera() {return pathOfCamera; }
    public Boolean getPanelLockStatus() {return IsPanelLock; }
    public void setPanelLockStatus(Boolean isPanelLock){IsPanelLock=isPanelLock;}

    public void setPathOfCamera(String PathOfCamera) {pathOfCamera=PathOfCamera;}
    public String getSelectedCameraName(){ return selectedCameraName;}
    public void setSelectedCameraName(String SelectedCameraName) {selectedCameraName=SelectedCameraName;};

    public String getSelectedCameraPreset(){ return selectedCameraPreset;}
    public void setSelectedCameraPreset(String SelectedCameraPreset) {selectedCameraPreset=SelectedCameraPreset;};

    public DBHelper.User getCurrentUserData() {
        return objUser;
    }

    @Override
    public HTTPCommunication getHttpCommunicationObject() {
        return mHTTPCommunication;
    }

    @Override
    public void startAdminFirstSetupMenu(DBHelper.User user) {
        objUser = user;
        getFragmentManager().beginTransaction().
                remove(fragmentAutorization).
              //  add(R.id.bottom_frame, fragmentAdminBar).
              //  add(R.id.top_frame, fragmentMaintanceConfigureMenu).
                      add(R.id.top_frame,fragmentadminsuperuserscreen).

                addToBackStack(null).
                commit();
    }

    @Override
    public void startAdminLogout() {
        getFragmentManager().beginTransaction().
                replace(R.id.top_frame, fragmentAdminLogout).
                addToBackStack(null).
                commit();
    }


    @Override
    public void startAutorizationDialog() {

        FragmentTransaction fTrans = getFragmentManager().beginTransaction();

        if (fragmentUserLogout.isVisible() && fragmentUserBar.isVisible() ) {

            fTrans.remove(fragmentUserLogout);
            fTrans.remove(fragmentUserBar);

        } else if (fragmentAdminLogout.isVisible() && fragmentAdminBar.isVisible() ) {
            fTrans.remove(fragmentAdminLogout);
            fTrans.remove(fragmentAdminBar);
        }
        else if(fragmentadminsuperuserscreen.isVisible())
        {
            fTrans.remove(fragmentadminsuperuserscreen);
        }
        fTrans.replace(R.id.main_frame, fragmentAutorization);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    @Override
    public void startUserLogout() {
        getFragmentManager().beginTransaction().
                replace(R.id.top_frame, fragmentUserLogout).
                addToBackStack(null).
                commit();
    }

    @Override
    public void closeApplication() {
        finish();
    }

    @Override
    public ArrayList<User> getUserList() {
        return userList;
    }

}
