package com.emtcontrols.cam.fragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.communication.HTTPCommunication;
import com.emtcontrols.cam.configuration.DBHelper;

import java.util.ArrayList;

public class FragmentAdminTestDiagMenu extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
     public Button text_daignose,text_reset,text_pan,text_lens,text_elevation,text_pedstal;
    private TextView text_daign,text_lastlogin,tvstatus,tv_error1,tv_error2,tv_error3,tv_error4,tv_error5;
    private FragmentCallBack dataActivity;
    HTTPCommunication mHTTPCommunication;
    GridView gv_Camera_list;
    UserCameraAdapter userCameraAdapter;
    ArrayList<String> arListCameraNames;
    DBHelper objDB;
    ArrayList<DBHelper.Camera> cameraList;
    int checkedCameraPostion =-1;
    String urlOfSelectedCamera;
    public FragmentAdminTestDiagMenu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_test_diag_menu, container, false);

        dataActivity = (FragmentCallBack) getActivity();
        DBHelper.User user = dataActivity.getCurrentUserData();

        objDB = new DBHelper(getActivity().getApplicationContext());
        cameraList = objDB.getAllCameras();
        arListCameraNames = new ArrayList<>(cameraList.size());
        for (DBHelper.Camera objCamera : cameraList)
            arListCameraNames.add(objCamera.name);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/graphik_bold.otf");
        tvstatus=(TextView) v.findViewById(R.id.tvstatus) ;
        tv_error1=(TextView) v.findViewById(R.id.tv_error1) ;
        tv_error2=(TextView) v.findViewById(R.id.tv_error2) ;
        tv_error3=(TextView) v.findViewById(R.id.tv_error3) ;
        tv_error4=(TextView) v.findViewById(R.id.tv_error4) ;
        tv_error5=(TextView) v.findViewById(R.id.tv_error5) ;
        tv_error1.setTypeface(Graphik_Regular);
        tv_error2.setTypeface(Graphik_Regular);
        tv_error3.setTypeface(Graphik_Regular);
        tv_error4.setTypeface(Graphik_Regular);
        tv_error5.setTypeface(Graphik_Regular);
        tvstatus.setTypeface(Graphik_Regular);
        text_daignose = (Button) v.findViewById(R.id.btn_daignose) ;
        text_reset =  (Button) v.findViewById(R.id.btn_reset) ;
        text_daign = (TextView) v.findViewById(R.id.tv_test_diagnostic) ;
        text_lastlogin =  (TextView) v.findViewById(R.id.tv_last_login) ;
        text_pan = (Button) v.findViewById(R.id.btn_pan_tilt) ;
        text_lens =  (Button) v.findViewById(R.id.btn_lens) ;
        text_elevation = (Button) v.findViewById(R.id.btn_elevation) ;
        text_pedstal =  (Button) v.findViewById(R.id.btn_predesta) ;
        text_daignose.setTypeface(Graphik_Medium);
        text_reset.setTypeface(Graphik_Medium);
        text_daign.setTypeface(Graphik_Regular);
        text_lastlogin.setTypeface(Graphik_Regular);
        text_pan.setTypeface(Graphik_Regular);
        text_lens.setTypeface(Graphik_Regular);
        text_elevation.setTypeface(Graphik_Regular);
        text_pedstal.setTypeface(Graphik_Regular);
        user = dataActivity.getCurrentUserData();
        String lastlogindatetime = dataActivity.getLastLoginOfuser(user.id);
        text_lastlogin.setText("Last login as("+user.name+"):"+lastlogindatetime );
        gv_Camera_list=(GridView) v.findViewById(R.id.gv_Camera_list) ;
        userCameraAdapter = new UserCameraAdapter(getActivity().getApplicationContext(), arListCameraNames);
        gv_Camera_list.setAdapter(userCameraAdapter);


      //  mHTTPCommunication = dataActivity.getHttpCommunicationObject();
       // dataActivity.getHttpCommunicationObject().setHttpCallBack(httpReceiverCallBack);


        //mHTTPCommunication.command(HTTPCommunication.COMMANDS.SEND,)

        text_daignose.setOnClickListener(onClickListener);
        text_pan.setOnClickListener(onClickListener);
        text_lens.setOnClickListener(onClickListener);
        text_elevation.setOnClickListener(onClickListener);
        text_pedstal.setOnClickListener(onClickListener);
        text_reset.setOnClickListener(onClickListener);
        resetBtnsColor ();
        return v;
    }

    private void resetBtnsColor() {
        text_pan.setBackgroundResource(R.drawable.maintance_inactive);
        text_lens.setBackgroundResource(R.drawable.maintance_inactive);
        text_elevation.setBackgroundResource(R.drawable.maintance_inactive);
        text_pedstal.setBackgroundResource(R.drawable.maintance_inactive);
        text_daignose.setBackgroundResource(R.drawable.button_admin_menu_unpressed);
        text_reset.setBackgroundResource(R.drawable.button_admin_menu_unpressed);
    }

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
                tbCameraName.setTypeface(Graphik_Regular);
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
                    row = inflater.inflate(R.layout.camera_list_rcp_control, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                String CameraName = cameraItems.get(position);
                String SelectedCameraName = dataActivity.getSelectedCameraName();

               /* if(SelectedCameraName !=null && !SelectedCameraName.isEmpty())
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

                }*/
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

                            if (isChecked) {
                               /* //  tv_cam_selector.setText(rb.getText());
                                checkedCameraPostion = position;
                                //Call api to get data of slider control (focus,sensitivity,zoom)
                                urlOfSelectedCamera = cameraList.get(checkedCameraPostion).path;

                                dataActivity.setPathOfCamera(urlOfSelectedCamera);
                                dataActivity.setSelectedCameraName(cameraList.get(checkedCameraPostion).name);
                                String arrayParms[] = {urlOfSelectedCamera, "status_rd"};*/
                            }

                    }
                });
            } catch (Exception ex) {
                String s = ex.getMessage();
            }
            return row;
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_pan_tilt): {
                    resetBtnsColor();
                    text_pan.setBackgroundResource(R.drawable.maintance_active);
                    break;
                }
                case (R.id.btn_lens): {
                    resetBtnsColor();
                    text_lens.setBackgroundResource(R.drawable.maintance_active);

                    break;
                }
                case (R.id.btn_elevation): {

                    resetBtnsColor();
                        text_elevation.setBackgroundResource(R.drawable.maintance_active);

                    break;
                }
                case (R.id.btn_predesta): {

                        resetBtnsColor();
                    text_pedstal.setBackgroundResource(R.drawable.maintance_active);

                    break;
                }
                case (R.id.btn_reset): {

                        resetBtnsColor();
                    text_reset.setBackgroundResource(R.drawable.button_admin_menu_pressed);

                    break;
                }
                case (R.id.btn_daignose): {

                        resetBtnsColor();
                        text_daignose.setBackgroundResource(R.drawable.button_admin_menu_pressed);

                    break;
                }
            }
        }
    };
    /*HTTPCommunication.HTTPListener httpReceiverCallBack = new HTTPCommunication.HTTPListener() {

        @Override
        public void onNewData(String data) {


        }
    };*/

}
