package com.emtcontrols.cam.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.communication.HTTPCommunication;
import com.emtcontrols.cam.communication.HTTPConnection;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;
import com.emtcontrols.cam.widget.VerticalSeekBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentUserControlMenu extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    public String urlForApiCall ;
    private FragmentCallBack dataActivity;
    private boolean isPanelLock = false;
    JSONArray jsonarray;
    JSONObject jsonobject;
   // private TextView tv_cam_selector;
    ArrayList<String> arListCameraNames;
    DBHelper objDB;
    ArrayList<DBHelper.Camera> cameraList;
    int checkedCameraPostion =-1;
    String urlOfSelectedCamera;
    GridView gridViewCameraList;

    private VerticalSeekBar sbFocus, sbZoom;
    private SeekBar sbSensitivity;
    private TextView tvAutoFocus,tvPanellock,tvFocus,tvCameraList,tvJoyStick,tvZoom,tvSenstivity,tvMode;

    private ToggleButton panelLock,autofocus;
    private ImageButton btnUP,btnRIGHT, btnLEFT, btnDOWN;
    private Button btn_camera_control,btn_elevation,btn_predestal;
    UserCameraAdapter userCameraAdapter;
    private int sickBarStepSize = 1;
    private boolean panelIsLocked = false;
    private boolean IsCameraSelectedOnLoad = false;


    public FragmentUserControlMenu() {
        // Required empty public constructor
    }

    private Object createDataObject(String input){

        JSONObject jssonObj   = new JSONObject();

        try{
            switch(input){
                case("UP"):
                    jssonObj.put("up", 1);
                    jssonObj.put("down", 0);
                    jssonObj.put("left", 0);
                    jssonObj.put("right", 0);
                    jssonObj.put("focus", sbFocus.getProgress());
                    jssonObj.put("zoom", sbZoom.getProgress());
                    jssonObj.put("sensitivity", sbSensitivity.getProgress());
                    break;
                case("DOWN"):
                    jssonObj.put("up", 0);
                    jssonObj.put("down", 1);
                    jssonObj.put("left", 0);
                    jssonObj.put("right", 0);
                    jssonObj.put("focus", sbFocus.getProgress());
                    jssonObj.put("zoom", sbZoom.getProgress());
                    jssonObj.put("sensitivity", sbSensitivity.getProgress());
                    break;
                case("LEFT"):
                    jssonObj.put("up", 0);
                    jssonObj.put("down", 0);
                    jssonObj.put("left", 1);
                    jssonObj.put("right", 0);
                    jssonObj.put("focus", sbFocus.getProgress());
                    jssonObj.put("zoom", sbZoom.getProgress());
                    jssonObj.put("sensitivity", sbSensitivity.getProgress());
                    break;
                case("RIGHT"):
                    jssonObj.put("up", 0);
                    jssonObj.put("down", 0);
                    jssonObj.put("left", 0);
                    jssonObj.put("right", 1);
                    jssonObj.put("focus", sbFocus.getProgress());
                    jssonObj.put("zoom", sbZoom.getProgress());
                    jssonObj.put("sensitivity", sbSensitivity.getProgress());
                    break;
                case("STOP"):
                    jssonObj.put("up", 0);
                    jssonObj.put("down", 0);
                    jssonObj.put("left", 0);
                    jssonObj.put("right", 0);
                    jssonObj.put("focus", sbFocus.getProgress());
                    jssonObj.put("zoom", sbZoom.getProgress());
                    jssonObj.put("sensitivity", sbSensitivity.getProgress());
                    break;
                case("SEEKBAR"):
                    jssonObj.put("up", 0);
                    jssonObj.put("down", 0);
                    jssonObj.put("left", 0);
                    jssonObj.put("right", 0);
                    jssonObj.put("focus", sbFocus.getProgress());
                    jssonObj.put("zoom", sbZoom.getProgress());
                    jssonObj.put("sensitivity", sbSensitivity.getProgress());
                    break;
            }
        }catch(Exception e){}

        return jssonObj;
    }



    private User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataActivity = (FragmentCallBack) getActivity();
        DBHelper.User user = dataActivity.getCurrentUserData();

        objDB = new DBHelper(getActivity().getApplicationContext());
        if(user.name.equals("admin"))
            cameraList = objDB.getAllCameras();
        else
            cameraList = objDB.getActiveCameraByUserID(user.id);
        arListCameraNames = new ArrayList<>(cameraList.size());
        for (DBHelper.Camera objCamera : cameraList)
            arListCameraNames.add(objCamera.name);

        View v = inflater.inflate(R.layout.fragment_user_control_menu, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        tvAutoFocus = (TextView) v.findViewById(R.id.auto_focus) ;
        tvPanellock =  (TextView) v.findViewById(R.id.panel_lock1) ;
        tvFocus = (TextView) v.findViewById(R.id.tvFocus) ;
        tvCameraList=(TextView) v.findViewById(R.id.tvCameraList) ;
        tvJoyStick=(TextView) v.findViewById(R.id.tvJoyStick) ;
        tvZoom=(TextView) v.findViewById(R.id.tvZoom) ;
        tvSenstivity = (TextView) v.findViewById(R.id.tvSenstivity) ;
        tvMode= (TextView) v.findViewById(R.id.tvMode) ;
        btn_camera_control = (Button) v.findViewById(R.id.btn_camera_control) ;
        btn_elevation = (Button) v.findViewById(R.id.btn_elevation) ;
        btn_predestal= (Button) v.findViewById(R.id.btn_predestal);

        tvAutoFocus.setTypeface(Graphik_Regular);
        tvPanellock.setTypeface(Graphik_Regular);
        tvFocus.setTypeface(Graphik_Regular);
        tvCameraList.setTypeface(Graphik_Regular);
        tvJoyStick.setTypeface(Graphik_Regular);
        tvZoom.setTypeface(Graphik_Regular);
        tvSenstivity.setTypeface(Graphik_Regular);
        tvMode.setTypeface(Graphik_Regular);
        btn_camera_control.setTypeface(Graphik_Regular);
        btn_elevation.setTypeface(Graphik_Regular);
        btn_predestal.setTypeface(Graphik_Regular);
       // tv_cam_selector = (TextView) v.findViewById(R.id.tvShowSelectedcamera) ;
        gridViewCameraList = (GridView) v.findViewById(R.id.gv_Camera_userlist);
        userCameraAdapter = new UserCameraAdapter(getActivity().getApplicationContext(), arListCameraNames);
        gridViewCameraList.setAdapter(userCameraAdapter);
        sbFocus =(VerticalSeekBar)v.findViewById(R.id.sb_focus);
        sbSensitivity =(SeekBar)v.findViewById(R.id.sb_sensitivity);
        sbZoom =(VerticalSeekBar)v.findViewById(R.id.sb_zoom);
        panelLock = (ToggleButton)v.findViewById(R.id.panel_lock);
        autofocus = (ToggleButton)v.findViewById(R.id.btnautofocus);



        btnUP = (ImageButton) v.findViewById(R.id.btnUP);
        btnLEFT = (ImageButton)v.findViewById(R.id.btnLEFT);
         btnRIGHT = (ImageButton)v.findViewById(R.id.btnRIGHT);
        btnDOWN = (ImageButton)v.findViewById(R.id.btnDOWN);

        btnUP.setOnTouchListener(joyListener);
        btnLEFT.setOnTouchListener(joyListener);
        btnRIGHT.setOnTouchListener(joyListener);
        btnDOWN.setOnTouchListener(joyListener);

        sbFocus.setOnSeekBarChangeListener(mFocusSeekBarChangeListener);
        sbSensitivity.setOnSeekBarChangeListener(mSensitivitySeekBarChangeListener);
        sbZoom.setOnSeekBarChangeListener(mZoomSeekBarChangeListener);

        panelLock.setOnClickListener(onClickListener);
        autofocus.setOnClickListener(onClickListener);
        btn_camera_control.setOnClickListener(onClickListener);
        btn_elevation.setOnClickListener(onClickListener);
        btn_predestal.setOnClickListener(onClickListener);
        //call api to set data
        resetBtnsColor();
        btn_camera_control.setBackgroundResource(R.drawable.mode_button_pressed);
        if (!dataActivity.getSelectedCameraName().equals(""))
        {
            IsCameraSelectedOnLoad=true;
        }
        if(dataActivity.getPanelLockStatus())
        {
            isPanelLock=true;
            panelLock.setChecked(true);
            changePanelLockControl(isPanelLock);
            panelLock.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
        }
        //Declare the timer
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                urlOfSelectedCamera = dataActivity.getPathOfCamera();
                if(!urlOfSelectedCamera.isEmpty()) {
                    String arrayParms[] = {urlOfSelectedCamera, "status_rd"};
                    new AsyncCallForApi(getActivity()).execute(arrayParms);
                }
            }

        }, 10000, 10000);
        return v;
    }
    private void changePanelLockControl(boolean isPanelLock)
    {
            sbFocus.setEnabled(!isPanelLock);
            sbSensitivity.setEnabled(!isPanelLock);
            sbZoom.setEnabled(!isPanelLock);
    }
    private void resetBtnsColor (){
       btn_camera_control.setBackgroundResource(R.drawable.mode_button_unpressed);
        btn_elevation.setBackgroundResource(R.drawable.mode_button_unpressed);
        btn_predestal.setBackgroundResource(R.drawable.mode_button_unpressed);
    }
    private void setDefaultControlValues()
    {

    }
    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.panel_lock): {
                    if (!isPanelLock) {
                        panelLock.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
                        dataActivity.setPanelLockStatus(true);
                        isPanelLock = true;
                        changePanelLockControl(isPanelLock);
                    }
                    else {
                        panelLock.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_off_button, 0,0 , 0);
                        dataActivity.setPanelLockStatus(false);
                        isPanelLock = false;
                        changePanelLockControl(isPanelLock);
                    }
                    break;
                }

                case (R.id.btnautofocus): {
                      if(autofocus.isChecked())
                      {
                          autofocus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
                      }
                      else
                      {
                          autofocus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.on_off_button, 0,0 , 0);
                      }
                    break;
                }

                case (R.id.btn_camera_control): {
                    if(!isPanelLock) {
                    resetBtnsColor();
                    btn_camera_control.setBackgroundResource(R.drawable.mode_button_pressed);
                    }
                    break;
                }
                case (R.id.btn_elevation): {
                    if (!isPanelLock){
                    resetBtnsColor();
                    btn_elevation.setBackgroundResource(R.drawable.mode_button_pressed);
                }
                    break;
                }
                case (R.id.btn_predestal): {
                    if(!isPanelLock) {
                    resetBtnsColor();
                    btn_predestal.setBackgroundResource(R.drawable.mode_button_pressed);
                    }
                    break;
                }
            }
        }
    };
    View.OnTouchListener joyListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch(view.getId()){
                case(R.id.btnUP):
                    if(!isPanelLock)
                    {
                    // Call api to send data of camera move up
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                           String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "UP")};
                           new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                        else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                           String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "STOP")};
                           new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                    }
                    break;
                // Call api to send data of camera move Left
                case(R.id.btnLEFT):
                    if(!isPanelLock) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "LEFT")};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "STOP")};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                    }
                    break;
                // Call api to send data of camera move Right
                case(R.id.btnRIGHT):
                    if(!isPanelLock) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "RIGHT")};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "STOP")};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                    }
                    break;
                // Call api to send data of camera move Down
                case(R.id.btnDOWN):
                    if(!isPanelLock) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "DOWN")};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                        else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "STOP")};
                            new AsyncCallForApi(getActivity()).execute(arrayParms);
                        }
                    }
                    break;
            }
            return false;
        }
    };







    SeekBar.OnSeekBarChangeListener mFocusSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public synchronized void  onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public synchronized void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public synchronized void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(!isPanelLock) {
                //vsProgress.setText(progress+"");
                progress = (progress / sickBarStepSize) * sickBarStepSize;
                seekBar.setProgress(progress);
                //Call api to send data of slider change
                String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "SEEKBAR")};
                new AsyncCallForApi(getActivity()).execute(arrayParms);
            }
        }
    };
    public String createApiUrl(String baseUrl,String CommandName,String Action)
    {
        Uri.Builder builder;

        builder = Uri.parse("http://" + baseUrl).buildUpon();
        builder.appendPath("camera").appendPath("headController").appendPath("joystickControl");
        builder.appendQueryParameter("command",CommandName);
        JSONObject outData = (JSONObject) createDataObject(Action);
        String dataOut = outData.toString();
        builder.appendQueryParameter("data", dataOut);
        return builder.toString();
    }
    SeekBar.OnSeekBarChangeListener mSensitivitySeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(!isPanelLock) {
                // TODO Auto-generated method stub
                //Call api to send data of slider change
                String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "SEEKBAR")};
                new AsyncCallForApi(getActivity()).execute(arrayParms);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(!isPanelLock) {
                //vsProgress.setText(progress+"");
                progress = (progress / sickBarStepSize) * sickBarStepSize;
                seekBar.setProgress(progress);
            }
        }
    };


    SeekBar.OnSeekBarChangeListener mZoomSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
            //Call api to send data of slider change


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(!isPanelLock) {
                //vsProgress.setText(progress+"");
                progress = (progress / sickBarStepSize) * sickBarStepSize;
                seekBar.setProgress(progress);
                // call api method
                String arrayParms[] = {createApiUrl(urlOfSelectedCamera, "joystick_wr", "SEEKBAR")};
                new AsyncCallForApi(getActivity()).execute(arrayParms);
            }
        }
    };
// create adapter for radio button  list
    class UserCameraAdapter extends BaseAdapter {
        private int mSelectedPosition = -1;
        ToggleButton mSelectedRB;
        ArrayList<String> cameraItems;
        Context context;
        RadioGroup rgp;
        UserCameraAdapter(Context context, ArrayList<String> cameraItems) {
            this.cameraItems = cameraItems;
            this.context = context;
            rgp = new RadioGroup(context);
        }

        @Override
        public int getCount() {
            return cameraItems.size();
        }

        @Override
        public String getItem(int position) {
            return cameraItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
           // RadioButton rbCameraName;
            ToggleButton tbCameraName;
            TextView tvCameraName;

            ViewHolder(View v) {
                tbCameraName = (ToggleButton) v.findViewById(R.id.tb_cameraname);
                tvCameraName = (TextView) v.findViewById(R.id.tv_cameraname);
                tbCameraName.setTypeface(Graphik_Medium);
                tvCameraName.setTypeface(Graphik_Regular);
            }
        }

        // this method call when we will bind the data from list view
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            View row = convertView;
            try {
                if (row == null) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    row = inflater.inflate(R.layout.camera_list_user_column, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                String CameraName = cameraItems.get(position);
                String SelectedCameraName = dataActivity.getSelectedCameraName();

                if(SelectedCameraName !=null && !SelectedCameraName.isEmpty())
                {
                    if(SelectedCameraName.equals(CameraName) && IsCameraSelectedOnLoad) {
                        holder.tbCameraName.setChecked(true);
                        IsCameraSelectedOnLoad=false;
                        mSelectedRB = (ToggleButton) holder.tbCameraName;
                        mSelectedPosition=position;
                        urlOfSelectedCamera = cameraList.get(position).path;
                        String arrayParms[] = {urlOfSelectedCamera,"status_rd"};
                        new AsyncCallForApi(getActivity()).execute(arrayParms);

                    }

                }
                holder.tvCameraName.setText(cameraList.get(position).name);
                holder.tbCameraName.setText((String.valueOf(position+1)));
                holder.tbCameraName.setTextOn((String.valueOf(position+1)));
                holder.tbCameraName.setTextOff((String.valueOf(position+1)));
                holder.tbCameraName.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                            if ((position != mSelectedPosition && mSelectedRB != null)) {
                                mSelectedRB.setChecked(false);
                            }
                        mSelectedPosition = position;
                        mSelectedRB = (ToggleButton) v;

                    }
                });
                holder.tbCameraName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        ToggleButton rb = (ToggleButton) buttonView;
                        if (!isPanelLock) {

                            if (isChecked) {
                                //  tv_cam_selector.setText(rb.getText());
                                checkedCameraPostion = position;
                                //Call api to get data of slider control (focus,sensitivity,zoom)
                                urlOfSelectedCamera = cameraList.get(checkedCameraPostion).path;

                                dataActivity.setPathOfCamera(urlOfSelectedCamera);
                                dataActivity.setSelectedCameraName(cameraList.get(checkedCameraPostion).name);
                                String arrayParms[] = {urlOfSelectedCamera, "status_rd"};
                                new AsyncCallForApi(getActivity()).execute(arrayParms);
                            }
                            else{
                                dataActivity.setPathOfCamera("");
                                dataActivity.setSelectedCameraName("");
                            }
                        }
                        else
                        {
                            if(rb.isChecked())
                              rb.setChecked(false);
                            else
                                rb.setChecked(true);
                        }
                    }
                });
            } catch (Exception ex) {
                String s = ex.getMessage();
            }
            return row;
        }
    }
   // Async call for api get and send data
    public class AsyncCallForApi extends AsyncTask<String, Void, String> {
        Context context;
        ProgressDialog dialog ;
        String CommandType ="";
        public AsyncCallForApi(Context context){
            this.context=context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
               //Remove dialog of loader
               /* // create ProgressDialog here ...
                dialog  = new ProgressDialog(context);
                dialog.setMessage("Loading...");
                //Show progress Dialog here
                dialog.show();*/
        }

        @Override
        protected String doInBackground(String... pParams) {
            HTTPConnection httpConnection = new HTTPConnection();
            String Url,Action,command;
            Url = pParams[0];
            // call method to get data
            String response = "";
            if(pParams.length==1)
            {
                response = httpConnection.CallHardwareApiSendData(Url);
                CommandType = HTTPCommunication.COMMANDS.SEND.toString();
            }
            else {
                command = pParams[1];
                response = httpConnection.CallHardwareApiGetData(Url, command);
                CommandType =HTTPCommunication.COMMANDS.READ.toString();
            }
            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(CommandType == HTTPCommunication.COMMANDS.READ.toString()) {
                try {
                    jsonobject = new JSONObject(result);
                    sbFocus.setProgress(((int) jsonobject.get("focus")));
                    sbSensitivity.setProgress(((int) jsonobject.get("sensitivity")));
                    sbZoom.setProgress(((int) jsonobject.get("zoom")));
                    dataActivity.setSelectedCameraPreset(jsonobject.get("currPresetNum").toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

          //  dialog.dismiss();

        }
    }
}