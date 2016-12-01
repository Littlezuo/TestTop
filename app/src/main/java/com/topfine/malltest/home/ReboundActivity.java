package com.topfine.malltest.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topfine.malltest.R;

public class ReboundActivity extends AppCompatActivity {

    private RecyclerView mRecyVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebound);

        mRecyVi = (RecyclerView) findViewById(R.id.recyVi);
        mRecyVi.setLayoutManager(new LinearLayoutManager(this));
//        mRecyVi.setFocusable(false);
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
                return 120;
            }
        });

    }
}
