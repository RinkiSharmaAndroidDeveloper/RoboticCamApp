package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emtcontrols.cam.R;

/**
 * Created by i3 on 07 06 2016.
 */
public class SubFragmentShowSettings extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_show_setting, container, false);

        return v;
    }
}
