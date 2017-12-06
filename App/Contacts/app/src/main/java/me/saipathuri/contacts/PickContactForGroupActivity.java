package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;

import io.objectbox.Box;

public class PickContactForGroupActivity extends AppCompatActivity implements GroupsListAdapter.GroupsRecyclerViewListener {
    RecyclerView mGroupsRecyclerView;
    GroupsListAdapter mAdapter;
    Box<Group> mGroupsBox;
    ArrayList<Group> mGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact_for_group);

        mGroupsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Group.class);
        mGroups = new ArrayList(mGroupsBox.getAll());
        mAdapter = new GroupsListAdapter(mGroups, this, Constants.GROUPS_ONCLICK_SELECT_GROUP, this);

        mGroupsRecyclerView = findViewById(R.id.rv_pick_group_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mGroupsRecyclerView.setLayoutManager(linearLayoutManager);
        mGroupsRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void groupClicked(int position) {
        //TODO: pass to prev activity
        Group selectedGroup = mGroups.get(position);
        long id = selectedGroup.getId();
        Intent intent = new Intent();
        intent.putExtra(Constants.SELECTED_GROUP_ID, id);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
