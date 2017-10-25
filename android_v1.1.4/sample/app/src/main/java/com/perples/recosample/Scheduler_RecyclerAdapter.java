package com.perples.recosample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haein on 2017-08-30.
 */
public class Scheduler_RecyclerAdapter extends RecyclerView.Adapter<Scheduler_RecyclerAdapter.MyViewHolder> {

    private List<Schedule> schedules;
    Context ctx;

    public Scheduler_RecyclerAdapter(List<Schedule> schedules, Context ctx)
    {
        this.schedules = schedules;

        this.ctx = ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_row_item, parent, false);
        return new MyViewHolder(view, ctx, schedules);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Plan.setText(schedules.get(position).getPlan());
        holder.Time.setText(schedules.get(position).getTime());
        holder.Location.setText(schedules.get(position).getLocation());
        //holder.Participant.setText(schedules.get(position).getParticipant());

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView Plan;
        TextView Time;
        TextView Location;
        //TextView Participant;

        List<Schedule> schedules;
        Context ctx;

        public MyViewHolder(View itemView, Context ctx, List<Schedule> schedules) {
            super(itemView);

            this.schedules = schedules;
            this.ctx = ctx;
            itemView.setOnClickListener(this);

            Plan = (TextView)itemView.findViewById(R.id.plan);
            Time = (TextView)itemView.findViewById(R.id.time);
            Location = (TextView)itemView.findViewById(R.id.location);
            //Participant = (TextView)itemView.findViewById(R.id.participant);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Schedule schedule = this.schedules.get(position);
            Intent intent =  new Intent(this.ctx, Scheduler_detail.class);
            intent.putExtra("plan", schedule.getPlan());
            intent.putExtra("time", schedule.getTime());
            intent.putExtra("location", schedule.getLocation());
            this.ctx.startActivity(intent);

        }
    }
}
