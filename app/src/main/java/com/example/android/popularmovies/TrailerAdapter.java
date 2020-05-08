package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.model.Trailer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{

    private List<Trailer> trailers;
    private Context context;

    public TrailerAdapter(Context context){
        this.context = context;
    }

    //for recyclerView
    public void setData(List<Trailer> data){
        this.trailers=data;
        notifyDataSetChanged();
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    //for recyclerView
    public void clear() {
        int size = trailers.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                trailers.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

    class TrailerAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_TrailerName;

        public TrailerAdapterViewHolder(View view){
            super(view);

            mTv_TrailerName = (TextView) view.findViewById(R.id.tv_trailerName);
        }
    }


    @NonNull
    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.trailer_list_item, parent, false);

        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapterViewHolder holder, int position) {
        holder.mTv_TrailerName.setText(trailers.get(position).getName());
        //System.out.println("TrailerName: " + trailers.get(position).getName());
    }

    @Override
    public int getItemCount() {

        if (trailers == null) {
            return 0;
        }

        return trailers.size();
    }


}
