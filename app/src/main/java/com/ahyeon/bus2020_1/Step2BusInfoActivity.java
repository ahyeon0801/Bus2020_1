package com.ahyeon.bus2020_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

public class Step2BusInfoActivity extends AppCompatActivity {

    private static final String TAG = "##Step2 BusInfoActivity";   //ํ๊ทธ๋ณ์
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private LinearLayout layoutDirUpDown;
    private TextView tvBusLineMsg;
    private Context mContext;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2_bus_info);

        TextView tvBusName = (TextView)findViewById(R.id.tvBusName);
        TextView tvDirPass = (TextView)findViewById(R.id.tvDirPass);
        TextView tvDirStart = (TextView)findViewById(R.id.tvDirStart);
        TextView tvDirEnd = (TextView)findViewById(R.id.tvDirEnd);
        layoutDirUpDown = (LinearLayout)findViewById(R.id.layoutDirUpDown);
        tvBusLineMsg = (TextView)findViewById(R.id.tvBusLineMsg);

        recyclerView = (RecyclerView)findViewById(R.id.rvBusStop);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), 1));


        Log.d(TAG, "onCreate ");
        Intent intent = getIntent();
        BusData busData = (BusData)intent.getSerializableExtra("busArrive");

        final String strLine_name = busData.getLine_name().toString();

        final String strDir_start_name = busData.getDir_start().toString();
        final String strDir_end_name = busData.getDir_end().toString();
        final String strLine_id = busData.getLine_id().toString();

        if(busData.getBusstop_id() == null){  //์๋ง ์๋?
            Log.d(TAG, "์์ด๋๋ ์๋ค");
        }
        if(busData.getDir_start() != null){
            Log.d(TAG, "Dir_start ์์");
        }
        final String strBusstop_name = busData.getBusstop_name().toString();    //์ด๊ฑฐ๋ ๊ทธ ๋ฒ์ค์ ํ์ฌ์์น์์. ๊ฒ์์์ ๋๋ฅธ ๋ฒ์ค์?๋ฅ์๊ฐ์ด ์๋

        if(busData.getSeleted_busstop_name() != null){
            Log.d(TAG, "๋ฐ๋ก์ฌ๊ธฐ์ ์์์ผ์ง ๋ฒ์ค์ด๋ฆ: "+busData.getSeleted_busstop_name().toString()+", "+busData.getSeleted_Ars_id());


        }
        String strSelected_busstop_name = busData.getSeleted_busstop_name().toString();
        String strSelected_ars_id = busData.getSeleted_Ars_id().toString();
        Log.d(TAG, "@ํ์คํธ ๋ฒ์คstop์ด๋ฆ: "+strBusstop_name);

        tvBusName.setText(strLine_name);
        tvDirStart.setText(strDir_start_name);
        tvDirEnd.setText(strDir_end_name);

        String url = "http://121.147.206.212/json/lineStationApi?LINE_ID=";
        String url_val =url+strLine_id;

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        queue = Volley.newRequestQueue(this);

        if(strLine_id.equals("1")){
            Log.d(TAG, "strLine_id == 1");
            tvBusLineMsg.setText("ํด๋น ๋ฒ์ค ๋ธ์? ์?๋ณด๊ฐ ์์ต๋๋ค.");
        }
        getBusStop(url_val, strSelected_busstop_name, strSelected_ars_id);

    }

    public void getBusStop(String url_val, final String selected_busstop_name, final String selected_ars_id){
        Log.d(TAG, "๋ธ์? api :"+url_val);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_val,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            int selectedPos = 0;
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray array_BUSSTOP_LIST = jsonObj.getJSONArray("BUSSTOP_LIST");

                            List<BusData> busDataArrayList = new ArrayList<>();
                            for(int i=0 ; i <array_BUSSTOP_LIST.length();i++){     //๋ฐ์ response์ BusData class๋ก ๋ถ๋ฅ
                                JSONObject obj = array_BUSSTOP_LIST.getJSONObject(i);
                                Log.d("obj.toString(): ",obj.toString());
                                BusData busData = new BusData(100);
                                busData.setBusstop_name(obj.getString("BUSSTOP_NAME"));
                                busData.setArs_id(obj.getString("ARS_ID"));
                                busData.setBusstop_id(obj.getString("BUSSTOP_ID"));

                                if(obj.getString("ARS_ID").toString().equals(selected_ars_id)){
                                    Log.d(TAG, "๋ช๋ฒ์ฌ onResponse: "+ i);
                                    selectedPos = i;
                                }
                                busData.setSeleted_busstop_name(selected_busstop_name);
                                busData.setSeleted_Ars_id(selected_ars_id);
                                busDataArrayList.add(busData);
                            }
                            mAdapter = new Step2BusInfoAdapter(busDataArrayList, selectedPos);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.scrollToPosition(selectedPos);

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
