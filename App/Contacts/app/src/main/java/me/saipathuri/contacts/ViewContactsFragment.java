package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import io.objectbox.Box;
import io.objectbox.query.Query;


/**
 * This Fragment displays a list of all existing contacts and allows users to create new contacts
 * and modify existing contacts.
 */
public class ViewContactsFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private ContactListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Contact> mContacts;
    private Box<Contact> mContactsBox;
    private FloatingActionButton mAddContactFloatingActionButton;
    private Query<Contact> contactsQuery;

    public ViewContactsFragment() {
        // Required empty public constructor
    }

    public static ViewContactsFragment newInstance() {
        ViewContactsFragment fragment = new ViewContactsFragment();
        return fragment;
    }

    /**
     * When the fragment is created, the Contacts database is loaded. It also creates a database query
     *  that retrieves all contacts in alphabetical order.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactsBox = ((ContactsApp) getActivity().getApplication()).getBoxStore().boxFor(Contact.class);
        contactsQuery = mContactsBox.query().order(Contact_.firstName).build();
        updateContacts(true);

        if(mContacts.size() == 0){
            Toast.makeText(getActivity(), "No contacts saved. Add Some!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method retrieves uses the contactsQuery to retrieve all contacts in alphabetical order.
     * It then assigns those contacts to mContacts, an ArrayList<Contact>
     * @param firstRun if false, then the adapter is notified. If true, the adapter is not notified because it is not created yet.
     */
    private void updateContacts(Boolean firstRun){
        mContacts = new ArrayList<>(contactsQuery.find());
        if(!firstRun) {
            mAdapter.updateContactsList(mContacts);
        }
    }

    /**
     * Initializes and sets up neccessary views.
     * @return a root view containing all the elements in the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_contacts, container, false);
        // set FAB and listener for FAB
        mAddContactFloatingActionButton = (FloatingActionButton) rootView.findViewById(R.id.fab_add_contact);
        mAddContactFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCreateContact();
            }
        });

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_contacts_list);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ContactListAdapter(mContacts, Constants.VIEW_ONCLICK);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    /**
     * Update the contacts list when resuming the fragment
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        updateContacts(false);
    }

    /**
     * Used to start the EditContact activity
     */
    private void startCreateContact() {
        Intent startEditContact = new Intent(getActivity(), EditContactActivity.class);
        startActivityForResult(startEditContact, 1);
    }

    /**
     * This method is called when EditContact activity is finished. If the result code is OK, then we run @see #updateContacts(Boolean firstRun).
     * @param requestCode the request code corresponds to the code that we started the activity with in @see #startCreateContact()
     * @param resultCode the result code indicates whether or not the activity finished successfully
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                updateContacts(false);
            }
        }
    }
}
