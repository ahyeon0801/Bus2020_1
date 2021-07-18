package com.ahyeon.bus2020_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class BusInfoActivity extends AppCompatActivity {
    private static final String TAG = "##BusInfoActivity";   //태그변수
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private Context mContext;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

        TextView tvBusName = (TextView)findViewById(R.id.tvBusName);
        TextView tvDirPass = (TextView)findViewById(R.id.tvDirPass);
        TextView tvDirUp = (TextView)findViewById(R.id.tvDirUp);
        TextView tvDirDown = (TextView)findViewById(R.id.tvDirDown);

        recyclerView = (RecyclerView)findViewById(R.id.rvBusStop);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));

        Log.d(TAG, "onCreate ");
        Intent intent = getIntent();
        BusData busData = (BusData)intent.getSerializableExtra("BusInfo");

        final String strLine_name = busData.getLine_name().toString();
        final String strDir_pass = busData.getDir_pass().toString();
        final String strDir_up_name = busData.getDir_up_name().toString();
        final String strDir_down_name = busData.getDir_down_name().toString();
        final String strLine_id = busData.getLine_id().toString();
        tvBusName.setText(strLine_name);
        tvDirPass.setText(strDir_pass);
        tvDirUp.setText(strDir_up_name);
        tvDirDown.setText(strDir_down_name);

        String url = "http://121.147.206.212/json/lineStationApi?LINE_ID=";
        String url_val =url+strLine_id;

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        queue = Volley.newRequestQueue(this);

        getBusStop(url_val);

    }
    public void getBusStop(String url_val){
        Log.d(TAG, "노선 api :"+url_val);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_val,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray array_BUSSTOP_LIST = jsonObj.getJSONArray("BUSSTOP_LIST");

                            List<BusData> busDataArrayList = new ArrayList<>();
                            for(int i=0 ; i <array_BUSSTOP_LIST.length();i++){     //받은 response을 BusData class로 분류
                                JSONObject obj = array_BUSSTOP_LIST.getJSONObject(i);
                                Log.d("obj.toString(): ",obj.toString());
                                BusData busData = new BusData(4);
                                busData.setBusstop_name(obj.getString("BUSSTOP_NAME"));
                                busData.setArs_id(obj.getString("ARS_ID"));
                                busData.setBusstop_id(obj.getString("BUSSTOP_ID"));

                                busDataArrayList.add(busData);
                            }
                            mAdapter = new BusStopAdapter(busDataArrayList);
                            recyclerView.setAdapter(mAdapter);
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
