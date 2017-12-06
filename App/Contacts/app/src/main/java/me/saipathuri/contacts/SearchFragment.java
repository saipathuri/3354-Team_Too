package me.saipathuri.contacts;


import android.os.Bundle;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private final String TAG = this.getClass().getSimpleName();
    private RecyclerView mRecyclerView;
    private ContactListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Contact> mContacts;
    private Box<Contact> mContactsBox;
    private Query<Contact> searchQuery;

    public SearchFragment() {
        //Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    /**
     * When the fragment is created, the Contacts database is loaded. It also creates a database query
     *  that searches for the given contact.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactsBox = ((ContactsApp) getActivity().getApplication()).getBoxStore().boxFor(Contact.class);
        searchQuery = mContactsBox.query().equal(Contact_.firstName, /*field from the user typing in */).build().find();
        updateContacts(true);

        if(mContacts.size() == 0){
            Toast.makeText(getActivity(), "The search was unsuccessful!", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method retrieves uses the contactsQuery to retrieve all contacts in alphabetical order.
     * It then assigns those contacts to mContacts, an ArrayList<Contact>
     * @param search if false, then the adapter is notified. If true, the adapter is not notified because it is not created yet.
     */
    private void updateContacts(boolean search) {
        mContacts = new ArrayList<>(searchQuery.find());
        if(!search) {
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
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_contacts_list);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ContactListAdapter(mContacts);
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
     * Used to start the Search activity
     */
    private void startCreateSearch() {

    }




}
