package com.example.android.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.model.Review;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    private List<Review> reviews;
    private Context context;

    public ReviewAdapter(Context context){
        this.context = context;
    }

    class ReviewAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView mTv_author;
        TextView mTv_content;
        TextView mTv_readMore;

        public ReviewAdapterViewHolder(View view){
            super(view);

            mTv_author = (TextView) view.findViewById(R.id.tv_author);
            mTv_content = (TextView) view.findViewById(R.id.tv_content);
            mTv_readMore = (TextView) view.findViewById(R.id.tv_readMore);
        }
    }

    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.review_list_item, parent, false);

        return new ReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReviewAdapterViewHolder holder, final int position) {
        holder.mTv_author.setText(reviews.get(position).getAuthor());
        holder.mTv_content.setText(reviews.get(position).getContent());

        if(reviews.get(position).getContent().length() < 300) { //hiding all readMore text assuming the review is less than 5 lines
            holder.mTv_readMore.setVisibility(View.GONE);
        }
        holder.mTv_readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(holder.mTv_readMore.getText().toString().equalsIgnoreCase("Read More")) { //you should set the text of tvReadMore textview to [Read more] in the beginning
                        holder.mTv_content.setMaxLines(Integer.MAX_VALUE); //set the maximum line to Maximum integer
                        holder.mTv_readMore.setText("Show Less");
                    } else {
                        holder.mTv_content.setMaxLines(5); //set the maximum line to 3
                        holder.mTv_readMore.setText("Read More");
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (reviews == null) {
            return 0;
        }

        return reviews.size();
    }

    //for recyclerView
    public void setData(List<Review> data){
        this.reviews=data;
        notifyDataSetChanged();
    }

    public List<Review> getTrailers() {
        return reviews;
    }

    //for recyclerView
    public void clear() {
        int size = reviews.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                reviews.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }

}
