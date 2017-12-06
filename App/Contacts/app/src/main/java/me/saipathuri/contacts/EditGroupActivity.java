package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import io.objectbox.Box;

/**
 * Created by haney on 12/2/2017.
 */

public class EditGroupActivity extends AppCompatActivity{

    private final String TAG = getClass().getSimpleName();
    private long mId;
    private EditText mGroupName;
    private Toolbar mToolbar;
    private Group mGroup;
    private Button mDeleteButton;
    private Button mDoneButton;
    private Box<Group> mGroupsBox;
    private boolean isInEditMode = false;
    private CoordinatorLayout editGroupsCoordinatorLayout;
    private CoordinatorLayout viewGroupsCoordinatorLayout;
    private Snackbar requiredInfoGroupSnackbar;
    private Snackbar successGroupSnackbar;
    
    private RecyclerView mContactsRecyclerView;
    private ContactListAdapter mAdapter;
    private Box<Contact> mContactsBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);
        

        
        mToolbar = (Toolbar) findViewById(R.id.tb_edit_contact);
        setSupportActionBar(mToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        mGroupsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Group.class);
        mGroup = new Group();

        mContactsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Contact.class);

        mContactsRecyclerView = (RecyclerView) findViewById(R.id.rv_edit_group);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ContactListAdapter(new ArrayList<Contact>(mContactsBox.getAll()));
        mContactsRecyclerView.setLayoutManager(linearLayoutManager);
        mContactsRecyclerView.setAdapter(mAdapter);
        
        //TODO: do delete and save buttons

        //Get all views in layout
        mGroupName = (EditText) findViewById(R.id.et_edit_group_name);
//        mDoneButton = (Button) findViewById(R.id.btn_done_editing);

        editGroupsCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_edit_groups);
        
        //Set snackbar info
        successGroupSnackbar = Snackbar.make(editGroupsCoordinatorLayout, Constants.GROUP_EDIT_SUCCESS,
                Snackbar.LENGTH_SHORT);

        requiredInfoGroupSnackbar = Snackbar.make(editGroupsCoordinatorLayout, Constants.REQUIRED_GROUP_INFO_MISSING_MSG,
                Snackbar.LENGTH_LONG);


        //pressing the done button will return to the contacts list
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leaveActivity();
            }
        });

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Constants.CONTACT_ID_EXTRA_KEY)){
            mId = intent.getLongExtra(Constants.CONTACT_ID_EXTRA_KEY, 0);
            mGroup = mGroupsBox.get(mId);
            fillContactInfoToFields();
            setViewMode();
        }
        if(mId == 0){
            setEditMode();
            hideDeleteButton();
        }

    }

    private void hideDeleteButton(){
        mDeleteButton.setEnabled(false);
        mDeleteButton.setVisibility(View.INVISIBLE);
    }
    // called if existing contact is being edited. This means mId is not null.
    private void fillContactInfoToFields() {
        mGroupName.setText(mGroup.getGroupName());
        mToolbar.setTitle(mGroup.getGroupName());
    }


    // called if new contact is being created or edited.
    // TODO: find a way to save image directory
    private void readGroupInfoFromFields() {
        mGroup.setGroupName(mGroupName.getText().toString().trim());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_contact_menu, menu);
        if(isInEditMode) {
            menu.findItem(R.id.action_save).setEnabled(true);
            menu.findItem(R.id.action_edit).setEnabled(false);
        }else{
            menu.findItem(R.id.action_save).setEnabled(false);
            menu.findItem(R.id.action_edit).setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                hideSoftKeyboard(this);
                if(saveGroup()){
                    isInEditMode = false;
                    setViewMode();
                    supportInvalidateOptionsMenu();
                }
                break;

            case android.R.id.home:
                leaveActivity();
                break;

            case R.id.action_edit:
                setEditMode();
                supportInvalidateOptionsMenu();
                break;

            default:
                Toast.makeText(this, "Default in EditContact onOptionsItemSelected", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean saveGroup() {
        readGroupInfoFromFields();
        if(!mGroup.getGroupName().isEmpty()) {
     //        &&   !mGroup.contacts().isEmpty()) {

     /**
           // mGroupsBox.put(mGroup);

            for (Contact contactInGroup : contactsToAdd){
                mGroup.contacts.add(contactInGroup);
            }
            mGroupsBox.boxFor(Group.class).put(mGroup);

            // for each contact add group like above
      */

            successGroupSnackbar.show();
            return true;
        } else{
            requiredInfoGroupSnackbar.show();
            return false;
        }
    }

    private void leaveActivity(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void setViewMode(){
        mGroupName.setEnabled(false);
        hideDeleteButton();
    }

    private void setEditMode(){
        mGroupName.setEnabled(true);
        mGroupName.requestFocus();

        showDeleteButton();
        isInEditMode = true;
    }

    private void showDeleteButton() {
        mDeleteButton.setEnabled(true);
        mDeleteButton.setVisibility(View.VISIBLE);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
    }