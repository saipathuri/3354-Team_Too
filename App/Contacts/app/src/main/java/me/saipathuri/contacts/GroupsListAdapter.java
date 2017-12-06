package me.saipathuri.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.saipathuri.contacts.utils.ImageUtils;

/**
 * Created by saipathuri on 12/5/17.
 */

public class GroupsListAdapter extends RecyclerView.Adapter {

    private ArrayList<Group> groups;
    private int onClickBehavior;


    GroupsListAdapter(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public void updateGroupsList(List<Group> newlist) {
        groups.clear();
        groups.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactCardView = inflater.inflate(R.layout.group_card, parent, false);
        GroupsListAdapter.GroupViewHolder viewHolder = new GroupsListAdapter.GroupViewHolder(contactCardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Group group = groups.get(position);

        String name = group.getGroupName();

        GroupsListAdapter.GroupViewHolder groupViewHolder = (GroupsListAdapter.GroupViewHolder) holder;
        groupViewHolder.setName(name);

        ((GroupsListAdapter.GroupViewHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: make a view group class
                Group group = groups.get(position);
                Intent intent = new Intent(view.getContext(), EditContactActivity.class);
                intent.putExtra(Constants.CONTACT_ID_EXTRA_KEY, group.getId());
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (groups != null ? groups.size() : 0);
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mGroupNameTextView;

        GroupViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            this.mGroupNameTextView = (TextView) itemView.findViewById(R.id.tv_view_group_name);
        }

        public void setName(String name){
            mGroupNameTextView.setText(name);
        }

        @Override
        public void onClick(View view) {
            return;
        }

        public void setOnClickListener(View.OnClickListener listener){
            itemView.setOnClickListener(listener);
        }
    }
}
