package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

/**
 * Created by i3 on 06 21 2016.
 */
public class SubFragmentChangePassword extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    private EditText NewPassword, ConfirmPassword;
    private TextView showSucessmessage,Username,UserNameText,tvNewPassword, tvConfirmPassword;
    private Button btnSave;
    private FragmentCallBack dataActivity;
    private DBHelper.User user;
    DBHelper objDB;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_change_password, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        Username = (TextView) v.findViewById(R.id.tvUserName);
        NewPassword = (EditText) v.findViewById(R.id.ed_Password);
        showSucessmessage = (TextView) v.findViewById(R.id.tv_passwordupdate);
        ConfirmPassword = (EditText) v.findViewById(R.id.ed_confirmpassword);
        btnSave =  (Button) v.findViewById(R.id.btn_savepassword);
        UserNameText= (TextView) v.findViewById(R.id.tvUserName1);
        tvNewPassword = (TextView) v.findViewById(R.id.tv_newPassword);
        tvConfirmPassword= (TextView) v.findViewById(R.id.tv_confirmpassword);
        UserNameText.setTypeface(Graphik_Regular);
        tvNewPassword.setTypeface(Graphik_Regular);
        tvConfirmPassword.setTypeface(Graphik_Regular);
        Username.setTypeface(Graphik_Regular);
        NewPassword.setTypeface(Graphik_Regular);
        ConfirmPassword.setTypeface(Graphik_Regular);
        showSucessmessage.setTypeface(Graphik_Regular);
        btnSave.setTypeface(Graphik_Regular);
        btnSave.setOnClickListener(btnListener);
        dataActivity = (FragmentCallBack) getActivity();
        user = dataActivity.getCurrentUserData();
        Username.setText(user.name);
        return v;

    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_savepassword):
                    showSucessmessage.setVisibility(v.INVISIBLE);
                    Boolean IsValid=true;
                    String vNewPassword= NewPassword.getText().toString();
                    String vConfirmPassword = ConfirmPassword.getText().toString();
                    if(IsValid&& TextUtils.isEmpty(vNewPassword)){
                        showSucessmessage.setText("Please enter new password");
                        showSucessmessage.setVisibility(v.getVisibility());
                        IsValid=false;
                    }
                    if(IsValid&& TextUtils.isEmpty(vConfirmPassword)){
                        showSucessmessage.setText("Please enter confirm password");
                        showSucessmessage.setVisibility(v.getVisibility());
                        IsValid=false;
                    }
                    if(IsValid && !vNewPassword.equals(vConfirmPassword))
                    {
                        showSucessmessage.setText("Password not match");
                        showSucessmessage.setVisibility(v.getVisibility());
                        IsValid=false;
                    }

                    if(IsValid) {
                            objDB = new DBHelper(getActivity().getApplicationContext());
                            objDB.updateUserPassword(user.id,vNewPassword );
                            showSucessmessage.setText("Your Password updated sucessfully");
                            showSucessmessage.setVisibility(v.getVisibility());
                            NewPassword.setText("");
                            ConfirmPassword.setText("");
                       /* fragmentSubShowUserData = new SubFragmentShowCameraDataAndManage();
                        getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubShowUserData).commit();*/
                    }
                    break;
            }

        }

    };
}
