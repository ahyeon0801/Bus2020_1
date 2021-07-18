package com.ahyeon.bus2020_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "[[ 메.인.액.티.비.티 ]]";
    private RecyclerView recyclerView;
    //    private TextInputEditText textInput;
    private EditText editText;
    private Button btGet;
    private Button btJsonGet;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView imgStar;
    private Context mContext;

    private JSONObject jsonObj_busstop_res;
    private JSONArray array_STATION_LIST;
    private JSONObject jsonObj_line_res;
    private JSONArray array_LINE_LIST;

    private AdView mAdView;
    // private String[] mDataset = {"11", "22", "33"};
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //화면과 매칭
/*
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);    //기존 타이틀 안써
*/
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),1)); //구분선
        editText = (EditText)findViewById(R.id.input_line_name);
        imgStar = (ImageView)findViewById(R.id.imgStar);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        queue = Volley.newRequestQueue(this);

        editText.setOnKeyListener(new View.OnKeyListener() {    // ENTER 키눌림 방지
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER) return true;
                return false;
            }
        });
        initSearch();     // init로 전체 jsonObject 넣기

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = editText.getText().toString();
                //Log.d(TAG, "onTextChanged: "+str.length());
                if(str.equals("") ){
                    search(str);
                }else{
                    search(str);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //1. 화면이 로딩 -> 뉴스 정보를 받아온다
        //2. 정보 -> 어댑터 넘겨준다
        //3. 어댑터 -> 셋팅

        imgStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FavoriteActivity.class);
                v.getContext().startActivity(intent);

            }
        });
    }

    public void initSearch(){
        String url_line ="http://121.147.206.212/json/lineApi";
        String url_busstop ="http://121.147.206.212/json/stationApi";

        StringRequest stringRequest_line = new StringRequest(Request.Method.GET, url_line,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "stringRequest_line");
                        try {
                            jsonObj_line_res = new JSONObject(response);
                            array_LINE_LIST = jsonObj_line_res.getJSONArray("LINE_LIST");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        StringRequest stringRequest_busstop = new StringRequest(Request.Method.GET, url_busstop,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "stringRequest_busstop");
                        try {
                            jsonObj_busstop_res = new JSONObject(response);
                            array_STATION_LIST = jsonObj_busstop_res.getJSONArray("STATION_LIST");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest_busstop);
        queue.add(stringRequest_line);

    }
    public void search(final String input){
        Log.d("####search() 맨앞",input);
        final List<BusData> busDataArrayList = new ArrayList<>();
        // Request a string response from the provided URL.

        try {

            for(int i=0 ; i <array_LINE_LIST.length();i++){     //받은 response을 BusData class로 분류
                JSONObject obj = array_LINE_LIST.getJSONObject(i);
                //Log.d("obj.toString(): ",obj.toString());

                if((obj.getString("LINE_NAME").contains(input))){//if(input.equals(obj.getString("LINE_NAME"))){   //input이랑 같을 때만

                    BusData busData = new BusData(Code.ViewType.LINE_API);
                    busData.setLine_name(obj.getString("LINE_NAME"));
                    busData.setDir_pass(obj.getString("DIR_PASS"));
                    busData.setLine_id(obj.getString("LINE_ID"));

                    busData.setDir_down_name(obj.getString("DIR_DOWN_NAME"));   //종점
                    busData.setDir_up_name(obj.getString("DIR_UP_NAME"));       //기점
                    busDataArrayList.add(busData);
                }
            }
            mAdapter = new MyAdapter(busDataArrayList);
            recyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            for(int i=0 ; i <array_STATION_LIST.length();i++){     //받은 response을 BusData class로 분류
                JSONObject obj = array_STATION_LIST.getJSONObject(i);
                //Log.d("obj.toString(): ",obj.toString());

                if((obj.getString("BUSSTOP_NAME").contains(input))){//if(input.equals(obj.getString("LINE_NAME"))){   //input이랑 같을 때만

                    BusData busData = new BusData(Code.ViewType.STATION_API);
                    busData.setBusstop_name(obj.getString("BUSSTOP_NAME"));
                    busData.setArs_id(obj.getString("ARS_ID"));
                    busData.setNext_busstop(obj.getString("NEXT_BUSSTOP"));
                    busData.setBusstop_id(obj.getString("BUSSTOP_ID"));

                    busDataArrayList.add(busData);
                }
            }
            mAdapter = new MyAdapter(busDataArrayList);
            recyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}