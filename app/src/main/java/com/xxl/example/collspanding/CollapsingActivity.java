package com.xxl.example.collspanding;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.xxl.example.R;

import java.util.ArrayList;
import java.util.List;

public class CollapsingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        RecyclerView recyclerView = findViewById(R.id.rv_recycler);

        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mData.add("item ===" + i);
        }
        Adapter adapter = new Adapter(mData);
        recyclerView.setAdapter(adapter);
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private List<String> mData = new ArrayList<>();

        public Adapter(List<String> data) {
            this.mData = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setHeight(80);
            return new ViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView;
            }
        }
    }
}
