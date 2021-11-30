package com.example.sanhakcommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Socket[] socket = new Socket[1];


        final DataOutputStream[] dos = new DataOutputStream[1];
        final DataInputStream[] dis = new DataInputStream[1];

        String ip = "192.168.22.33";            // IP 번호
        int port = 9999;


        Thread checkUpdate = new Thread() {
            public void run() {
                // 서버 접속
                try {
                    socket[0] = new Socket(ip, port);
                    Log.w("서버 접속됨", "서버 접속됨");

                } catch (IOException e1) {
                    Log.w("서버접속못함", "서버접속못함");
                    e1.printStackTrace();
                }
                Log.w("edit 넘어가야 할 값 : ", "안드로이드에서 서버로 연결요청");
                try {
                    dos[0] = new DataOutputStream(socket[0].getOutputStream());   // output에 보낼꺼 넣음
                    dis[0] = new DataInputStream(socket[0].getInputStream());     // input에 받을꺼 넣어짐
                    dos[0].writeUTF("안드로이드에서 서버로 연결요청");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼", "버퍼생성 잘못됨");
                }
                Log.w("버퍼", "버퍼생성 잘됨");
                try {
                    Log.d("while", "실행");
                    int rev = (int) dis[0].read();
                    Log.d("rev", "값 받음");


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("오류", "데이터 안옴");
                }
            }
        };
        checkUpdate.start();

    }
}