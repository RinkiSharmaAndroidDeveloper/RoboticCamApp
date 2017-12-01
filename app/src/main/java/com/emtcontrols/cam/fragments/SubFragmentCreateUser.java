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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubFragmentCreateUser extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    private Button btnSave,btnCancel;
    private EditText etUserName, etUserPass;
    private TextView tvUsername,tvPassword;
    private String newUserName, newUserPass;
    private Fragment fragmentSubDeleteUser;
    private FragmentCallBack dataActivity;
    private ArrayList<DBHelper.User> userList;
    private GridView gridViewCameraList;
    SampleAdapter sampleAdapter;
    Bundle args;
    int UserID=0;
    int userPosition;
    ArrayList<Camera> camListParam;
    ArrayList<Integer> checkBoxChecked;
    ArrayList<DBHelper.UserCamera> userCameraList;
    ArrayList<String> arListCameraNames;
    DBHelper objDB;
    ArrayList<DBHelper.Camera> cameraList;
    CheckBox cbCamera;

    public SubFragmentCreateUser() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_create_user, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();

        objDB = new DBHelper(getActivity().getApplicationContext());
        userList = objDB.getAllUsers();
        cameraList = objDB.getAllCameras();
        tvUsername= (TextView) v.findViewById(R.id.tvUserName1);
        tvPassword=(TextView) v.findViewById(R.id.tv_Password);
        btnSave = (Button) v.findViewById(R.id.btn_save);
        btnCancel = (Button) v.findViewById(R.id.btn_usercancel);
        etUserName = (EditText) v.findViewById(R.id.et_name);
        etUserPass = (EditText) v.findViewById(R.id.et_pass);
        btnSave.setTypeface(Graphik_Medium);
        btnCancel.setTypeface(Graphik_Medium);
        etUserName.setTypeface(Graphik_Regular);
        etUserPass.setTypeface(Graphik_Regular);
        tvUsername.setTypeface(Graphik_Regular);
        tvPassword.setTypeface(Graphik_Regular);
        gridViewCameraList = (GridView) v.findViewById(R.id.gv_Camera_listData);

        gridViewCameraList.setVerticalScrollBarEnabled(false);

        arListCameraNames = new ArrayList<>(cameraList.size());
        checkBoxChecked = new ArrayList<>();
        for (DBHelper.Camera objCamera : cameraList)
            arListCameraNames.add(objCamera.name);
        args = getArguments();
        if (args != null) {
            userPosition = args.getInt("UserPosition");
            userCameraList = objDB.getAllUserCameraByUserID(userList.get(userPosition).id);
            UserID = userList.get(userPosition).id;
            //showing user password, name, access cams;
            etUserName.setText(userList.get(userPosition).name);     //write to Edit text user login
            etUserPass.setText(userList.get(userPosition).password);
        }
        sampleAdapter = new SampleAdapter(getActivity().getApplicationContext(), arListCameraNames,userCameraList);
        gridViewCameraList.setAdapter(sampleAdapter);
        btnSave.setOnClickListener(btnListener);
        btnCancel.setOnClickListener(btnListener);
        //Getting userPosition from SubFragmentDeleteUser

        return v;

    }



    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.btn_save):
                    String  vUserName =etUserName.getText().toString();
                    String  vPassword = etUserPass.getText().toString();
                    boolean IsValid=true;
                    if(IsValid&& TextUtils.isEmpty(vUserName))
                    {
                        etUserName.setError("Please enter username");
                        IsValid =false;
                    }
                    if(IsValid && (TextUtils.isEmpty(vPassword) || vPassword.length() < 6))
                    {
                        etUserPass.setError("You must have 6 characters in your password");
                        IsValid =false;
                    }
                    Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");;
                    Matcher ms;
                    if(IsValid) {

                        ms = ps.matcher(etUserName.getText().toString());
                        IsValid = ms.matches();
                        if (IsValid == false) {
                            etUserName.setError("Only letters and numbers are allowed!");
                        }
                    }

                    if(IsValid) {
                        ms = ps.matcher(etUserPass.getText().toString());
                        IsValid = ms.matches();
                        if (IsValid == false) {
                            etUserPass.setError("Only letters and numbers are allowed!");
                        }
                    }

                      if(IsValid) {
                          if (UserID > 0) {
                              newUserName = String.valueOf(etUserName.getText());
                              newUserPass = String.valueOf(etUserPass.getText());
                              objDB = new DBHelper(getActivity().getApplicationContext());
                              objDB.updateUser(UserID, newUserName, newUserPass);
                              objDB.deleteUserCameraByUserID(UserID);
                              for (int i = 0; i < checkBoxChecked.size(); i++) {
                                  int cameraID = cameraList.get(checkBoxChecked.get(i)).id;
                                  objDB.insertUserCamera(UserID, cameraID);
                              }
                          } else {
                              newUserName = String.valueOf(etUserName.getText());
                              newUserPass = String.valueOf(etUserPass.getText());
                              objDB = new DBHelper(getActivity().getApplicationContext());
                              int ID = objDB.insertUser(newUserName, newUserPass, 0);
                              for (int i = 0; i < checkBoxChecked.size(); i++) {
                                  int cameraID = cameraList.get(checkBoxChecked.get(i)).id;
                                  objDB.insertUserCamera(ID, cameraID);
                              }
                              objDB.insertUserSetting(ID,"","100","");
                          }
                          fragmentSubDeleteUser = new SubFragmentDeleteUser();
                          getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubDeleteUser).commit();
                      }
                    break;
                case (R.id.btn_usercancel):
                    fragmentSubDeleteUser = new SubFragmentDeleteUser();
                    getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubDeleteUser).commit();
                    break;
            }
        }
    };

    // create adapter to bind list view
    class SampleAdapter extends BaseAdapter {
        ArrayList<String> cameraItems;
        Context context;
        ArrayList<DBHelper.UserCamera> userCameraList;
        SampleAdapter(Context context, ArrayList<String> cameraItems,ArrayList<DBHelper.UserCamera> userCameraList) {
            this.cameraItems = cameraItems;
            this.context = context;
            this.userCameraList = userCameraList;
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
            ToggleButton tbCameraName;
            TextView tvCameraName;

            ViewHolder(View v) {
                tbCameraName = (ToggleButton) v.findViewById(R.id.tb_cameranumber);
                tvCameraName = (TextView) v.findViewById(R.id.tv_cameraname);
               /* tbCameraName.setTypeface(Graphik_Medium);
                tvCameraName.setTypeface(Graphik_Regular);*/
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
                    row = inflater.inflate(R.layout.camera_list_column, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                String CameraName = cameraItems.get(position);
                holder.tvCameraName.setText(CameraName);
                holder.tbCameraName.setText((String.valueOf(position+1)));
                holder.tbCameraName.setTextOn((String.valueOf(position+1)));
                holder.tbCameraName.setTextOff((String.valueOf(position+1)));
                if(userCameraList!=null) {
                    for (DBHelper.UserCamera objUserCamera : userCameraList) {
                        DBHelper.Camera cam = objDB.getCameraByID(objUserCamera.cameraid);
                        if (cam.name.equals(CameraName)) {
                            holder.tbCameraName.setChecked(true);
                            Integer index=position;
                            if(!checkBoxChecked.contains(index))
                               checkBoxChecked.add(index);
                        }
                    }
                }
                holder.tbCameraName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        Integer index=position;
                        if ( isChecked )
                          checkBoxChecked.add(index);
                        if(!isChecked)
                            checkBoxChecked.remove(index);
                    }
                });
            } catch (Exception ex) {
                String s = ex.getMessage();
            }
            return row;
        }
    }

}
