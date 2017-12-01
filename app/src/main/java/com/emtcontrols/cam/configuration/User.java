package com.emtcontrols.cam.configuration;

import java.util.ArrayList;

public class User {

    private String userLogin;
    private String userPassword;
    private int backgroundColor;
    private int brightness;
    private int textSize;
    private int cameraLayout;
    private final int CAM_QUANTITY = 12; //Quantity of cameras in system

    ArrayList<String>  historyList = new ArrayList<>();
    ArrayList<Camera> camListParam;
    //boolean[] camListAccess = new boolean[] {true, true, true, true, true, true, true, true, true, true, true, true};

    public User(String userName, String password, ArrayList<Camera> camListParam) {
        this.userLogin = userName;
        this.userPassword = password;
        this.camListParam = camListParam;
        //this.camListAccess = camListAccess;
    }

    public String getUserLogin() {
        return userLogin;
    }
    public String getUserPassword() {
        return userPassword;
    }

    public boolean isMyLogin(String login, String password){

        if(userLogin.equals(login) && userPassword.equals(password))
            return true;
        else
            return false;
    }

    public void addTimeToHistory(String time, String date){

        if(time.length()!=0 && date.length()!=0)
            historyList.add(time + " " + date);
    }

    public String getLastTime(){

        String data = "";

        try {

            data = historyList.get(historyList.size()-1);

            data.indexOf(" ");



        } catch (Exception e){

            data = "ex";

        }

        return data;
    }

    String getAllTimeHistory(){

        return "soon";
    }

    public void getCurrendTime(){

    }

    /*
    public boolean[] getCamListAccess() {
        return camListAccess;
    }
    */

    public ArrayList<Camera> getCamListParam() {

        camListParam = new ArrayList<>(CAM_QUANTITY);
        Camera camParam = new Camera();

        for (int i = 0; i < CAM_QUANTITY; i++ ) {


            camListParam.add(i, camParam);
        }
        return camListParam;
    }
}
