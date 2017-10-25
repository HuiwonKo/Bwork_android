package com.perples.recosample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by haein on 2017-07-22.
 */
public class NoticeFragment_RecyclerAdapter extends RecyclerView.Adapter<NoticeFragment_RecyclerAdapter.MyViewHolder> {

    private List<Notice> notices;

    public NoticeFragment_RecyclerAdapter(List<Notice> notices)
    {
        this.notices = notices;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_deatil_row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Title.setText(notices.get(position).getTitle());
        holder.Content.setText(notices.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView Title;
        TextView Content;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title = (TextView)itemView.findViewById(R.id.title);
            Content = (TextView)itemView.findViewById(R.id.content);
        }
    }

}
