package com.emtcontrols.cam.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUserOptionMenu extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    private TextView Password,tvBrightness,tvBackgroundcolor,tvTextsize,tvCameralayout;
    private LinearLayout llPassword,llBrightness,llBackgroundcolor,llTextsize,llCameralayout;;
    private FragmentCallBack dataActivity;
    private DBHelper.User user;
    Fragment fragmentSubChangepassword,fragmentSubChangeTextSize,fragmentCameraLayout,fragmentBrightness,fragmentBackgroundColor;
    public FragmentUserOptionMenu() {
        // Required empty public constructor
    }
    private void resetBtnsColor (){
        llPassword.setBackgroundResource(0);
        llBrightness.setBackgroundResource(0);
        llBackgroundcolor.setBackgroundResource(0);
        llTextsize.setBackgroundResource(0);
        llCameralayout.setBackgroundResource(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_option_menu, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();
        user = dataActivity.getCurrentUserData();

        llPassword= (LinearLayout) v.findViewById(R.id.llPassword);
        llBrightness = (LinearLayout) v.findViewById(R.id.llBrightness);
        llBackgroundcolor = (LinearLayout) v.findViewById(R.id.llBackgroundcolor);
        llTextsize = (LinearLayout) v.findViewById(R.id.llTextsize);
        llCameralayout = (LinearLayout) v.findViewById(R.id.llCameralayout);
        Password = (TextView) v.findViewById(R.id.tvPassword);
        tvBrightness = (TextView) v.findViewById(R.id.tvBrightness);
        tvBackgroundcolor = (TextView) v.findViewById(R.id.tvBackgroundcolor);
        tvTextsize = (TextView) v.findViewById(R.id.tvTextsize);
        tvCameralayout = (TextView) v.findViewById(R.id.tvCameralayout);
        Password.setTypeface(Graphik_Regular);
        tvBrightness.setTypeface(Graphik_Regular);
        tvBackgroundcolor.setTypeface(Graphik_Regular);
        tvTextsize.setTypeface(Graphik_Regular);
        tvCameralayout.setTypeface(Graphik_Regular);
        fragmentSubChangepassword = new SubFragmentChangePassword();
        fragmentSubChangeTextSize =  new SubFragmentChangeTextSize();
        fragmentCameraLayout =  new SubFragmentCameraLayout();
        fragmentBrightness = new SubFragmentBrightness();
        fragmentBackgroundColor = new SubFragmentBackgroundColor();
        Password.setOnClickListener(textViewListener);
        tvBrightness.setOnClickListener(textViewListener);
        tvBackgroundcolor.setOnClickListener(textViewListener);
        tvTextsize.setOnClickListener(textViewListener);
        tvCameralayout.setOnClickListener(textViewListener);
        return v;
    }

    View.OnClickListener textViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case (R.id.tvPassword):
                    resetBtnsColor();
                    llPassword.setBackgroundResource(R.color.RedTextView);
                    getFragmentManager().beginTransaction().
                            replace(R.id.user_option_menu, fragmentSubChangepassword).
                            addToBackStack(null).
                            commit();
                    break;
                case (R.id.tvBrightness):
                    resetBtnsColor();
                    llBrightness.setBackgroundResource(R.color.RedTextView);
                    getFragmentManager().beginTransaction().
                            replace(R.id.user_option_menu, fragmentBrightness).
                            addToBackStack(null).
                            commit();
                    break;
                case (R.id.tvBackgroundcolor):
                    resetBtnsColor();
                    llBackgroundcolor.setBackgroundResource(R.color.RedTextView);
                    getFragmentManager().beginTransaction().
                            replace(R.id.user_option_menu, fragmentBackgroundColor).
                            addToBackStack(null).
                            commit();
                    break;
                case (R.id.tvTextsize):
                    resetBtnsColor();
                    llTextsize.setBackgroundResource(R.color.RedTextView);
                    getFragmentManager().beginTransaction().
                            replace(R.id.user_option_menu, fragmentSubChangeTextSize).
                            addToBackStack(null).
                            commit();
                    break;
                case (R.id.tvCameralayout):
                    resetBtnsColor();
                    llCameralayout.setBackgroundResource(R.color.RedTextView);
                    getFragmentManager().beginTransaction().
                            replace(R.id.user_option_menu, fragmentCameraLayout).
                            addToBackStack(null).
                            commit();

                    break;
            }
        }
    };
}
