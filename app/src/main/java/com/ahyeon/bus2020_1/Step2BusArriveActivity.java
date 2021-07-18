package com.ahyeon.bus2020_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Step2BusArriveActivity extends AppCompatActivity {

    private static final String TAG = "#222BusArriveActivity#";   //태그변수
    private RecyclerView rvBusArrive;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TextView tvArriveMsg;
    private TextView tvBusStopArsId;
    private CheckBox cbFavorite;
    private Button bnHome;
    private SwipeRefreshLayout refreshLayout;

    private Context mContext;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2_bus_arrive);

        TextView tvBusStopName = (TextView)findViewById(R.id.tvBusStopName22);
        tvArriveMsg = (TextView)findViewById(R.id.tvArriveMsg);
        rvBusArrive = (RecyclerView)findViewById(R.id.rvBusArrive);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshLayout);
        tvBusStopArsId = (TextView)findViewById(R.id.tvBusStopArsId);
        cbFavorite = (CheckBox)findViewById(R.id.cbFavorite);

        tvArriveMsg.setVisibility(View.GONE);
        rvBusArrive.addItemDecoration(new DividerItemDecoration(rvBusArrive.getContext(), 1));

        Intent intent = getIntent();
        BusData busData = (BusData)intent.getSerializableExtra("busArrive");

        final String strBusStopName = busData.getBusstop_name().toString();
        //final String strBusStopArsId = busData.getArs_id().toString();  //즐겨찾기에서 넘어오는 게 아니기 깨문에 Ars_id가 null 일리 없다
        final String strBusStopId = busData.getBusstop_id().toString();

        //즐겨찾기에서 넘어온거기 때문에 버스 Ars_id 정보는 가지고 있지 않다.
        final String strBusStopArsId;
        if(busData.getArs_id() != null){
            strBusStopArsId = busData.getArs_id().toString();
        }else {
            strBusStopArsId = " ";
        }

        tvBusStopName.setText(strBusStopName);
        tvBusStopArsId.setText(strBusStopArsId);

        String url = "http://121.147.206.212/json/arriveApi?BUSSTOP_ID=";
        final String url_val =url+strBusStopId;

        rvBusArrive.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvBusArrive.setLayoutManager(layoutManager);

        queue = Volley.newRequestQueue(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBusArrive(url_val);
                refreshLayout.setRefreshing(false);
            }
        });

        getBusArrive(url_val);

        mContext = this;
        ArrayList<String> arrayId = PreferenceManager.arrayList(mContext);
        int n = arrayId.size();
        Log.d(TAG, "arrayId.size(): "+n);
        if(n>=1) {
            for (int i = 0; i < arrayId.size(); i++) {
                if (arrayId.get(i).toString().equals(strBusStopId)) {
                    Log.d(TAG, "onCreate: 똑 같 은 거!!!");
                    cbFavorite.setChecked(true);
                }
            }
        }


        cbFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()){
                    Log.d(TAG, "+++++++++++++CHECKED!!!!! ");
                    PreferenceManager.setString(v.getContext(), strBusStopId, strBusStopName);
                }else {
                    Log.d(TAG, "+++++++++++++CHECKED 풀렸다~~~~~~ ");
                    PreferenceManager.removeKey(v.getContext(), strBusStopId);
                }
            }
        });

    }
    public void getBusArrive(String url_arrive){
        Log.d(TAG, "@@@@도착 api :"+url_arrive);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_arrive,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray array_ARRIVE_LIST = jsonObj.getJSONArray("ARRIVE_LIST");

                            List<BusData> busDataArrayList = new ArrayList<>();
                            Log.d(TAG, "@@@@ List 길이 :"+array_ARRIVE_LIST.length());
                            if(array_ARRIVE_LIST.length() == 0){
                                tvArriveMsg.setVisibility(View.VISIBLE);
                                tvArriveMsg.setText("도착 정보가 없습니다");
                            }
                            else {
                                for(int i=0 ; i <array_ARRIVE_LIST.length();i++){     //받은 response을 BusData class로 분류
                                    JSONObject obj = array_ARRIVE_LIST.getJSONObject(i);
                                    Log.d("obj.toString(): ",obj.toString());
                                    BusData busData = new BusData(3);
                                    busData.setLine_name(obj.getString("LINE_NAME"));
                                    busData.setRemain_min(obj.getString("REMAIN_MIN"));
                                    busData.setRemain_stop(obj.getString("REMAIN_STOP"));
                                    busData.setBusstop_name(obj.getString("BUSSTOP_NAME"));
                                    busData.setDir_start(obj.getString("DIR_START"));
                                    busData.setDir_end(obj.getString("DIR_END"));

                                    busDataArrayList.add(busData);
                                }
                            }

                            // Display the first 500 characters of the response string.
                            mAdapter = new Step2BusArriveAdapter(busDataArrayList);
                            rvBusArrive.setAdapter(mAdapter);
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
        queue.add(stringRequest);
    }
}
