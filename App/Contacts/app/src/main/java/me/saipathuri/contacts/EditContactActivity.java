package me.saipathuri.contacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.objectbox.Box;
import me.saipathuri.contacts.utils.ImageUtils;

public class EditContactActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
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
    private Button mDoneButton;
    private ImageButton mCallButton;
    private ImageButton mSMSButton;
    private Box<Contact> mContactsBox;
    private boolean isInEditMode = false;
    private CoordinatorLayout editContactCoordinatorLayout;
    private Snackbar requiredInfoSnackbar;
    private Snackbar successSnackbar;

    //for taking picture
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //this is used in case the user opens camera, but doesn't take a new picture
    private String temp_photo_path;

    /**
     * This method initializes all views, sets their behaviors as needed, and sets the UI to be in view mode (@see #setViewMode()) or edit mode (@see #setEditMode()).
     */
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
        mDoneButton = (Button) findViewById(R.id.btn_done_editing);
        mCallButton = (ImageButton) findViewById(R.id.btn_view_contact_call);
        mSMSButton = (ImageButton) findViewById(R.id.btn_view_contact_sms);

        editContactCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_edit_contact);

        //Set snackbar info
        requiredInfoSnackbar = Snackbar.make(editContactCoordinatorLayout, Constants.REQUIRED_INFO_MISSING_MSG,
                Snackbar.LENGTH_LONG);

        successSnackbar = Snackbar.make(editContactCoordinatorLayout, Constants.CONTACT_EDIT_SUCCESS,
                Snackbar.LENGTH_SHORT);

        final Snackbar needPhoneNumberSnackbar = Snackbar.make(editContactCoordinatorLayout, "This contact needs a phone number to complete this action.", Snackbar.LENGTH_SHORT);


        //set photo button onClick
        mContactImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        //pressing delete will delete contact from the DB and go to contacts list
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactsBox.remove(mContact);
                leaveActivity();
            }
        });
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
            mContact = mContactsBox.get(mId);
            fillContactInfoToFields();
            setViewMode();
        }
        if(mId == 0){
            setEditMode();
            hideDeleteButton();
        }

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mContact.getPhoneNumber1().isEmpty()){
                    String phoneNumber = mContact.getPhoneNumber1();
                    String uri = "tel:"+phoneNumber.trim();
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse(uri));
                    startActivity(dialIntent);
                }else{
                    needPhoneNumberSnackbar.show();
                }
            }
        });

        mSMSButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mContact.getPhoneNumber1().isEmpty()){
                    String phoneNumber = mContact.getPhoneNumber1();
                    String uri = "sms:"+phoneNumber.trim();
                    Intent SMSIntent = new Intent(Intent.ACTION_VIEW);
                    SMSIntent.setData(Uri.parse(uri));
                    startActivity(SMSIntent);
                }else{
                    needPhoneNumberSnackbar.show();
                }
            }
        });
    }

    /**
     * Hides the delete button when a new contact is being created.
     */
    private void hideDeleteButton(){
        mDeleteButton.setEnabled(false);
        mDeleteButton.setVisibility(View.INVISIBLE);
    }

    /**
     * This method populates the view with information from the database.
     */
    private void fillContactInfoToFields() {
        mContactImageButton.setImageBitmap(ImageUtils.getBitmapFromPath(this, mContactImageButton, mContact.getPhotoPath()));
        mContactFirstName.setText(mContact.getFirstName());
        mContactLastName.setText(mContact.getLastName());
        mContactEmailAddress.setText(mContact.getEmailAddress());
        mContactPhoneNumber1.setText(mContact.getPhoneNumber1());
        mContactPhoneNumber2.setText(mContact.getPhoneNumber2());
        mContactPhoneNumber3.setText(mContact.getPhoneNumber3());
        mToolbar.setTitle(mContact.getFirstName() + " " + mContact.getLastName());
    }

    /**
     * This method is called if a new contact is being saved, or an existing contact has been edited and is being saved.
     * It takes information from the fields and stores in the Contact (@link #Contact) model.
     */
    private void readContactInfoFromFields() {
        mContact.setFirstName(mContactFirstName.getText().toString().trim());
        mContact.setLastName(mContactLastName.getText().toString().trim());
        mContact.setEmailAddress(mContactEmailAddress.getText().toString().trim());
        mContact.setPhoneNumber1(mContactPhoneNumber1.getText().toString().trim());
        mContact.setPhoneNumber2(mContactPhoneNumber2.getText().toString().trim());
        mContact.setPhoneNumber2(mContactPhoneNumber3.getText().toString().trim());
    }

    /**
     * Sets the behaviors of the menu buttons.
     */
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

    /**
     * Sets behavior of the menu bar buttons.
     * @param item the item that was clicked by the user
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                hideSoftKeyboard(this);
                if(saveContact()){
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

    /**
     * Used for the picture taking functionality.
     * Saves the filepath to Contact model and sets the ImageButton to the picture.
     * @param requestCode code indicates what activity the result is from
     * @param resultCode code indicates whether the result was successful or not
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            mContact.setPhotoPath(temp_photo_path);
            Bitmap imageBitmap = ImageUtils.getBitmapFromPath(this, mContactImageButton, mContact.getPhotoPath());
            mContactImageButton.setImageBitmap(imageBitmap);
        }
    }

    /**
     * Saves contact to database.
     * @return True if contact has been saved. False if more information needed.
     */
    private boolean saveContact() {
        readContactInfoFromFields();
        if(!mContact.getFirstName().isEmpty() &&
                !mContact.getLastName().isEmpty() &&
                !mContact.getPhoneNumber1().isEmpty()) {
            mContactsBox.put(mContact);
            successSnackbar.show();
            return true;
        } else{
            requiredInfoSnackbar.show();
            return false;
        }
    }

    /**
     * Called when user is done with editing.
     */
    private void leaveActivity(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /**
     * Disables all fields from being edited.
     */
    private void setViewMode(){
        mContactImageButton.setClickable(false);
        mContactFirstName.setEnabled(false);
        mContactLastName.setEnabled(false);
        mContactEmailAddress.setEnabled(false);
        mContactPhoneNumber1.setEnabled(false);
        mContactPhoneNumber2.setEnabled(false);
        mContactPhoneNumber3.setEnabled(false);
        hideDeleteButton();
    }

    /**
     * Enables all fields to be edited.
     */
    private void setEditMode(){
        mContactImageButton.setClickable(true);
        mContactFirstName.setEnabled(true);
        mContactFirstName.requestFocus();
        mContactLastName.setEnabled(true);
        mContactEmailAddress.setEnabled(true);
        mContactPhoneNumber1.setEnabled(true);
        mContactPhoneNumber2.setEnabled(true);
        mContactPhoneNumber3.setEnabled(true);

        showDeleteButton();
        isInEditMode = true;
    }

    /**
     * Used to show the delete button
     */
    private void showDeleteButton() {
        mDeleteButton.setEnabled(true);
        mDeleteButton.setVisibility(View.VISIBLE);
    }

    /**
     * used to hide the keyboard
     */
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    //from: https://developer.android.com/training/camera/photobasics.html
    private void dispatchTakePictureIntent(File photoFile) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "me.saipathuri.contacts.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    /**
     * Used to take a contact picture.
     */
    private void takePicture(){
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = ImageUtils.createImageFile(this);
            temp_photo_path = photoFile.getAbsolutePath();
            dispatchTakePictureIntent(photoFile);
        } catch (IOException ex) {
            // Error occurred while creating the File
            Log.d(TAG, ex.toString());
        }
    }
}
