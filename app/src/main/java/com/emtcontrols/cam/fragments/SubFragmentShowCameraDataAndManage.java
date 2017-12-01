package com.emtcontrols.cam.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;

import java.util.ArrayList;

/**
 * Created by i3 on 06 03 2016.
 */
public class SubFragmentShowCameraDataAndManage extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    DBHelper objDB;
    FragmentCallBack dataActivity;
    ArrayList<DBHelper.Camera> cameraList;
    ListView lvCameraList;
    Button cameraDelete, cameraEdit, camraCreate;
    DialogFragment dlgCameraDelete;
    int cameraPosition;
    Fragment fragmentSubCreateUser;
    CameraDataAdapter cameraDataAdapter;
    ArrayList<CameraData> cameraDataList;

    public SubFragmentShowCameraDataAndManage() {
        // Required empty public constructor
    }

    private void resetButtons() {
         cameraDelete.setBackgroundResource(R.drawable.maintance_button_unpressed);
         cameraEdit.setBackgroundResource(R.drawable.maintance_button_unpressed);
        camraCreate.setBackgroundResource(R.drawable.maintance_button_unpressed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_show_camera_data, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();


        cameraDelete = (Button) v.findViewById(R.id.btn_camera_delete);
        cameraEdit = (Button) v.findViewById(R.id.btn_camera_edit);
        camraCreate = (Button) v.findViewById(R.id.btn_create_camera);
        lvCameraList = (ListView) v.findViewById(R.id.lv_Camera_list);
        cameraDelete.setTypeface(Graphik_Regular);
        cameraEdit.setTypeface(Graphik_Regular);
        camraCreate.setTypeface(Graphik_Regular);
        // btnCreateUser = (Button)getActivity().findViewById(R.id.create_user);
        // btnDeleteUser = (Button)getActivity().findViewById(R.id.delete_user);

        dlgCameraDelete = new ConfirmDialog();
        dlgCameraDelete.setTargetFragment(this, 0);
        objDB = new DBHelper(getActivity().getApplicationContext());
        cameraList = objDB.getAllCameras();

        CameraData cameraData ;
        cameraDataList = new ArrayList<>(cameraList.size());
        for (DBHelper.Camera objCamera : cameraList) {
            cameraData = new CameraData(objCamera.id.toString(),objCamera.name,objCamera.path);
            cameraDataList.add(cameraData);
        }
        //cameraList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cameraDataAdapter = new CameraDataAdapter(getActivity().getApplicationContext(), cameraDataList);
        lvCameraList.setAdapter(cameraDataAdapter);
       /* adapter = new ArrayAdapter<>(getActivity(), android.R.layout.test_list_item, arListCameraNames);      //creating adapter

        lvCameraList.setAdapter(adapter);*/
        lvCameraList.setOnItemClickListener(mOnItemClickListener);
        cameraDelete.setOnClickListener(btnListener);
        cameraEdit.setOnClickListener(btnListener);
        camraCreate.setOnClickListener(btnListener);

        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            cameraPosition = ((int) id);

            for(int i=0; i<lvCameraList.getChildCount(); i++) {
                lvCameraList.getChildAt(i).setBackgroundColor(0);
            }
            view.setBackgroundColor(getResources().getColor(R.color.RedDark));
        }
    };

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_camera_delete):
                    resetButtons();
                    cameraDelete.setBackgroundResource(R.drawable.maintance_button_pressed);
                    dlgCameraDelete.show(getFragmentManager(), "dlgCameraDelete");
                    break;
                case (R.id.btn_camera_edit):
                    resetButtons();
                    cameraEdit.setBackgroundResource(R.drawable.maintance_button_pressed);
                    fragmentSubCreateUser = new SubFragmentCreateCamera();
                    Bundle bundle = new Bundle();
                    bundle.putInt("CameraPosition", cameraPosition);
                    fragmentSubCreateUser.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubCreateUser).commit();
                    break;
                case (R.id.btn_create_camera):
                    resetButtons();
                    camraCreate.setBackgroundResource(R.drawable.maintance_button_pressed);
                    fragmentSubCreateUser = new SubFragmentCreateCamera();
                    getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubCreateUser).commit();
                    break;
            }

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if(cameraPosition>=0) {
                Integer ID = cameraList.get(cameraPosition).id;
                objDB = new DBHelper(getActivity().getApplicationContext());
                objDB.deleteCamera(ID);
                cameraList.remove(cameraPosition);
                cameraDataList.remove(cameraPosition);
                cameraDataAdapter.notifyDataSetChanged();
            }
        }
    }

    class CameraData{
        String cameraID;
        String cameraname;
        String url;
        public CameraData(String cameraId,String cameraName,String Url)
        {
            cameraID=cameraId;
            cameraname=cameraName;
            url =Url;
        }
        public CameraData()
        {

        }
    }
    class CameraDataAdapter extends BaseAdapter {
        private int mSelectedPosition = -1;
        ToggleButton mSelectedRB;
        ArrayList<CameraData> cameraItems;
        Context context;
        CameraDataAdapter(Context context, ArrayList<CameraData> cameraItems) {
            this.cameraItems = cameraItems;
            this.context = context;
        }

        @Override
        public int getCount() {
            return cameraItems.size();
        }

        @Override
        public CameraData getItem(int position) {
            return cameraItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            // RadioButton rbCameraName;
            TextView tv_camerano,tv_cameraname,tv_url;

            ViewHolder(View v) {
                tv_camerano = (TextView) v.findViewById(R.id.tv_camerano);
                tv_cameraname = (TextView) v.findViewById(R.id.tv_cameraname);
                tv_url = (TextView) v.findViewById(R.id.tv_url);
                tv_camerano.setTypeface(Graphik_Regular);
                tv_cameraname.setTypeface(Graphik_Regular);
                tv_url.setTypeface(Graphik_Regular);
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
                    row = inflater.inflate(R.layout.all_camera_list_admin, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                CameraData cameraData = cameraItems.get(position);


                holder.tv_camerano.setText((String.valueOf(position+1)));
                holder.tv_cameraname.setText(cameraData.cameraname);
                holder.tv_url.setText(cameraData.url);
            } catch (Exception ex) {
                String s = ex.getMessage();
            }
            return row;
        }
    }

    

}
