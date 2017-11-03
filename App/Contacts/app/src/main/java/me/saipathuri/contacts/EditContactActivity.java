package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import io.objectbox.Box;
import me.saipathuri.contacts.utils.ImageUtils;

public class EditContactActivity extends AppCompatActivity {
    private long mId;
    private ImageButton mContactImageButton;
    private EditText mContactFirstName;
    private EditText mContactLastName;
    private EditText mContactEmailAddress;
    private EditText mContactPhoneNumber1;
    private EditText mContactPhoneNumber2;
    private EditText mContactPhoneNumber3;
    private Toolbar mToolbar;
    private Contact mContact;
    private Button mDeleteButton;
    private Box<Contact> mContactsBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        mToolbar = (Toolbar) findViewById(R.id.tb_edit_contact);
        setSupportActionBar(mToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        mContactsBox = ((ContactsApp) getApplication()).getBoxStore().boxFor(Contact.class);
        mContact = new Contact();

        //Get all views in layout
        mContactImageButton = (ImageButton) findViewById(R.id.ib_edit_contact);
        mContactFirstName = (EditText) findViewById(R.id.et_edit_contact_first_name);
        mContactLastName = (EditText) findViewById(R.id.et_edit_contact_last_name);
        mContactEmailAddress = (EditText) findViewById(R.id.et_edit_contact_email_address);
        mContactPhoneNumber1 = (EditText) findViewById(R.id.et_edit_contact_phone_number_1);
        mContactPhoneNumber2 = (EditText) findViewById(R.id.et_edit_contact_phone_number_2);
        mContactPhoneNumber3 = (EditText) findViewById(R.id.et_edit_contact_phone_number_3);
        mDeleteButton = (Button) findViewById(R.id.btn_edit_contact_delete);

        //pressing delete will delete contact from the DB and go to contacts list
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactsBox.remove(mContact);
                leaveActivity();
            }
        });

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(Constants.CONTACT_ID_EXTRA_KEY)){
            mId = intent.getLongExtra(Constants.CONTACT_ID_EXTRA_KEY, 0);
            mContact = mContactsBox.get(mId);
            fillContactInfoToFields();
        }
        if(mId == 0){
            mDeleteButton.setEnabled(false);
            mDeleteButton.setVisibility(View.INVISIBLE);
            ab.setTitle("Edit Contact");
        }

    }

    // called if existing contact is being edited. This means mId is not null.
    private void fillContactInfoToFields() {
        mContactImageButton.setImageBitmap(ImageUtils.getBitmapFromPath(this, mContact.getPhotoPath()));
        mContactFirstName.setText(mContact.getFirstName());
        mContactLastName.setText(mContact.getLastName());
        mContactEmailAddress.setText(mContact.getEmailAddress());
        mContactPhoneNumber1.setText(mContact.getPhoneNumber1());
        mContactPhoneNumber2.setText(mContact.getPhoneNumber2());
        mContactPhoneNumber3.setText(mContact.getPhoneNumber3());
        mToolbar.setTitle(mContact.getFirstName() + " " + mContact.getLastName());
    }


    // called if new contact is being created or edited.
    // TODO: find a way to save image directory
    private void readContactInfoFromFields() {
        mContact.setFirstName(mContactFirstName.getText().toString().trim());
        mContact.setLastName(mContactLastName.getText().toString().trim());
        mContact.setEmailAddress(mContactEmailAddress.getText().toString().trim());
        mContact.setPhoneNumber1(mContactPhoneNumber1.getText().toString().trim());
        mContact.setPhoneNumber2(mContactPhoneNumber2.getText().toString().trim());
        mContact.setPhoneNumber2(mContactPhoneNumber3.getText().toString().trim());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                saveContact();
                break;

            case android.R.id.home:
                leaveActivity();
                break;

            default:
                Toast.makeText(this, "Default in EditContact onOptionsItemSelected", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void saveContact() {
        readContactInfoFromFields();
        if(!mContact.getFirstName().isEmpty() ||
                !mContact.getLastName().isEmpty() ||
                !mContact.getPhoneNumber1().isEmpty() ||
                !mContact.getEmailAddress().isEmpty()) {
            mContactsBox.put(mContact);
            leaveActivity();
        } else{
            Toast.makeText(this, "First four fields must be filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void leaveActivity(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}
