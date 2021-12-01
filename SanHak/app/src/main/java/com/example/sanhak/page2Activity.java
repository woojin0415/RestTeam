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

        String[] Region = {"-지역 선택-","서울 인천 경기", "부산 울산 경상남도", "대구 경상북도", "광주 전라남도", "전라북도", "대전 세종 충청남도",
        "충청북도", "강원도", "제주도"};
        String[] Region_Seoul={"-세부 지역 선택-", "서울","인천","수원","성남","안양","광명","과천","평택","오산","의왕","용인","군포","안성","화성",
        "양평","구리","남양주","하남","이천","여주","광주","의정부","고양","파주","양주","동두천","연천","포천","가평","강화","김포","시흥","부천","안산","백령도"};
        String[] Region_Busan={"-세부 지역 선택-","부산","울산","김해","양산","창원","밀양","함안","창녕","의령","진주","하동","사천","거창","합천","산청",
        "함양","통영","거제","고성","남해"};
        


        Spinner sp1 = (Spinner) findViewById(R.id.region);
        Spinner selectbox_detail = (Spinner) findViewById(R.id.region_detail);



        ArrayAdapter<String> adapter_seoul = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Region_Seoul);
        adapter_seoul.setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayAdapter<String> adapter_busan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Region_Busan);
        adapter_busan.setDropDownViewResource(android.R.layout.simple_spinner_item);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,Region);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp1.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(sp1.getSelectedItem().toString() == "서울 인천 경기"){
                    selectbox_detail.setAdapter(adapter_seoul);
                }
                else if(sp1.getSelectedItem().toString() == "부산 울산 경상남도"){
                    selectbox_detail.setAdapter(adapter_busan);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectRegion;
                selectRegion = selectbox_detail.getSelectedItem().toString();
                Intent intent = new Intent(getApplicationContext(),page3Activity.class);
                intent.putExtra("region",selectRegion);
                startActivity(intent);
            }
        });


    }

}
