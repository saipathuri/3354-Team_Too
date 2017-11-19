package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class ViewContactsActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private ContactListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Contact> mContacts;
    private Box<Contact> mContactsBox;
    private FloatingActionButton mAddContactFloatingActionButton;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        mToolbar = (Toolbar) findViewById(R.id.tb_view_contacts);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("Contacts");
        // get stored contacts from objectbox
        mContactsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Contact.class);
        mContacts = new ArrayList<>(mContactsBox.getAll());

        if(mContacts.size() == 0){
            Toast.makeText(this, "No contacts saved. Add Some!", Toast.LENGTH_SHORT).show();
        }

        // set FAB and listener for FAB
        mAddContactFloatingActionButton = (FloatingActionButton) findViewById(R.id.fab_add_contact);
        mAddContactFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateContact();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_contacts_list);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ContactListAdapter(mContacts);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void startCreateContact() {
        Intent startEditContact = new Intent(this, EditContactActivity.class);
        startActivityForResult(startEditContact, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                mContacts = new ArrayList<>(mContactsBox.getAll());
                mAdapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mContacts = new ArrayList<>(mContactsBox.getAll());
        mAdapter.updateContactsList(mContacts);
    }
}
