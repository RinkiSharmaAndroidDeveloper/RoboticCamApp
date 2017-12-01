package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.Camera;
import com.emtcontrols.cam.configuration.DBHelper;

import java.util.ArrayList;

/**
 * Created by i3 on 06 22 2016.
 */
public class SubFragmentCameraLayout extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    private GridView gvCameraForUser;
    private FragmentCallBack dataActivity;
    SampleAdapter sampleAdapter;
    private DBHelper.User user;
    ArrayList<DBHelper.Camera> cameraList;
    ArrayList<DBHelper.Camera> ActiveCameraList;
    //ArrayList<>
    ArrayList<CameraListForUser> arCameraListForUser;
    ArrayList<Integer> CameraIDForInactiveUserCamera ;
    ArrayList<Integer> CameraIDForactiveUserCamera;
    DBHelper objDB;
    DBHelper.Camera objcam;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_camera_layout, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        gvCameraForUser = (GridView) v.findViewById(R.id.gv_Camera_ForUser);
        CameraIDForInactiveUserCamera = new ArrayList<Integer>();
        CameraIDForactiveUserCamera = new ArrayList<Integer>();
        dataActivity = (FragmentCallBack) getActivity();
        user = dataActivity.getCurrentUserData();
        objDB = new DBHelper(getActivity().getApplicationContext());
        cameraList = objDB.getCameraByUserID(user.id);
        ActiveCameraList = objDB.getActiveCameraByUserID(user.id);
        arCameraListForUser = new ArrayList<>(cameraList.size());
        CameraListForUser usercameralist;
        for (DBHelper.Camera objCamera : cameraList) {
            usercameralist = new CameraListForUser(objCamera.id.toString(), objCamera.name, objCamera.path, user.id.toString());
            arCameraListForUser.add(usercameralist);
        }
        sampleAdapter = new SampleAdapter(getActivity().getApplicationContext(), arCameraListForUser,ActiveCameraList);
        gvCameraForUser.setAdapter(sampleAdapter);
        return v;

    }
    // Create class for get data through api
    class CameraListForUser
    {
        String CameraID;
        String CameraName;
        String CameraURL;
        String UserID;

        public CameraListForUser()
        {

        }
        public CameraListForUser(String CameraID,String CameraName,String CameraURL,String UserID)
        {
            this.CameraID = CameraID;
            this.CameraName=CameraName;
            this.CameraURL=CameraURL;
            this.UserID = UserID;
        }
    }
    // create adapter to bind list view
    class SampleAdapter extends BaseAdapter {
        ArrayList<CameraListForUser> cameraItems;
        Context context;
        ArrayList<DBHelper.Camera> userActiveCamera;

        SampleAdapter(Context context, ArrayList<CameraListForUser> cameraItems,ArrayList<DBHelper.Camera> UerActiveCamera) {
            this.cameraItems = cameraItems;
            this.context = context;
            this.userActiveCamera = UerActiveCamera;
        }

        @Override
        public int getCount() {
            return cameraItems.size();
        }

        @Override
        public CameraListForUser getItem(int position) {
            return cameraItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
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
                    row = inflater.inflate(R.layout.camera_list_user_column, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                String CameraName = cameraItems.get(position).CameraName;
                holder.tvCameraName.setText(cameraList.get(position).name);
                holder.tbCameraName.setText((String.valueOf(position+1)));
                holder.tbCameraName.setTextOn((String.valueOf(position+1)));
                holder.tbCameraName.setTextOff((String.valueOf(position+1)));
                boolean isActive = false;
                for (DBHelper.Camera objCam : userActiveCamera) {
                    if(cameraItems.get(position).CameraName.equals(objCam.name) )
                        isActive = true;
                }
                holder.tbCameraName.setChecked(isActive);
                holder.tbCameraName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        Integer item =Integer.parseInt(cameraItems.get(position).CameraID);
                        if ( isChecked ) {
                            objDB.setstateUserCamera(user.id,item,1);
                        }
                        if(!isChecked) {
                            objDB.setstateUserCamera(user.id,item,0);
                        }

                    }
                });
            } catch (Exception ex) {
                String s = ex.getMessage();
            }
            return row;
        }
    }
}
