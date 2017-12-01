package com.emtcontrols.cam.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emtcontrols.cam.R;

public class FragmentAdminBar extends Fragment {

    public FragmentAdminBar() {
        // Required empty public constructor
    }
    private Typeface Graphik_Regular,Graphik_Medium;
    private FragmentCallBack dataActivity;
    Fragment fragmentMaintananceConfigMenu, fragmentAdminCreateMenu, fragmentUserControlMenu, fragmentTestDiagMenu;
    Button btnMaintananceConfMenu,  btnRcpControlMenu, btnTestDiagnosticMenu, btnLogOut;

    private void resetBtnsColor (){
        btnMaintananceConfMenu.setBackgroundResource(R.drawable.button_menu_unpressed);
      //  btnUserCreateMenu.setBackgroundResource(R.drawable.button_menu_unpressed);
        btnRcpControlMenu.setBackgroundResource(R.drawable.button_menu_unpressed);
        btnTestDiagnosticMenu.setBackgroundResource(R.drawable.button_menu_unpressed);
        btnLogOut.setBackgroundResource(R.drawable.button_menu_unpressed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_admin_bar, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();

        btnMaintananceConfMenu = (Button) v.findViewById(R.id.maintanance_conf_menu);
       // btnUserCreateMenu      = (Button) v.findViewById(R.id.user_create_menu);
        btnRcpControlMenu      = (Button) v.findViewById(R.id.rcp_control_menu);
        btnTestDiagnosticMenu  = (Button) v.findViewById(R.id.test_diagnostic_menu);
        btnLogOut              = (Button) v.findViewById(R.id.logout);
        btnMaintananceConfMenu.setTypeface(Graphik_Regular);
       // btnUserCreateMenu.setTypeface(Graphik_Medium);
        btnRcpControlMenu.setTypeface(Graphik_Regular);
        btnTestDiagnosticMenu.setTypeface(Graphik_Regular);
        btnLogOut.setTypeface(Graphik_Regular);

        btnMaintananceConfMenu.setOnClickListener(myListener);
      //  btnUserCreateMenu.setOnClickListener(myListener);
        btnRcpControlMenu.setOnClickListener(myListener);
        btnTestDiagnosticMenu.setOnClickListener(myListener);
        btnLogOut.setOnClickListener(myListener);

        btnMaintananceConfMenu.setBackgroundResource(R.drawable.button_admin_menu_pressed);

        return v;
    }

    View.OnClickListener myListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        resetBtnsColor();

        switch (v.getId()){
            case (R.id.maintanance_conf_menu) :

                //Starting Maintenance and Configure Menu
                fragmentMaintananceConfigMenu = new FragmentMaintanceConfigureMenu();
                getFragmentManager().beginTransaction().
                        replace(R.id.top_frame, fragmentMaintananceConfigMenu).
                        addToBackStack(null).
                        commit();
                btnMaintananceConfMenu.setBackgroundResource(R.drawable.button_admin_menu_pressed); break;

          /*  case (R.id.user_create_menu) :

                //Starting User Create Menu
                fragmentAdminCreateMenu = new FragmentAdminCreateMenu();
                getFragmentManager().beginTransaction().
                        replace(R.id.top_frame, fragmentAdminCreateMenu).
                        addToBackStack(null).
                        commit();
                btnUserCreateMenu.setBackgroundResource(R.drawable.button_admin_menu_pressed);break;*/

            case (R.id.rcp_control_menu) :

                //Starting RCP Control Menu
                fragmentUserControlMenu = new FragmentRCPControl();
                getFragmentManager().beginTransaction().
                        replace(R.id.top_frame, fragmentUserControlMenu).
                        addToBackStack(null).
                        commit();
                btnRcpControlMenu.setBackgroundResource(R.drawable.button_admin_menu_pressed);break;

            case (R.id.test_diagnostic_menu) :

                //Starting Test and Configure Menu
                fragmentTestDiagMenu = new FragmentAdminTestDiagMenu();
                getFragmentManager().beginTransaction().
                        replace(R.id.top_frame, fragmentTestDiagMenu).
                        addToBackStack(null).
                        commit();
                btnTestDiagnosticMenu.setBackgroundResource(R.drawable.button_admin_menu_pressed);break;

            case (R.id.logout) :

                //Starting Logout Menu
                dataActivity.startAdminLogout();
                btnLogOut.setBackgroundResource(R.drawable.button_admin_menu_pressed);break;
        }
    }
};

}
