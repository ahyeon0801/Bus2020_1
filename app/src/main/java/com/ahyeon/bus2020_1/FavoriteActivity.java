package com.ahyeon.bus2020_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    private static final String TAG = "[[ FavoriteActivity ]]";   //태그변수
    private RecyclerView rvFavStop;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TextView tvFavBus;
    private TextView tvFavMsg;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        tvFavBus = (TextView)findViewById(R.id.tvFavBus);
        rvFavStop = (RecyclerView)findViewById(R.id.rvFavStop);
        tvFavMsg = (TextView)findViewById(R.id.tvFavMsg);
        tvFavMsg.setVisibility(View.GONE);

        rvFavStop.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvFavStop.setLayoutManager(layoutManager);
        //rvFavStop.addItemDecoration(new DividerItemDecoration(rvFavStop.getContext(), 1));

        mContext = this;
        String str = PreferenceManager.listData(mContext);
        //Log.d(TAG, "listData: "+str);

        ArrayList<String> arrayName = new ArrayList<>();


        arrayName = PreferenceManager.arrayList(mContext);
        int n = arrayName.size();
        if(n == 0){
            tvFavMsg.setVisibility(View.VISIBLE);
            tvFavMsg.setText("자주 가는 정류소를 즐겨찾기에 저장해 사용해보세요!");
        }
        Log.d(TAG, "arrayName.size(): "+n);
        if(n>=1) {
            for (int i = 0; i < arrayName.size(); i++) {
                    //arrayName.add(PreferenceManager.getString(mContext, ));
                }
            }
    //여기서 버스 이름 get해서 arrayId처럼 같이 넘겨줄까, 파라미터 두개로
        mAdapter = new FavoriteAdapter(arrayName);
        rvFavStop.setAdapter(mAdapter);
        }
//클릭을 하면 그 정류소 이름으로 get해와야겟다


    }


