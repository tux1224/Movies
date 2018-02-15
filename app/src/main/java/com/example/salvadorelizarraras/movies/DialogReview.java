package com.example.salvadorelizarraras.movies;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.security.MessageDigest;
import java.util.ArrayList;



/**
 * Created by Salvador Elizarraras on 09/02/2018.
 */

public class DialogReview extends android.support.v4.app.DialogFragment {


    private RecyclerView mRecyclerView;
    private ArrayList<Review> reviews;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_review,container, true);

        reviews = getArguments().getParcelableArrayList("reviews");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.m_recycler_review);
        Context context = getActivity().getApplicationContext();
        String TAG = "DialogREview";
        Log.i(TAG, "context " + context);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayout);
        ReviewAdapter mAdapter = new ReviewAdapter();
        mAdapter.setReviewData(reviews);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }



}
