package com.example.sanhak;

import android.app.AppComponentFactory;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class page3Activity extends AppCompatActivity {


    String janggi;
    String dangi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        //장기 예보

        String region = (String) getIntent().getStringExtra("region");

        String service_key = "TBK8Bq75wKqViN5AH0Hzxl25kH3AbT8G5ps96GKkAg%2Fw63QDxpysaPMLy8Gc3r4nOD3MCn%2Bn0dl0I2wON9ZwSQ%3D%3D";
        String num_of_rows = "10";
        String page_no = "1";
        String data_type = "XML";
        String stnId = "11B00000";
        String tmFc = "202111211800";

        String url_janggi = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?"+
                "serviceKey="+service_key+
                "&numOfRows="+num_of_rows+
                "&pageNo="+page_no+
                "&regId="+stnId+
                "&tmFc="+tmFc;

        // 단기 예보



        String service_key_dan = "TBK8Bq75wKqViN5AH0Hzxl25kH3AbT8G5ps96GKkAg/w63QDxpysaPMLy8Gc3r4nOD3MCn+n0dl0I2wON9ZwSQ==";
        String num_of_rows_dan = "10";
        String page_no_dan = "1";
        String data_type_dan = "XML";
        String regId_dan = "11C10301";

        String url_dangi = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0?"+
                "serviceKey="+service_key_dan+
                "&numOfRows="+num_of_rows_dan+
                "&pageNo="+page_no_dan+
                "&regId="+regId_dan;


        NetworkTask_jang networktask_janggi = new NetworkTask_jang(url_janggi, null);
        networktask_janggi.execute();

        NetworkTask_dan networktask_dan = new NetworkTask_dan(url_dangi, null);
        networktask_dan.execute();

    }
    public class RequsetHttpConnection {
        public String request(String _url, ContentValues _params) {
            HttpURLConnection urlConn = null;
            StringBuffer sbParmas = new StringBuffer();

            if (_params == null)
                sbParmas.append("");
            else {
                boolean isAnd = false;

                String key;
                String value;

                for (Map.Entry<String, Object> parameter : _params.valueSet()) {
                    key = parameter.getKey();
                    value = parameter.getValue().toString();

                    if (isAnd)
                        sbParmas.append("&");

                    sbParmas.append(key).append("=").append(value);

                    if (!isAnd)
                        if (_params.size() >= 2)
                            isAnd = true;
                }
            }

            try {
                URL url = new URL(_url);
                urlConn = (HttpURLConnection) url.openConnection();

                urlConn.setRequestMethod("GET");
                urlConn.setRequestProperty("Accept-Charset", "UTF-8");
                urlConn.setDoOutput(false);

                if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.d("TAG", "API 연결 요청 실패");
                    return null;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                String line;
                String page = "";

                while ((line = reader.readLine()) != null){
                    page += line;
                }

                return page;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(urlConn != null)
                    urlConn.disconnect();
            }

            return null;
        }
    }


    public class NetworkTask_jang extends AsyncTask<Void, Void, String>{
        private String url;
        private ContentValues values;

        public NetworkTask_jang(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {

            String result;
            RequsetHttpConnection requestHttpConnection = new RequsetHttpConnection();
            result = requestHttpConnection.request(url,values);
            janggi = result;
            System.out.println(result);

            return result;
        }

        @Override
        protected  void onPostExecute(String s){
            super.onPostExecute(s);

            janggi = s;
        }
    }


    public class NetworkTask_dan extends AsyncTask<Void, Void, String>{
        private String url;
        private ContentValues values;

        public NetworkTask_dan(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {

            String result;
            RequsetHttpConnection requestHttpConnection = new RequsetHttpConnection();
            result = requestHttpConnection.request(url,values);
            dangi = result;
            System.out.println(result);

            return result;
        }

        @Override
        protected  void onPostExecute(String s){
            super.onPostExecute(s);

            dangi = s;
        }
    }
}
