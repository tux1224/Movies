package com.example.salvadorelizarraras.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Salvador Elizarraras on 11/01/2017.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder> {

    private final MoviesOnClickHandler mOnClickHandler;



    private Movie[] movies;

    public MoviesRecyclerViewAdapter(MoviesOnClickHandler mOnClickHandler) {
        this.mOnClickHandler = mOnClickHandler;

    }

    public void setMovieData(Movie... moviesData){
            movies = moviesData;
            this.notifyDataSetChanged();

    }

     @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int resource = R.layout.item_movie;
        boolean attachToroot = false;
        View view = inflater.inflate(resource,parent,attachToroot);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view, mOnClickHandler);

        return moviesViewHolder;
    }

    public Movie getMovie(int position){
        return movies[position];
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Movie movie = movies[position];
        holder.imageView.setImageBitmap(movie.getImageBitmap());
    }

    @Override
    public int getItemCount() {
     return (movies == null) ? 0 : movies.length;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private final MoviesOnClickHandler mOnCLickHandler;

        public final ImageView imageView;

        public MoviesViewHolder(View itemView, MoviesOnClickHandler handler) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img_movie);
            itemView.setOnClickListener(this);
            mOnCLickHandler = handler;

        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mOnCLickHandler.onClick(adapterPosition);
        }
    }
}
