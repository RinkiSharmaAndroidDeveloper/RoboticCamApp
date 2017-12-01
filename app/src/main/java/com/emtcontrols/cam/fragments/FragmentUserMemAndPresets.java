package com.emtcontrols.cam.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.communication.HTTPCommunication;
import com.emtcontrols.cam.communication.HTTPConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentUserMemAndPresets extends Fragment {

    private Typeface Graphik_Regular,Graphik_Medium;
    public FragmentUserMemAndPresets() {
        // Required empty public constructor
    }

    private FragmentCallBack dataActivity;
    SeekBar sbTimeScale;
    private boolean isPanelLock = false;
    private int sickBarStepSize = 10;
    private String cameraPath = "";
    private boolean multiselectItem = false;
    ArrayList<Integer> selectedItemList = new ArrayList<Integer>();
    public int SelectedItemNo = 0;

    TextView tvnumber1, tvnumber2, tvnumber3, tvnumber4, tvnumber5, tvnumber6, tvnumber7, tvnumber8, tvnumber9, tvnumber10, tvnumber11, tvnumber12, tvnumber13, tvnumber14, tvnumber15, tvnumber16, tvnumber17, tvnumber18, tvnumber19, tvnumber20, tvnumber21, tvnumber22, tvnumber23, tvErrorMessageForSelectCamera, tvnumber24, tvnumber25, tvnumber26, tvnumber27, tvnumber28, tvnumber29, tvnumber30,tvPanleLock,memory_preset_head,tvTimeScale;
    Button multipleselectButton, btnSave, btnExcute;
    ToggleButton btnPanelLock;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_mem_and_presets, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        sbTimeScale = (SeekBar) v.findViewById(R.id.sb_time_scale);
        btnExcute = (Button) v.findViewById(R.id.btnExcute);
        tvErrorMessageForSelectCamera = (TextView) v.findViewById(R.id.tvErrorMessageForSelectCamera);
        tvnumber1 = (TextView) v.findViewById(R.id.tvnumber1);
        tvnumber2 = (TextView) v.findViewById(R.id.tvnumber2);
        tvnumber3 = (TextView) v.findViewById(R.id.tvnumber3);
        tvnumber4 = (TextView) v.findViewById(R.id.tvnumber4);
        tvnumber5 = (TextView) v.findViewById(R.id.tvnumber5);
        tvnumber6 = (TextView) v.findViewById(R.id.tvnumber6);
        tvnumber7 = (TextView) v.findViewById(R.id.tvnumber7);
        tvnumber8 = (TextView) v.findViewById(R.id.tvnumber8);
        tvnumber9 = (TextView) v.findViewById(R.id.tvnumber9);
        tvnumber10 = (TextView) v.findViewById(R.id.tvnumber10);
        tvnumber11 = (TextView) v.findViewById(R.id.tvnumber11);
        tvnumber12 = (TextView) v.findViewById(R.id.tvnumber12);
        tvnumber13 = (TextView) v.findViewById(R.id.tvnumber13);
        tvnumber14 = (TextView) v.findViewById(R.id.tvnumber14);
        tvnumber15 = (TextView) v.findViewById(R.id.tvnumber15);
        tvnumber16 = (TextView) v.findViewById(R.id.tvnumber16);
        tvnumber17 = (TextView) v.findViewById(R.id.tvnumber17);
        tvnumber18 = (TextView) v.findViewById(R.id.tvnumber18);
        tvnumber19 = (TextView) v.findViewById(R.id.tvnumber19);
        tvnumber20 = (TextView) v.findViewById(R.id.tvnumber20);
        tvnumber21 = (TextView) v.findViewById(R.id.tvnumber21);
        tvnumber22 = (TextView) v.findViewById(R.id.tvnumber22);
        tvnumber23 = (TextView) v.findViewById(R.id.tvnumber23);
        tvnumber24 = (TextView) v.findViewById(R.id.tvnumber24);
        tvnumber25 = (TextView) v.findViewById(R.id.tvnumber25);
        tvnumber26 = (TextView) v.findViewById(R.id.tvnumber26);
        tvnumber27 = (TextView) v.findViewById(R.id.tvnumber27);
        tvnumber28 = (TextView) v.findViewById(R.id.tvnumber28);
        tvnumber29 = (TextView) v.findViewById(R.id.tvnumber29);
        tvnumber30 = (TextView) v.findViewById(R.id.tvnumber30);
        tvPanleLock = (TextView) v.findViewById(R.id.tv_panel_lock);
        memory_preset_head = (TextView) v.findViewById(R.id.memory_preset_head);
        tvPanleLock.setTypeface(Graphik_Regular);
        memory_preset_head.setTypeface(Graphik_Regular);
        multipleselectButton = (Button) v.findViewById(R.id.btnMultipleSelect);
        tvTimeScale=(TextView) v.findViewById(R.id.memory_preset_head);
        tvTimeScale.setTypeface(Graphik_Regular);
        btnSave = (Button) v.findViewById(R.id.btnSave);
        btnPanelLock = (ToggleButton) v.findViewById(R.id.btnPanelLock);
        tvnumber1.setTypeface(Graphik_Regular);
        tvnumber2.setTypeface(Graphik_Regular);
        tvnumber3.setTypeface(Graphik_Regular);
        tvnumber4.setTypeface(Graphik_Regular);
        tvnumber5.setTypeface(Graphik_Regular);
        tvnumber6.setTypeface(Graphik_Regular);
        tvnumber7.setTypeface(Graphik_Regular);
        tvnumber8.setTypeface(Graphik_Regular);
        tvnumber9.setTypeface(Graphik_Regular);
        tvnumber10.setTypeface(Graphik_Regular);
        tvnumber11.setTypeface(Graphik_Regular);
        tvnumber12.setTypeface(Graphik_Regular);
        tvnumber13.setTypeface(Graphik_Regular);
        tvnumber14.setTypeface(Graphik_Regular);
        tvnumber15.setTypeface(Graphik_Regular);
        tvnumber16.setTypeface(Graphik_Regular);
        tvnumber17.setTypeface(Graphik_Regular);
        tvnumber18.setTypeface(Graphik_Regular);
        tvnumber19.setTypeface(Graphik_Regular);
        tvnumber20.setTypeface(Graphik_Regular);
        tvnumber21.setTypeface(Graphik_Regular);
        tvnumber22.setTypeface(Graphik_Regular);
        tvnumber23.setTypeface(Graphik_Regular);
        tvnumber24.setTypeface(Graphik_Regular);
        tvnumber25.setTypeface(Graphik_Regular);
        tvnumber26.setTypeface(Graphik_Regular);
        tvnumber27.setTypeface(Graphik_Regular);
        tvnumber28.setTypeface(Graphik_Regular);
        tvnumber29.setTypeface(Graphik_Regular);
        tvnumber30.setTypeface(Graphik_Regular);
        multipleselectButton.setTypeface(Graphik_Regular);
        btnSave.setTypeface(Graphik_Regular);
        btnExcute.setTypeface(Graphik_Regular);
        btnPanelLock.setTypeface(Graphik_Regular);
        tvnumber1.setOnClickListener(btnListener);
        tvnumber2.setOnClickListener(btnListener);
        tvnumber3.setOnClickListener(btnListener);
        tvnumber4.setOnClickListener(btnListener);
        tvnumber5.setOnClickListener(btnListener);
        tvnumber6.setOnClickListener(btnListener);
        tvnumber7.setOnClickListener(btnListener);
        tvnumber8.setOnClickListener(btnListener);
        tvnumber9.setOnClickListener(btnListener);
        tvnumber10.setOnClickListener(btnListener);
        tvnumber11.setOnClickListener(btnListener);
        tvnumber12.setOnClickListener(btnListener);
        tvnumber13.setOnClickListener(btnListener);
        tvnumber14.setOnClickListener(btnListener);
        tvnumber15.setOnClickListener(btnListener);
        tvnumber16.setOnClickListener(btnListener);
        tvnumber17.setOnClickListener(btnListener);
        tvnumber18.setOnClickListener(btnListener);
        tvnumber19.setOnClickListener(btnListener);
        tvnumber20.setOnClickListener(btnListener);
        tvnumber21.setOnClickListener(btnListener);
        tvnumber22.setOnClickListener(btnListener);
        tvnumber23.setOnClickListener(btnListener);

        tvnumber24.setOnClickListener(btnListener);
        tvnumber25.setOnClickListener(btnListener);
        tvnumber26.setOnClickListener(btnListener);
        tvnumber27.setOnClickListener(btnListener);
        tvnumber28.setOnClickListener(btnListener);
        tvnumber29.setOnClickListener(btnListener);
        tvnumber30.setOnClickListener(btnListener);

        multipleselectButton.setOnClickListener(btnListener);
        btnSave.setOnClickListener(btnListener);
        btnExcute.setOnClickListener(btnListener);
        btnPanelLock.setOnClickListener(btnListener);
        resetButtonList(0);

        multipleselectButton.setBackgroundResource(R.drawable.mode_button_unpressed);
        sbTimeScale.setOnSeekBarChangeListener(mTimeScaleSeekBarChangeListener);

        dataActivity = (FragmentCallBack) getActivity();
        cameraPath = dataActivity.getPathOfCamera();
        if(dataActivity.getPanelLockStatus())
        {
            isPanelLock=true;
            changePanelLockControl(isPanelLock);
            btnPanelLock.setChecked(true);
            btnPanelLock.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
        }
        return v;
    }
    private void changePanelLockControl(boolean isPanelLock)
    {
        sbTimeScale.setEnabled(!isPanelLock);
    }
    private void resetButtonList(Integer selectedValue) {
        if (!multiselectItem) {
            SelectedItemNo = selectedValue;

            tvnumber1.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber2.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber3.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber4.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber5.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber6.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber7.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber8.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber9.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber10.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber11.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber12.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber13.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber14.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber15.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber16.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber17.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber18.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber19.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber20.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber21.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber22.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber23.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber24.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber25.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber26.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber27.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber28.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber29.setBackgroundResource(R.drawable.preset_unpressed);
            tvnumber30.setBackgroundResource(R.drawable.preset_unpressed);
        } else {
            if (!selectedItemList.contains(selectedValue))
                selectedItemList.add(selectedValue);
        }
    }


    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.tvnumber1):
                    if(!isPanelLock) {
                        resetButtonList(1);
                        tvnumber1.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber2):
                    if(!isPanelLock) {
                        resetButtonList(2);

                        tvnumber2.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber3):
                    if(!isPanelLock) {
                        resetButtonList(3);
                        tvnumber3.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber4):
                    if(!isPanelLock) {
                        resetButtonList(4);
                        tvnumber4.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber5):
                    if(!isPanelLock) {
                        resetButtonList(5);
                        tvnumber5.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber6): {
                    if(!isPanelLock) {
                        resetButtonList(6);
                        tvnumber6.setBackgroundResource(R.drawable.preset_pressed);
                    }
                }
                    break;
                case (R.id.tvnumber7):
                    if(!isPanelLock) {
                        resetButtonList(7);
                        tvnumber7.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber8):
                    if(!isPanelLock) {
                        resetButtonList(8);
                        tvnumber8.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber9):
                    if(!isPanelLock) {
                        resetButtonList(9);
                        tvnumber9.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber10):
                    if(!isPanelLock) {
                        resetButtonList(10);
                        tvnumber10.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber11):
                    if(!isPanelLock) {
                        resetButtonList(11);
                        tvnumber11.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber12):
                    if(!isPanelLock) {
                        resetButtonList(12);
                        tvnumber12.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber13):
                    if(!isPanelLock) {
                        resetButtonList(13);
                        tvnumber13.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber14):
                    if(!isPanelLock) {
                        resetButtonList(14);
                        tvnumber14.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber15):
                    if(!isPanelLock) {
                        resetButtonList(15);
                        tvnumber15.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber16):
                    if(!isPanelLock) {
                        resetButtonList(16);
                        tvnumber16.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber17):
                    if(!isPanelLock) {
                        resetButtonList(17);
                        tvnumber17.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber18):
                    if(!isPanelLock) {
                        resetButtonList(18);
                        tvnumber18.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber19):
                    if(!isPanelLock) {
                        resetButtonList(19);
                        tvnumber19.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber20):
                    if(!isPanelLock) {
                        resetButtonList(20);
                        tvnumber20.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber21):
                    if(!isPanelLock) {
                        resetButtonList(21);
                        tvnumber21.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber22):
                    if(!isPanelLock) {
                        resetButtonList(22);
                        tvnumber22.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber23):
                    if(!isPanelLock) {
                        resetButtonList(23);
                        tvnumber23.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber24):
                    if(!isPanelLock) {
                        resetButtonList(24);
                        tvnumber24.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber25):
                    if(!isPanelLock) {
                        resetButtonList(25);
                        tvnumber25.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber26):
                    if(!isPanelLock) {
                        resetButtonList(26);
                        tvnumber26.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber27):
                    if(!isPanelLock) {
                        resetButtonList(27);
                        tvnumber27.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber28):
                    if(!isPanelLock) {
                        resetButtonList(28);
                        tvnumber28.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber29):
                    if(!isPanelLock) {
                        resetButtonList(29);
                        tvnumber29.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.tvnumber30):
                    if(!isPanelLock) {
                        resetButtonList(30);
                        tvnumber30.setBackgroundResource(R.drawable.preset_pressed);
                    }
                    break;
                case (R.id.btnMultipleSelect):
                    if(!isPanelLock) {
                        if (multiselectItem) {
                            multiselectItem = false;
                            multipleselectButton.setBackgroundResource(R.drawable.mode_button_unpressed);
                            resetButtonList(0);
                            selectedItemList.clear();
                        } else {
                            resetButtonList(0);
                            multiselectItem = true;
                            multipleselectButton.setBackgroundResource(R.drawable.mode_button_pressed);
                            selectedItemList = new ArrayList<Integer>();
                        }
                    }
                    break;
                case (R.id.btnSave):
                    if(!isPanelLock) {
                        if (SelectedItemNo > 0 && cameraPath != null && !cameraPath.isEmpty()) {
                            tvErrorMessageForSelectCamera.setVisibility(v.INVISIBLE);
                            String arrayParms[] = {createApiUrl(cameraPath, "presetSave", "Save", false)};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        } else if (cameraPath == null || cameraPath.isEmpty()) {
                            tvErrorMessageForSelectCamera.setVisibility(v.getVisibility());
                        }
                    }
                    break;
                case (R.id.btnExcute):

                    if(!isPanelLock) {
                        if (multiselectItem && selectedItemList.size() > 0 && cameraPath != null && !cameraPath.isEmpty()) {
                            tvErrorMessageForSelectCamera.setVisibility(v.INVISIBLE);
                            String arrayParms[] = {createApiUrl(cameraPath, "presetExecute", "Excute", false)};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        } else if (SelectedItemNo > 0 && cameraPath != null && !cameraPath.isEmpty()) {
                            tvErrorMessageForSelectCamera.setVisibility(v.INVISIBLE);
                            String arrayParms[] = {createApiUrl(cameraPath, "presetExecute", "Excute", true)};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        } else if (cameraPath == null || cameraPath.isEmpty()) {
                            tvErrorMessageForSelectCamera.setVisibility(v.getVisibility());
                        }
                    }
                    break;
                case (R.id.btnPanelLock):
                    if (!isPanelLock) {
                        dataActivity.setPanelLockStatus(true);
                        isPanelLock = true;
                        changePanelLockControl(isPanelLock);
                        btnPanelLock.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
                    }
                    else {
                        dataActivity.setPanelLockStatus(false);
                        isPanelLock = false;
                        changePanelLockControl(isPanelLock);
                        btnPanelLock.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_off_button, 0,0 , 0);
                    }
                    break;


            }

        }

    };

    SeekBar.OnSeekBarChangeListener mTimeScaleSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

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
            if(cameraPath!=null && !cameraPath.isEmpty()) {
                String arrayParms[] = {createApiUrl(cameraPath, "presetTimeScale_wr", "Timescale", false)};
                new AsyncCallForApi(getActivity()).execute(arrayParms);
            }
        }
    };

    private String createApiUrl(String baseUrl, String CommandName, String Action,Boolean isSingleExecute) {
        Uri.Builder builder;

        builder = Uri.parse("http://" + baseUrl).buildUpon();
        builder.appendPath("camera").appendPath("headController").appendPath("presetsControl");
        builder.appendQueryParameter("command", CommandName);
        JSONObject outData = (JSONObject) createDataObject(Action,isSingleExecute);
        String dataOut = outData.toString();
        builder.appendQueryParameter("data", dataOut);
        return builder.toString();
    }

    private Object createDataObject(String input,Boolean isSingleExecute) {

        JSONObject jssonObj = new JSONObject();

        try {
            switch (input) {
                case ("Save"):
                    jssonObj.put("presetNum", SelectedItemNo);
                    break;
                case ("Excute"):
                    jssonObj.put("timeScale", sbTimeScale.getProgress());
                   if(isSingleExecute) {
                       ArrayList<Integer> singleItem =  new ArrayList<Integer>();
                       singleItem.add(SelectedItemNo);
                       JSONArray jsArray = new JSONArray(singleItem);
                       jssonObj.put("presets", jsArray);
                   }
                    else {
                       JSONArray jsArray = new JSONArray(selectedItemList);
                       jssonObj.put("presets", jsArray);
                   }


                    break;
                case ("Timescale"):
                    jssonObj.put("timeScale", sbTimeScale.getProgress());
                    break;

            }
        } catch (Exception e) {
        }

        return jssonObj;
    }

    // Async call for api get and send data
    public class AsyncCallForApi extends AsyncTask<String, Void, String> {
        Context context;
        ProgressDialog dialog;
        String CommandType = "";

        public AsyncCallForApi(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... pParams) {
            HTTPConnection httpConnection = new HTTPConnection();
            String Url, Action, command;
            Url = pParams[0];
            // call method to get data
            String response = "";
            response = httpConnection.CallHardwareApiSendData(Url);
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}
