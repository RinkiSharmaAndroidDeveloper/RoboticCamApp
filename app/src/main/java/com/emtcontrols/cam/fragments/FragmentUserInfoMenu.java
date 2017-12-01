package com.emtcontrols.cam.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.CursorJoiner;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.communication.HTTPCommunication;
import com.emtcontrols.cam.communication.HTTPConnection;
import com.emtcontrols.cam.configuration.Camera;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentUserInfoMenu extends Fragment {
    //LinearLayout cam_selectorArea;
    private Typeface Graphik_Regular,Graphik_Medium;
    public TextView camSelector, tvPreset,panel_lock1,info_header;
    ToggleButton mSelectedRB;
    ToggleButton panelLock;
    private FragmentCallBack dataActivity;
    private short i = 0;
    private boolean panelIsLocked = false;
    public String X ="X         :",Y="Y         :",Preset="Preset :";
    ArrayList<Camera> camListParam;
    JSONObject jsonobject;
    ArrayList<DBHelper.Camera> cameraList;
    public ToggleButton tbCameraNumber;
    DBHelper objDB;
    ArrayList<CameraInfoList> cameraInfoList;
    GridView gridviewCameraInfo;
    UserCameraSelectedAdapter userCameraSelectedAdapter;
    private boolean IsCameraSelectedOnLoad = false;
    String SelectedPresetNum="";
    public FragmentUserInfoMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user_info_menu, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();
        objDB = new DBHelper(getActivity().getApplicationContext());
        DBHelper.User user = dataActivity.getCurrentUserData();
        cameraList = objDB.getActiveCameraByUserID(user.id);
        cameraInfoList = new  ArrayList<CameraInfoList>(cameraList.size());

        camSelector = (TextView) v.findViewById(R.id.cam_selector);
        camSelector.setTypeface(Graphik_Regular);

        tvPreset = (TextView) v.findViewById(R.id.tvPreset);
        tvPreset.setTypeface(Graphik_Regular);
        panel_lock1=(TextView) v.findViewById(R.id.panel_lock1);
        panel_lock1.setTypeface(Graphik_Regular);
        info_header=(TextView) v.findViewById(R.id.panel_lock1);
        info_header.setTypeface(Graphik_Regular);
       // cam_selectorArea=(LinearLayout) v.findViewById(R.id.cam_selectorArea);


        panelLock = (ToggleButton)v.findViewById(R.id.panel_lock);
        gridviewCameraInfo = (GridView) v.findViewById(R.id.gv_Camera_selected_detail);
        for (DBHelper.Camera objCamera: cameraList) {
            CameraInfoList cameraInfoListItem = new CameraInfoList();
            cameraInfoListItem.CameraID = objCamera.id.toString();
            cameraInfoListItem.CameraName = objCamera.name;
            cameraInfoListItem.CameraURL=objCamera.path;
            cameraInfoListItem.CameraPresetNumber = "";
            cameraInfoListItem.CameraPresetNumber = "";
            cameraInfoListItem.CameraXaxis = "";
            cameraInfoListItem.CameraYaxis = "";
            cameraInfoList.add(cameraInfoListItem);
        }
        userCameraSelectedAdapter = new UserCameraSelectedAdapter(getActivity().getApplicationContext(), cameraInfoList);
        gridviewCameraInfo.setAdapter(userCameraSelectedAdapter);

        /*for (DBHelper.Camera objCamera: cameraList) {
            if(cameraList.size()==i+1)
            {
                String arrayParms[] = {objCamera.id.toString(), objCamera.name, objCamera.path, "status_rd","1"};
                new AsyncCallForApi(getActivity()).execute(arrayParms);
            }
            else {
                String arrayParms[] = {objCamera.id.toString(), objCamera.name, objCamera.path, "status_rd","0"};
                new AsyncCallForApi(getActivity()).execute(arrayParms);
            }
            i++;
        }*/

        if (dataActivity.getSelectedCameraName()!="")
        {
            camSelector.setText(dataActivity.getSelectedCameraName() + " is selected |");
            tvPreset.setText(dataActivity.getSelectedCameraPreset());
            IsCameraSelectedOnLoad=true;
        }
        //bind list view after getting data from api call
        gridviewCameraInfo.setOnItemClickListener(mOnItemClickListener);
        panelLock.setOnClickListener(toggleListener);
        Integer i=0;
        for (DBHelper.Camera objCamera: cameraList)
        {
            //RelativeLayout obj=(RelativeLayout) gridviewCameraInfo.getChildAt(i);
             String arrayParms[] = {objCamera.id.toString(), objCamera.name, objCamera.path, "status_rd", i.toString()};
            new AsyncCallForApi(getActivity()).execute(arrayParms);
            i++;
        }
        if(dataActivity.getPanelLockStatus())
        {
            panelLock.setChecked(true);
            panelIsLocked=true;
            panelLock.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
        }

        return v;
    }


    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!panelLock.isChecked()) {
               /* for(int i=0; i<gridviewCameraInfo.getChildCount(); i++) {
                    gridviewCameraInfo.getChildAt(i).setBackgroundResource(R.drawable.layout_border_unselected);
                }*/
                CameraInfoList item = (CameraInfoList) userCameraSelectedAdapter.getItem(position);
                camSelector.setText(item.CameraName + " is selected |");
                dataActivity.setSelectedCameraPreset("Preset :" + ((TextView)gridviewCameraInfo.getChildAt(position).findViewById(R.id.tvPresetsNumber)).getText().toString().replace("Preset :" ,"")+" | X :"+((TextView)gridviewCameraInfo.getChildAt(position).findViewById(R.id.tvCameraXaxis)).getText().toString().replace("X :" ,"")+" | Y: "+((TextView)gridviewCameraInfo.getChildAt(position).findViewById(R.id.tvCameraXaxis)).getText().toString().replace("Y :" ,""));
                 tvPreset.setText(dataActivity.getSelectedCameraPreset());
                if(((ToggleButton)gridviewCameraInfo.getChildAt(position).findViewById(R.id.tb_cameranumber)).isChecked())
                  ((ToggleButton)gridviewCameraInfo.getChildAt(position).findViewById(R.id.tb_cameranumber)).setChecked(false);
                else
                    ((ToggleButton)gridviewCameraInfo.getChildAt(position).findViewById(R.id.tb_cameranumber)).setChecked(true);

                dataActivity.setSelectedCameraName(item.CameraName);
                dataActivity.setPathOfCamera(item.CameraURL);
            }

        }
    };
    View.OnClickListener toggleListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (panelLock.isChecked()) {
                dataActivity.setPanelLockStatus(true);
                panelIsLocked = true;
                panelLock.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.on_off_button, 0);
            } else {
                dataActivity.setPanelLockStatus(false);
                panelIsLocked = false;
                panelLock.setCompoundDrawablesWithIntrinsicBounds( R.drawable.on_off_button, 0,0, 0);
            }
        }
    };

       // Create class for get data through api
    class CameraInfoList
    {
        String CameraID;
        String CameraName;
        String CameraXaxis;
        String CameraYaxis;
        String CameraPresetNumber;
        String CameraURL;

        public CameraInfoList()
        {

        }
        public CameraInfoList(String CameraID,String CameraName,String CameraXaxis,String CameraYaxis,String CameraPresetNumber,String CameraURL)
        {
            this.CameraID = CameraID;
            this.CameraName=CameraName;
            this.CameraXaxis=CameraXaxis;
            this.CameraYaxis = CameraYaxis;
            this.CameraPresetNumber = CameraPresetNumber;
            this.CameraURL=CameraURL;
        }
    }

    class UserCameraSelectedAdapter extends BaseAdapter {
        private int mSelectedPosition = -1;
        RadioButton mSelectedRB1;
        ArrayList<CameraInfoList> cameraInfo;
        Context context;
        RadioGroup rgp;
        UserCameraSelectedAdapter(Context context, ArrayList<CameraInfoList> cameraInfo) {
            this.cameraInfo = cameraInfo;
            this.context = context;
        }

        @Override
        public int getCount() {
            return cameraInfo.size();
        }

        @Override
        public CameraInfoList getItem(int position) {
            return cameraInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView tvCameraName,tvCameraXaxis,tvCameraYaxis,tvPresetsNumber;



            ViewHolder(View v) {
                tbCameraNumber =(ToggleButton) v.findViewById(R.id.tb_cameranumber);
                tvCameraName = (TextView) v.findViewById(R.id.tvCameraName);
                tvCameraXaxis = (TextView) v.findViewById(R.id.tvCameraXaxis);
                tvCameraYaxis = (TextView) v.findViewById(R.id.tvCameraYaxis);
                tvPresetsNumber = (TextView) v.findViewById(R.id.tvPresetsNumber);
                tbCameraNumber.setTypeface(Graphik_Regular);
                tvCameraName.setTypeface(Graphik_Regular);
                tvCameraXaxis.setTypeface(Graphik_Regular);
                tvCameraYaxis.setTypeface(Graphik_Regular);
                tvPresetsNumber.setTypeface(Graphik_Regular);
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
                    row = inflater.inflate(R.layout.camera_user_infolist, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                CameraInfoList cameraInfoList = cameraInfo.get(position);

                String SelectedCameraName = dataActivity.getSelectedCameraName();
                holder.tvCameraName.setText(cameraInfoList.CameraName);
                holder.tvCameraXaxis.setText(X+cameraInfoList.CameraXaxis);
                holder.tvCameraYaxis.setText(Y+cameraInfoList.CameraYaxis);



                holder.tvPresetsNumber.setText(Preset+cameraInfoList.CameraPresetNumber);
                tbCameraNumber.setText((String.valueOf(position+1)));
                tbCameraNumber.setTextOn((String.valueOf(position+1)));
                tbCameraNumber.setTextOff((String.valueOf(position+1)));
                if (cameraInfoList.CameraName.equals(dataActivity.getSelectedCameraName()) && IsCameraSelectedOnLoad) {
                    SelectedPresetNum= cameraInfoList.CameraPresetNumber ;
                    IsCameraSelectedOnLoad=false;
                    tbCameraNumber.setChecked(true);
                    mSelectedRB = (ToggleButton) tbCameraNumber;
                    mSelectedPosition=position;
                }
                tbCameraNumber.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if ((position != mSelectedPosition && mSelectedRB != null)) {
                            mSelectedRB.setChecked(false);
                        }
                        mSelectedPosition = position;
                        mSelectedRB = (ToggleButton) v;

                    }
                });
               tbCameraNumber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        ToggleButton rb = (ToggleButton) buttonView;

                        if (!panelIsLocked) {
                            if (isChecked) {
                                Integer pos = Integer.parseInt(rb.getText().toString()) - 1;
                                CameraInfoList item = (CameraInfoList) userCameraSelectedAdapter.getItem(pos);
                                camSelector.setText(item.CameraName + " is selected |");
                                dataActivity.setSelectedCameraName(item.CameraName);
                                dataActivity.setSelectedCameraPreset("Preset :" + ((TextView) gridviewCameraInfo.getChildAt(position).findViewById(R.id.tvPresetsNumber)).getText().toString().replace(Preset, "") + " | X :" + ((TextView) gridviewCameraInfo.getChildAt(position).findViewById(R.id.tvCameraXaxis)).getText().toString().replace(X, "") + " | Y: " + ((TextView) gridviewCameraInfo.getChildAt(position).findViewById(R.id.tvCameraYaxis)).getText().toString().replace(Y, ""));
                                tvPreset.setText(dataActivity.getSelectedCameraPreset());
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
        CameraInfoList cameraInfoListItem = new CameraInfoList();
        String Url,command,cameraid,cameraname;
        Integer index;
        public AsyncCallForApi(Context context){
            this.context=context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... pParams) {
            HTTPConnection httpConnection = new HTTPConnection();

            cameraid= pParams[0];
            cameraname=pParams[1];
            Url = pParams[2];
            command = pParams[3];
            index =  Integer.parseInt(pParams[4]) ;
            // call method to get data
            String response = "";
            response = httpConnection.CallHardwareApiGetData(Url, command);

            return response;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
                try {
                    if(!result.isEmpty()) {

                      jsonobject=new JSONObject(result);

                        LinearLayout obj=(LinearLayout) gridviewCameraInfo.getChildAt(0);
                        ((TextView)gridviewCameraInfo.getChildAt(index).findViewById(R.id.tvPresetsNumber )).setText(Preset + jsonobject.get("currPresetNum").toString());
                        ((TextView)gridviewCameraInfo.getChildAt(index).findViewById(R.id.tvCameraXaxis )).setText(X + jsonobject.get("x").toString());
                        ((TextView)gridviewCameraInfo.getChildAt(index).findViewById(R.id.tvCameraYaxis )).setText(Y + jsonobject.get("y").toString());

                        if(((TextView)gridviewCameraInfo.getChildAt(index).findViewById(R.id.tvCameraName )).getText().equals(dataActivity.getSelectedCameraName())) {
                            dataActivity.setSelectedCameraPreset("Preset : " + jsonobject.get("currPresetNum").toString()+ " | X :"+jsonobject.get("x").toString()+" | Y: "+ jsonobject.get("y").toString());
                            tvPreset.setText("Preset : " + jsonobject.get("currPresetNum").toString()+ " | X :"+jsonobject.get("x").toString()+" | Y: "+ jsonobject.get("y").toString());
                        }
                    }
                    else
                    {
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
