package me.saipathuri.contacts;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by saipathuri on 12/5/17.
 */

public class AddGroupDialogFragment extends DialogFragment {

    private AddGroupDialogListener mListener;

    public interface AddGroupDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_group, null);
        final EditText groupNameEditText = view.findViewById(R.id.et_group_name);
        mListener = (AddGroupDialogListener) getTargetFragment();
        builder.setView(view).setTitle("Group Name").setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //do nothing here
                mListener.onDialogPositiveClick(AddGroupDialogFragment.this, groupNameEditText.getText().toString().trim());
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddGroupDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        // Verify that the host activity implements the callback interface
//        try {
//            // Instantiate the NoticeDialogListener so we can send events to the host
//            mListener = (AddGroupDialogListener) activity;
//        } catch (ClassCastException e) {
//            // The activity doesn't implement the interface, throw exception
//            throw new ClassCastException(activity.toString()
//                    + " must implement AddGroupListener");
//        }
//    }
}
