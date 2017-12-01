package com.emtcontrols.cam.fragments;

import com.emtcontrols.cam.communication.HTTPCommunication;
import com.emtcontrols.cam.configuration.DBHelper;
import com.emtcontrols.cam.configuration.User;

import java.util.ArrayList;

public interface FragmentCallBack {


    abstract public void startAutorizationDialog();
    abstract public String getLastLoginOfuser(Integer userID);
    void checkAutorizationInformation(DBHelper.User user);

    abstract public void startAdminFirstSetupMenu(DBHelper.User user);
    abstract public void startAdminLogout();

    abstract public void startUserLogout();
    abstract public void setAdminScreen();

    abstract public void closeApplication();
    abstract public void setBrightness(String brightnes);
    abstract public void setBackgroundColor(String backgroundColor);
    abstract public void setBackgroundImage();
    abstract public void setBackgroundAdminImage();
    abstract ArrayList<User> getUserList();
    abstract User getCurrentUser();
    abstract Boolean getPanelLockStatus();
    abstract void setPanelLockStatus(Boolean isPanelLock);
    abstract String getPathOfCamera();
    abstract void setPathOfCamera(String PathOfCamera);
    abstract String getSelectedCameraName();
    abstract void setSelectedCameraName(String CameraName);
    abstract DBHelper.User getCurrentUserData();

    abstract String getSelectedCameraPreset();
    abstract void setSelectedCameraPreset(String CameraPreset);
    abstract void setRememberme(String Username,String Password);
    abstract String getRememberme();

    abstract HTTPCommunication getHttpCommunicationObject();
}
