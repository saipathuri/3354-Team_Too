package me.saipathuri.contacts;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

/**
 * Created by saipathuri on 11/1/17.
 * This class is a model for the information that we store in our database.
 * The @Entity annotation lets our database library know that this is a model.
 * The @Id annotation on long id lets our database library know to update that field with the id in the db.
 */

@Entity
public class Contact {

    @Id
    long id;
    String firstName;
    String lastName;
    String phoneNumber1;
    String phoneNumber2;
    String phoneNumber3;
    String emailAddress;
    String photoPath;

    ToMany<Group> groupsRelation;

    public Contact(long id, String firstName, String lastName, String phoneNumber1, String phoneNumber2, String phoneNumber3, String emailAddress, String photoPath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.phoneNumber3 = phoneNumber3;
        this.emailAddress = emailAddress;
        this.photoPath = photoPath;
    }

    public Contact() {

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
