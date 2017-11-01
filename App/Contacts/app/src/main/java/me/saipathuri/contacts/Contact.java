package me.saipathuri.contacts;

import java.util.ArrayList;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by saipathuri on 11/1/17.
 */

@Entity
public class Contact {

    @Id
    long id;
    String name;

    //three different phone number strings because ObjectBox doesn't support lists
    String phoneNumber1;
    String phoneNumber2;
    String phoneNumber3;
    String emailAddress;
    String photoPath;

    public Contact(long id, String name, String phoneNumber1, String phoneNumber2, String phoneNumber3, String emailAddress, String photoPath) {
        this.id = id;
        this.name = name;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber3 = phoneNumber3;
        this.emailAddress = emailAddress;
        this.photoPath = photoPath;
    }

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getPhoneNumber3() {
        return phoneNumber3;
    }

    public void setPhoneNumber3(String phoneNumber3) {
        this.phoneNumber3 = phoneNumber3;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
