package me.saipathuri.contacts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Query<Contact> searchQueryFirstName;
    private EditText mSearchQueryEditText;

    public SearchFragment() {
        //Required empty public constructor
    }

    /**
     * Factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
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
        mContacts = new ArrayList<Contact>();

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

        mSearchQueryEditText = rootView.findViewById(R.id.et_search_terms);

        mSearchQueryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String queryTerm = charSequence.toString().trim();
                if(queryTerm.length() > 0) {
                    searchQueryFirstName = mContactsBox.query().equal(Contact_.firstName, queryTerm).build();
                    Query searchQueryLastName = mContactsBox.query().equal(Contact_.lastName, queryTerm).build();
                    List<Contact> firstNameMatches = searchQueryFirstName.find();
                    List<Contact> lastNameMatches = searchQueryLastName.find();
                    HashSet<Contact> matches = new HashSet<>();
                    matches.addAll(firstNameMatches);
                    matches.addAll(lastNameMatches);
                    ArrayList<Contact> finalMatches = new ArrayList<>();
                    finalMatches.addAll(matches);
                    mAdapter.updateContactsList(finalMatches);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                return;
            }
        });

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
    }

}
