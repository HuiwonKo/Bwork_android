package com.perples.recosample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by haein on 2017-09-15.
 */
public class Manage_RecyclerAdapter extends RecyclerView.Adapter<Manage_RecyclerAdapter.ViewHolder> {

    private List<CommutesClass> commutesClasses;

    public Manage_RecyclerAdapter(List<CommutesClass> commutesClasses) {
        this.commutesClasses = commutesClasses;
    }

    @Override
    public Manage_RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manage, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int pos = position;

        String in_time = commutesClasses.get(position).getIn_time();
        String out_time = commutesClasses.get(position).getOut_time();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            //Date date = format.parse(in_time);
            viewHolder.tvDate.setText(new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(format.parse(in_time)));
            viewHolder.tvIn_time.setText(new SimpleDateFormat("hh시 mm분", Locale.KOREA).format(format.parse(in_time)));
            viewHolder.tvOut_time.setText(new SimpleDateFormat("hh시 mm분", Locale.KOREA).format(format.parse(out_time)));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        viewHolder.tvShift_time.setText(commutesClasses.get(position).getShift() + " 시간");

       /* CommutesClass contact = (CommutesClass) viewHolder.tvShift_time.getTag();
        contact.setShift(commutesClasses.get(position).getShift());

        commutesClasses.get(pos).setShift(commutesClasses.get(position).getShift());*/
    }

    @Override
    public int getItemCount() {
        return commutesClasses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate;
        public TextView tvIn_time;
        public TextView tvOut_time;
        public TextView tvShift_time;

        //public CommutesClass singlecommutes;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvDate = (TextView) itemLayoutView.findViewById(R.id.dateTv);
            tvIn_time = (TextView) itemLayoutView.findViewById(R.id.tv_in_time);
            tvOut_time = (TextView) itemLayoutView.findViewById(R.id.tv_out_time);
            tvShift_time = (TextView) itemLayoutView.findViewById(R.id.shift_time);

        }
    }

    //public List<CommutesClass> getCommutesClasses() {
    //return commutesClasses;
    ///}
}
