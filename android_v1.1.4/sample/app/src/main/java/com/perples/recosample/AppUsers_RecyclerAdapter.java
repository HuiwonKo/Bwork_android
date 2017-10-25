package com.perples.recosample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by haein on 2017-08-31.
 */
public class AppUsers_RecyclerAdapter extends RecyclerView.Adapter<AppUsers_RecyclerAdapter.ViewHolder> {

    private List<AppUser> auList;

    public AppUsers_RecyclerAdapter(List<AppUser> appUsers) {
        this.auList = appUsers;
    }

    @Override
    public AppUsers_RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appusers_row_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final int pos = position;

        viewHolder.tvUsername.setText(auList.get(position).getUsername());
        viewHolder.tvEmail.setText(auList.get(position).getEmail());
        viewHolder.chkSelected.setChecked(auList.get(position).isSelected());
        viewHolder.chkSelected.setTag(auList.get(position));

        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                AppUser contact = (AppUser) cb.getTag();

                contact.setSelected(cb.isChecked());
                auList.get(pos).setSelected(cb.isChecked());

                Toast.makeText(v.getContext(), "Clicked on Checkbox: " + cb.getText() + "is"
                 + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return auList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvUsername;
        public TextView tvEmail;

        public CheckBox chkSelected;

        public AppUser singleappuser;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvUsername = (TextView) itemLayoutView.findViewById(R.id.tvUsername);

            tvEmail = (TextView) itemLayoutView.findViewById(R.id.tvEmail);
            chkSelected = (CheckBox) itemLayoutView.findViewById(R.id.chkSelected);

        }
    }

    public List<AppUser> getAuList() {
        return auList;
    }
}
