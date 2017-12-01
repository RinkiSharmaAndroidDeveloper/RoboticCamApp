package com.emtcontrols.cam.configuration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Vikas Monga on 6/2/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Robotics.db";

    //Camera table
    public static final String Camera_TABLE_NAME = "camera";
    public static final String Camera_COLUMN_ID = "id";
    public static final String Camera_COLUMN_NAME = "name";
    public static final String Camera_COLUMN_Path = "path";
    public static final String Camera_COLUMN_Elevation = "elevation";
    public static final String Camera_COLUMN_Predestal = "predestal";




    //UserLoginHistory Table

    public static final String UserLoginHistory_TABLE_NAME = "userloginhistory";
    public static final String UserLoginHistory_COLUMN_ID = "id";
    public static final String UserLoginHistory_COLUMN_USERID = "userid";
    public static final String UserLoginHistory_COLUMN_LOGINDATETIME = "logintime";
    public static final String UserLoginHistory_COLUMN_LOGOUTDATETIME = "logouttime";

    //User Table

    public static final String User_TABLE_NAME = "user";
    public static final String User_COLUMN_ID = "id";
    public static final String User_COLUMN_NAME = "name";
    public static final String User_COLUMN_PASSWORD = "password";
    public static final String User_COLUMN_ISADMIN = "isadmin";

    //UserCamera Table

    public static final String UserCamera_TABLE_NAME = "usercamera";
    public static final String UserCamera_COLUMN_ID = "id";
    public static final String UserCamera_COLUMN_USERID = "userid";
    public static final String UserCamera_COLUMN_CAMERAID = "cameraid";
    public static final String UserCamera_COLUMN_ISACTIVE = "isactive";

    //User Setting table

    public static final String UserSetting_TABLE_NAME = "usersetting";
    public static final String UserSetting_COLUMN_ID = "id";
    public static final String UserSetting_COLUMN_UserID = "userid";
    public static final String UserSetting_COLUMN_BackgroundColor = "backgroundcolor";
    public static final String UserSetting_COLUMN_Brightness = "brightness";
    public static final String UserSetting_COLUMN_TextSize = "textsize";





    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table camera " +
                        "(id integer primary key, name text,path text,elevation text,predestal text)"
        );

        db.execSQL(
                "create table user " +
                        "(id integer primary key, name text,password text, isadmin integer)"
        );
        db.execSQL(
                "create table userloginhistory " +
                        "(id integer primary key, userid integer,logintime text, logouttime text)"
        );
        db.execSQL(
                "create table usercamera " +
                        "(id integer primary key, userid integer,cameraid integer,isactive integer)"
        );

        db.execSQL(
                "create table usersetting " +
                        "(id integer primary key, userid integer,backgroundcolor text,brightness text,textsize text)"
        );

        //Create admin user for application

        db.execSQL(
                "Insert into user (name,password,isadmin) values ('Administrator','BCSTechnologies',1)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS camera");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS userloginhistory");
        db.execSQL("DROP TABLE IF EXISTS usercamera");
        db.execSQL("DROP TABLE IF EXISTS usersetting");
        onCreate(db);
    }

    //Camera CRUD

    public boolean insertCamera(String name, String path,String elevation,String predestal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("path", path);
        contentValues.put("elevation", elevation);
        contentValues.put("predestal", predestal);
        db.insert("camera", null, contentValues);
        return true;
    }


    public Camera getCameraByID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from camera where id=" + id + "", null);
        res.moveToFirst();
        Camera objCamera = new Camera(res.getInt(res.getColumnIndex(Camera_COLUMN_ID)), res.getString(res.getColumnIndex(Camera_COLUMN_NAME)), res.getString(res.getColumnIndex(Camera_COLUMN_Path)),res.getString(res.getColumnIndex(Camera_COLUMN_Elevation)),res.getString(res.getColumnIndex(Camera_COLUMN_Predestal)));
        return objCamera;
    }


    public boolean updateCamera(Integer id, String name, String path,String elevation,String predestal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("path", path);
        contentValues.put("elevation", elevation);
        contentValues.put("predestal", predestal);
        db.update("camera", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteCamera(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("camera",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<Camera> getAllCameras() {
        ArrayList<Camera> array_list = new ArrayList<Camera>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from camera", null);
        res.moveToFirst();

        Camera objCamera;
        while (res.isAfterLast() == false) {
            objCamera = new Camera(res.getInt(res.getColumnIndex(Camera_COLUMN_ID)), res.getString(res.getColumnIndex(Camera_COLUMN_NAME)), res.getString(res.getColumnIndex(Camera_COLUMN_Path)),res.getString(res.getColumnIndex(Camera_COLUMN_Elevation)),res.getString(res.getColumnIndex(Camera_COLUMN_Predestal)));
            array_list.add(objCamera);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean validateCameraName(int cameraID,String cameraName ) {
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String query="";
        if(cameraID>0)
            query="select * from camera Where id != "+cameraID+" and name='"+cameraName+"'";
        else
            query="select * from camera Where name='"+cameraName+"'";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();
        if(res!=null && res.getCount()>0)
            return false;
        if( res.getCount()<=0)
            return true;
        return false;
    }

    // userloginlog Crud

    public boolean insertUserloginhistory(int userid, String logindate,String logouttime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("logintime", logindate);
        contentValues.put("logouttime", logouttime);
        db.insert("userloginhistory", null, contentValues);
        return true;
    }
    public UserLoginHistory getUserloginhistory(int userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from userloginhistory where userid=" + userid + " order by 1 desc limit 1", null);
        res.moveToFirst();
        UserLoginHistory objUserLoginHistory = new UserLoginHistory(res.getInt(res.getColumnIndex(UserLoginHistory_COLUMN_ID)),res.getInt(res.getColumnIndex(UserLoginHistory_COLUMN_USERID)), res.getString(res.getColumnIndex(UserLoginHistory_COLUMN_LOGINDATETIME)), res.getString(res.getColumnIndex(UserLoginHistory_COLUMN_LOGOUTDATETIME)));
        return objUserLoginHistory;
    }

    public ArrayList<UserLoginHistory> getUserloginhistories(int userid, int limit) {
        ArrayList<UserLoginHistory> array_list = new ArrayList<UserLoginHistory>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from userloginhistory where userid=" + userid + " order by 1 desc limit " + limit, null);
        res.moveToFirst();
        UserLoginHistory objUserLoginHistory;
        while (res.isAfterLast() == false) {
            objUserLoginHistory = new UserLoginHistory(res.getInt(res.getColumnIndex(UserLoginHistory_COLUMN_ID)),res.getInt(res.getColumnIndex(UserLoginHistory_COLUMN_USERID)), res.getString(res.getColumnIndex(UserLoginHistory_COLUMN_LOGINDATETIME)), res.getString(res.getColumnIndex(UserLoginHistory_COLUMN_LOGOUTDATETIME)));
            array_list.add(objUserLoginHistory);
            res.moveToNext();
        }
        return array_list;
    }


    public boolean updateUserLoginHistory(Integer userid,String logouttime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("logouttime", logouttime);
        Cursor res = db.rawQuery("select * from userloginhistory where userid=" + userid + " order by 1 desc limit 1", null);
        res.moveToFirst();
        int id= res.getInt(res.getColumnIndex(UserLoginHistory_COLUMN_ID));
        db.update("userloginhistory", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }


    //user CRUD

    public Integer insertUser(String name, String password, Integer isadmin) {
        int lastId = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("isadmin", isadmin);
        db.insert("user", null, contentValues);
        /*String query = "insert into user(name,password,isadmin) values('" + name+"','"+ password +"',"+ isadmin+")";
        Cursor res = db.rawQuery(query,null);*/
        Cursor res = db.rawQuery("select id from user order by id desc limit 1 ", null);
        if (res != null && res.moveToFirst()) {
            lastId = res.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        return lastId ;
    }

    public User getUserData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user where id=" + id + "", null);
        res.moveToFirst();
        User objUser = new User(res.getInt(res.getColumnIndex(User_COLUMN_ID)), res.getString(res.getColumnIndex(User_COLUMN_NAME)), res.getString(res.getColumnIndex(User_COLUMN_PASSWORD)), res.getInt(res.getColumnIndex(User_COLUMN_ISADMIN)));

        return objUser;
    }
    public User validateUser(String username,String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user where name='" + username + "' and password='" + password + "'", null);
        if(res!=null && res.getCount()>0) {
            res.moveToFirst();
            User objUser = new User(res.getInt(res.getColumnIndex(User_COLUMN_ID)), res.getString(res.getColumnIndex(User_COLUMN_NAME)), res.getString(res.getColumnIndex(User_COLUMN_PASSWORD)), res.getInt(res.getColumnIndex(User_COLUMN_ISADMIN)));
            return objUser;
        }

        return null;
    }

    public int usernumberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, User_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser(Integer id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        db.update("user", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean updateUserPassword(Integer id, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        db.update("user", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteUser(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> array_list = new ArrayList<User>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user where isadmin="+ 0 , null);
        res.moveToFirst();

        User objUser;
        while (res.isAfterLast() == false) {
            objUser = new User(res.getInt(res.getColumnIndex(User_COLUMN_ID)), res.getString(res.getColumnIndex(User_COLUMN_NAME)), res.getString(res.getColumnIndex(User_COLUMN_PASSWORD)), res.getInt(res.getColumnIndex(User_COLUMN_ISADMIN)));
            array_list.add(objUser);
            res.moveToNext();
        }


        return array_list;
    }

    //CRUD UserCamera

    public boolean insertUserCamera(Integer userid, Integer cameraid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("cameraid", cameraid);
        contentValues.put("isactive", 1);
        db.insert("usercamera", null, contentValues);
        return true;
    }

    public UserCamera getUserCameraData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from usercamera where id=" + id + "", null);

        UserCamera objUserCamera = new UserCamera(res.getInt(res.getColumnIndex(UserCamera_COLUMN_ID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_USERID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_CAMERAID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_ISACTIVE)));

        return objUserCamera;
    }


    public boolean updateUserCamera(Integer id, Integer userid, Integer cameraid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("cameraid", cameraid);
        db.update("usercamera", contentValues, "id = ? ", new String[]{Integer.toString(id)});



        return true;
    }
    public boolean setstateUserCamera(Integer userid, Integer cameraid,Integer isactive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isactive", isactive);
        db.update("usercamera", contentValues, "userid='"+ userid+"' and cameraid='"+ cameraid+"'",null);
        return true;
    }


    public Integer deleteUserCameraByUserID(Integer userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("usercamera",
                "userid = ? ",
                new String[]{Integer.toString(userid)});
    }
    public Integer deleteUserCamera(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("usercamera",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<UserCamera> getAllUserCameraByUserID(Integer userid) {
        ArrayList<UserCamera> array_list = new ArrayList<UserCamera>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from usercamera where userid=" + userid, null);
        res.moveToFirst();


        UserCamera objUserCamera;
        while (res.isAfterLast() == false) {
            objUserCamera = new UserCamera(res.getInt(res.getColumnIndex(UserCamera_COLUMN_ID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_USERID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_CAMERAID)),res.getInt(res.getColumnIndex(UserCamera_COLUMN_ISACTIVE)));
            array_list.add(objUserCamera);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<UserCamera> getAllActiveUserCameraByUserID(Integer userid) {
        ArrayList<UserCamera> array_list = new ArrayList<UserCamera>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from usercamera where isactive=1 and userid=" + userid, null);
        res.moveToFirst();


        UserCamera objUserCamera;
        while (res.isAfterLast() == false) {
            objUserCamera = new UserCamera(res.getInt(res.getColumnIndex(UserCamera_COLUMN_ID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_USERID)), res.getInt(res.getColumnIndex(UserCamera_COLUMN_CAMERAID)),res.getInt(res.getColumnIndex(UserCamera_COLUMN_ISACTIVE)));
            array_list.add(objUserCamera);
            res.moveToNext();
        }
        return array_list;
    }



    public ArrayList<Camera> getCameraByUserID(Integer userid) {
        ArrayList<Camera> array_list = new ArrayList<Camera>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from camera where id in (select cameraid from usercamera where  userid=" + userid + ")", null);
        res.moveToFirst();


        Camera camera;
        while (res.isAfterLast() == false) {
            camera = new Camera(res.getInt(res.getColumnIndex(Camera_COLUMN_ID)), res.getString(res.getColumnIndex(Camera_COLUMN_NAME)), res.getString(res.getColumnIndex(Camera_COLUMN_Path)), res.getString(res.getColumnIndex(Camera_COLUMN_Elevation)), res.getString(res.getColumnIndex(Camera_COLUMN_Predestal)));
            array_list.add(camera);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Camera> getActiveCameraByUserID(Integer userid) {
        ArrayList<Camera> array_list = new ArrayList<Camera>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from camera where id in (select cameraid from usercamera where  userid=" + userid + " and isactive="+1+")", null);
        res.moveToFirst();


        Camera camera;
        while (res.isAfterLast() == false) {
            camera = new Camera(res.getInt(res.getColumnIndex(Camera_COLUMN_ID)), res.getString(res.getColumnIndex(Camera_COLUMN_NAME)), res.getString(res.getColumnIndex(Camera_COLUMN_Path)),res.getString(res.getColumnIndex(Camera_COLUMN_Elevation)),res.getString(res.getColumnIndex(Camera_COLUMN_Predestal)));
            array_list.add(camera);
            res.moveToNext();
        }
        return array_list;
    }

    //User Setting CRUD



    public boolean insertUserSetting(int userid,String backgroundcolor,String brightness,String textsize) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid", userid);
        contentValues.put("backgroundcolor", backgroundcolor);
        contentValues.put("brightness", brightness);
        contentValues.put("textsize", textsize);
        db.insert("usersetting", null, contentValues);
        return true;
    }

    public UserSetting getUserSettingByUserId(int userid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from usersetting where userid=" + userid + "", null);
        res.moveToFirst();
        UserSetting objUserSetting = new UserSetting(res.getInt(res.getColumnIndex(UserSetting_COLUMN_ID)),res.getInt(res.getColumnIndex(UserSetting_COLUMN_UserID)), res.getString(res.getColumnIndex(UserSetting_COLUMN_BackgroundColor)), res.getString(res.getColumnIndex(UserSetting_COLUMN_Brightness)),res.getString(res.getColumnIndex(UserSetting_COLUMN_TextSize)));
        return objUserSetting;
    }


    public boolean updateUserSettingByUserId(Integer userid, String Key, String Value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Key, Value);
        db.update("usersetting", contentValues, "userid = ? ", new String[]{Integer.toString(userid)});
        return true;
    }

    public Integer deleteUserSettingByUserId(Integer userid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("usersetting",
                "userid = ? ",
                new String[]{Integer.toString(userid)});
    }


    public class Camera {
        public Integer id;
        public String name;
        public String path;
        public String elevation;
        public String predestal;

        public Camera(Integer id, String name, String path, String elevation, String predestal) {
            this.id = id;
            this.name = name;
            this.path = path;
            this.elevation = elevation;
            this.predestal = predestal;
        }
    }


    public class UserSetting {
        public Integer id;
        public Integer userid;
        public String backgroundcolor;
        public String brightness;
        public String textsize;

        public UserSetting(Integer id,Integer userid, String backgroundcolor, String brightness,String textsize) {
            this.id = id;
            this.userid = userid;
            this.backgroundcolor = backgroundcolor;
            this.brightness = brightness;
            this.textsize = textsize;
        }
    }



    public class UserLoginHistory {
        public Integer id;
        public Integer user;
        public String logintgime;
        public String logoutime;


        public UserLoginHistory(Integer id,Integer userID, String logintgime, String logoutime) {
            this.id = id;
            this.id = userID;
            this.logintgime = logintgime;
            this.logoutime = logoutime;
        }
    }


    public class User {
        public Integer id;
        public String name;
        public String password;
        public Integer isadmin;

        public User(Integer id, String name, String password, Integer isadmin) {
            this.id = id;
            this.name = name;
            this.password = password;
            this.isadmin = isadmin;

        }
    }

    public class UserCamera {
        public Integer id;
        public Integer userid;
        public Integer cameraid;
        public Integer isactive;

        public UserCamera(Integer id, Integer userid, Integer cameraid,Integer isactive) {
            this.id = id;
            this.userid = userid;
            this.cameraid = cameraid;
            this.isactive = isactive;
        }
    }
}
