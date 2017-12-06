package me.saipathuri.contacts;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.objectbox.Box;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends Fragment implements AddGroupDialogFragment.AddGroupDialogListener {
    RecyclerView mGroupsRecyclerView;
    Box<Group> mGroupsBox;
    Box<Contact> mContactsBox;
    ArrayList<Group> groups;
    private GroupsListAdapter mAdapter;
    FloatingActionButton addGroupFloatingActionButton;

    public GroupsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment GroupsFragment.
     */
    public static GroupsFragment newInstance() {
        GroupsFragment fragment = new GroupsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactsBox = ((ContactsApp) getActivity().getApplication()).getBoxStore().boxFor(Contact.class);
        mGroupsBox = ((ContactsApp) getActivity().getApplication()).getBoxStore().boxFor(Group.class);
        groups = new ArrayList(mGroupsBox.getAll());
        mAdapter = new GroupsListAdapter(groups, Constants.GROUPS_ONCLICK_VIEW_GROUP);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        mGroupsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_groups_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mGroupsRecyclerView.setLayoutManager(linearLayoutManager);
        mGroupsRecyclerView.setAdapter(mAdapter);

        addGroupFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add_group);
        setupFabOnClick();
        return rootView;
    }

    private void setupFabOnClick() {
        addGroupFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddGroupDialogFragment dialog = new AddGroupDialogFragment();
                dialog.setTargetFragment(GroupsFragment.this, 1);
                dialog.show(getActivity().getSupportFragmentManager(), "AddGroupDialogFragment");
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name) {
        Group group = new Group();
        group.setGroupName(name);
        mGroupsBox.put(group);
        groups = new ArrayList(mGroupsBox.getAll());
        mAdapter.updateGroupsList(groups);
    }
}
