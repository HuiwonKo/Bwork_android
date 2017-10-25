package com.perples.recosample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haein on 2017-09-17.
 */
public class Participant_RecyclerAdapter extends RecyclerView.Adapter<Participant_RecyclerAdapter.MyViewHolder> {

    private List<Participant> participants;

    public Participant_RecyclerAdapter(List<Participant> participants) {
        this.participants = participants;
    }

    @Override
    public Participant_RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.usernameTv.setText(participants.get(position).getUser_name());

        if(participants.get(position).getIs_participate() == true){
            holder.is_participateTv.setText("참석");
        }else{
            holder.is_participateTv.setText("미참석");
        }
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView usernameTv;
        TextView is_participateTv;

        public MyViewHolder(View itemView) {
            super(itemView);
            usernameTv = (TextView)itemView.findViewById(R.id.usernameTv);
            is_participateTv = (TextView)itemView.findViewById(R.id.is_participateTv);
        }
    }
}
