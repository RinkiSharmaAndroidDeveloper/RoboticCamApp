package com.emtcontrols.cam.configuration;

public class Camera {

    private boolean avaliable = true;
    private int focus = 50;
    private int zoom = 50;
    private int sensitivity = 50;


//    public Camera(boolean avaliable, int focus, int zoom, int sensitivity) {
//
//        this.avaliable = avaliable;
//        this.focus = focus;
//        this.zoom = zoom;
//        this.sensitivity = sensitivity;
//
//    }


    //setters
    public void setAvaliable(boolean avaliable) {
        this.avaliable = avaliable;
    }


    //getters
    public boolean isAvaliable() {
        return avaliable;
    }

    public int getFocus() {
        return focus;
    }

    public int getZoom() {
        return zoom;
    }

    public int getSensitivity() {
        return sensitivity;
    }
}
