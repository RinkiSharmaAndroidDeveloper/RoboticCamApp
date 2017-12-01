package com.emtcontrols.cam.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emtcontrols.cam.R;

public class FragmentUserBar extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    private FragmentCallBack dataActivity;

    Fragment fragmentUserControlMenu, fragmentUserMemAndPresetsMenu, fragmentUserInfoMenu, fragmentUserOptionMenu;
    Button btnControlScreen, btnMemoryAndPresets, btnInformationMenu, btnOptionMenu, btnLogOut;

    private void resetBtnsColor (){
        btnControlScreen.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_unselected);
        btnMemoryAndPresets.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_unselected);
        btnInformationMenu.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_unselected);
        btnOptionMenu.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_unselected);
        btnLogOut.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_unselected);
        btnControlScreen.setTextColor(getActivity().getResources().getColor(R.color.white));
        btnMemoryAndPresets.setTextColor(getActivity().getResources().getColor(R.color.white));
        btnInformationMenu.setTextColor(getActivity().getResources().getColor(R.color.white));
        btnOptionMenu.setTextColor(getActivity().getResources().getColor(R.color.white));
        btnLogOut.setTextColor(getActivity().getResources().getColor(R.color.white));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataActivity = (FragmentCallBack) getActivity();

        View v = inflater.inflate(R.layout.fragment_user_bar, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        btnControlScreen    = (Button) v.findViewById(R.id.control_screen);
        btnMemoryAndPresets = (Button) v.findViewById(R.id.memory_and_presets);
        btnInformationMenu  = (Button) v.findViewById(R.id.information_menu);
        btnOptionMenu       = (Button) v.findViewById(R.id.option_menu);
        btnLogOut           = (Button) v.findViewById(R.id.logout);
        btnControlScreen.setTypeface(Graphik_Regular);
        btnMemoryAndPresets.setTypeface(Graphik_Regular);
        btnInformationMenu.setTypeface(Graphik_Regular);
        btnOptionMenu.setTypeface(Graphik_Regular);
        btnLogOut.setTypeface(Graphik_Regular);

        btnControlScreen.setOnClickListener(myListener);
        btnMemoryAndPresets.setOnClickListener(myListener);
        btnInformationMenu.setOnClickListener(myListener);
        btnOptionMenu.setOnClickListener(myListener);
        btnLogOut.setOnClickListener(myListener);


        btnControlScreen.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_selected);
        btnControlScreen.setTextColor(getActivity().getResources().getColor(R.color.white));
        return v;
    }

    View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            resetBtnsColor();

            switch (v.getId()){
                case (R.id.control_screen) :

                    //Starting Control Screen
                    fragmentUserControlMenu = new FragmentUserControlMenu();
                    getFragmentManager().beginTransaction().
                            replace(R.id.top_frame, fragmentUserControlMenu).
                            addToBackStack(null).
                            commit();
                    btnControlScreen.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_selected);
                    btnControlScreen.setTextColor(getActivity().getResources().getColor(R.color.white));
                    break;

                case (R.id.memory_and_presets) :

                    //Starting Memory and Presets menu
                    fragmentUserMemAndPresetsMenu = new FragmentUserMemAndPresets();
                    getFragmentManager().beginTransaction().
                            replace(R.id.top_frame, fragmentUserMemAndPresetsMenu).
                            addToBackStack(null).
                            commit();
                    btnMemoryAndPresets.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_selected);
                    btnMemoryAndPresets.setTextColor(getActivity().getResources().getColor(R.color.white));break;

                case (R.id.information_menu) :

                    //Starting Information Menu
                    fragmentUserInfoMenu = new FragmentUserInfoMenu();
                    getFragmentManager().beginTransaction().
                            replace(R.id.top_frame, fragmentUserInfoMenu).
                            addToBackStack(null).
                            commit();
                    btnInformationMenu.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_selected);
                    btnInformationMenu.setTextColor(getActivity().getResources().getColor(R.color.white));
                    break;

                case (R.id.option_menu) :

                    //Starting User Options Menu
                    fragmentUserOptionMenu = new FragmentUserOptionMenu();
                    getFragmentManager().beginTransaction().
                            replace(R.id.top_frame, fragmentUserOptionMenu).
                            addToBackStack(null).
                            commit();
                    btnOptionMenu.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_selected);
                    btnOptionMenu.setTextColor(getActivity().getResources().getColor(R.color.white));
                    break;

                case (R.id.logout) :

                    //Starting User Logout Menu
                    dataActivity.startUserLogout();
                    btnLogOut.setBackgroundResource(R.drawable.bottom_menu_user_bar_item_selected);
                    btnLogOut.setTextColor(getActivity().getResources().getColor(R.color.white));
                    break;

            }
        }
    };
}
