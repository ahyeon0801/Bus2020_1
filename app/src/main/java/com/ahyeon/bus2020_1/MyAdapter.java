package com.ahyeon.bus2020_1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {   //RecyclerView.Adapter<MyAdapter.MyViewHolder>
    private static final String TAG = "MyAdapter";   //태그변수
    private List<BusData> mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<BusData> myDataset) {  // 초기 데이터형태
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //이 함수는 RecyclerView의 항목화면 연결
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == Code.ViewType.LINE_API){
            view = inflater.inflate(R.layout.row_buses, parent, false);
            return new LineViewHolder(view);
        }else{
            view = inflater.inflate(R.layout.row_station, parent, false);
            return new StationViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final BusData bus = mDataset.get(position);
        if(holder instanceof LineViewHolder){
            ((LineViewHolder) holder).mTextViewName.setText(bus.getLine_name());
            ((LineViewHolder) holder).mTextViewDirPass.setText(bus.getDir_pass());
            ((LineViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "#########LineViewHolder.setOnClickListener##########");
                    Intent intent = new Intent(v.getContext(), BusInfoActivity.class);

                    intent.putExtra("BusInfo", bus);

                    v.getContext().startActivity(intent);
                }
            });

        }else if(holder instanceof StationViewHolder){
            ((StationViewHolder)holder).tvBusStationName.setText(bus.getBusstop_name());
            ((StationViewHolder)holder).tvArsId.setText("("+bus.getArs_id()+")");
            if(bus.getNext_busstop() != "null"){
                ((StationViewHolder)holder).tvNextBusStop.setText("NEXT: "+bus.getNext_busstop());
            }else {
                ((StationViewHolder)holder).tvNextBusStop.setText("NEXT: 없음 ");
            }

            ((StationViewHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "#########StationViewHolder.setOnClickListener##########");
                    Intent intent = new Intent(v.getContext(), BusArriveActivity.class);
                    intent.putExtra("busArrive", bus);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }

    public  int getItemViewType(int position){
        return mDataset.get(position).getViewType();
    }
    public class LineViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewName;
        public TextView mTextViewDirPass;
        LineViewHolder(View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.lineName);
            mTextViewDirPass = itemView.findViewById(R.id.dirPass);
            //itemView.setClickable(true);
            //itemView.setEnabled(true);
        }
    }

    public class StationViewHolder extends RecyclerView.ViewHolder{
        public TextView tvBusStationName;
        public TextView tvArsId;
        public TextView tvNextBusStop;
        StationViewHolder(View itemView) {
            super(itemView);
            tvBusStationName = itemView.findViewById(R.id.tvBusStationName);
            tvArsId = itemView.findViewById(R.id.tvArsId);
            tvNextBusStop = itemView.findViewById(R.id.tvNextBusStop);
        }
    }
}