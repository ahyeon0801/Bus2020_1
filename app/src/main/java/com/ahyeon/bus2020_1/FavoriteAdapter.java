package com.ahyeon.bus2020_1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {   //RecyclerView.Adapter<MyAdapter.MyViewHolder>
    private static final String TAG = "FavoriteAdapter";   //태그변수
    private ArrayList<String> mDataset;
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoriteAdapter(ArrayList<String> myDataset) {  // 초기 데이터형태
        //mContext = myContext;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //이 함수는 RecyclerView의 항목화면 연결
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

     //   if(viewType == Code.ViewType.STATION_FAV){
            view = inflater.inflate(R.layout.row_favorite, parent, false);
     //       mContext = view.getContext();
            return new FavStationViewHolder(view);
        /*}else{
            view = inflater.inflate(R.layout.row_favorite_bus, parent, false);
            return new FavBusViewHolder(view);
        }*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final String stop_id = mDataset.get(position);
        final String stop_name = PreferenceManager.getString(mContext, stop_id);

        final BusData bus = new BusData(Code.ViewType.STATION_FAV);
        bus.setBusstop_id(stop_id);
        bus.setBusstop_name(stop_name);
        if(holder instanceof FavStationViewHolder){
            ((FavStationViewHolder) holder).tvFavStop_row.setText(stop_name);
            ((FavStationViewHolder) holder).imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {   //휴지통 버튼
                    Log.d(TAG, "btPosition: "+position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("즐겨찾기");
                    builder.setMessage("해당 정류소를 삭제하시겠습니까?");
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDataset.remove(position);
                            notifyDataSetChanged();

                            PreferenceManager.removeKey(mContext, stop_id);
                        }
                    });
                    builder.create().show();
                }
            });

            ((FavStationViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "#########LineViewHolder.setOnClickListener##########");
                    Log.d(TAG, "제대로 넣었나 버스 : "+bus.getBusstop_id().toString()+", "+bus.getBusstop_name());
                    Intent intent = new Intent(v.getContext(), Step2BusArriveActivity.class);
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
/*
    public  int getItemViewType(int position){
        return mDataset.get(position).getViewType();
    }
  */
    public class FavStationViewHolder extends RecyclerView.ViewHolder{
        public TextView tvFavStop_row;
        public ImageView imgDel;
        FavStationViewHolder(View itemView) {
            super(itemView);

            tvFavStop_row = itemView.findViewById(R.id.tvFavStop_row);
            imgDel = itemView.findViewById(R.id.imgDel);
            mContext = itemView.getContext();
            itemView.setClickable(true);
            itemView.setEnabled(true);
        }
    }

    public class FavBusViewHolder extends RecyclerView.ViewHolder{
        public TextView tvFavBusname_row;
        public ImageView imgDel;
        FavBusViewHolder(View itemView) {
            super(itemView);
            tvFavBusname_row = itemView.findViewById(R.id.tvFavBusname_row);
            imgDel = itemView.findViewById(R.id.imgDel);
            mContext = itemView.getContext();
            itemView.setClickable(true);
            itemView.setEnabled(true);
        }
    }

}
/*
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private static final String TAG = "[[ FavoriteAdapter ]]";   //태그변수
    private ArrayList<String> mDataset;
    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {  //여기(클래스) Holder 에서 findById 해야하는 위치 (onCreateViewHolder에 들어가는 요소)
        // each data item is just a string in this case
        public CheckBox cbFavorite_row;
        public TextView tvFavStop_row;
        public MyViewHolder(View v) {
            super(v);
            cbFavorite_row = v.findViewById(R.id.cbFavorite_row);
            tvFavStop_row = v.findViewById(R.id.tvFavStop_row);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoriteAdapter(ArrayList<String> myDataset) {  // 초기 데이터형태
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,  //이 함수는 RecyclerView의 항목화면 연결
                                           int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_favorite, parent, false);    //레이아웃 연결

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

        final String stop_id = mDataset.get(position);

        String stop_name = PreferenceManager.getString(,stop_id.toString());

        // mDataset.get(position).getLine_name()이랑 mDataset.get(position).getDir_pass() 두 줄을 써야하니 앞에꺼를 변수화해서 꺼내쓴다!

        holder.cbFavorite_row.setChecked(true);
        holder.tvFavStop_row.setText(stop_name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        //삼항 연산자
        return mDataset == null ? 0 : mDataset.size();
    }
}
*/