package com.boltu.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boltu.myapplication.controller.activity.TeamsActivity;
import com.boltu.myapplication.model.teams.TeamsListModel;
import com.sports.bettercricketway.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder> {
    Context context;
    List<TeamsListModel> teamsListModelList;
    TeamsListModel teamsListModel;
    public class TeamsViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        TextView teamId;
        ImageView teamLogo;
        public TeamsViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName = itemView.findViewById(R.id.team_name);
            teamId = itemView.findViewById(R.id.team_id);
            teamLogo = itemView.findViewById(R.id.team_logo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, teamId.getText(),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    public TeamsAdapter(Context context, List<TeamsListModel> teamsListModelList){
        this.context = context;
        this.teamsListModelList = teamsListModelList;
    }

    @NonNull
    @Override
    public TeamsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_list,parent,false);
        return new TeamsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TeamsViewHolder holder, int position) {
        teamsListModel = teamsListModelList.get(position);
        holder.teamName.setText(teamsListModel.getName() + "\n(" + teamsListModel.getShortName() +")");
        holder.teamId.setText(teamsListModel.getId().toString());
        Picasso.get().load(teamsListModel.getLogoUrl()).into(holder.teamLogo);
    }

    @Override
    public int getItemCount() {
        return teamsListModelList.size();
    }


}
