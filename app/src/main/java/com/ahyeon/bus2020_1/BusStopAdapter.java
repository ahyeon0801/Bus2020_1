package com.ahyeon.bus2020_1;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BusStopAdapter extends RecyclerView.Adapter<BusStopAdapter.MyViewHolder> {
    private static final String TAG = "BusStopAdapter";   //태그변수
    private List<BusData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {  //여기(클래스) Holder 에서 findById 해야하는 위치 (onCreateViewHolder에 들어가는 요소)
        // each data item is just a string in this case
        public TextView tvBusStopName;
        public TextView tvArsId;
        public MyViewHolder(View v) {
            super(v);
            tvBusStopName = v.findViewById(R.id.tvBusStopName);
            tvArsId = v.findViewById(R.id.tvArsId);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BusStopAdapter(List<BusData> myDataset) {  // 초기 데이터형태
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,  //이 함수는 RecyclerView의 항목화면 연결
                                           int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_busstop, parent, false);    //레이아웃 연결

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //여기서 데이터를 셋팅 : onBindViewHolder는 원본데이터의 크기만큼 반복한다!
        //position = 리사이클러뷰의 항목 n번째 = 데이터의 n번째
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final BusData bus = mDataset.get(position);
        // mDataset.get(position).getLine_name()이랑 mDataset.get(position).getDir_pass() 두 줄을 써야하니 앞에꺼를 변수화해서 꺼내쓴다!

        holder.tvBusStopName.setText(bus.getBusstop_name());
        holder.tvArsId.setText("( "+bus.getArs_id()+" )");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "#########holder.itemView.setOnClickListener##########  "+bus.getBusstop_id());

                Intent intent = new Intent(v.getContext(), Step2BusArriveActivity.class);

                intent.putExtra("busArrive",bus);
                v.getContext().startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }
}