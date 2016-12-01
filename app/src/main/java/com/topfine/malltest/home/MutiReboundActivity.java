package com.topfine.malltest.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topfine.malltest.R;

public class MutiReboundActivity extends AppCompatActivity {

    private RecyclerView mRecyVi;
    private RecyclerView mRecyVi2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muti_rebound);
        mRecyVi = (RecyclerView) findViewById(R.id.recyVi);
        mRecyVi.setLayoutManager(new LinearLayoutManager(this));
        mRecyVi.setFocusable(false);
        mRecyVi.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_nomore, parent, false);

                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 20;
            }


        });
        mRecyVi2 = (RecyclerView) findViewById(R.id.recyVi2);
        mRecyVi2.setLayoutManager(new LinearLayoutManager(this));
        mRecyVi.setFocusable(false);
        mRecyVi2.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_error, parent, false);

                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 20;
            }


        });
    }
}
