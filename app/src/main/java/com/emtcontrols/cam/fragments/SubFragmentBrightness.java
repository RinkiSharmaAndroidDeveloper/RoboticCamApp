package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.EditText;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

/**
 * Created by i3 on 06 22 2016.
 */
public class SubFragmentBrightness extends Fragment {
    private SeekBar Brightness;
   // private TextView showSucessmessage;
    private Button btnSave;
    private int sickBarStepSize = 1;
    private FragmentCallBack dataActivity;
    private DBHelper.User user;
    private DBHelper.UserSetting userSetting;
    DBHelper objDB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_change_brightness, container, false);
        Brightness = (SeekBar) v.findViewById(R.id.sb_brightness);
      //  showSucessmessage = (TextView) v.findViewById(R.id.tvShowUpdateMessageForBrightness);
       // btnSave =  (Button) v.findViewById(R.id.btn_savebrightness);
       // btnSave.setOnClickListener(btnListener);
        dataActivity = (FragmentCallBack) getActivity();
        user = dataActivity.getCurrentUserData();
        objDB = new DBHelper(getActivity().getApplicationContext());
        userSetting = objDB.getUserSettingByUserId(user.id);
        if(!userSetting.brightness.isEmpty())
         Brightness.setProgress(Integer.parseInt(userSetting.brightness));
        Brightness.setOnSeekBarChangeListener(mTimeScaleSeekBarChangeListener);
        return v;

    }
   /* View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_savebrightness):
                    Boolean IsValid=true;
                    String vBrightness=  String.valueOf(Brightness.getProgress());
                    if(IsValid) {
                        objDB = new DBHelper(getActivity().getApplicationContext());
                        //  objDB.se(user.id,vNewPassword );
                        objDB.updateUserSettingByUserId(user.id,"brightness",vBrightness);
                        dataActivity.setBrightness(vBrightness);
                       *//* fragmentSubShowUserData = new SubFragmentShowCameraDataAndManage();
                        getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubShowUserData).commit();*//*
                    }
                    break;

            }

        }

    };*/

    SeekBar.OnSeekBarChangeListener mTimeScaleSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            String vBrightness=  String.valueOf(Brightness.getProgress());
            objDB = new DBHelper(getActivity().getApplicationContext());
            //  objDB.se(user.id,vNewPassword );
            objDB.updateUserSettingByUserId(user.id,"brightness",vBrightness);
            dataActivity.setBrightness(vBrightness);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //vsProgress.setText(progress+"");
            progress = (progress / sickBarStepSize) * sickBarStepSize;
            seekBar.setProgress(progress);

        }
    };
}
