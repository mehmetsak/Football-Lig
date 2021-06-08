package com.mehmetsakiratasayin.football.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mehmetsakiratasayin.football.R;
import com.mehmetsakiratasayin.football.model.Teams;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<Teams> teamsArrayList;

    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#a4c2f4","#8ee5ee","#cd950c","#f5f5f5","#f47932"};

    public RecyclerViewAdapter(ArrayList<Teams> teamsArrayList) {

        this.teamsArrayList = teamsArrayList;
    }
    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(teamsArrayList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return
                teamsArrayList.size();
    }
    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName1;
        public RowHolder(@NonNull View itemView) {
            super(itemView);
            textName1 = itemView.findViewById(R.id.text1_name1);
        }
        public void bind(Teams teamsModel,String[] colors, Integer position) {

            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]));
                textName1.setText(teamsModel.isp1); }
    }
}
