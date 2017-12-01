package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

/**
 * Created by i3 on 06 22 2016.
 */
public class SubFragmentChangeTextSize extends Fragment {
    private SeekBar TextSize;
  //  private TextView showSucessmessage;
    //private Button btnSave;
    private int sickBarStepSize = 1;
    private FragmentCallBack dataActivity;
    private DBHelper.User user;
    private DBHelper.UserSetting userSetting;
    DBHelper objDB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_change_text_size, container, false);
        TextSize = (SeekBar) v.findViewById(R.id.sb_textsize);
       // showSucessmessage = (TextView) v.findViewById(R.id.tvShowUpdateMessageForTextSize);
       // btnSave =  (Button) v.findViewById(R.id.btn_savetextsize);
      //  btnSave.setOnClickListener(btnListener);
        dataActivity = (FragmentCallBack) getActivity();
        user = dataActivity.getCurrentUserData();
        objDB = new DBHelper(getActivity().getApplicationContext());
        userSetting = objDB.getUserSettingByUserId(user.id);
        TextSize.setOnSeekBarChangeListener(mTimeScaleSeekBarChangeListener);
        if(!userSetting.textsize.isEmpty())
        TextSize.setProgress(Integer.parseInt(userSetting.textsize));
        return v;

    }
  /*  View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_savetextsize):
                    Boolean IsValid=true;
                    String vTextSize= String.valueOf(TextSize.getProgress());
                    if(IsValid) {
                        objDB = new DBHelper(getActivity().getApplicationContext());
                        objDB.updateUserSettingByUserId(user.id,"textsize",vTextSize);
                      //  objDB.se(user.id,vNewPassword );
                        showSucessmessage.setVisibility(v.getVisibility());
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
            Boolean IsValid=true;
            String vTextSize= String.valueOf(TextSize.getProgress());
            if(IsValid) {
                objDB = new DBHelper(getActivity().getApplicationContext());
                objDB.updateUserSettingByUserId(user.id, "textsize", vTextSize);
            }
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

