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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.emtcontrols.cam.R;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;
import java.util.ArrayList;

public class SubFragmentDeleteUser extends Fragment {
    private Typeface Graphik_Regular,Graphik_Medium;
    DBHelper objDB;
    FragmentCallBack dataActivity;
    ArrayList<DBHelper.User> userList;
    ListView lvUserList;
    Button userDelete, userEdit,addUser;
    DialogFragment dlgUserDelete;

   // TextView tvShowMessageForEdit;
    int userPosition=-1;
    UserDataAdapter userDataAdapter;
    ArrayList<UserData> userDataList;
    Fragment fragmentSubCreateUser;

    final String LOG_TAG = "myLogs";
    public SubFragmentDeleteUser() {
        // Required empty public constructor
    }

    private void resetButtons() {
        addUser.setBackgroundResource(R.drawable.maintance_button_unpressed);
        userDelete.setBackgroundResource(R.drawable.maintance_button_unpressed);
        userEdit.setBackgroundResource(R.drawable.maintance_button_unpressed);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_fragment_delete_user, container, false);
        Graphik_Regular= Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Regular.otf");
        Graphik_Medium = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Graphik-Medium.otf");
        dataActivity = (FragmentCallBack) getActivity();


        addUser = (Button)v.findViewById(R.id.btn_create_user);
        userDelete = (Button)v.findViewById(R.id.btn_user_delete);
        userEdit = (Button)v.findViewById(R.id.btn_user_edit);
        lvUserList = (ListView)v.findViewById(R.id.lv_user_list);

        addUser.setTypeface(Graphik_Regular);
        userDelete.setTypeface(Graphik_Regular);
        userEdit.setTypeface(Graphik_Regular);
        dlgUserDelete = new ConfirmDialog();
        dlgUserDelete.setTargetFragment(this, 0);
        objDB = new DBHelper(getActivity().getApplicationContext());
        userList = objDB.getAllUsers();
        UserData userdata ;
        userDataList = new ArrayList<>(userList.size());
        for (DBHelper.User objUser : userList) {
            userdata = new UserData(objUser.id.toString(),objUser.name,objUser.password);
            userDataList.add(userdata);
        }
        userDataAdapter = new UserDataAdapter(getActivity().getApplicationContext(), userDataList);
        lvUserList.setAdapter(userDataAdapter);

        lvUserList.setOnItemClickListener(mOnItemClickListener);

        userDelete.setOnClickListener(btnListener);
        addUser.setOnClickListener(btnListener);
        userEdit.setOnClickListener(btnListener);
      //  tvShowMessageForEdit = (TextView) v.findViewById(R.id.tvShowMessageForEditUser);

        return v;
    }

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            userPosition = ((int) id);
            for(int i=0; i<lvUserList.getChildCount(); i++) {
                lvUserList.getChildAt(i).setBackgroundColor(0);
            }
            view.setBackgroundColor(getResources().getColor(R.color.RedDark));
           // tvShowMessageForEdit.setVisibility(view.INVISIBLE);

        }
    };

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case (R.id.btn_user_delete) :
                    if(userPosition>=0) {
                        userDelete.setBackgroundResource(R.drawable.maintance_button_pressed);
                        dlgUserDelete.show(getFragmentManager(), "dlgUserDelete");
                    }
                    else {
                        //tvShowMessageForEdit.setVisibility(v.getVisibility());
                    }
                    break;
                case (R.id.btn_user_edit) :
                    if(userPosition>=0) {
                        userEdit.setBackgroundResource(R.drawable.maintance_button_pressed);
                    fragmentSubCreateUser = new SubFragmentCreateUser();
                    Bundle bundle = new Bundle();
                    bundle.putInt("UserPosition", userPosition);
                    fragmentSubCreateUser.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubCreateUser).commit();
                    }
                    else {
                       // tvShowMessageForEdit.setVisibility(v.getVisibility());
                    }

                    break;
                case (R.id.btn_create_user) :
                        addUser.setBackgroundResource(R.drawable.maintance_button_pressed);
                        fragmentSubCreateUser = new SubFragmentCreateUser();
                        getFragmentManager().beginTransaction().replace(R.id.camera_create_menu_frame, fragmentSubCreateUser).commit();
                    break;
            }

        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Integer ID = userList.get(userPosition).id;
            objDB = new DBHelper(getActivity().getApplicationContext());
            objDB.deleteUserSettingByUserId(ID);
            objDB.deleteUserCameraByUserID(ID);
            objDB.deleteUser(ID);
            userList.remove(userPosition);
            userDataList.remove(userPosition);
            userDataAdapter.notifyDataSetChanged();
        }
    }
    class UserData{
        String userID;
        String username;
        String password;
        public UserData(String userId,String userName,String Password)
        {
            userID=userId;
            username=userName;
            password =Password;
        }
        public UserData()
        {

        }
    }
    class UserDataAdapter extends BaseAdapter {
        private int mSelectedPosition = -1;
        ToggleButton mSelectedRB;
        ArrayList<UserData> userItems;
        Context context;
        UserDataAdapter(Context context, ArrayList<UserData> userItems) {
            this.userItems = userItems;
            this.context = context;
        }

        @Override
        public int getCount() {
            return userItems.size();
        }

        @Override
        public UserData getItem(int position) {
            return userItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            // RadioButton rbCameraName;
            TextView tv_no,tv_username,tv_pass;

            ViewHolder(View v) {
                tv_no = (TextView) v.findViewById(R.id.tv_no);
                tv_username = (TextView) v.findViewById(R.id.tv_username);
                tv_pass = (TextView) v.findViewById(R.id.tv_pass);
                tv_no.setTypeface(Graphik_Regular);
                tv_username.setTypeface(Graphik_Regular);
                tv_pass.setTypeface(Graphik_Regular);
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
                    row = inflater.inflate(R.layout.all_user_list_admin, parent, false);
                    holder = new ViewHolder(row);

                    row.setTag(holder);

                } else {
                    holder = (ViewHolder) row.getTag();
                }
                UserData userData = userItems.get(position);


                holder.tv_no.setText((String.valueOf(position+1)));
                holder.tv_username.setText(userData.username);
                holder.tv_pass.setText(userData.password);
            } catch (Exception ex) {
                String s = ex.getMessage();
            }
            return row;
        }
    }

}
