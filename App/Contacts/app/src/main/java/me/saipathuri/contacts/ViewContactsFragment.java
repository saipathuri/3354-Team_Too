package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
 * Use the {@link ViewContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
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

//    private OnFragmentInteractionListener mListener;

    public ViewContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ViewContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewContactsFragment newInstance() {
        ViewContactsFragment fragment = new ViewContactsFragment();
        return fragment;
    }

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

    private void updateContacts(Boolean firstRun){
        mContacts = new ArrayList<>(contactsQuery.find());
        if(!firstRun) {
            mAdapter.notifyDataSetChanged();
        }
//        mAdapter.updateContactsList(mContacts);
    }

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
        mAdapter = new ContactListAdapter(mContacts);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        updateContacts(false);

    }

    private void startCreateContact() {
        Intent startEditContact = new Intent(getActivity(), EditContactActivity.class);
        startActivityForResult(startEditContact, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                updateContacts(false);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
