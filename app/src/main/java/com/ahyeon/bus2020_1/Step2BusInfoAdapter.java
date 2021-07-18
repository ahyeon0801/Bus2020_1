package com.ahyeon.bus2020_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Step2BusInfoAdapter extends RecyclerView.Adapter<Step2BusInfoAdapter.MyViewHolder>{
    private static final String TAG = "Step2BusInfoAdapter";   //태그변수
    private List<BusData> mDataset;
    private int mSelectedPos;
    int flag = 0;

    public static class MyViewHolder extends RecyclerView.ViewHolder {  //여기(클래스) Holder 에서 findById 해야하는 위치 (onCreateViewHolder에 들어가는 요소)
        // each data item is just a string in this case
        public TextView tvBusStopName;
        public TextView tvArsId;
        public MyViewHolder(View v) {
            super(v);
            tvBusStopName = v.findViewById(R.id.tvBusStopName);
            tvArsId = v.findViewById(R.id.tvArsId);

            this.setIsRecyclable(false);
        }
    }

    public Step2BusInfoAdapter(List<BusData> myDataset, int selectedPos) {  // 초기 데이터형태
        mDataset = myDataset;
        mSelectedPos = selectedPos;
        Log.d(TAG, "Step2BusInfoAdapter생성자에서 받은 pos: "+mSelectedPos);
    }

    @Override
    public Step2BusInfoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,  //이 함수는 RecyclerView의 항목화면 연결
                                                          int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_busstop_step2, parent, false);    //레이아웃 연결

        Step2BusInfoAdapter.MyViewHolder vh = new Step2BusInfoAdapter.MyViewHolder(v);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(Step2BusInfoAdapter.MyViewHolder holder, int position) {
        final BusData bus = mDataset.get(position);

        holder.tvBusStopName.setText(bus.getBusstop_name());
        holder.tvArsId.setText("( "+bus.getArs_id()+" )");

        //Log.d(TAG, "getOldPosition: "+holder.getOldPosition());
        if(position == mSelectedPos){   //getAdapterPosition
            Log.d(TAG, "position == mSelectedPos ");
            holder.itemView.setBackgroundColor(Color.parseColor("#dcd6f7"));    //색깔을 이렇게 셋팅해주는거였음!!
            //holder.layoutBusstop.setBackgroundColor(R.color.colorThema1);

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }
}
