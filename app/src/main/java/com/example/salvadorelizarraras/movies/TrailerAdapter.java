package com.example.salvadorelizarraras.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Salvador Elizarraras on 07/02/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolderTrailer> {

    private Trailer[] trailers;
    private MoviesOnClickHandler listener;

    public void setListener(MoviesOnClickHandler listener){
        this.listener = listener;
    }

    public void setData(Trailer... trailers) {
        this.trailers = trailers;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolderTrailer onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        int resource = R.layout.item_trailer;
        boolean attachToroot = false;
        View view = inflater.inflate(resource, parent, attachToroot);

        ViewHolderTrailer viewHolderTrailer = new ViewHolderTrailer(view);
        return viewHolderTrailer;
    }

    @Override
    public int getItemCount() {
        if(trailers == null) return 0;

        return trailers.length;
    }

    public Trailer getTrailer(int position) {
        if(trailers == null)return null;

        return trailers[position];
    }

    @Override
    public void onBindViewHolder(ViewHolderTrailer holder, int position) {
        Trailer trailer = trailers[position];

        holder.mTrailerName.setText(trailer.getName());
    }

    public class ViewHolderTrailer extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTrailerName;
        public ViewHolderTrailer(View itemView) {
            super(itemView);

            mTrailerName = (TextView) itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onClick(getPosition());
        }
    }
}
