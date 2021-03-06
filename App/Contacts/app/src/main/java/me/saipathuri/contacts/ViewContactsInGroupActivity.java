package me.saipathuri.contacts;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import io.objectbox.Box;

public class ViewContactsInGroupActivity extends AppCompatActivity {
    RecyclerView mDisplayContactsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts_in_group);

        mDisplayContactsRecyclerView = findViewById(R.id.rv_view_contacts_in_group);

        Intent intent = getIntent();
        final long id = intent != null ? intent.getLongExtra(Constants.VIEW_GROUP_ID_EXTRA, 0) : 0;

        final Box<Group> groupsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Group.class);
        Box<Contact> contactsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Contact.class);

        Group group = groupsBox.get(id);

        ArrayList<Contact> contacts = new ArrayList<Contact>();
        for(Contact c : group.contactsRelation){
            contacts.add(c);
        }

        ContactListAdapter adapter = new ContactListAdapter(contacts, Constants.VIEW_ONCLICK);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mDisplayContactsRecyclerView.setLayoutManager(linearLayoutManager);
        mDisplayContactsRecyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.tb_view_group);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Group: " + group.getGroupName());
        Button deleteGroupButton = findViewById(R.id.btn_delete_group);
        deleteGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupsBox.remove(id);
                finish();
            }
        });
    }
}
