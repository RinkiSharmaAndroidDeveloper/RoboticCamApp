package com.emtcontrols.cam.fragments;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created  6/2/2016.
 */
public class SubFragmentCreateCamera extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    private FragmentCallBack dataActivity;
    private Button btnSave, btnCancel;
    private EditText CamName, CamUrl,Elevation,Predestal;
    TextView camnametext,camurltext;
    ArrayList<DBHelper.Camera> cameraList;
    Bundle args;
    int cameraPostion;
    int ID = 0;
    DBHelper objDB;
    Fragment fragmentSubShowUserData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {View v = inflater.inflate(R.layout.sub_fragment_cemera_create, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        CamName = (EditText) v.findViewById(R.id.et_camname);
        camnametext= (TextView) v.findViewById(R.id.tvcamname);
        camurltext= (TextView) v.findViewById(R.id.tv_cameraurl);
        CamUrl = (EditText) v.findViewById(R.id.et_camurl);
        Elevation = (EditText) v.findViewById(R.id.et_elevation);
        Predestal = (EditText) v.findViewById(R.id.et_predestal);
        btnCancel = (Button) v.findViewById(R.id.btn_camcancel);
        dataActivity = (FragmentCallBack) getActivity();
        btnSave = (Button) v.findViewById(R.id.btn_camsave);
        Elevation.setTypeface(Graphik_Regular);
        Predestal.setTypeface(Graphik_Regular);
        CamName.setTypeface(Graphik_Regular);
        CamUrl.setTypeface(Graphik_Regular);
        btnCancel.setTypeface(Graphik_Medium);
        btnSave.setTypeface(Graphik_Medium);
        camnametext.setTypeface(Graphik_Regular);
        CamUrl.setTypeface(Graphik_Regular);
        btnSave.setOnClickListener(btnListener);
        btnCancel.setOnClickListener(btnListener);
        args = getArguments();
        if (args != null) {
            cameraPostion = args.getInt("CameraPosition");
            objDB = new DBHelper(getActivity().getApplicationContext());
            cameraList = objDB.getAllCameras();
            DBHelper.Camera Camera = cameraList.get(cameraPostion);
            ID = Camera.id;
            CamName.setText(Camera.name);
            CamUrl.setText(Camera.path);
            Elevation.setText(Camera.elevation);
            Predestal.setText(Camera.predestal);
        }

        return v;

    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_camsave):
                    Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                    Matcher ms = ps.matcher(CamName.getText().toString());
                    boolean isValid = ms.matches();
                    if (isValid == false) {
                        CamName.setError("Only letters and numbers are allowed!");
                    }
                    else
                    {
                        objDB = new DBHelper(getActivity().getApplicationContext());
                        if (ID > 0)
                            objDB.updateCamera(ID, CamName.getText().toString(), CamUrl.getText().toString(),Elevation.getText().toString(),Predestal.getText().toString());
                        else
                            objDB.insertCamera(CamName.getText().toString(), CamUrl.getText().toString(),Elevation.getText().toString(),Predestal.getText().toString());
                        fragmentSubShowUserData = new SubFragmentShowCameraDataAndManage();
                        getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubShowUserData).commit();
                    }
                    break;
                case (R.id.btn_camcancel):
                    fragmentSubShowUserData = new SubFragmentShowCameraDataAndManage();
                    getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubShowUserData).commit();
                    break;

            }

        }

    };

}
