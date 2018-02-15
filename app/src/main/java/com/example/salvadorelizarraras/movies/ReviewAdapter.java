package com.example.salvadorelizarraras.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Salvador Elizarraras on 12/02/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MViewHolder> {


    ArrayList<Review> reviews;

    public void setReviewData(ArrayList<Review> reviews){
        if(reviews == null) return;

        this.reviews = reviews;
        notifyDataSetChanged();
    }
    @Override
    public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int resource = R.layout.item_review;
        boolean attachToroot = false;
        View view = inflater.inflate(resource,parent,attachToroot);
        ReviewAdapter.MViewHolder mViewHolder= new ReviewAdapter.MViewHolder(view);

        return mViewHolder;

    }

    public Review getReview(int position){

        return reviews.get(position);
    }

    @Override
    public void onBindViewHolder(MViewHolder holder, int position) {

        Review review = getReview(position);

        holder.mTitle.setText(review.getAuthor());
        holder.mContent.setText(review.getContent());

    }

    @Override
    public int getItemCount() {

        return reviews == null ? 0 : reviews.size();
    }

    public class MViewHolder extends RecyclerView.ViewHolder{


        TextView mTitle, mContent;
        public MViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mContent = (TextView) itemView.findViewById(R.id.tv_content);

        }


    }
}
