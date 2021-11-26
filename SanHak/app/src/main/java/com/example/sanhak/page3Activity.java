package com.example.sanhak;

import android.app.AppComponentFactory;
import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class page3Activity extends AppCompatActivity {


    String janggi;
    String dangi;
    long today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        String std_day = "2021:08:01";
        long diff_first;
        long diff_day;
        long week_first;
        long week_today;
        long today_month;
        try {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");

            String getTime = sdf.format(date);

            String year = getTime.substring(0, 4);
            String month = getTime.substring(5, 7);
            String day = getTime.substring(8, 10);

            today_month = Long.parseLong(month);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,0);
            String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

            String Year = Date.substring(0, 4);
            String Month = Date.substring(5, 7);
            String Day = Date.substring(8, 10);

            Date std_date = sdf.parse(std_day);
            diff_day = date.getTime() - std_date.getTime();
            diff_day = diff_day / (24 * 60 * 60 * 1000);
            diff_day = Math.abs(diff_day);

            String date_first = Year+":"+Month+":"+"01";
            Date first_date = sdf.parse(date_first);
            diff_first = first_date.getTime() - std_date.getTime();
            diff_first = diff_first / (24 * 60 * 60 * 1000);
            diff_first = Math.abs(diff_first);

            today = diff_first%7 + diff_day - diff_first;

            System.out.println(today);

            String APItime = Year + Month + Day + "0600";

            //장기 예보

            String region = (String) getIntent().getStringExtra("region");

            String service_key = "TBK8Bq75wKqViN5AH0Hzxl25kH3AbT8G5ps96GKkAg%2Fw63QDxpysaPMLy8Gc3r4nOD3MCn%2Bn0dl0I2wON9ZwSQ%3D%3D";
            String num_of_rows = "10";
            String page_no = "1";
            String data_type = "JSON";
            String stnId = "11B00000";
            String tmFc = APItime;

            String url_janggi = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?" +
                    "serviceKey=" + service_key +
                    "&numOfRows=" + num_of_rows +
                    "&pageNo=" + page_no +
                    "&dataType=" + data_type +
                    "&regId=" + stnId +
                    "&tmFc=" + tmFc;

            NetworkTask_jang networktask_janggi = new NetworkTask_jang(url_janggi, null);
            networktask_janggi.execute();

            // 단기 예보



        /*String service_key_dan = "TBK8Bq75wKqViN5AH0Hzxl25kH3AbT8G5ps96GKkAg%2Fw63QDxpysaPMLy8Gc3r4nOD3MCn%2Bn0dl0I2wON9ZwSQ%3D%3D";
        String num_of_rows_dan = "10";
        String page_no_dan = "1";
        String data_type_dan = "XML";
        String regId_dan = "11C10301";

        String url_dangi = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0?"+
                "serviceKey="+service_key_dan+
                "&numOfRows="+num_of_rows_dan+
                "&pageNo="+page_no_dan+
                "&regId="+regId_dan;*/

        /*NetworkTask_dan networktask_dan = new NetworkTask_dan(url_dangi, null);
        networktask_dan.execute();*/

        } catch (ParseException e) {
            e.printStackTrace();
        }


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

                while ((line = reader.readLine()) != null) {
                    page += line;
                }

                return page;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConn != null)
                    urlConn.disconnect();
            }

            return null;
        }
    }


    public class NetworkTask_jang extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask_jang(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {

            String result;
            String response = "";
            String body = "";
            String items = "";
            String item = "";
            String wf3Pm = "";
            String wf4Pm = "";
            String wf5Pm = "";
            String wf6Pm = "";
            String wf7Pm = "";
            String wf8 = "";
            String wf9 = "";
            String wf10 = "";

            ImageView iv0 = findViewById(R.id.c0);  ImageView iv1 = findViewById(R.id.c1);
            ImageView iv2 = findViewById(R.id.c2);  ImageView iv3 = findViewById(R.id.c3);
            ImageView iv4 = findViewById(R.id.c4);  ImageView iv5 = findViewById(R.id.c5);
            ImageView iv6 = findViewById(R.id.c6);  ImageView iv7 = findViewById(R.id.c7);
            ImageView iv8 = findViewById(R.id.c8);  ImageView iv9 = findViewById(R.id.c9);
            ImageView iv10 = findViewById(R.id.c10);  ImageView iv11 = findViewById(R.id.c11);
            ImageView iv12 = findViewById(R.id.c12);  ImageView iv13 = findViewById(R.id.c13);
            ImageView iv14 = findViewById(R.id.c14);  ImageView iv15 = findViewById(R.id.c15);
            ImageView iv16 = findViewById(R.id.c16);  ImageView iv17 = findViewById(R.id.c17);
            ImageView iv18 = findViewById(R.id.c18);  ImageView iv19 = findViewById(R.id.c19);
            ImageView iv20 = findViewById(R.id.c20);  ImageView iv21 = findViewById(R.id.c21);
            ImageView iv22 = findViewById(R.id.c22);  ImageView iv23 = findViewById(R.id.c23);
            ImageView iv24 = findViewById(R.id.c24);  ImageView iv25 = findViewById(R.id.c25);
            ImageView iv26 = findViewById(R.id.c26);  ImageView iv27 = findViewById(R.id.c27);
            ImageView iv28 = findViewById(R.id.c28);  ImageView iv29 = findViewById(R.id.c29);
            ImageView iv30 = findViewById(R.id.c30);  ImageView iv31 = findViewById(R.id.c31);
            ImageView iv32 = findViewById(R.id.c32);  ImageView iv33 = findViewById(R.id.c33);
            ImageView iv32 = findViewById(R.id.c34);
            
            


            RequsetHttpConnection requestHttpConnection = new RequsetHttpConnection();
            result = requestHttpConnection.request(url, values);
            System.out.println(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                response = jsonObject.getString("response");

                JSONObject jsonObject_body = new JSONObject(response);
                body = jsonObject_body.getString("body");

                JSONObject jsonObject_items = new JSONObject(body);
                items = jsonObject_items.getString("items");

                JSONObject jsonObject_item = new JSONObject(items);
                item = jsonObject_item.getString("item");

                JSONArray jsonArray = new JSONArray(item);

                JSONObject jsonObject_wf = jsonArray.getJSONObject(0);
                wf3Pm = jsonObject_wf.getString("wf3Pm");
                wf4Pm = jsonObject_wf.getString("wf4Pm");
                wf5Pm = jsonObject_wf.getString("wf5Pm");
                wf6Pm = jsonObject_wf.getString("wf6Pm");
                wf7Pm = jsonObject_wf.getString("wf7Pm");
                wf8 = jsonObject_wf.getString("wf8");
                wf9 = jsonObject_wf.getString("wf9");
                wf10 = jsonObject_wf.getString("wf10");


            } catch (JSONException e) {
                e.printStackTrace();

            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public class NetworkTask_dan extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask_dan(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {

            String result;
            RequsetHttpConnection requestHttpConnection = new RequsetHttpConnection();
            result = requestHttpConnection.request(url, values);
            System.out.println(result.toString());

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dangi = s;
        }
    }
}
package com.example.sanhak;

import android.app.AppComponentFactory;
import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class page3Activity extends AppCompatActivity {


    String janggi;
    String dangi;
    long today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page3);

        String std_day = "2021:08:01";
        long diff_first;
        long diff_day;
        long week_first;
        long week_today;
        long today_month;
        try {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");

            String getTime = sdf.format(date);

            String year = getTime.substring(0, 4);
            String month = getTime.substring(5, 7);
            String day = getTime.substring(8, 10);

            today_month = Long.parseLong(month);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE,0);
            String Date = new java.text.SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

            String Year = Date.substring(0, 4);
            String Month = Date.substring(5, 7);
            String Day = Date.substring(8, 10);

            Date std_date = sdf.parse(std_day);
            diff_day = date.getTime() - std_date.getTime();
            diff_day = diff_day / (24 * 60 * 60 * 1000);
            diff_day = Math.abs(diff_day);

            String date_first = Year+":"+Month+":"+"01";
            Date first_date = sdf.parse(date_first);
            diff_first = first_date.getTime() - std_date.getTime();
            diff_first = diff_first / (24 * 60 * 60 * 1000);
            diff_first = Math.abs(diff_first);

            today = diff_first%7 + diff_day - diff_first;

            System.out.println(today);

            String APItime = Year + Month + Day + "0600";

            //장기 예보

            String region = (String) getIntent().getStringExtra("region");

            String service_key = "TBK8Bq75wKqViN5AH0Hzxl25kH3AbT8G5ps96GKkAg%2Fw63QDxpysaPMLy8Gc3r4nOD3MCn%2Bn0dl0I2wON9ZwSQ%3D%3D";
            String num_of_rows = "10";
            String page_no = "1";
            String data_type = "JSON";
            String stnId = "11B00000";
            String tmFc = APItime;

            String url_janggi = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?" +
                    "serviceKey=" + service_key +
                    "&numOfRows=" + num_of_rows +
                    "&pageNo=" + page_no +
                    "&dataType=" + data_type +
                    "&regId=" + stnId +
                    "&tmFc=" + tmFc;

            NetworkTask_jang networktask_janggi = new NetworkTask_jang(url_janggi, null);
            networktask_janggi.execute();

            // 단기 예보



        /*String service_key_dan = "TBK8Bq75wKqViN5AH0Hzxl25kH3AbT8G5ps96GKkAg%2Fw63QDxpysaPMLy8Gc3r4nOD3MCn%2Bn0dl0I2wON9ZwSQ%3D%3D";
        String num_of_rows_dan = "10";
        String page_no_dan = "1";
        String data_type_dan = "XML";
        String regId_dan = "11C10301";

        String url_dangi = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0?"+
                "serviceKey="+service_key_dan+
                "&numOfRows="+num_of_rows_dan+
                "&pageNo="+page_no_dan+
                "&regId="+regId_dan;*/

        /*NetworkTask_dan networktask_dan = new NetworkTask_dan(url_dangi, null);
        networktask_dan.execute();*/

        } catch (ParseException e) {
            e.printStackTrace();
        }


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

                while ((line = reader.readLine()) != null) {
                    page += line;
                }

                return page;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConn != null)
                    urlConn.disconnect();
            }

            return null;
        }
    }


    public class NetworkTask_jang extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask_jang(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {

            String result;
            String response = "";
            String body = "";
            String items = "";
            String item = "";
            String wf3Pm = "";
            String wf4Pm = "";
            String wf5Pm = "";
            String wf6Pm = "";
            String wf7Pm = "";
            String wf8 = "";
            String wf9 = "";
            String wf10 = "";

            ImageView iv0 = findViewById(R.id.c0);  ImageView iv1 = findViewById(R.id.c1);
            ImageView iv2 = findViewById(R.id.c2);  ImageView iv3 = findViewById(R.id.c3);
            ImageView iv4 = findViewById(R.id.c4);  ImageView iv5 = findViewById(R.id.c5);
            ImageView iv6 = findViewById(R.id.c6);  ImageView iv7 = findViewById(R.id.c7);
            ImageView iv8 = findViewById(R.id.c8);  ImageView iv9 = findViewById(R.id.c9);
            ImageView iv10 = findViewById(R.id.c10);  ImageView iv11 = findViewById(R.id.c11);
            ImageView iv12 = findViewById(R.id.c12);  ImageView iv13 = findViewById(R.id.c13);
            ImageView iv14 = findViewById(R.id.c14);  ImageView iv15 = findViewById(R.id.c15);
            ImageView iv16 = findViewById(R.id.c16);  ImageView iv17 = findViewById(R.id.c17);
            ImageView iv18 = findViewById(R.id.c18);  ImageView iv19 = findViewById(R.id.c19);
            ImageView iv20 = findViewById(R.id.c20);  ImageView iv21 = findViewById(R.id.c21);
            ImageView iv22 = findViewById(R.id.c22);  ImageView iv23 = findViewById(R.id.c23);
            ImageView iv24 = findViewById(R.id.c24);  ImageView iv25 = findViewById(R.id.c25);
            ImageView iv26 = findViewById(R.id.c26);  ImageView iv27 = findViewById(R.id.c27);
            ImageView iv28 = findViewById(R.id.c28);  ImageView iv29 = findViewById(R.id.c29);
            ImageView iv30 = findViewById(R.id.c30);  ImageView iv31 = findViewById(R.id.c31);
            ImageView iv32 = findViewById(R.id.c32);  ImageView iv33 = findViewById(R.id.c33);
            ImageView iv32 = findViewById(R.id.c34);
            
            


            RequsetHttpConnection requestHttpConnection = new RequsetHttpConnection();
            result = requestHttpConnection.request(url, values);
            System.out.println(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                response = jsonObject.getString("response");

                JSONObject jsonObject_body = new JSONObject(response);
                body = jsonObject_body.getString("body");

                JSONObject jsonObject_items = new JSONObject(body);
                items = jsonObject_items.getString("items");

                JSONObject jsonObject_item = new JSONObject(items);
                item = jsonObject_item.getString("item");

                JSONArray jsonArray = new JSONArray(item);

                JSONObject jsonObject_wf = jsonArray.getJSONObject(0);
                wf3Pm = jsonObject_wf.getString("wf3Pm");
                wf4Pm = jsonObject_wf.getString("wf4Pm");
                wf5Pm = jsonObject_wf.getString("wf5Pm");
                wf6Pm = jsonObject_wf.getString("wf6Pm");
                wf7Pm = jsonObject_wf.getString("wf7Pm");
                wf8 = jsonObject_wf.getString("wf8");
                wf9 = jsonObject_wf.getString("wf9");
                wf10 = jsonObject_wf.getString("wf10");


            } catch (JSONException e) {
                e.printStackTrace();

            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    public class NetworkTask_dan extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask_dan(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }


        @Override
        protected String doInBackground(Void... voids) {

            String result;
            RequsetHttpConnection requestHttpConnection = new RequsetHttpConnection();
            result = requestHttpConnection.request(url, values);
            System.out.println(result.toString());

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dangi = s;
        }
    }
}
