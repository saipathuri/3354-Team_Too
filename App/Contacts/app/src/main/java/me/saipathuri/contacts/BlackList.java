package me.saipathuri.contacts;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


/**
 * Created by Trey Smith on 11/25/2017.
 */


public class BlackList {


    private DatabaseOpenHelper mDatabaseOpenHelper;
    //private final Context context;
    private SQLiteDatabase mDatabase;
    public static final String DATABASE_NAME = "Contact Block List ";
    public static final int DATABASE_VERSION = 1;
    public static final String COL_NAMEFIRST = "First name ";
    public static final String COL_NAMELAST = "Last name ";
    public static final String COL_NUM1 = "Phone Number 1: ";
    public static final String COL_NUM2 = "Phone Number 2: ";
    public static final String COL_NUM3 = "Phone Number 3: ";
    public static final String COL_EMAIL = "Email Address: ";
    public static final String FTS_VIRTUAL_TABLE = "FTS";
    private Snackbar contactBlockedMessage;

    /**
     * Creation of virtual table to store the contacts that will be blocked
     *
     */
    public BlackList open(Context context) {
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
        mDatabase = mDatabaseOpenHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDatabaseOpenHelper.close();
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
       private SQLiteDatabase mDatabase;

        private static final String FTS_TABLE_CREATE =
                        COL_NAMEFIRST + ", " +
                        COL_NAMELAST + ", " +
                        COL_NUM1 + ", " +
                        COL_NUM2 + ", " +
                        COL_NUM3 + ", " +
                        COL_EMAIL + ")";

        public DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }

        /**
         * This is called when a contact is to be blocked. Takes the provided contact information and stores it
         * within the database reserved for blocked contacts.
         */
    public long addBlock(String nameFirst, String nameLast, String num1, String num2, String num3, String email) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_NAMEFIRST, nameFirst);
            initialValues.put(COL_NAMELAST, nameLast);
            initialValues.put(COL_NUM1, num1);

            if (num2 != null) {
                initialValues.put(COL_NUM2, num2);
            }
            if (num3 != null) {
                initialValues.put(COL_NUM3, num3);
            }

            if (email != null) {
                initialValues.put(COL_EMAIL, email);
            }

            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }

        /**
         * Called when the contact is no longer to be blocked. Searches to see if the contact is in the blocked contact
         * database and removes it.
         */
        public long removeBlock(String nameFirst, String nameLast) {


            return mDatabase.delete(FTS_VIRTUAL_TABLE, "name=? and name=?",new String[]{nameFirst, nameLast});
        }

    }


    public class readContactInfo extends AppCompatActivity {

       // private Switch blockContact;

        EditText firstNameInfo = (EditText) findViewById(R.id.et_edit_contact_first_name);
        String firstNameInfoStr = firstNameInfo.getText().toString().trim();

        EditText lastNameInfo = (EditText) findViewById(R.id.et_edit_contact_last_name);
        String lastNameInfoStr = lastNameInfo.getText().toString().trim();

        EditText emailAddressInfo = (EditText) findViewById(R.id.et_edit_contact_email_address);
        String emailAddressInfoStr = emailAddressInfo.getText().toString().trim();

        EditText phoneNumber1Info = (EditText) findViewById(R.id.et_edit_contact_phone_number_1);
        String phoneNumber1InfoStr = phoneNumber1Info.getText().toString().trim();

        EditText phoneNumber2Info = (EditText) findViewById(R.id.et_edit_contact_phone_number_2);
        String phoneNumber2InfoStr = phoneNumber2Info.getText().toString().trim();

        EditText phoneNumber3Info = (EditText) findViewById(R.id.et_edit_contact_phone_number_3);
        String phoneNumber3InfoStr = phoneNumber3Info.getText().toString().trim();

        private boolean isChecked = false;
        private boolean isInEditMode = false;

        //contactBlockedMessage = Snackbar.make()


        /**
         * Allows the user to change the block contact setting when editting the contact. However will not
         *
         */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.

            getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
            MenuItem checkable = menu.findItem(R.id.block_contact);
            checkable.setChecked(isChecked);
            if(isInEditMode){
                menu.findItem(R.id.block_contact).setEnabled(true);
                menu.findItem(R.id.block_contact).setVisible(true);
            }else{
                menu.findItem(R.id.block_contact).setEnabled(false);
                menu.findItem(R.id.block_contact).setVisible(false);
            }

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            /**
             * If the contact does not have the required parameters it prompts the user input at least the first and last
             * name as well as at least one phone number
             */
            if((firstNameInfoStr == null) || (lastNameInfoStr ==null) || (phoneNumber1InfoStr == null)){


            else{
                    switch (item.getItemId()) {
                        case R.id.block_contact:
                            isChecked = !item.isChecked();
                            item.setChecked(isChecked);
                            mDatabaseOpenHelper.addBlock(firstNameInfoStr, lastNameInfoStr, phoneNumber1InfoStr,
                                    phoneNumber2InfoStr, phoneNumber3InfoStr, emailAddressInfoStr);
                            return true;
                        default:
                            mDatabaseOpenHelper.removeBlock(firstNameInfoStr,lastNameInfoStr);

                            return false;
                    }
                }

        }

        }
    }
}
