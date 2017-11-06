package me.saipathuri.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import me.saipathuri.contacts.utils.ImageUtils;

/**
 * Created by saipathuri on 11/2/17.
 */

class ContactListAdapter extends RecyclerView.Adapter {
    private List<Contact> contacts;


    ContactListAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public void updateContactsList(List<Contact> newlist) {
        contacts.clear();
        contacts.addAll(newlist);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactCardView = inflater.inflate(R.layout.contact_card, parent, false);
        ContactViewHolder viewHolder = new ContactViewHolder(contactCardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Contact contact = contacts.get(position);

        String name = contact.getFirstName() + " " + contact.getLastName();
        String phoneNumber = contact.getPhoneNumber1();
        String emailAddress = contact.getEmailAddress();
        String photoPath = contact.getPhotoPath();

        ContactViewHolder contactViewHolder = (ContactViewHolder) holder;
        contactViewHolder.setName(name);
        contactViewHolder.setPhoneNumber(phoneNumber);
        contactViewHolder.setEmail(emailAddress);
        contactViewHolder.setImage(photoPath);
        ((ContactViewHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = contacts.get(position);
                Intent intent = new Intent(view.getContext(), EditContactActivity.class);
                intent.putExtra(Constants.CONTACT_ID_EXTRA_KEY, contact.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (contacts != null ? contacts.size() : 0);
    }

    private static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mContactNameTextView;
        private TextView mContactPhoneNumberTextView;
        private TextView mContactEmailAddressTextView;
        private ImageButton mContactImageButton;

        ContactViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            this.mContactNameTextView = (TextView) itemView.findViewById(R.id.tv_view_contact_name);
            this.mContactPhoneNumberTextView = (TextView) itemView.findViewById(R.id.tv_view_contact_phone_number);
            this.mContactEmailAddressTextView = (TextView) itemView.findViewById(R.id.tv_view_contact_email_address);
            this.mContactImageButton = (ImageButton) itemView.findViewById(R.id.ib_view_contact_image);
        }

        public void setName(String name){
            mContactNameTextView.setText(name);
        }

        void setEmail(String email){
            mContactEmailAddressTextView.setText(email);
        }

        void setPhoneNumber(String phoneNumber){
            mContactPhoneNumberTextView.setText(phoneNumber);
        }

        void setImage(String photoPath){
            Bitmap bitmap = ImageUtils.getBitmapFromPath(itemView.getContext(), mContactImageButton,photoPath);
            mContactImageButton.setImageBitmap(bitmap);
        }

        @Override
        public void onClick(View view) {
            return;
        }

        public void setOnClickListener(View.OnClickListener listener){
            itemView.setOnClickListener(listener);
        }
    }
}
