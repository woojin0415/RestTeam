package com.example.sanhak;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class page2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        String[] Region = {"-지역 선택-","서울", "경기", "인천", "충청북도"};
        String[] Region_Seoul={"-세부 지역 선택-", "강서구", "양천구", "구로구","영등포구","금천구","관악구","동작구","서초구"
        , "강남구", "송파구", "강동구", "흥신구","마포구","서대문구","종로구","중구","성동구","광진구","중랑구","동대문구","서대문구",
        "은평구","성북구","강북구","도봉구","노원구"};
        String[] Region_Chungbuk={"단양군","제천시","충주시","음성군","진천군","증평군","괴산군","청주시","보은군","옥천군","영동군"};
        
        
        
        
        Spinner sp1 = (Spinner) findViewById(R.id.region);
        Spinner selectbox_detail = (Spinner) findViewById(R.id.region_detail);
        
        
        
        ArrayAdapter<String> adapter_seoul = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Region_Seoul);
        adapter_seoul.setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter<String> adapter_chungbuk = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Region_Chungbuk);
        adapter_chungbuk.setDropDownViewResource(android.R.layout.simple_spinner_item);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Region);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sp1.getSelectedItem().toString() == "서울"){
                    selectbox_detail.setAdapter(adapter_seoul);
                }
                else if(sp1.getSelectedItem().toString() == "충청북도"){
                    selectbox_detail.setAdapter(adapter_chungbuk);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

}
