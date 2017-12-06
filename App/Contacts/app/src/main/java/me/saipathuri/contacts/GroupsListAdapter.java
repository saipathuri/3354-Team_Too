package me.saipathuri.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private static final String TAG = "GroupsListAdapter";
    private ArrayList<Group> groups;
    GroupsRecyclerViewListener mListener;
    int onClickBehavior;

    GroupsListAdapter(ArrayList<Group> groups, int onClickBehavior) {
        this.groups = groups;
        this.onClickBehavior = onClickBehavior;
    }

    GroupsListAdapter(ArrayList<Group> groups, int onClickBehavior, GroupsRecyclerViewListener listener) {
        this.groups = groups;
        mListener = listener;
        this.onClickBehavior = onClickBehavior;
    };

    public interface GroupsRecyclerViewListener{
        public void groupClicked(int position);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Group group = groups.get(position);

        String name = group.getGroupName();

        GroupsListAdapter.GroupViewHolder groupViewHolder = (GroupsListAdapter.GroupViewHolder) holder;
        groupViewHolder.setName(name);


        if(onClickBehavior == Constants.GROUPS_ONCLICK_SELECT_GROUP) {
            ((GroupViewHolder) holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "listened to select group");
                    if(mListener != null)
                        mListener.groupClicked(position);
                }
            });
        }

        if(onClickBehavior == Constants.GROUPS_ONCLICK_VIEW_GROUP) {
            ((GroupViewHolder) holder).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "TBD");
                }
            });
        }


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
