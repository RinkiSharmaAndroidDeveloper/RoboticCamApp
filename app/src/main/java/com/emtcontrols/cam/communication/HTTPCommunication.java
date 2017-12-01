package com.emtcontrols.cam.communication;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.params.BasicHttpParams;
        import org.apache.http.params.HttpConnectionParams;
        import org.apache.http.util.EntityUtils;
        import org.json.JSONArray;
        import org.json.JSONObject;
        import android.net.Uri;

        import java.net.URL;

        import javax.net.ssl.HttpsURLConnection;

public class HTTPCommunication {

    public enum COMMANDS
    { SEND, READ
    };

    private BasicHttpParams httpParameters;
    private Uri.Builder builder;

    HTTPListener httpCallBack;

    public interface HTTPListener {
        public void onNewData(String data);
    }

    public HTTPCommunication( ) {

        httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        HttpConnectionParams.setSoTimeout(httpParameters, 3000);
    }

    public void setHttpCallBack(HTTPListener httpCallBack) {
        this.httpCallBack = httpCallBack;
    }

    public String command(COMMANDS com, String data_1, String data_2, String data_3, Object data) {

        String dataReturn = "";

        try {

            builder = Uri.parse("http://" + data_1).buildUpon();
            builder.appendPath("camera").appendPath("headController").appendPath("joystickControl");

            switch (com) {

                case SEND:

                    JSONObject outData = (JSONObject) data;
                    String dataOut = outData.toString();
                    builder.appendQueryParameter("command", "joystick_wr");
                    builder.appendQueryParameter("data", dataOut);
                    dataOut = builder.toString();

                    ll_sendToServerThread(dataOut);
                    break;

                case READ:

                    JSONArray jsArray = (JSONArray) data;
                    String dataIn = jsArray.toString();
                    builder.appendQueryParameter("operation", "read_regs");
                    builder.appendQueryParameter("data", dataIn);
                    dataIn = builder.toString();

                    ll_askServerThread(dataIn);
                    break;
            }

        } catch (Exception e) {
        }

        return dataReturn;
    }

    private void ll_sendToServerThread(final String dataRead) {

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                    HttpPost httpPost = new HttpPost(dataRead);
                    HttpResponse response =  httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                } catch (Exception e) {
                    String str=e.getMessage();

                }

            }
        });

        t.setName("sendRegs");
        t.setDaemon(true);
        t.start();
    }

    private void ll_askServerThread(final String dataWrite) {

        Thread t = new Thread(new Runnable() {
            public void run() {

                try {

                    DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                    HttpPost httpPost = new HttpPost(dataWrite); // "http://192.168.1.10/appLink/json.txt"

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    String xmlStr = EntityUtils.toString(httpEntity);

                    httpCallBack.onNewData(xmlStr);

                } catch (Exception e) {
                }
            }
        });

        t.setName("askNewRegs");
        t.start();
    }
}

