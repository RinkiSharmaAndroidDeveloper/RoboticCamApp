package com.emtcontrols.cam.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emtcontrols.cam.R;

public class FragmentAdminCreateMenu extends Fragment {

    public FragmentAdminCreateMenu() {
        // Required empty public constructor
    }

    Fragment fragmentSubCreateUser, fragmentSubDeleteUser;
    FragmentCallBack dataActivity;
    Button btnCreateUser, btnDeleteUser;

    private void startSubUserDeleteMenu() {
        getFragmentManager().beginTransaction().
                replace(R.id.user_create_menu_frame, fragmentSubDeleteUser).
                addToBackStack(null).
                commit();
    }
    private void resetBtnsColor (){
        btnCreateUser.setBackgroundResource(R.drawable.button_menu_unpressed);
        btnDeleteUser.setBackgroundResource(R.drawable.button_menu_unpressed);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_create_menu, container, false);

        dataActivity = (FragmentCallBack) getActivity();

        btnCreateUser = (Button)v.findViewById(R.id.create_user);
        btnDeleteUser = (Button)v.findViewById(R.id.delete_user);

        btnCreateUser.setOnClickListener(myListener);
        btnDeleteUser.setOnClickListener(myListener);

        fragmentSubDeleteUser = new SubFragmentDeleteUser();

        btnDeleteUser.setBackgroundResource(R.drawable.button_menu_pressed);
        startSubUserDeleteMenu();

        return v;
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            resetBtnsColor();

            switch (v.getId()){
                case (R.id.create_user) :
                        fragmentSubCreateUser = new SubFragmentCreateUser();
                        getFragmentManager().beginTransaction().
                                replace(R.id.user_create_menu_frame, fragmentSubCreateUser).
                                addToBackStack(null).
                                commit();
                        btnCreateUser.setBackgroundResource(R.drawable.button_menu_pressed);

                    break;

                case (R.id.delete_user) :
                 if (!fragmentSubDeleteUser.isVisible()) {
                     startSubUserDeleteMenu();
                    btnDeleteUser.setBackgroundResource(R.drawable.button_menu_pressed);break;
                 }
            }
        }
    };


}
